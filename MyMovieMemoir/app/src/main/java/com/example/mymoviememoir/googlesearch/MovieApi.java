package com.example.mymoviememoir.googlesearch;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieApi{
    private OkHttpClient client = null;
    private String results = "fuck";
    private MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private Map map;


    public  void setMap(){
        map=  new HashMap<String, String>();
        map.put("28","Action");
        map.put("12","Advemture");
        map.put("16","Animation");
        map.put("35","Comedy");
        map.put("80","Crime");
        map.put("99","Documentary");
        map.put("18","Drama");
        map.put("1075","Family");
        map.put("14","Fantasy");
        map.put("36","History");
        map.put("27","Horror");
        map.put("10402","Music");
        map.put("9648","Mystery");
        map.put("10749","Romance");
        map.put("878","Science Fiction");
        map.put("10770","TV Movie");
        map.put("53","Thriller");
        map.put("10752","War");
        map.put("37","Western");

    }


    public MovieApi() {
        client = new OkHttpClient();
    }

        public String search(String keyword) {
            final String methodPath = "https://api.themoviedb.org/3/search/movie?api_key=5b049d97c6f236fc74d43cbb375dc7d4&query=" + keyword;
            Request.Builder builder = new Request.Builder();
            builder.url(methodPath);
            Request request = builder.build();
            try {
                Response response = client.newCall(request).execute();
                results=response.body().string();

            }catch (Exception e){
                e.printStackTrace();

            }
            return results;

        }

    public  ArrayList<String> getMoreInfo (String result){
        String genreId;
        String genre = "";
        String releasedate;
        String summary;
        String title;
        ArrayList<String> list = new ArrayList<>();
        String rating;
        setMap();
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject jo = jsonObject.getJSONArray("results").getJSONObject(0);
            title = jo.getString("original_title");
            genreId = jo.getString("genre_ids");
            String[] g = genreId.substring(1, genreId.length() - 1).split(",");
            for (String a : g)
            {
                genre = genre +map.get(a) + ", ";
            }
            genre = genre.substring(0,genre.length()-2);

            releasedate = jo.getString("release_date");
            summary = jo.getString("overview");
            rating = Integer.toString(jo.getInt("vote_average"));
            list.add(title);
            list.add(releasedate);
            list.add(rating);
            list.add(genre);
            list.add(summary);
        } catch (JSONException e) {
            e.printStackTrace();
            list.add("error");
        }
        return list;
    }


    }









