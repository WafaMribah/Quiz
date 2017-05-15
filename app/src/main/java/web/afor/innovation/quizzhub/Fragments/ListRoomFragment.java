package web.afor.innovation.quizzhub.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.AllRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.AllUsersEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveUserInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.MatchedRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.RoomRequestListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.ZoneRequestListener;

import java.util.HashMap;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import web.afor.innovation.quizzhub.Activities.MainActivity;
import web.afor.innovation.quizzhub.Activities.QuizActivity;
import web.afor.innovation.quizzhub.Adaptors.RoomListAdapter;
import web.afor.innovation.quizzhub.R;


public class ListRoomFragment extends Activity implements ZoneRequestListener {

    private WarpClient theClient;
    private RoomListAdapter roomlistAdapter;
    private ListView listView;
    private View clickButton;
    private ProgressDialog progressDialog = null;
    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_list_room, container, false);
        listView = (ListView) view.findViewById(R.id.roomList);
        roomlistAdapter = new RoomListAdapter(this);
        init(view);
        return view;
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list_room);
        listView = (ListView)findViewById(R.id.roomList);
        roomlistAdapter = new RoomListAdapter(this);
        init();
    }
    private void init() {
        try {
            theClient = WarpClient.getInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        clickButton = (Button) findViewById(R.id.bt_newRoom);
        clickButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("join neyy","joiiiiiiiiinnn");
               onJoinNewRoomClicked();


            }
        });

    }

    public void onStart(){
        super.onStart();
        theClient.addZoneRequestListener(this);
        theClient.getRoomInRange(1, 1);// trying to get room with at least one user
    }

    public void joinRoom(String roomId) {
        if(roomId!=null && roomId.length()>0){
           // goToGameScreen(roomId);
           // ((MainActivity)getApplicationContext()).replaceFragmentWithBundle("roomId",roomId);
            Intent intent = new Intent(this, QuizActivity.class);
            intent.putExtra("roomId", roomId);
            startActivity(intent);
            Log.d("joinRoom", "success:"+roomId);
        }else{
            Log.d("joinRoom", "failed:"+roomId);
        }

    }
    public void onJoinNewRoomClicked(){
        progressDialog = ProgressDialog.show(this,"","Pleaes wait...");
        progressDialog.setCancelable(true);

        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("topLeft", "");
        properties.put("topRight", "");
        properties.put("bottomLeft", "");
        properties.put("bottomRight", "");
        theClient.createRoom(""+System.currentTimeMillis(), "Saurav", 4, properties);
    }

    @Override
    public void onDeleteRoomDone(RoomEvent roomEvent) {

    }

    @Override
    public void onGetAllRoomsDone(AllRoomsEvent allRoomsEvent) {

    }

    @Override
    public void onCreateRoomDone(final RoomEvent roomEvent) {
       runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(progressDialog!=null){
                    progressDialog.dismiss();
                    progressDialog = null;
                }

                if(roomEvent.getResult()==WarpResponseResultCode.SUCCESS){// if room created successfully
                    String roomId = roomEvent.getData().getId();
                    joinRoom(roomId);
                    Log.d("onCreateRoomDone", roomEvent.getResult()+" "+roomId);
                }else{
                    //Utils.showToastAlert(RoomListActivity.this, "Room creation failed...");
                }
            }
        });

    }

    @Override
    public void onGetOnlineUsersDone(AllUsersEvent allUsersEvent) {

    }

    @Override
    public void onGetLiveUserInfoDone(LiveUserInfoEvent liveUserInfoEvent) {

    }

    @Override
    public void onSetCustomUserDataDone(LiveUserInfoEvent liveUserInfoEvent) {

    }

    @Override
    public void onGetMatchedRoomsDone(final MatchedRoomsEvent matchedRoomsEvent) {
       runOnUiThread(new Runnable(){
            @Override
            public void run() {
                RoomData[] roomDataList = matchedRoomsEvent.getRoomsData();
//                Log.d("Rooom","pp"+roomDataList.length);
                if(roomDataList!=null && roomDataList.length>0){
                    roomlistAdapter.setData(roomDataList);
                    listView.setAdapter(roomlistAdapter);
                }else{
                    roomlistAdapter.clear();
                }
            }
        });

    }

    @Override
    public void onGetRoomsCountDone(RoomEvent roomEvent) {

    }

    @Override
    public void onGetUsersCountDone(AllUsersEvent allUsersEvent) {

    }
}
