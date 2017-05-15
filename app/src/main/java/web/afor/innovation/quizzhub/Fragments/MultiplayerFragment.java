package web.afor.innovation.quizzhub.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;

import butterknife.ButterKnife;
import web.afor.innovation.quizzhub.Activities.MainActivity;
import web.afor.innovation.quizzhub.Config.Constant;
import web.afor.innovation.quizzhub.R;
import web.afor.innovation.quizzhub.Utils.Utils;


public class MultiplayerFragment extends Activity implements ConnectionRequestListener {


   // private WarpClient theClient;
    private TextView textView;
    Button clickButton;
    private WarpClient theClient;

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_multiplayer, container, false);

        init(view);

        return view;
    }*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_multiplayer);
        init();
    }

    private void init(){
        WarpClient.initialize(Constant.apiKey, Constant.secretKey);
        try {
            theClient = WarpClient.getInstance();
        } catch (Exception ex) {

        }
        theClient.addConnectionRequestListener(this);


        //click button play
        clickButton = (Button) findViewById(R.id.clickButton);

        clickButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("eazea","azeaze");
                theClient.connectWithUserName("testUser2");



            }
        });

    }


    @Override
    public void onConnectDone(ConnectEvent connectEvent) {
        if( connectEvent.getResult() == WarpResponseResultCode.SUCCESS){// go to room  list
            //isConnected = true;
            Log.d("yesssssss::","aaaa");
            Intent intent = new Intent(this, ListRoomFragment.class);
            startActivity(intent);
            //Utils.launchActivity(MultiplayerFragment.this,ListRoomFragment.class);
            //((MainActivity)getActivity()).replaceFragmentWithBundleandTimer("mode","easy",30,5);
        }else{
            //isConnected = false;
            //Utils.showToastOnUIThread(MainActivity.this, "connection failed");
            Log.d("NOOOOOO::","aaaa");
        }

    }

    @Override
    public void onDisconnectDone(ConnectEvent connectEvent) {

    }

    @Override
    public void onInitUDPDone(byte b) {

    }
}
