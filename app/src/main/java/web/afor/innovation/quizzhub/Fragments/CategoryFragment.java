package web.afor.innovation.quizzhub.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import web.afor.innovation.quizzhub.Activities.preferences;
import web.afor.innovation.quizzhub.Config.Constant;
import web.afor.innovation.quizzhub.Models.Answer;
import web.afor.innovation.quizzhub.R;

public class CategoryFragment extends Fragment {

    private TextView textQuestion;
    private TextView textAnswer1;
    private TextView textAnswer2;
    private TextView textAnswer3;
    private TextView textAnswer4;
    private TextView username ;

    private ArrayList<Answer> answerList;
    int score;
    public TextView score_view;
    String Question_Tag;
    private RelativeLayout transparentLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle bundle = getArguments();
        Question_Tag = bundle.getString("fragment");
        View view =  inflater.inflate(R.layout.fragment_quiz, container, false);
        initviews(view);

       // RelativeLayout Layout    = (RelativeLayout)   inflater.inflate(R.layout.activity_quiz, container, false);


        preferences.saveInt("HK",0,getContext());
        new DownloadQuestion().execute(Constant.BASE_URL + Constant.URL_BY_TAG + Question_Tag );


        //SharedPreferences sharedpref=getSharedPreferences("QuizApp",MODE_PRIVATE);
        //score =sharedpref.getInt("score",0);
        //score_view.setText(String.valueOf(score));

        Intent i = super.getActivity().getIntent();
        String usr =getActivity().getIntent().getStringExtra("username");
        username.setText(usr);
        // Don't use this method, it's handled by inflater.inflate() above :


        // The FragmentActivity doesn't contain the layout directly so we must use our instance of     LinearLayout :
        //Layout.findViewById(R.id.frag_quiz);
        // Instead of :
        return view;
    }
    protected  void save(){
        Context context= getContext();
        SharedPreferences sharedpref =context.getSharedPreferences("QuizApp",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpref.edit();
        Log.d("sc",score+"");
        editor.putInt("score",score);
        editor.commit();


    }
    private void initviews(final View view) {

        textQuestion = (TextView) view.findViewById(R.id.textviewQuestion);
        textAnswer1 =(TextView) view.findViewById(R.id.textviewResponse1);
        textAnswer2 =(TextView) view.findViewById(R.id.textviewResponse2);
        textAnswer3 =(TextView) view.findViewById(R.id.textviewResponse3);
        textAnswer4 =(TextView) view.findViewById(R.id.textviewResponse4);
        username= (TextView)   view.findViewById(R.id.textviewUsername);
        score_view=(TextView)  view.findViewById(R.id.scoreText);
         transparentLayout = (RelativeLayout) view.findViewById(R.id.transparentLayout);

        textAnswer1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if (answerList.get(0).isValidAnswer()) {
                    Log.d("TAG","your response is true");
                    incrementScore();

                    textAnswer1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rdgreen));


                    newQuestion(view);


                } else {
                    Log.d("TAG","your response is false");
                    textAnswer1.setBackgroundDrawable(getResources().getDrawable(R.drawable.red));
                    decrementScore();



                }
                save();

            }



        });
        textAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answerList.get(1).isValidAnswer()) {
                    Log.d("TAG","your response is true");

                    textAnswer2.setBackgroundDrawable(getResources().getDrawable(R.drawable.rdgreen));
                    incrementScore();

                    newQuestion(view);


                } else {
                    Log.d("TAG","your response is false");
                    textAnswer2.setBackgroundDrawable(getResources().getDrawable(R.drawable.red));
                    decrementScore();
                }
                save();

            }



        });
        textAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answerList.get(2).isValidAnswer()) {
                    Log.d("TAG","your response is true");
                    textAnswer3.setBackgroundDrawable(getResources().getDrawable(R.drawable.rdgreen));

                    newQuestion(view);
                    incrementScore();

                } else {
                    Log.d("TAG","your response is false");
                    textAnswer3.setBackgroundDrawable(getResources().getDrawable(R.drawable.red));
                    decrementScore();
                }
                save();

            }



        });
        textAnswer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answerList.get(3).isValidAnswer()) {
                    Log.d("TAG","your response is true");
                    textAnswer4.setBackgroundDrawable(getResources().getDrawable(R.drawable.rdgreen));

                    incrementScore();
                    newQuestion(view);
                } else {
                    Log.d("TAG","your response is false");
                    textAnswer4.setBackgroundDrawable(getResources().getDrawable(R.drawable.red));
                    decrementScore();
                }
                save();

            }




        });

        preferences.loadInt("HK",getContext());
    }
    public void setData(ArrayList<Answer> listanswer){
        textAnswer1.setText(listanswer.get(0).getResponse());
        textAnswer2.setText(listanswer.get(1).getResponse());
        textAnswer3.setText(listanswer.get(2).getResponse());
        textAnswer4.setText(listanswer.get(3).getResponse());
    }
    private void newQuestion (View view) {
        view.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                new DownloadQuestion().execute(Constant.BASE_URL+Constant.URL_BY_TAG + Question_Tag);

                textAnswer1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rd));
                textAnswer2.setBackgroundDrawable(getResources().getDrawable(R.drawable.rd));
                textAnswer3.setBackgroundDrawable(getResources().getDrawable(R.drawable.rd));
                textAnswer4.setBackgroundDrawable(getResources().getDrawable(R.drawable.rd));
                textAnswer1.setText("");
                textAnswer2.setText("");
                textAnswer3.setText("");
                textAnswer4.setText("");
                textQuestion.setText("");


            }
        },1500);
    }

    protected class DownloadQuestion extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();


        protected String doInBackground(String... params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            Request request = builder.build();
            try {
                Response response = client.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            transparentLayout.setVisibility(View.INVISIBLE);

            final JSONObject resultObject;
            final JSONArray answersObject;
            answerList = new ArrayList<Answer>();

            try {

                JSONObject jsonObject = new JSONObject(s);
                resultObject = jsonObject.getJSONObject("result");
                answersObject = resultObject.getJSONArray("answers");
                String question = resultObject.getString("question");
                int answers_length = answersObject.length();

                for(int i=0;i<answers_length;i++){

                    JSONObject answer = answersObject.getJSONObject(i);
                    String answer_text=answer.getString("answer_text");
                    Boolean valid=answer.getBoolean("correct");

                    answerList.add(new Answer(answer_text,valid,false));
                    Log.d("Tag","response "+answer_text+" ");

                }

                textQuestion.setText(question);
                if(answers_length>0)
                    textAnswer1.setText(answerList.get(0).getResponse());
                if(answers_length>1)
                    textAnswer2.setText(answerList.get(1).getResponse());
                if(answers_length>2)
                    textAnswer3.setText(answerList.get(2).getResponse());
                if(answers_length>3)
                    textAnswer4.setText(answerList.get(3).getResponse());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            getView().findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
            Log.d("Tag","response "+s);
            super.onPostExecute(s);
        }

    }


    public void incrementScore(){
        score+= Constant.incr;
        score_view.setText(String.valueOf(score));
    }
    public void decrementScore(){
        score-=Constant.decr;
        score_view.setText(String.valueOf(score));
    }
    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
