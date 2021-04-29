package com.example.mymoviememoir.googlesearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchGoogleAPI {

    private OkHttpClient client = null;
    private static final String API_KEY = "AIzaSyDeElZURE96GTUVjA2YpxU2tkd_5dWZQoY";
    private static final String SEARCH_ID_cx = "004784903787466164595:uga1mu7mnii";
    private MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public SearchGoogleAPI() {
        client = new OkHttpClient();
    }
    public static String search(String keyword, String[] params, String[] values) {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter = "";
        if (params != null && values != null) {
            for (int i = 0; i < params.length; i++) {
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "=";
                query_parameter += values[i];
            }
        }
        try {
            url = new URL("https://www.googleapis.com/customsearch/v1?key=" + API_KEY + "&cx=" + SEARCH_ID_cx + "&q=" + keyword + query_parameter);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{ connection.disconnect(); } return textResult;
    }



    public static ArrayList<String> getInfo(String result){
        String title = null;
        String image = null;
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if(jsonArray != null && jsonArray.length() > 0) {
                title =jsonArray.getJSONObject(0).getString("title");
                int i =title.length();
                title = title.substring(0,i-6) + "\n" ;

            }
        }catch (Exception e){
            e.printStackTrace();
            title = "NO INFO FOUND";

        }
        try{
            JSONObject jsonObjecta = new JSONObject(result);
            JSONArray jsonArraya = jsonObjecta.getJSONArray("items");
            image =  jsonArraya.getJSONObject(0).getJSONObject("pagemap").getJSONArray("metatags").getJSONObject(0).getString("og:image");



        }catch (Exception x){
            image = "No Image";
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(title);
        arrayList.add(image);
        return arrayList;
    }


    public String convertAdress(String address) {
        final String method = "http://api.opencagedata.com/geocode/v1/json?q=" + address + "&key=3f370d3842454a4eb72e716b40bc8c99";
        Request.Builder builder = new Request.Builder();
        builder.url(method);
        Request request = builder.build();
        String results = "";
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}




