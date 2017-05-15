package web.afor.innovation.quizzhub.Config;

import android.support.v4.app.Fragment;

/**
 * Created by wafa on 7/27/16.
 */
public class Constant {

    public final static String apiKey = "4ff9a22faca12d0e2a94d21bf97692611bee84c765e606f85cfddab0e1d1e645";
    public final static String secretKey = "3c571911e2e5a26e22af70e6f664f77fb8049a0b6ce2b9f9919d9bace7563d66";
    public final static int incr = 10;
    public final static int decr = 3;
    public final static String URL_LOGIN = "api/authenticate";
    public final static String BASE_URL = "http://web4innovation.bianucci.org/";
    public final static String  RANDOM_URL="api/question/random";
    public final static String URL_TAGS="api/tags";
    public final static String URL_BY_TAG = "api/question/bytag/";
    public static int MY_SOCKET_TIMEOUT_MS = 50000;
    public static String URL_inscription = "api/register";
    public static final int SPLASH_DELAY = 500;
    public static String PACKAGE_NAME = "web.afor.innovation.quizzhub.Fragments.";
    public static String[] ArrayFragment ={
            PACKAGE_NAME + "QuizFragment",
            PACKAGE_NAME + "CategoryFragment" ,
            PACKAGE_NAME+ "TagFragment",
            PACKAGE_NAME+"ConfigurationFragment",
            PACKAGE_NAME+ "DifficultyFragment",
            PACKAGE_NAME+ "ListRoomFragment",
            PACKAGE_NAME+ "MultiplayerFragment"
    };


}