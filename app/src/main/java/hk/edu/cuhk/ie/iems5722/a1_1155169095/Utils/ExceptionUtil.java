package hk.edu.cuhk.ie.iems5722.a1_1155169095.Utils;

public class ExceptionUtil {

    static public class IllegalChatroomIDException extends Exception{
        public IllegalChatroomIDException(){
            super("The chat room id you searched is not exist!!");
        }
    }

    static public class GetInfoException extends Exception{
        public GetInfoException(){
            super("Failed to get info from server!!");
        }
    }

}
