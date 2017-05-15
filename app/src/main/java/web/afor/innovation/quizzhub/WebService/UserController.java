package web.afor.innovation.quizzhub.WebService;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;



    import android.app.Activity;
    import android.content.Context;
    import android.content.Intent;
    import android.util.Log;
    import android.view.View;
    import android.widget.ProgressBar;
    import android.widget.Toast;

    import com.android.volley.Request;
import com.android.volley.Response;
    import com.android.volley.VolleyError;
    import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

    import java.util.HashMap;
    import java.util.Map;

import web.afor.innovation.quizzhub.Activities.MainActivity;
import web.afor.innovation.quizzhub.Activities.preferences;
import web.afor.innovation.quizzhub.Config.Constant;
import web.afor.innovation.quizzhub.AppController;


public class UserController {



        RequestQueue requestQueue;
        private ProgressBar progressBar;
        private Context context;

        public UserController(Context context, ProgressBar progressBar) {
            requestQueue = Volley.newRequestQueue(context);
            this.progressBar = progressBar;
            this.context = context;

        }


        //liste_event
        public void seConnecter(final String login, final String pwd) {

            // Tag used to cancel the request
            String tag_json_obj = "json_obj_connect";

            progressBar.setVisibility(View.VISIBLE);

            Map<String, String> params = new HashMap<String, String>();
            params.put("email", login );
            params.put("pass", pwd);

            JsonObjectRequest myRequest = new JsonObjectRequest(
                    Request.Method.POST, Constant.BASE_URL+Constant.URL_LOGIN, new JSONObject(params),

                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            progressBar.setVisibility(View.GONE);

                            Log.d("cccccccccc seConnecter", response.toString());

                            if (response.toString().contains("token")){
                                Intent intent = new Intent(context,MainActivity.class);
                                context.startActivity(intent);
                                ((Activity) context).finish();
                            }

                        /*ObjectMapper mapper = new ObjectMapper();
                        try {
                            User emp = mapper.readValue(response.toString(), User.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/

                        }


                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                    Log.d("cccccccccccc", "" + error.getMessage() + "," + error.toString());
                    //pDialog.hide();
                    Toast.makeText(context,"Vérifier votre login ou password!! !!", Toast.LENGTH_LONG).show();
                }
            })

            {
            };


            AppController.getInstance().addToRequestQueue(myRequest,tag_json_obj);

        }

        public void inscription (final String email , final String pwd , final String username){

            String tag_json_obj="json_obj_inscription";
            progressBar.setVisibility(View.VISIBLE);
            Map<String, String> params = new HashMap<String, String>();
            params.put("email", email );
            params.put("pass", pwd);
            params.put("nickname",username);

            JsonObjectRequest myRequest = new JsonObjectRequest(
                    Request.Method.POST, Constant.BASE_URL+Constant.URL_inscription, new JSONObject(params),

                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            progressBar.setVisibility(View.GONE);

                            Log.d("cccccccccc seConnecter", response.toString());

                            if (response.toString().contains("token")){
                                //preferences.saveString(u, "username",context);
                                preferences.saveString(username,"username",context);
                                Intent intent = new Intent(context,MainActivity.class);
                                intent.putExtra("username",username.toString());
                                context.startActivity(intent);
                                ((Activity) context).finish();

                            }


                        /*ObjectMapper mapper = new ObjectMapper();
                        try {
                            User emp = mapper.readValue(response.toString(), User.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/

                        }


                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                    Log.d("cccccccccccc", "" + error.getMessage() + "," + error.toString());
                    //pDialog.hide();
                    Toast.makeText(context,"veuillez verifier les champs", Toast.LENGTH_LONG).show();
                }
            })

            {
            };


            AppController.getInstance().addToRequestQueue(myRequest,tag_json_obj);

        }

    }





