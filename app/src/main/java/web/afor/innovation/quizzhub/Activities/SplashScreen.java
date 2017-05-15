package web.afor.innovation.quizzhub.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import web.afor.innovation.quizzhub.Config.Constant;
import web.afor.innovation.quizzhub.Fragments.MultiplayerFragment;
import web.afor.innovation.quizzhub.Fragments.TagFragment;
import web.afor.innovation.quizzhub.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                Intent intent= new Intent(SplashScreen.this,MultiplayerFragment.class);
                startActivity(intent);
                finish();


            }
        }, Constant.SPLASH_DELAY);
    }
}
