package web.afor.innovation.quizzhub.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import web.afor.innovation.quizzhub.R;
import web.afor.innovation.quizzhub.WebService.UserController;

public class inscription extends AppCompatActivity {
    private EditText password;
    private EditText username;
    private EditText email;
    private ProgressBar progressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_inscription);

        initViews();
    }

    protected void initViews(){
        password =(EditText) findViewById(R.id.t_password);
        username =(EditText) findViewById(R.id.ed_username);
        email =(EditText) findViewById(R.id.t_email);
        progressbar =(ProgressBar) findViewById(R.id.progressBar);
    }

    protected void inscription (View view ){


        UserController userController=new UserController(this,progressbar);
        userController.inscription(email.getText().toString(),password.getText().toString(),username.getText().toString());



    }







}
