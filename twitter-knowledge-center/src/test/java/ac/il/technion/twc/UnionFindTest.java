package ac.il.technion.twc;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import ac.il.technion.twc.api.Tweet;
import ac.il.technion.twc.api.exceptions.TweetNotFoundException;
import ac.il.technion.twc.impl.retweet_info.UnionFind;

import org.junit.Before;
import org.junit.Test;

public class UnionFindTest {
	UnionFind underInspection;
	Date d1,d2,d3,d4, d5, d6, d7, d8, d9, d10;
	Tweet t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11;
	
	static final String ID1_JESUS1 = "jesus was born! praise the lord!";
	static final String ID3_JESUS2 = "jesus reincarnated!";
	static final String ID7_JESUS3 = "jesus died again!";
	static final String ID5_SATAN1 = "satan was born";
	static final String ID6_SATAN2 = "satan has revolted against god";
	static final String ID8_SATAN3 = "satan now rules the universe";
	static final String ID9_SATAN4 = "yet another satan was born";
	static final String ID2_NOW = "now!";
	static final String ID4_BUG = "bug2000";
	static final String ID10_END = "byebye";
	static final List empty = Collections.EMPTY_LIST;

	@Before
	public void setUpInitialSets() throws Exception {
		underInspection = new UnionFind();
		
		d1 = new Date(0,0,0);
		d2 = new Date();
		d3 = new Date(1993, 4, 8);
		d4 = new Date(2000, 1, 1);
		d5 = new Date(1993, 9, 12);
		d6 = new Date(40000, 4, 1);
		d7 = new Date(43210, 4, 1);
		d8 = new Date(40000, 5, 1);
		d9 = new Date(54321, 9, 12);
		d10 = new Date(99999, 9, 9);
		
		t1 = new Tweet(ID1_JESUS1, null, d1,empty, null);
		t2 = new Tweet(ID2_NOW, null, d2,empty, null);
		t3 = new Tweet(ID3_JESUS2, ID1_JESUS1, d3,empty, null);
		t4 = new Tweet(ID4_BUG, null, d4,empty, null);
		t5 = new Tweet(ID5_SATAN1, null, d5,empty, null);
		t6 = new Tweet(ID6_SATAN2, ID5_SATAN1, d6,empty, null);
		t7 = new Tweet(ID7_JESUS3, ID3_JESUS2, d7,empty, null);
		t8 = new Tweet(ID8_SATAN3, ID6_SATAN2, d8,empty, null);
		t9 = new Tweet(ID9_SATAN4, ID5_SATAN1, d9,empty, null);
		t10 = new Tweet(ID10_END, ID2_NOW, d10,empty, null);
		t11 = new Tweet("time_test", ID2_NOW, d10,empty, null);
		
		underInspection.addElement(t1);
		underInspection.addElement(t2);
		underInspection.addElement(t3);
		underInspection.addElement(t4);
		underInspection.addElement(t5);
		underInspection.addElement(t6);
		underInspection.addElement(t7);
		underInspection.addElement(t8);
		underInspection.addElement(t9);
		underInspection.addElement(t10);
		
		underInspection.unionAllTweets();
	}

	@Test
	public void newElementsShouldHaveTheirOwnDates() {
		assertEquals(underInspection.getLatestRetweetTime(ID4_BUG),d4);
		assertEquals(underInspection.getLatestRetweetTime(ID10_END),d10);
	}
	
	@Test
	public void getLatestRetweetTimeShouldReturnLatestDateInSet() {
		assertEquals(underInspection.getLatestRetweetTime(ID1_JESUS1),d7);
		assertEquals(underInspection.getLatestRetweetTime(ID3_JESUS2),d7);
		assertEquals(underInspection.getLatestRetweetTime(ID7_JESUS3),d7);
		
		assertEquals(underInspection.getLatestRetweetTime(ID5_SATAN1),d9);
		assertEquals(underInspection.getLatestRetweetTime(ID6_SATAN2),d9);
		assertEquals(underInspection.getLatestRetweetTime(ID8_SATAN3),d9);
		assertEquals(underInspection.getLatestRetweetTime(ID9_SATAN4),d9);
	}
	
	@Test
	public void getLifeTimeShouldBeAccurate() {
		assertEquals(underInspection.getLifeTimeOfTweet(ID1_JESUS1),
				(Long)(d7.getTime()-d1.getTime()));
		assertEquals(underInspection.getLifeTimeOfTweet(ID5_SATAN1),
				(Long)(d9.getTime()-d5.getTime()));
	}
	
	@Test
	public void ShouldCountAllRetweets() {
		assertEquals((Integer)2,	underInspection.getNumberOfRetweets(ID1_JESUS1));
		assertEquals((Integer)3, underInspection.getNumberOfRetweets(ID5_SATAN1));
		assertEquals((Integer)0, underInspection.getNumberOfRetweets(ID4_BUG));
	}
	
	@Test(expected = TweetNotFoundException.class)
	public void searchForNoneExistendTweetShouldThrow(){
		Tweet exceptional = new Tweet("fake mustache", "fake ID", new Date(),empty, null);
		underInspection.getLatestRetweetTime("fake mustache");
	}
	
	@Test(timeout = 10)
	public void setInsertionShouldBeQuick() {
		underInspection.addElement(t11);
	}
}
