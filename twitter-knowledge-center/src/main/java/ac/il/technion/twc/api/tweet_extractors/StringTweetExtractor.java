package ac.il.technion.twc.api.tweet_extractors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ac.il.technion.twc.api.Tweet;

/**
 * Created by Matan on 5/8/14.
 */
public class StringTweetExtractor implements TweetExtractor{

    @Override
    public Tweet extractTweet(String s) {
        String originalID =extractOriginalTweetID(s);
        List<String> hashtags = extractHashtags(s);
        String id = extractID(s);
        Date date = extractDate(s);

        Tweet tweet = new Tweet(id,originalID,date,hashtags);
        return tweet;
    }

    private String extractOriginalTweetID(String tweet) {
    	int pos = POSITION.ORIGINAL_TWEET_POSITION.getPosition();
        String[] tweetString = splitTweetString(tweet);
        try {
        	return tweetString[POSITION.ORIGINAL_TWEET_POSITION.getPosition()];
        } catch(ArrayIndexOutOfBoundsException e){
        	return null;
        }
    }

    private List<String> extractHashtags(String s) {
        return Collections.EMPTY_LIST;
    }

    private String extractID(String tweet) {
        String[] tweetString = splitTweetString(tweet);

        //Removes unwanted space character at the beginning
        return tweetString[POSITION.ID_POSITION.getPosition()];
    }

    private Date extractDate(String tweet) {
        String[] tweetString = splitTweetString(tweet);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            return format.parse(tweetString[POSITION.DATE_POSITION.getPosition()]);
        } catch (ParseException e) {
//            e.printStackTrace();
            return null;
        }
    }



    private static String[] splitTweetString(String tweet) {
        return tweet.split(", ");
    }

    private enum POSITION {
        DATE_POSITION(0), ID_POSITION(1), ORIGINAL_TWEET_POSITION(2);

        private final int position;

        POSITION(int position) {
            this.position = position;
        }

        int getPosition() {
            return this.position;
        }
    }

}
