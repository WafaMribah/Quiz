package web.afor.innovation.quizzhub.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import web.afor.innovation.quizzhub.Config.Constant;

/**
 * Created by wafa on 7/30/16.
 */
public class ApiClient {




        private static Retrofit retrofit = null;


        public static Retrofit getClient() {
            if (retrofit==null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(Constant.URL_TAGS)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }
