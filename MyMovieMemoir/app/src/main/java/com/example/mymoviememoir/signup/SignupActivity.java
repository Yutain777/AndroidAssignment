package com.example.mymoviememoir.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.mymoviememoir.MainActivity;
import com.example.mymoviememoir.R;
import com.example.mymoviememoir.count.Count;
import com.example.mymoviememoir.daterelated.DateRelated;
import com.example.mymoviememoir.home_Activity;
import com.example.mymoviememoir.md5hass.MD5Hass;
import com.example.mymoviememoir.networkconnection.NetworkConnection;

import java.io.IOException;
import java.text.ParseException;

import static com.example.mymoviememoir.daterelated.DateRelated.dateCompared;

public class SignupActivity extends AppCompatActivity {

    private Button btnSignup;
    private DatePicker datepicker;
    private TextView textDob;
    private Button btnDob;
    private EditText editUsername;
    private Button btnBack;
    private EditText editPassword;
    private EditText editFirsname;
    private EditText editSurname;
    private EditText editAdress;
    private Spinner stateSpinner;
    private EditText editPostCode;
    private TextView dob;
    private RadioGroup genderSelect;
    private Button btnSignup2;
    private String gender = null;
    private TextView resultTextView;
    private Button btnVerify;
    private String pid;
    private String cid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editPassword = findViewById(R.id.editPassword);
        editFirsname = findViewById(R.id.editFirstname);
        datepicker = findViewById(R.id.dobPicker);
        textDob = findViewById(R.id.dob1);
        btnBack = findViewById(R.id.btnBack);
        btnDob = findViewById(R.id.btnDob);
        CountPerson countPerson = new CountPerson();
        countPerson.execute();

        // back to sign in page
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
            } });
        // set DoB
        btnDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = datepicker.getYear();
                int month = datepicker.getMonth()+1;
                int day = datepicker.getDayOfMonth();
                textDob.setText(year+"-"+month+"-"+day);
            }
        });
        // set gender
        genderSelect =findViewById(R.id.radioGroup);
        genderSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == 2131230862)
                {
                    gender = "Male";

                }
                else
                {

                    gender = "Female";
                }


            }
        });
        // check username
         btnVerify = findViewById(R.id.btnVerify);
        editUsername = findViewById(R.id.editUsername);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetByUserTask getByUserTask = new GetByUserTask();
                String userName = editUsername.getText().toString();
                if (!userName.isEmpty())
                {
                    getByUserTask.execute(userName);
                } } });
        //sign up
        btnSignup2 = findViewById(R.id.createUser);
        editFirsname = findViewById(R.id.editFirstname);
        editSurname = findViewById(R.id.editSurname);
        editAdress = findViewById(R.id.editAdress);
        editPostCode = findViewById(R.id.editPostcode);
        stateSpinner = findViewById(R.id.stateSpinner);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        resultTextView = findViewById(R.id.tvByCourse);
        btnSignup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetByUserTask getByUserTask = new GetByUserTask();
                AddPersonTask addPersonTask = new AddPersonTask();
                AddCredentialsTask addCredentialsTask = new AddCredentialsTask();
                String firstName = editFirsname.getText().toString();
                String surname = editSurname.getText().toString();
                String dob = textDob.getText().toString();
                String address = editAdress.getText().toString();
                String state = stateSpinner.getSelectedItem().toString();
                String postcode = editPostCode.getText().toString();
                String userName = editUsername.getText().toString();
                String password = editPassword.getText().toString();
                String passwordHash = MD5Hass.hassPassword(password);
                String id = "";
                int datecompared = 1;
                datecompared = dateCompared(dob);
                if (!userName.isEmpty() && !firstName.isEmpty() && !surname.isEmpty() && !address.isEmpty() && !state.isEmpty()&& !postcode.isEmpty() && !password.isEmpty() && resultTextView.getText().toString().equals("good")  && datecompared < 0)
                {
                    int i = Integer.parseInt(pid) +1;
                    id = Integer.toString(i);
                    String[] detials = {id,firstName,surname,gender,dob,address,state,postcode};
                    String[] userDetail = {id,userName,passwordHash,detials[0]};
                    addPersonTask.execute(detials);
                    addCredentialsTask.execute(userDetail);
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(intent);
                } else
                {
                    resultTextView.setText("reset");
                }
                }
             });

    }

    private class GetByUserTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            NetworkConnection networkConnection = new NetworkConnection();
            String id = params[0];

            String a = networkConnection.findByUsername(id);
            if (a.equals("[]")){
                return "good";
            }
            else{
                return "already exsit";
            }
        }

        @Override
        protected void onPostExecute(String userInfo) {
            resultTextView.setText(userInfo);
        }
    }

    private class AddCredentialsTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            NetworkConnection networkConnection = new NetworkConnection();
            return networkConnection.addCredentials(params);
        }
        @Override
        protected void onPostExecute(String result) {
            resultTextView.setText(result);
        } }

    private class AddPersonTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            NetworkConnection networkConnection = new NetworkConnection();
            try {
                return networkConnection.addPerson(params);
            } catch (ParseException e) {
                e.printStackTrace();
                return "";
            }
        }
        @Override
        protected void onPostExecute(String result) {

            resultTextView.setText(result);
        } }

    private class CountPerson extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            NetworkConnection networkConnection = new NetworkConnection();
            return networkConnection.countPerson();
        }
        @Override
        protected void onPostExecute(String result) {

            pid=result;
        } }




}
