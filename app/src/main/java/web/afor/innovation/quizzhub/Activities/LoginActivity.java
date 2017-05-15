package web.afor.innovation.quizzhub.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.StringCharacterIterator;

import web.afor.innovation.quizzhub.R;
import web.afor.innovation.quizzhub.WebService.UserController;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {
    private EditText email;
    private EditText password;
    private ProgressBar progressbar;
     private CallbackManager callbackManager;
    private LoginButton loginButton;
    private  TextView info ;

    protected void onCreate (Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
          setContentView(R.layout.activity_login);
        initviews  ();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                info.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );

            }

            @Override
            public void onCancel() {
                info.setText("Login attempt canceled.");

            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed.");


            }
        });




}
    protected void initviews  (){
        email =(EditText) findViewById(R.id.ed_email);
        password =(EditText) findViewById(R.id.ed_password);
        progressbar =(ProgressBar)findViewById(R.id.progressBar);
        loginButton = (LoginButton)findViewById(R.id.login_button);
        info=(TextView)findViewById(R.id.info);

    }
    public void login_method(View v){
        Toast.makeText(this,"sign_in",Toast.LENGTH_LONG);
        //Intent intent = new Intent(this, QuizFragment.class);
        //startActivity(intent);
        UserController userController=new UserController(this,progressbar);

        userController.seConnecter(email.getText().toString(),password.getText().toString());




    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



    protected void inscription (View view ){

    Toast.makeText(this,"sign_up",Toast.LENGTH_LONG);
    Intent intent= new Intent(this,inscription.class);
    startActivity(intent);

}


}

