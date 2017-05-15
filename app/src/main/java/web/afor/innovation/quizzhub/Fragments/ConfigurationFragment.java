package web.afor.innovation.quizzhub.Fragments;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Vibrator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import web.afor.innovation.quizzhub.Activities.MainActivity;
import web.afor.innovation.quizzhub.R;


public class ConfigurationFragment extends Fragment {


    private ToggleButton toggleButton1;
    private TextView text1;
    private ToggleButton toggleButton2;
    private TextView text2;
    public Vibrator v;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_configuration, container, false);
        toggleButton1 = (ToggleButton) view.findViewById(R.id.toggleButton1);
        text1 = (TextView) view.findViewById(R.id.textView1);
        toggleButton2 = (ToggleButton) view.findViewById(R.id.toggleButton2);
        text2 = (TextView) view.findViewById(R.id.textView2);
        Vibrator v = (Vibrator) this.getContext().getSystemService(Context.VIBRATOR_SERVICE);



            if(toggleButton1.isChecked())
                    MainActivity.mpSplash.start();
                    text1.setText("Sound ON");
            toggleButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!toggleButton1.isChecked()){
                        MainActivity.mpSplash.pause();
                        text1.setText("Sound ON");
                    }
                    else {
                        MainActivity.mpSplash.start();
                        MainActivity.mpSplash.isLooping();
                        text1.setText("Sound OFF");
                    }
                }
            });
        if(toggleButton2.isChecked()){


           v.vibrate(500);
        text2.setText("Vibration ON");}
        else{
            v.vibrate(0);
            text2.setText("Vibration OFF");
        }



        return view;

    }
}
