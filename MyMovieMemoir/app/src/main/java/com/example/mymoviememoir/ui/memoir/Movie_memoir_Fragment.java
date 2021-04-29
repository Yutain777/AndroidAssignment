package com.example.mymoviememoir.ui.memoir;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.googlesearch.MovieApi;
import com.example.mymoviememoir.googlesearch.SearchGoogleAPI;
import com.example.mymoviememoir.networkconnection.NetworkConnection;
import com.example.mymoviememoir.room.dao.database.entity.Movie;
import com.example.mymoviememoir.sharedpreferences.SharedPreferencesFile;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Movie_memoir_Fragment extends Fragment {

    private ArrayList<String> items;
    private ListView listView;
    private TextView textView1;
    private  ImageView imageView;
    private RatingBar ratingBar;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie_memoir_, container, false);
        items = new ArrayList<>();
       listView = root.findViewById(R.id.memoirList);
       ratingBar = root.findViewById(R.id.ratingBar1);
        textView1 = root.findViewById(R.id.detial);
         imageView =root.findViewById(R.id.imageView8);
        SharedPreferencesFile sp = SharedPreferencesFile.getInstance(getActivity());
        Long personId= sp.getLong("personId", (long) 0);
        final String pid = Long.toString(personId);
        GetMemoir getMemoir = new GetMemoir();
        getMemoir.execute(pid);
        //sort by date
        Button btnDate = root.findViewById(R.id.btnDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items = new ArrayList<>();
                GetMemoir getMemoir = new GetMemoir();
                getMemoir.execute(pid,"date");
            }
        });
        //sort by rating
        Button btnRating = root.findViewById(R.id.btnUsr);
        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items = new ArrayList<>();
                GetMemoir getMemoir = new GetMemoir();
                getMemoir.execute(pid,"rating");
            }
        });
        //sort by public rating
        Button btnPrating = root.findViewById(R.id.btnPublic);
        btnPrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });







        //listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String item =items.get(position);
                String[] detail = item.split(",");
                String title = detail[0].substring(12);
                Search search = new Search();
                search.execute(title);
                SearchMore searchMore = new SearchMore();
                searchMore.execute(title);



                    }});













        return root;
    }

    private class GetMemoir extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            NetworkConnection networkConnection = new NetworkConnection();
            if(params.length ==1)
            {
                return networkConnection.getMemoir(params[0],null);
            }else {
                return networkConnection.getMemoir(params[0],params[1]);
            }

        }
        protected void onPostExecute(String param) {
            JSONArray jsonArray;
            try {
                jsonArray = new JSONArray(param);
                int i = jsonArray.length();
                for (int a=0; a<i;a++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(a);
                    String movieName = jsonObject.getString("movieName");
                    String releaseDate = jsonObject.getString("movieReleaseDate").substring(0,10);
                    String watchDate = jsonObject.getString("watchDate").substring(0,10);
                    String comment = jsonObject.getString("comment");
                    String rating = Double.toString(jsonObject.getDouble("rating"));
                    String cinemaPostcode = jsonObject.getJSONObject("cinemaId").getString("cinemaPostcode");
                    String item = "Movie Name: " + movieName + ", Release Date: "+releaseDate+ ", watchDate: " +watchDate+ ", comment: "+ comment + ", rating: "+rating + ", Cinema Postcode: "+ cinemaPostcode;
                    items.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                items.add("nothing");
            }

            listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1,items));

        }
    }

    private class Search extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return SearchGoogleAPI.search(params[0], new String[]{"num"}, new String[]{"3"});
        }


        @Override
        protected void onPostExecute(String info){
            Picasso.get()
                    .load(SearchGoogleAPI.getInfo(info).get(1))
                    .placeholder(R.mipmap.ic_launcher)
                    .resize(200, 200)
                    .centerInside()
                    .into(imageView);
        }
    }

    private class SearchMore extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            MovieApi movieApi = new MovieApi();
            return movieApi.search(params[0]);
        }

        @Override
        protected void onPostExecute(String info) {
            MovieApi movieApi = new MovieApi();
            try {
                ArrayList<String> list;
                list = movieApi.getMoreInfo(info);
                String infoDetail = "error";
                if (list.size() == 5) {
                    infoDetail = "title: " + list.get(0) + "\n" + "release date: " + list.get(1) + "\n" + "rating: " + list.get(2) + "\n" + "geners: " + list.get(3) + "\n" + "overview: " + list.get(4);
                }
                textView1.setText(infoDetail);
                int rating = Integer.parseInt(list.get(2));
                float star = (float) (rating / 2.0);
                ratingBar.setRating(star);
            } catch (Exception e) {
                textView1.setText("no information");
            }
        }
    }



    private class SortRating extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            MovieApi movieApi = new MovieApi();
            String[] detail = params[0].split(",");
            String title = detail[0].substring(12);
            return movieApi.search(title);
        }

        @Override
        protected void onPostExecute(String info) {
            MovieApi movieApi = new MovieApi();
            int rating =0;
            try {
                ArrayList<String> list;
                list = movieApi.getMoreInfo(info);
                String infoDetail = "error";
                if (list.size() == 5) {
                    infoDetail = "title: " + list.get(0) + "\n" + "release date: " + list.get(1) + "\n" + "rating: " + list.get(2) + "\n" + "geners: " + list.get(3) + "\n" + "overview: " + list.get(4);
                }
                textView1.setText(infoDetail);
                rating = Integer.parseInt(list.get(2));
                float star = (float) (rating / 2.0);
                ratingBar.setRating(star);
            } catch (Exception e) {

            }
            String a = items.get(0) + rating;
        }
    }






















}
