package ac.il.technion.twc.impl.retweet_info;

import ac.il.technion.twc.api.Tweet;
import ac.il.technion.twc.api.exceptions.TweetNotFoundException;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UnionFind implements RetweetQueryEngine{
    Map<String, UFelement> disjointSets = new HashMap<String, UFelement>();


    public UFelement find(String elementId) {
        UFelement $ = disjointSets.get(elementId);
        if ($==null)
        	throw new TweetNotFoundException("requested tweet is not in index");
        
        List<UFelement> path = new LinkedList<UFelement>();
        while ($.leader != null) {
            path.add($);
            $ = $.leader;
        }
        for (UFelement u : path) {
            u.leader = $;
            u.latest = $.latest;
        }
        return $;
    }

    private boolean union(String first, String second) {
    	UFelement set1;
    	UFelement set2;
    	try{
    		set1 = find(first);
    		set2 = find(second);
    	} catch (TweetNotFoundException e){
    		return false; // dangling tweets are possible, just ignore them bastards.
    	}
    	if (set1 == set2) return true;
    	if (set2.getSize() > set1.getSize()) {
    		UFelement tmp = set1;
    		set1 = set2;
    		set2 = tmp;
    	}
    	set2.leader = set1;
    	set1.incSize(set2.getSize());
    	set1.updateTimes(set2.getEarliest(), set2.getLatest());
    	
    	return true;
    }
    
    @Override
    public void addElement(Tweet element) {
    	disjointSets.put(element.getTweetID(), new UFelement(element));
    }
    
    @Override
    public Date getLatestRetweetTime(String id){
        return find(id).getLatest();
    }
    
    @Override
    public Long getLifeTimeOfTweet(String id){
    	UFelement tweetSet = find(id);
    	Long $ = tweetSet.getLatest().getTime() - tweetSet.getEarliest().getTime(); 
        return $;
    }
    
    @Override
    public Integer getNumberOfRetweets(String id){
    	return find(id).getSize()-1;
    }
    
    @Override
    public void unionAllTweets(){
    	for(String id : disjointSets.keySet()){
    		Tweet t = disjointSets.get(id).content;
    		if(t.isRetweet())
    			union(t.getParentTweet(),id);
    	}
    }
    
    @Override
    public void clear() {
    	disjointSets = new HashMap<String, UFelement>();	
	}

    private class UFelement {
        private final Tweet content;
        protected UFelement leader;
        private Date latest;
        private Date earliest;
        private int size;

        public UFelement(Tweet content) {
            this.content = content;
            this.latest = content.getDate();
            this.earliest = content.getDate();
            this.size = 1;
            this.leader = null;
        }

        public int getSize() {
            return size;
        }

        public void incSize(int amount) {
            size += amount;
        }

        public Date getLatest() {
            return latest;
        }
        
        public Date getEarliest() {
            return earliest;
        }

        public void updateTimes(Date earliest2, Date latest2) {
        	earliest = earliest2.before(earliest) ? earliest2 : earliest; 
            latest = latest2.after(latest) ? latest2 : latest;
        }
    }

	

}
