package com.example.mymoviememoir.networkconnection;

import android.util.Log;

import com.example.mymoviememoir.daterelated.DateRelated;
import com.example.mymoviememoir.dbentity.Cinema;
import com.example.mymoviememoir.dbentity.Credentials;
import com.example.mymoviememoir.dbentity.Memoir;
import com.example.mymoviememoir.dbentity.Person;
import com.google.gson.Gson;


import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkConnection {

    private OkHttpClient client = null;
    private String results = "fuck";
    private MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public NetworkConnection() {
        client = new OkHttpClient();
    }
    private static final String BASE_URL = "http://169.254.28.177:8080/MovieMemoir/webresources/";
    // log in
    public String getByUsernameAndPossward(String username, String passwordHash){
        final String methodPath = "ass1.credentials/findByUserNameAndpassportHash/" + username + "/" + passwordHash;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results=response.body().string();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }
    // veriry username exist or not
    public String findByUsername(String username) {
        final String methodPath = "ass1.credentials/findByUsername/" + username;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results=response.body().string();

        }catch (Exception e){
            e.printStackTrace();

        }
        return results;

    }

// add person
    public String addPerson(String[] details) throws ParseException {
        Long id = Long.parseLong(details[0]);
        Date d = new SimpleDateFormat("yyyy-MM-dd").parse(details[4]);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.CHINA);
        String dob = formatter.format(d);
        Person person = new Person(id,details[1],details[2],details[3],dob,details[5],details[6],details[7]);
        Gson gson = new Gson();
        String personJson = gson.toJson(person);
        String strResponse="";
        //this is for testing, you can check how the json looks like in Logcat
        Log.i("json " , personJson);
        final String methodPath = "ass1.person/";
        RequestBody body = RequestBody.create(personJson,JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + methodPath)
                .post(body)
                .build();
        try {
            Response response= client.newCall(request).execute();
            strResponse= response.body().string();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return strResponse;
    }
    // add credentials
    public String addCredentials(String[] details)  {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.CHINA);
        String signDate = formatter.format(DateRelated.getCurrentDate());
        Long id = Long.parseLong(details[0]);
        Credentials credentials = new Credentials(id,details[1],details[2],signDate);
        Long pid = Long.parseLong(details[3]);
        credentials.setPersonId(pid);
        Gson gson = new Gson();
        String credentialsJson = gson.toJson(credentials);
        String strResponse="";
        Log.i("json " , credentialsJson);
        final String methodPath = "ass1.credentials/";
        RequestBody body = RequestBody.create(credentialsJson,JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + methodPath)
                .post(body)
                .build();
        try {
            Response response= client.newCall(request).execute();
            strResponse= response.body().string();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return strResponse;
    }

    public String findTopFive(String personId) {
        final String methodPath = "ass1.memoir/getTheTopFiveRecentYear/" + personId;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        results = "fuck yy";
        try {
            Response response = client.newCall(request).execute();
            results=response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }


    // get cinema name postcode
    public String getCinema(String cinemaName,String postcode) {
        final String methodPath = "ass1.cinema/findByCinemaPostcodeAndName/" + cinemaName +"/" + postcode;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results=response.body().string();

        }catch (Exception e){
            e.printStackTrace();

        }
        return results;
    }

    public String getAllCinema(){
        final String methodPath = "ass1.cinema/";
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results=response.body().string();

        }catch (Exception e){
            e.printStackTrace();

        }
        return results;
    }

    //add cinema
    public String countCinema(){
        final String methodPath = "ass1.cinema/count";
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results=response.body().string();

        }catch (Exception e){
            e.printStackTrace();

        }
        return results;
    }
    public String countPerson(){
        final String methodPath = "ass1.person/count";
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results=response.body().string();

        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }
    public String countCredentials(){
        final String methodPath = "ass1.credentials/count";
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results=response.body().string();

        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }










    public String addCinema(String[] details) {
        Long id = Long.parseLong(details[0]);
        Cinema cinema = new Cinema(id,details[1],details[2]);
        Gson gson = new Gson();
        String personJson = gson.toJson(cinema);
        String strResponse="";
        //this is for testing, you can check how the json looks like in Logcat
        Log.i("json " , personJson);
        final String methodPath = "ass1.cinema/";
        RequestBody body = RequestBody.create(personJson,JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + methodPath)
                .post(body)
                .build();
        try {
            Response response= client.newCall(request).execute();
            strResponse= response.body().string();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return strResponse;
    }
    //add memoir
    public String countMemoir(){
        final String methodPath = "ass1.memoir/count";
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results=response.body().string();

        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }
    public String addMemoir(String[] details) {
       // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.CHINA);
        Long id = Long.parseLong(details[0]);
        //Cinema cinema = new Cinema(id,details[1],details[2]);
        Memoir memoir = new Memoir(id,details[1],details[2],details[3],details[4],Float.parseFloat(details[5]));
        Long pid = Long.parseLong(details[6]);
        Long cid = Long.parseLong(details[7]);
        memoir.setPersonId(pid);
        memoir.setCinemaId(cid);
        Gson gson = new Gson();
        String credentialsJson = gson.toJson(memoir);
        String strResponse="";
        Log.i("json " , credentialsJson);
        final String methodPath = "ass1.memoir/";
        RequestBody body = RequestBody.create(credentialsJson,JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + methodPath)
                .post(body)
                .build();
        try {
            Response response= client.newCall(request).execute();
            strResponse= response.body().string();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return strResponse;
    }
    //get memoir
    public String getMemoir(String id,String sortway) {
        String methodPath="";
        if(sortway == null) {
             methodPath = "ass1.memoir/findByPersonId/" + id;
        }else{
             methodPath = "ass1.memoir/sort/" + id+ "/"+sortway;
        }
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results=response.body().string();

        }catch (Exception e){
            e.printStackTrace();

        }
        return results;
    }

    public String getPieChart(String personId,String  start,String end)
    {

        final String methodPath = "ass1.memoir/countBypersonIdANDWatchDate/" + personId +"/"+start +"/"+end+"?timestamp=1590830955391";
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results=response.body().string();

        }catch (Exception e){
            e.printStackTrace();

        }
        return results;
    }





    public String getBarChart(String personId,String year)
    {

        final String methodPath = "ass1.memoir/countBypersonIdANDMonth/" + personId +"/"+year;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results=response.body().string();

        }catch (Exception e){
            e.printStackTrace();

        }
        return results;

    }











































}