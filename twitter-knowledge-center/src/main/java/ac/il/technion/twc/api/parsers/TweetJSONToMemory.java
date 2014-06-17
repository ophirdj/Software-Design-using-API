package ac.il.technion.twc.api.parsers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import ac.il.technion.twc.api.Tweet;
import ac.il.technion.twc.api.exceptions.ParsingErrorException;
import ac.il.technion.twc.api.file_io.FileWriterReader;
import ac.il.technion.twc.api.file_io.WriterReader;

public class TweetJSONToMemory implements TweetToMemory {
	static final String ID_FIELD = "id_str";
	static final String DATE_FIELD = "created_at";
	static final String RETWEET_FIELD = "original_tweet";
	static final String HASHTAG_FIELD = "hashtags";
	static final String DELIMITER = "|";
	static final String JSON_PATH = "src/main/resources/tweetsDataBase.json";
	static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	WriterReader fileHandler = new FileWriterReader(JSON_PATH);
	
	public String encode(Tweet t){
		
		JSONObject $ = new JSONObject();
		$.put(ID_FIELD, t.getTweetID())
			.put(RETWEET_FIELD, t.getParentTweet())
			.put(DATE_FIELD, dateFormat.format(t.getDate()))
			.put(HASHTAG_FIELD,t.getHashtags());
		
		return $.toString();
	}
	
	public Tweet decode(String s){
		try {
			List<String> hashTags = new LinkedList<String>();
			
			JSONObject obj = new JSONObject(s);
			
			String id = obj.getString(ID_FIELD);
			String origId = obj.has(RETWEET_FIELD) ? obj.getString(RETWEET_FIELD) : null;
			Date date = dateFormat.parse(obj.getString(DATE_FIELD));
			
			JSONArray htList = obj.getJSONArray(HASHTAG_FIELD);
			for (int i=0 ; i<htList.length(); i++)
				hashTags.add(htList.getString(i));
			
			return new Tweet(id, origId, date, hashTags, null);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParsingErrorException();
		}
	}
	public String getDelimiter(){
		return DELIMITER;
	}

	@Override
	public void reserve(List<Tweet> tweets) {
		List<String> encodedTweets = new LinkedList<String>();
		for(Tweet t : tweets){
			encodedTweets.add(encode(t));
		}
		try {
			fileHandler.write(encodedTweets);
		} catch (ParsingErrorException e) {
			e.printStackTrace();
			throw new ParsingErrorException();
		}
	}

	@Override
	public List<Tweet> recover() {
		try{
			List<Tweet> decodedTweets = new LinkedList<Tweet>();
			for(String s : fileHandler.read())
				decodedTweets.add(decode(s));
			return decodedTweets;
		} catch(IOException e){
			e.printStackTrace();
			throw new ParsingErrorException();
		}
	}

	@Override
	public void clear() {
		fileHandler.clean();
		
	}
	
}
