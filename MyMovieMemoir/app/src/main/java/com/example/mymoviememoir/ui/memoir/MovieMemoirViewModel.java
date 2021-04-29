package com.example.mymoviememoir.ui.memoir;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymoviememoir.networkconnection.NetworkConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieMemoirViewModel extends ViewModel {
    private MutableLiveData<String> movieList;



    private class getMemoir extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            NetworkConnection networkConnection = new NetworkConnection();
            return networkConnection.getMemoir(params[0],null);


        }
        protected void onPostExecute(String param) {
            try {
                JSONArray jsonArray = new JSONArray(param);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                JSONArray jsonArray1 = new JSONArray(jsonObject.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }




}
