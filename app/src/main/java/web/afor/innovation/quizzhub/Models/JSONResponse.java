package web.afor.innovation.quizzhub.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wafa on 7/30/16.
 */
public class JSONResponse {


    public List <Tags> result ;

    public JSONResponse(List<Tags> results) {
        result = results;
    }

    public List<Tags> getResults() {
        return result;
    }

    public void setResults(List<Tags> results) {
        result = results;
    }
}
