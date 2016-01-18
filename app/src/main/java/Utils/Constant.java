package Utils;

/**
 * Created by Administrator on 2015/11/24.
 */
public class Constant {
    /**
     * SharedPreferenc
     */
    public static final String PREFERENCE_FILE = "anApp_preference";  // sharedpreferenc文件名
    public static final String LOGIN_NAME="userName";//用户名
    public static final String LOGIN_PWD="userPassword";//密码

    public static final String MOB_SMS_SDK_APP_KEY="dccee8015952";
    public static final String MOB_SMS_SDK_APP_SECRET="4dbb774063c6580beaa227070fcd4d54";

    /**
     * Http
     */
    public static final String IP = "192.168.1.107";
    public static final String URL_LOGIN = "http://" + IP + "/GoTravel/login_check.php";
    public static final String URL_POST_COMMENT = "http://" + IP + "/GoTravel/post_comment.php";
}
