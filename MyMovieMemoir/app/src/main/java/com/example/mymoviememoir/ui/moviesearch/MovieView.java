package com.example.mymoviememoir.ui.moviesearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.daterelated.DateRelated;
import com.example.mymoviememoir.googlesearch.MovieApi;
import com.example.mymoviememoir.googlesearch.SearchGoogleAPI;
import com.example.mymoviememoir.room.dao.database.entity.Movie;
import com.example.mymoviememoir.room.dao.database.entity.MovieDatabase;
import com.example.mymoviememoir.ui.memoir.AddToMemoir;
import com.example.mymoviememoir.ui.watchlist.WatchList_Fragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovieView extends AppCompatActivity {
    private int uid;
    private RatingBar ratingBar;
   private ArrayList<String> list;
   MovieApi movieApi = null;
    MovieDatabase db = null;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);
        list = new ArrayList<>();
        uid =1;
        db = MovieDatabase.getInstance(this);
        ratingBar = findViewById(R.id.ratingBar);
        Button btn = findViewById(R.id.button);
       Button btnWatch = findViewById(R.id.btnWatch);
       // pass param
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
      //  String movieAndYear = bundle.getString("name");
        final String movie = bundle.getString("movie");
        final ImageView imageView =findViewById(R.id.imageView);
        //imageView.setImageBitmap((Bitmap) intent.getParcelableExtra("bitmap"));
      // textView.setText(movieAndYear);
        SearchMore searchMore = new SearchMore();
        searchMore.execute(movie);
        Search search = new Search();
        search.execute(movie);

        //add to watch list
        btnWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // judge exist or not by name and release date
                try {
                    ReadMovie readMovie = new ReadMovie();
                    readMovie.execute();
                }catch (Exception e){

                }
            }

        });
        // add to memoir
        Button btnAddMemoir = findViewById(R.id.btnMemoir);
        btnAddMemoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent1 = new Intent(MovieView.this, AddToMemoir.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", list.get(0));
                    bundle.putString("releaseDate", list.get(1));
                    bundle.putString("image", url);
                    intent1.putExtras(bundle);
                    startActivity(intent1);
                }catch (Exception e){

                }
            }
        });

    }
    private class SearchMore extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            movieApi = new MovieApi();
            return movieApi.search(params[0]);
        }
        @Override
        protected void onPostExecute(String info){
            movieApi = new MovieApi();
            try {
                list = movieApi.getMoreInfo(info);
                TextView textView1 = findViewById(R.id.textDetail);
                String infoDetail = "error";
                if (list.size() == 5) {
                    infoDetail = "title: " + list.get(0) + "\n" + "release date: " + list.get(1) + "\n" + "rating: " + list.get(2) + "\n" + "geners: " + list.get(3) + "\n" + "overview: " + list.get(4);
                }
                else {
                    infoDetail = "not find more information";
                }
                textView1.setText(infoDetail);
                int rating = Integer.parseInt(list.get(2));
                float star = (float) (rating / 2.0);
                ratingBar.setRating(star);
            }catch (Exception e){
                TextView textView1 = findViewById(R.id.textDetail);
                textView1.setText("no information");
            }
        }
    }

    private class InsertDatabase extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            Date date = DateRelated.convertToDate(params[2]);
            Date curDate = DateRelated.getCurrentDate();
            Movie movie = new Movie(Integer.parseInt(params[0]), params[1], date, curDate);
            long id = db.movieDAO().insert(movie);
            return (id + " " + params[0] + " " + params[1] + " " +
                    date + " " + curDate);
        }

        @Override
        protected void onPostExecute(String details) {
            TextView textView = findViewById(R.id.textAddDetail);
            textView.setText("Added: " + details);
        }





    }


    private class ReadMovie extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... params) {
            List<Movie> movie = db.movieDAO().getAll();
            String mname = list.get(0);
            String release = list.get(1);
            if (movie != null) {
                for (Movie m : movie) {
                    if (mname.equals(m.getMovieName()) && DateRelated.dateString(m.getReleaseDate()).equals(release)) {
                        uid = 0;
                        break;
                    } else {
                         uid =  movie.get(movie.size()-1).getUid() + 1;
                    }
                }
            }
            return uid;
        }

        @Override
        protected void onPostExecute(Integer details) {
            if(uid != 0) {
                InsertDatabase insertDatabase = new InsertDatabase();
                insertDatabase.execute(Integer.toString(uid), list.get(0), list.get(1));
            }
            TextView textView = findViewById(R.id.textAddDetail);
            textView.setText(Integer.toString(uid) + "added");

        }
    }


    private class Search extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return SearchGoogleAPI.search(params[0], new String[]{"num"}, new String[]{"3"});
        }


        @Override
        protected void onPostExecute(String info){
            ImageView imageView =findViewById(R.id.imageView3);
            Picasso.get()
                    .load(SearchGoogleAPI.getInfo(info).get(1))
                    .placeholder(R.mipmap.ic_launcher)
                    .resize(200, 200)
                    .centerInside()
                    .into(imageView);
            url = SearchGoogleAPI.getInfo(info).get(1);
        }
    }
}
