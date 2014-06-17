package ac.il.technion.twc;

/**
 * This class is meant to act as a wrapper to test your functionality. You should implement <b>only</b> the methods
 * matching the selected functionality and not change any method's signature. The methods you choose not to implement
 * may remain with their default (i.e., exception throwing) behaviour. You can also implement an argumentless
 * constructor if you wish, and any number of new public methods.
 * 
 * @author Gal Lalouche
 */
public class FunctionalityTester {
	/**
	 * Loads the data from an array of lines
	 * 
	 * @param lines An array of lines, each line formatted as <time (dd/MM/yyyy HH:mm:ss)>,<tweet id>[,original tweet]
	 * @throws Exception If for any reason, handling the data failed
	 */
	public void importData(String[] lines) throws Exception {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	/**
	 * Loads the data from an array of JSON lines
	 * 
	 * @param lines An array of lines, each line is a JSON string
	 * @throws Exception If for any reason, handling the data failed
	 */
	public void importDataJson(String[] lines) throws Exception {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	/**
	 * Loads the index, allowing for queries on the data that was imported using
	 * {@link TwitterKnowledgeCenter#importData(String[])}. setupIndex will be called before any queries can be run on
	 * the system
	 * 
	 * @throws Exception If for any reason, loading the index failed
	 */
	public void setupIndex() throws Exception {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	//GROUP A
	
	/**
	 * Gets the content of tweet with the given id
	 * 
	 * @return A string, of the raw content of the tweet
	 * @throws Exception If it is not possible to complete the operation
	 */
	public String getTweetsContent(String tweetId) throws Exception {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	/**
	 * Gets original tweet's id from a retweet; if the input itself is an original tweet, then this function returns its
	 * id
	 * 
	 * @return A string, of the raw content of the tweet
	 * @throws Exception If it is not possible to complete the operation
	 */
	public String getOriginalTweetsId(String tweetId) throws Exception {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	/**
	 * Gets an id of <i>a</i> tweet that was published between two dates
	 * 
	 * @param t1 A date string in the format of <b>dd/MM/yyyy HH:mm:ss</b>; The tweet should have been published after
	 *        this time.
	 * @param t2 A date string in the format of <b>dd/MM/yyyy HH:mm:ss</b>; The tweet should have been published before
	 *        this time.
	 * @return The id of a tweet published between t1 and t2
	 * @throws Exception If it is not possible to complete the operation
	 */
	public String findTweetInTime(String t1, String t2) throws Exception {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	/**
	 * Gets the ids of all the retweets that <b>directly</b> retweeted this tweet
	 * 
	 * @return An array of the retweets ids
	 * @throws Exception If it is not possible to complete the operation
	 */
	public String[] getAllDirectRetweets(String tweetId) throws Exception {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	/**
	 * Gets all appearances of the hashtag in different tweets. Note that the hashtag should only be counted one time at
	 * most per tweet.
	 * 
	 * @param hashtag the hashtag
	 * @return The number of appearances
	 */
	public String countHashtagAppearances(String hashtag) {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	/**
	 * Gets the id of the first tweet (by publication date) made by a user
	 * 
	 * @param userId the id of the user
	 * @return The id of the user's first tweet
	 * @throws Exception If it is not possible to complete the operation
	 */
	public String getFirstTweet(String userId) throws Exception {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	//GROUP B
	
	/**
	 * Checks if two tweets are retweets (recursive) of the same tweet
	 * 
	 * @param tweetId1 the id of the first tweet
	 * @param tweetId2 the id of the second tweet
	 * @return "true" if they originated from the same tweet, "false" otherwise
	 * @throws Exception If it is not possible to complete the operation
	 */
	public String belongToSameTree(String tweetId1, String tweetId2) throws Exception {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	/**
	 * Gets the number of retweets (recursive) of an original tweet
	 * 
	 * @param tweetId the id of the tweet
	 * @return The number of retweets
	 */
	public String numberOfRetweets(String tweetId) {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	/**
	 * Gets the number shares (including in the content) of a youtube link
	 * 
	 * @param youtubeUrl the youtube url
	 * @return The number of shares, or appearances in tweets (counted at most one per tweet)
	 * @throws Exception If it is not possible to complete the operation
	 */
	public String numberOfYoutubeShares(String youtubeUrl) throws Exception {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	/**
	 * Gets the number of tweets or retweets made by a specific user
	 * 
	 * @param userId the id of the user
	 * @return The number of tweets made by the user
	 */
	public String numberTweetsByUser(String usersId) {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	// GROUP C
	/**
	 * Gets the top k coupled hashtags
	 * 
	 * @param k the number of hashtags
	 * @return The number of tweets made by the user
	 * @throws Exception If it is not possible to complete the operation
	 */
	public String[] hashtagCoupling(int k) throws Exception {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	/**
	 * Gets the id of the original tweet of a via retweet
	 * 
	 * @param retweetId the retweet's id
	 * @return The id of the original tweet
	 * @throws Exception If it is not possible to complete the operation
	 */
	public String viaRetweet(String retweetId) throws Exception {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	/**
	 * Returns the number of occurrence of the word (as a substring of any sort in the text) in tweets. A word can be
	 * counted multiple times in the same tweet. <b>if you implement this query, you have to also implement
	 * {@link FunctionalityTester#setupWordTrends(String[])}!</b>
	 * 
	 * @param word the word to check
	 * @return The number of occurrences of the word
	 * @throws Exception If it is not possible to complete the operation
	 */
	public String wordTrends(String word) throws Exception {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	/**
	 * Sets up the words to look for in word trends. You can assume that this method will be invoked before <b>every</b>
	 * invocation of {@link FunctionalityTester#importData(String[])} or
	 * {@link FunctionalityTester#importDataJson(String[])}, and its input will never change.
	 * 
	 * @param words The words search for
	 * @throws Exception If it is not possible to complete the operation
	 */
	public void setupWordTrends(String[] words) throws Exception {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	/**
	 * Cleans up all persistent data from the system; this method will be called before every test, to ensure that all
	 * tests are independent.
	 */
	public void cleanPersistentData() {
		throw new UnsupportedOperationException("Not implemented");
	}
}