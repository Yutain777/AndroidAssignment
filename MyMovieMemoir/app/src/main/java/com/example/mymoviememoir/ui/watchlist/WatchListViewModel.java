package com.example.mymoviememoir.ui.watchlist;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymoviememoir.daterelated.DateRelated;
import com.example.mymoviememoir.room.dao.database.entity.Movie;
import com.example.mymoviememoir.room.dao.database.entity.MovieDatabase;

import java.util.List;

public class WatchListViewModel extends ViewModel {
    private MovieDatabase db;
    private MutableLiveData<String> movieList;
    private MutableLiveData<String> mText;
    private int id = 0;

    public WatchListViewModel() {
        movieList = new MutableLiveData<>();

    }

    public LiveData<String> getMovieList() {
        return movieList;
    }
    public LiveData<String> getMtext() {return mText;}
    public void setMovieList(String str) {
        movieList.setValue(str);
    }

    public void initDatabase(Context context){
        db = MovieDatabase.getInstance(context);
    }
    //Display all movie list
    public void readDb(){
        ReadDatabase readDatabase = new ReadDatabase();
        readDatabase.execute();
    }

    public void deleteList(int id){
            Delete delete = new Delete();
            delete.execute(id);

    }



    private class ReadDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
             List<Movie> movie = db.movieDAO().getAll();
            if (!(movie.isEmpty() || movie == null) ){
                String allMovie = "";
                for (Movie temp : movie) {
                    String userstr = (temp.getUid() + " " + temp.getMovieName() +
                            " " + DateRelated.dateString(temp.getReleaseDate())
                             + " "+ DateRelated.dateString(temp.getAddDate())+ " , " );
                    allMovie = allMovie + System.getProperty("line.separator") +
                            userstr;
                }
                return allMovie;
            }
            else
                return "";
        }
        @Override
        protected void onPostExecute(String details) {
            movieList.setValue(details);

        }


        }

    private class Delete extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            try {
                Movie movie = db.movieDAO().findByUid(params[0]);
                db.movieDAO().delete(movie);
            }catch (Exception e)
            {

            }
            return null;

        }
        protected void onPostExecute(Void param) {


        }
    }














    }































