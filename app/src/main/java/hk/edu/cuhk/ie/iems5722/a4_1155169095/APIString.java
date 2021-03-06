package hk.edu.cuhk.ie.iems5722.a4_1155169095;

public class APIString {

    public static String Head = "http://";

//    public static String ServerHost = "10.0.2.2:8080";
//    public static String ServerHost = "42.193.248.26:8080"; //Tencent Cloud Server
    public static String ServerHost = "47.250.45.252:8080"; //AliCloud Server

    public static String Version = "api/a4";

    static public String GET_CHAT_ROOMS = contactAPI(ServerHost, Version, "get_chatrooms");

    static public String GET_MESSAGES = contactAPI(ServerHost, Version, "get_messages");

    static public String SEND_MESSAGES = contactAPI(ServerHost, Version, "send_message");

    static public String SEND_TOKEN = contactAPI(ServerHost, Version, "submit_push_token");

    static public String contactAPI(String server, String version, String func){
        return Head + server +  "/" + version + "/" + func;
    }
}
