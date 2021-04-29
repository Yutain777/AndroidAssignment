package com.example.mymoviememoir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mymoviememoir.md5hass.MD5Hass;
import com.example.mymoviememoir.networkconnection.NetworkConnection;
import com.example.mymoviememoir.signup.SignupActivity;


public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button btnSignin;
    private Button btnSignup;
    private CheckBox showPassword;
    private Button btnGohome;
    private SigninViewModel signinViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignup = findViewById(R.id.btnSignup);
        btnSignin = findViewById(R.id.btnSignin);
        signinViewModel = new ViewModelProvider(this).get(SigninViewModel.class);
        signinViewModel.initContext(this);

        showPassword = findViewById(R.id.showPassword);
        //sign in
        username = findViewById(R.id.signinUsername);
        password = findViewById(R.id.signinPassword);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
       btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // GetUser getUserTask = new GetUser();
                String inputUsername = username.getText().toString();
                String inputPassword = password.getText().toString();
               // String hashPassword = MD5Hass.hassPassword(inputPassword);
                if (!inputUsername.isEmpty() && !inputPassword.isEmpty()) {
                   // getUserTask.execute(inputUsername, hashPassword);
                    signinViewModel.signIn(inputUsername, inputPassword);
                }
            }
        });
       signinViewModel.getText().observe(this, new Observer<String>() {
           @Override
           public void onChanged(String s) {
               TextView textView = findViewById(R.id.test123);
               if(s.equals("correct")){
                //go home page
                   //textView.setText(s);
                   Intent intent = new Intent(MainActivity.this,home_Activity.class);
                   startActivity(intent);
               }
               else{

                   textView.setText(s);
               }
           }
       });
        // show password or not
        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton mCompoundButton, boolean isChecked) {
                //Auto-generated method stub
                if(isChecked)
                {
                    password.setTransformationMethod(null);
                }else password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
       //sign up
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });




    }

    private class GetUser extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            NetworkConnection networkConnection = new NetworkConnection();
            String username = params[0];
            String passwordHash = params[1];
            String result = networkConnection.getByUsernameAndPossward(username, passwordHash);
            if(result.equals("[]"))
            {
                return "not correct";
            }
            else
            {
                return "correct";
            }

        }
        @Override
        protected void onPostExecute(String info) {
            TextView resultTextView = findViewById(R.id.test123);
            resultTextView.setText(info);
        }
    }


}
