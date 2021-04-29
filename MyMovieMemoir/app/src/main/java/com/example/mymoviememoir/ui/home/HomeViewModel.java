package com.example.mymoviememoir.ui.home;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymoviememoir.networkconnection.NetworkConnection;
import com.example.mymoviememoir.sharedpreferences.SharedPreferencesFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private HomeFragment context;
    String result = "";
    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(result);
    }

    public LiveData<String> getText() {
        return mText;
    }
    public void setMessage(String message){
        mText.setValue(message);
    }

    public void initContext(HomeFragment newConext){
        this.context = newConext;
    }

    /*public void getMovie(Long id){
        GetTopFive getTopFive = new GetTopFive();
        String personId = id.toString();
        getTopFive.execute(personId);
    }*/




    /*private class GetTopFive extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            NetworkConnection networkConnection = new NetworkConnection();
            String a = networkConnection.findTopFive(params[0]);

            return a;
        }

        @Override
        protected void onPostExecute(String memoir) {
            JSONArray jsonArray = null;
            JSONObject jsonObject = null;

            try {
                jsonArray = new JSONArray(memoir);

                    jsonObject = jsonArray.getJSONObject(0);
                    String json = jsonObject.toString();
                    String[] jsonList = json.split("\\}");
                    ArrayList<String> a = new ArrayList<>();
                    String b = jsonList[0] + "}";
                    a.add(b);
                    for (int i = 1; i <jsonList.length; i++)
                    {
                        String c = jsonList[i].substring(1) + "}";
                        a.add(c);
                    }


                    String movieName = jsonObject.getString("movieName");
                    String release = jsonObject.getString("movieReleaseDate");
                    String score = jsonObject.getString("rating");
                    result = result + "movie: " + movieName + " release: " + release + " score: " + score + "\n";

            } catch (JSONException e) {
                e.printStackTrace();
            }

            mText.setValue(result);

        }*/
    }






