package ac.il.technion.twc;

public class JsonTweetMaker {
    public static String createOriginalTweetJson(String id, String text){
        return "{\"created_at\":\"Sun April 07 13:00:00 +0000 2013\","
                + "\"id\":"+id+",\"id_str\":\""+id+"\","
                + "\"text\":\""+text+"\"}";
    }

    public static String createRetweetJson(String id, String originalTweetJson,String text){
        return "{\"created_at\":\"Sun April 07 13:00:00 +0000 2013\","
                + "\"id\":"+id+",\"id_str\":\""+id+"\","
                + "\"text\":\""+text+"\",\"retweeted_status\":"+originalTweetJson+"}";
    }

}
