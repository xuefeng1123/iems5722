package hk.edu.cuhk.ie.iems5722.a1_1155169095;

public class APIString {

    public static String Head = "http://";

    public static String ServerHost = "18.217.125.61";

    public static String Version = "api/a3";

    static public String GET_CHAT_ROOMS = contactAPI(ServerHost, Version, "get_chatrooms");

    static public String GET_MESSAGES = contactAPI(ServerHost, Version, "get_messages");

    static public String SEND_MESSAGES = contactAPI(ServerHost, Version, "send_message");

    static public String contactAPI(String server, String version, String func){
        return Head + server +  "/" + version + "/" + func;
    }
}
