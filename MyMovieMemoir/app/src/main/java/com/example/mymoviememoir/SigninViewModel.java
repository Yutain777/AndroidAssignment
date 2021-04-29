package com.example.mymoviememoir;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymoviememoir.dbentity.Credentials;
import com.example.mymoviememoir.md5hass.MD5Hass;
import com.example.mymoviememoir.networkconnection.NetworkConnection;
import com.example.mymoviememoir.sharedpreferences.SharedPreferencesFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SigninViewModel extends ViewModel {
    private Context context;
    private MutableLiveData<String> mText;
    NetworkConnection networkConnection = null;
    Credentials signinUser = new Credentials();

    public SigninViewModel(){
        mText = new MediatorLiveData<>();
        mText.setValue("hello");

    }
    public void setMessage(String message){
        mText.setValue(message);
    }
    public LiveData<String> getText(){
        return mText;
    }
    public void initContext(Context newConext){
        this.context = newConext;
    }


    public void signIn(String userName, String password){
        GetUser getUser = new GetUser();
        password = MD5Hass.hassPassword(password);
        getUser.execute(userName,password);
    }

    private class GetUser extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            NetworkConnection networkConnection = new NetworkConnection();
            String username = params[0];
            String passwordHash = params[1];
            String result = networkConnection.getByUsernameAndPossward(username, passwordHash);
            return result;
        }
        @Override
        protected void onPostExecute(String info) {
            try {
                JSONArray jsonArray = new JSONArray(info);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                Long credentialId = jsonObject.getLong("credentialsId");
                String userName = jsonObject.getString("username");
                String password = jsonObject.getString("passportHash");
                String signdate = jsonObject.getString("signupDate");
                JSONObject po = jsonObject.getJSONObject("personId");
                String firstname = po.getString("firstname");
                Long personId = po.getLong("personId");
                String adress = po.getString("adress");

               // signinUser = new Credentials(credentialId,userName,password,signdate);

                SharedPreferencesFile sp = SharedPreferencesFile.getInstance(context);
                sp.saveLong("credentialsId",credentialId);
                sp.saveString("username",userName);
                sp.saveString("passwordHash",password);
                sp.saveString("signupDate",signdate);
                sp.saveString("firstname",firstname);
                sp.saveLong("personId",personId);
                sp.saveString("adress",adress);
                if(!userName.isEmpty())
                {
                    setMessage("correct");
                }
                else{
                    setMessage("incorrect");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                setMessage(("error"));
            }

        }
    }





}
