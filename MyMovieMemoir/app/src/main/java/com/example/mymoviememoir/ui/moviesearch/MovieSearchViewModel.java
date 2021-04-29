package com.example.mymoviememoir.ui.moviesearch;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymoviememoir.googlesearch.SearchGoogleAPI;


public class MovieSearchViewModel extends ViewModel {
    private MutableLiveData<String> mText;
    private MutableLiveData<String> nameText;
    private MutableLiveData<String> movieName;
    public MovieSearchViewModel(){
        mText = new MediatorLiveData<>();
        mText.setValue("hello");
        nameText = new MediatorLiveData<>();
        nameText.setValue("hello");
        movieName = new MediatorLiveData<>();
        movieName.setValue("no");

    }
    public void setMessage(String message){
        mText.setValue(message);
    }
    public void setNameText(String message){
        nameText.setValue(message);
    }
    public void setmovieName(String message){
        movieName.setValue(message);
    }
    public LiveData<String> getText(){
        return mText;
    }
    public LiveData<String> getNameText(){
        return nameText;
    }
    public LiveData<String> getmovieName(){
        return movieName;
    }



    public void searchMovie(String keyword){
        movieName.setValue(keyword);
        Search search = new Search();
        search.execute(keyword);


    }

    private class Search extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return SearchGoogleAPI.search(params[0], new String[]{"num"}, new String[]{"3"});
        }


        @Override
        protected void onPostExecute(String info){
            setMessage(SearchGoogleAPI.getInfo(info).get(1));
            setNameText(SearchGoogleAPI.getInfo(info).get(0));
        }
    }




}

