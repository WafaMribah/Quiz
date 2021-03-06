package web.afor.innovation.quizzhub.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ChatEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LobbyData;
import com.shephertz.app42.gaming.multiplayer.client.events.MoveEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.UpdateEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.NotifyListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.RoomRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import web.afor.innovation.quizzhub.Activities.preferences;
import web.afor.innovation.quizzhub.Config.Constant;
import web.afor.innovation.quizzhub.Models.Answer;
import web.afor.innovation.quizzhub.R;

public class QuizActivity extends AppCompatActivity implements RoomRequestListener, NotifyListener {

    private TextView textviewQuestion;
    private TextView rep1;
    private TextView rep2;
    private TextView rep3;
    private TextView rep4;
    private int score;
    private TextView textviewScore;
    String roomId;
    private ArrayList<Answer> answer;
    ProgressDialog progresswait=null;
    private RelativeLayout transparentLayout;
    private TextView username;
    private WarpClient theClient;

    @Override
    public void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_quiz);

        preferences.savePreferencesBoolean(getApplicationContext(), "firstrun", false);

        /*DownloadQuestion question = new DownloadQuestion();
        question.execute(Constant.BASE_URL + Constant.RANDOM_URL);*/

        answer = new ArrayList<Answer>();

        initViews();
        init();

    }

    private void initViews() {
        textviewQuestion = (TextView) findViewById(R.id.textviewQuestion);
        rep1 = (TextView) findViewById(R.id.textviewResponse1);
        verifyAnswer(rep1,0);
        rep2 = (TextView) findViewById(R.id.textviewResponse2);
        verifyAnswer(rep2,1);
        rep3 = (TextView) findViewById(R.id.textviewResponse3);
         verifyAnswer(rep2,2);
        rep4 = (TextView) findViewById(R.id.textviewResponse4);
         verifyAnswer(rep2,3);
        textviewScore = (TextView) findViewById(R.id.scoreText);

        transparentLayout = (RelativeLayout) findViewById(R.id.transparentLayout);
        username = (TextView) findViewById(R.id.textviewUsername);
        //transparentLayout.setVisibility(View.GONE);

        /* SharedPreferences.Editor editor = sharedpreferences.edit();
        score=sharedpreferences.getInt("score",0);*/



    }

    private void init(){
        //  WarpClient.initialize(Constant.apiKey, Constant.secretKey);
        try{
            theClient = WarpClient.getInstance();
        }catch(Exception e){
            e.printStackTrace();
        }

        Intent bundle=getIntent();
        if (bundle!=null){
            roomId=bundle.getStringExtra("roomId");
            if(theClient!=null){
                // Log.d("onSubscribeRoomDone:  "," Failed=elseee"+roomId);
                theClient.addRoomRequestListener(this);
                theClient.addNotificationListener(this);
                theClient.joinRoom(roomId);
                // theClient.j
            }
        }

    }
    private void verifyAnswer(final TextView textView , final int i) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (answer.size() > i) {
                    if(!answer.get(i).isClicked()) {
                        if (answer.get(i).isValidAnswer()) {
                            incrementScore();
                            sendUpdateEvent("You Loose !");//the other user lost
                            //Preferences.saveInt("key",score,getContext());
                            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.rdgreen));
                            newQuestion();
                        } else {
                            decrementScore();

                            sendUpdateEvent("You Win !");;//the other user wins
                            // Preferences.saveInt("key",score,getContext());
                            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.red));
                        }
                        textviewScore.setText(String.valueOf(score));
                        answer.get(i).setClicked(true);
                    }
                }
            }
        });
    }

    private void newQuestion () {
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                new DownloadQuestion().execute(Constant.BASE_URL+Constant.URL_TAGS);

                rep4.setBackgroundDrawable(getResources().getDrawable(R.drawable.rd));
                rep1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rd));
                rep2.setBackgroundDrawable(getResources().getDrawable(R.drawable.rd));
                rep3.setBackgroundDrawable(getResources().getDrawable(R.drawable.rd));
                rep1.setText("");
                rep2.setText("");
                rep3.setText("");
                rep4.setText("");
                textviewQuestion.setText("");


            }
        },1500);
    }

    @Override
    public void onRoomCreated(RoomData roomData) {

    }

    @Override
    public void onRoomDestroyed(RoomData roomData) {

    }

    @Override
    public void onUserLeftRoom(RoomData roomData, String s) {

    }

    @Override
    public void onUserJoinedRoom(RoomData roomData, final String name) {
        final String owner=roomData.getRoomOwner();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                transparentLayout.setVisibility(View.GONE);
                if(owner.equals(name))
                    username.setText(name);
                else{
                    username.append(" vs "+name);
                    if(progresswait!=null){
                        progresswait.dismiss();
                        progresswait = null;
                        DownloadQuestion question = new DownloadQuestion();
                        question.execute(Constant.BASE_URL + Constant.RANDOM_URL);
                    }

                }

            }
        });

    }

    @Override
    public void onUserLeftLobby(LobbyData lobbyData, String s) {

    }

    @Override
    public void onUserJoinedLobby(LobbyData lobbyData, String s) {

    }

    @Override
    public void onChatReceived(ChatEvent chatEvent) {
        String sender = chatEvent.getSender();
        if(sender.equals("testUser2")==false){// if not same user
            String message = chatEvent.getMessage();
            try{
                JSONObject object = new JSONObject(message);

                if(object.length()<1){
                    resul(object);
                }
                else {
                    gettQuestion(object);
                }
                // Toast.makeText(this,"you win",Toast.LENGTH_LONG);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    private void resul(JSONObject object) throws JSONException {
        final String win = (String) (object.get("win")+"");
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(QuizActivity.this, "You "+win, Toast.LENGTH_SHORT).show();

            }
        });

        Log.d("eee","lost=="+win);
    }

    private void gettQuestion(JSONObject respo) throws JSONException {
        final String question = respo.getString("question");
        JSONArray jArray = respo.getJSONArray("answers");

        for (int i = 0; i < jArray.length(); i++) {
            try {
                JSONObject oneObject = jArray.getJSONObject(i);
                // Pulling items from the array
                String rep=oneObject.getString("answer_text");
                boolean b= oneObject.getBoolean("correct");

                Log.d("ced","d"+rep);
                Answer a=new Answer(rep,b,false);
                answer.add(a);

            } catch (JSONException e) {
                // Oops
            }
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textviewQuestion.setText(question);
                if(answer.size()>0)
                    rep1.setText(answer.get(0).getResponse());
                if(answer.size()>1)
                    rep2.setText(answer.get(1).getResponse());
                if(answer.size()>2)
                    rep3.setText(answer.get(2).getResponse());
                if(answer.size()>3)
                    rep4.setText(answer.get(3).getResponse());
            }

        });
    }


    @Override
    public void onPrivateChatReceived(String s, String s1) {

    }

    @Override
    public void onPrivateUpdateReceived(String s, byte[] bytes, boolean b) {

    }

    @Override
    public void onUpdatePeersReceived(UpdateEvent updateEvent) {

    }

    @Override
    public void onUserChangeRoomProperty(RoomData roomData, String s, HashMap<String, Object> hashMap, HashMap<String, String> hashMap1) {

    }

    @Override
    public void onMoveCompleted(MoveEvent moveEvent) {

    }

    @Override
    public void onGameStarted(String s, String s1, String s2) {

    }

    @Override
    public void onGameStopped(String s, String s1) {

    }

    @Override
    public void onUserPaused(String s, boolean b, String s1) {

    }

    @Override
    public void onUserResumed(String s, boolean b, String s1) {

    }

    @Override
    public void onNextTurnRequest(String s) {

    }

    @Override
    public void onSubscribeRoomDone(RoomEvent roomEvent) {
        if(roomEvent.getResult()==WarpResponseResultCode.SUCCESS){
            Log.d("onSubscribeRoomDone:  "," Failedpppp="+roomEvent.getResult());
            theClient.getLiveRoomInfo(roomId);
        }else{
            //Utils.showToastOnUIThread(this, "onSubscribeRoomDone: Failed "+event.getResult());
            Log.d("onSubscribeRoomDone:  "," Failed="+roomEvent.getResult());
        }

    }

    @Override
    public void onUnSubscribeRoomDone(RoomEvent roomEvent) {

    }

    @Override
    public void onJoinRoomDone(RoomEvent roomEvent) {
        if(roomEvent.getResult()== WarpResponseResultCode.SUCCESS){
            Log.d("onJoinRoomDone: wqe"," fq="+roomEvent.getResult());
            theClient.subscribeRoom(roomId);
        }else{

            Log.d("onJoinRoomDone: Failed "," fq="+roomEvent.getResult());
        }

    }

    @Override
    public void onLeaveRoomDone(RoomEvent roomEvent) {

    }

    @Override
    public void onGetLiveRoomInfoDone(LiveRoomInfoEvent liveRoomInfoEvent) {
        if(liveRoomInfoEvent.getResult()==WarpResponseResultCode.SUCCESS){
            final String[] joinedUser = liveRoomInfoEvent.getJoinedUsers();
            if(joinedUser!=null && joinedUser.length==1){

                runOnUiThread(new Runnable() {
                    public void run() {
                        transparentLayout.setVisibility(View.VISIBLE);
                        progresswait=ProgressDialog.show(QuizActivity.this," ","Wainting for users ..");
                        username.setText(joinedUser[0]);

                    }
                });

            }else if (joinedUser!=null && joinedUser.length==2){

                runOnUiThread(new Runnable() {
                    public void run() {
                        transparentLayout.setVisibility(View.GONE);
                        if(progresswait!=null){
                            progresswait.dismiss();
                            progresswait = null;
                        }
                        username.setText(joinedUser[1]+" vs "+joinedUser[0]);

                    }
                });
                //username.setText(joinedUser[1]+"deuxx");
            }

        }else{
            Log.d("onGetLiveRoomInfoDone", " "+liveRoomInfoEvent.getResult());
            //Utils.showToastOnUIThread(this, "onGetLiveRoomInfoDone: Failed "+event.getResult());
        }

    }

    @Override
    public void onSetCustomRoomDataDone(LiveRoomInfoEvent liveRoomInfoEvent) {

    }

    @Override
    public void onUpdatePropertyDone(LiveRoomInfoEvent liveRoomInfoEvent) {

    }

    @Override
    public void onLockPropertiesDone(byte b) {

    }

    @Override
    public void onUnlockPropertiesDone(byte b) {

    }

    private class DownloadQuestion extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            Request request = builder.build();
            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String s) {
            transparentLayout.setVisibility(View.INVISIBLE);

            try {
                JSONObject respo = new JSONObject(s);
                String question = respo.getString("question");
                JSONArray jArray = respo.getJSONArray("answers");

                for (int i = 0; i < jArray.length(); i++) {
                    try {
                        JSONObject oneObject = jArray.getJSONObject(i);
                        // Pulling items from the array
                        String rep=oneObject.getString("answer_text");
                        boolean b= oneObject.getBoolean("correct");

                        Log.d("ced","d"+rep);
                        Answer a=new Answer(rep,b,false);
                        answer.add(a);

                    } catch (JSONException e) {
                        // Oops
                    }
                }

                textviewQuestion.setText(question);
                if(answer.size()>0)
                    rep1.setText(answer.get(0).getResponse());
                if(answer.size()>1)
                    rep2.setText(answer.get(1).getResponse());
                if(answer.size()>2)
                    rep3.setText(answer.get(2).getResponse());
                if(answer.size()>3)
                    rep4.setText(answer.get(3).getResponse());

                sendUpQuestion((JSONArray) jArray ,question);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            super.onPostExecute(s);
        }

    }

    public void incrementScore(){
        score+= Constant.incr;
        textviewScore.setText(String.valueOf(score));
    }
    public void decrementScore(){
        score-=Constant.decr;
        textviewScore.setText(String.valueOf(score));
    }

    private void sendUpdateEvent(String  win) {
        try {
            JSONObject object = new JSONObject();

            object.put("win", win);
            //object.put("correct", correct);
            theClient.sendChat(object.toString());
        } catch (Exception e) {
            Log.d("sendUpdateEvent", e.getMessage());
        }
    }

    private void sendUpQuestion( JSONArray answers ,String question) {
        try {
            JSONObject object = new JSONObject();

            object.put("answers",answers);
            object.put("question",question);
            //object.put("correct", correct);
            theClient.sendChat(object.toString());
        } catch (Exception e) {
            Log.d("sendUpdateEvent", e.getMessage());
        }
    }
}



