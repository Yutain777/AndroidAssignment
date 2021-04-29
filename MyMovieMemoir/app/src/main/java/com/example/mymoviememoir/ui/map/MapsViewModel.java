package com.example.mymoviememoir.ui.map;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymoviememoir.googlesearch.SearchGoogleAPI;
import com.example.mymoviememoir.networkconnection.NetworkConnection;
import com.example.mymoviememoir.sharedpreferences.SharedPreferencesFile;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapsViewModel extends ViewModel {
    private MutableLiveData<List<LatLng>> adressList;
    private MutableLiveData<List<LatLng>> cinemaAddressList;
    private List<LatLng> adrss;
    private List<LatLng> adrss1;
    private Context context;
    private ArrayList<String> cinemaList = new ArrayList<>();
    public MapsViewModel() {
        adressList = new MutableLiveData<>();
        cinemaAddressList = new MutableLiveData<>();
        adrss = new ArrayList<>();
        adrss1 = new ArrayList<>();
    }

    public LiveData<List<LatLng>> getList() {
        return adressList;
    }
    public LiveData<List<LatLng>> getCinemaList() {
        return cinemaAddressList;
    }

    public void init(Context context){
        this.context = context;
        GetCinema getCinema = new GetCinema();
        getCinema.execute();
    }
    public void getAdress(String myAdress){
            ConvertAd convertAd = new ConvertAd();
            convertAd.execute(myAdress);
    }
    public void getCinemaAdress(){

        for(String i:cinemaList){
            ConvertCinemaAd convertAd = new ConvertCinemaAd();
            convertAd.execute(i);
        }
    }
    private class ConvertAd extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            SearchGoogleAPI searchGoogleAPI =new SearchGoogleAPI();
            return searchGoogleAPI.convertAdress(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                    JSONObject firstAddObj = new JSONObject(jsonArray.get(0).toString());
                    JSONObject jo = firstAddObj.getJSONObject("geometry");
                    LatLng latLng = new LatLng(jo.getDouble("lat"),jo.getDouble("lng"));
                    adrss.add(latLng);
                    adressList.setValue(adrss);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class ConvertCinemaAd extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            SearchGoogleAPI searchGoogleAPI =new SearchGoogleAPI();
            return searchGoogleAPI.convertAdress(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                JSONObject firstAddObj = new JSONObject(jsonArray.get(0).toString());
                JSONObject jo = firstAddObj.getJSONObject("geometry");
                LatLng latLng = new LatLng(jo.getDouble("lat"),jo.getDouble("lng"));
                adrss1.add(latLng);
                cinemaAddressList.setValue(adrss1);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }








    private class GetCinema extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            NetworkConnection networkConnection = new NetworkConnection();
            return networkConnection.getAllCinema();

        }

        @Override
        protected void onPostExecute(String info) {

            try {
                JSONArray jsonArray = new JSONArray(info);

                JSONObject jsonObject;

                for (int i = 0 ; i<jsonArray.length(); i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    String cinema = jsonObject.getString("cinemaName");
                    String postcode = jsonObject.getString("cinemaPostcode");
                    cinemaList.add(cinema + " " + postcode);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
