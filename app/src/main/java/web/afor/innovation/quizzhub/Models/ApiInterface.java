package web.afor.innovation.quizzhub.Models;

import retrofit2.Call;
import retrofit2.http.GET;
import web.afor.innovation.quizzhub.Config.Constant;

/**
 * Created by wafa on 7/30/16.
 */

    public interface ApiInterface {
        @GET(Constant.URL_TAGS)
        Call<JSONResponse> getResults();

}


