package web.afor.innovation.quizzhub.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import web.afor.innovation.quizzhub.Config.Constant;
import web.afor.innovation.quizzhub.Models.Answer;
import web.afor.innovation.quizzhub.R;
import web.afor.innovation.quizzhub.Activities.preferences;

public class TagActivity extends AppCompatActivity {

    private TextView textViewQuestion;
    private TextView textViewAnswer1;
    private TextView textViewAnswer2;
    private TextView textViewAnswer3;
    private TextView textViewAnswer4;
    private TextView textViewScore;
    private RelativeLayout progressBar;
    private ArrayList<Answer> answerList;
    private boolean timerRunning = false;
    private int score;
    private String userName;
    private SharedPreferences sharedPreferences;
    private String strtext;
    Bundle bundle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Questions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_open_scale,
                R.anim.activity_close_translate);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);
        setupToolbar();
        getData();
        initViews();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private void getData() {
        bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("tag")) {
            strtext = bundle.getString("tag");
            Log.d("testBundle", strtext);
        }

        preferences.loadInt("key", TagActivity.this);

        new DownloadQuestion().execute(Constant.BASE_URL + Constant.RANDOM_URL);
    }

    protected void save() {
        Context context = TagActivity.this;
        SharedPreferences sharedpref = context.getSharedPreferences("QuizApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpref.edit();
        Log.d("sc", score + "");
        editor.putInt("score", score);
        editor.commit();


    }

    private void initViews() {
        score = 0;
        progressBar = (RelativeLayout) findViewById(R.id.progress);
        textViewQuestion = (TextView) findViewById(R.id.TextQuestion);
        textViewAnswer1 = (TextView) findViewById(R.id.TextAnswer1);
        verifyAnswer(textViewAnswer1, 0);
        textViewAnswer2 = (TextView) findViewById(R.id.TextAnswer2);
        verifyAnswer(textViewAnswer2, 1);
        textViewAnswer3 = (TextView) findViewById(R.id.TextAnswer3);
        verifyAnswer(textViewAnswer3, 2);
        textViewAnswer4 = (TextView) findViewById(R.id.TextAnswer4);
        verifyAnswer(textViewAnswer4, 3);
        textViewScore = (TextView) findViewById(R.id.score);
        save();
    }

    private void verifyAnswer(final TextView textView, final int i) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answerList.size() > i && !timerRunning) {

                    if (answerList.get(i).isValidAnswer()) {
                        incrementScore();
                        preferences.saveInt("key", score, TagActivity.this);
                        textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.rdgreen));
                        newQuestion();
                        save();
                    } else {
                        decrementScore();
                        preferences.saveInt("key", score, TagActivity.this);
                        textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.red));
                        save();
                    }
                    textViewScore.setText("\u2605" + String.valueOf(score));

                }
            }
        });
    }

    private void newQuestion() {

        timerRunning = true;
        progressBar.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() { //parce qu'il faut que ça s'execute sur le main thread de la UI
                    @Override
                    public void run() {

                        timerRunning = false;

                        new DownloadQuestion().execute(Constant.BASE_URL.concat(Constant.RANDOM_URL));
                        textViewAnswer1.setBackground(null);
                        textViewAnswer1.setText("");
                        textViewAnswer2.setBackground(null);
                        textViewAnswer2.setText("");
                        textViewAnswer3.setBackground(null);
                        textViewAnswer3.setText("");
                        textViewAnswer4.setBackground(null);
                        textViewAnswer4.setText("");

                    }
                });


            }
        }, 2000);

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Tag Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://web.afor.innovation.quizzhub.Activities/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Tag Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://web.afor.innovation.quizzhub.Activities/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    class DownloadQuestion extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();

        @Override
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
            // Log.d("TAG", "response" + s);
            //Toast.makeText(TagActivity.this, s, Toast.LENGTH_SHORT).show();

            progressBar.setVisibility(View.INVISIBLE);
            try {
                JSONObject jsonRootObject = new JSONObject(s);

                //Get the instance of JSONArray that contains JSONObjects
                String question = jsonRootObject.getString("question");

                JSONArray answers = jsonRootObject.optJSONArray("answers");
                answerList = new ArrayList<>();
                for (int i = 0; i < answers.length(); i++) {
                    JSONObject jsonObject = answers.getJSONObject(i);
                    answerList.add(new Answer(jsonObject.getString("answer_text"), jsonObject.getBoolean("correct"), false));
                    Log.d("TAG", "answer " + i + " =" + answerList.get(i).getResponse() + "  " + answerList.get(i).isValidAnswer());
                }
                Log.d("TAG", "question =" + question);

                setData(question, answerList);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            super.onPostExecute(s);
        }


    }

    private void setData(String question, ArrayList<Answer> array) {


        textViewQuestion.setText(question);
        if (array.size() > 0) {
            textViewAnswer1.setText(array.get(0).getResponse());
            if (array.size() > 1) {
                textViewAnswer2.setText(array.get(1).getResponse());
                if (array.size() > 2) {
                    textViewAnswer3.setText(array.get(2).getResponse());
                    if (array.size() > 3) {
                        textViewAnswer4.setText(array.get(3).getResponse());
                    }
                }

            }


        }

    }

    public void incrementScore() {
        score += Constant.incr;
        textViewScore.setText(String.valueOf(score));
    }

    public void decrementScore() {
        score -= Constant.decr;
        textViewScore.setText(String.valueOf(score));
    }

}
