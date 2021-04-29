package com.example.mymoviememoir.ui.memoir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.count.Count;
import com.example.mymoviememoir.daterelated.DateRelated;
import com.example.mymoviememoir.networkconnection.NetworkConnection;
import com.example.mymoviememoir.sharedpreferences.SharedPreferencesFile;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLDisplay;

public class AddToMemoir extends AppCompatActivity {
    private List<CharSequence> cinemaList = null;
    private ArrayAdapter<CharSequence> cinemaAdapter = null;
    private String cinemaCount;
    private String memoirCount;
    private String cid;
    private String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_memoir);
        CountCinema countCinema = new CountCinema();
        countCinema.execute();
        CountMemoir countMemoir = new CountMemoir();
        countMemoir.execute();
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String movie = bundle.getString("name");
        final String releaseDate = bundle.getString("releaseDate");
        String imageUrl = bundle.getString("image");
        TextView textView = findViewById(R.id.textMemoirInfo);
        textView.setText(movie + " " + releaseDate);
        ImageView imageView = findViewById(R.id.imageView4);
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .resize(200, 200)
                .centerInside()
                .into(imageView);
        //spinner
        final Spinner spinner = findViewById(R.id.spinnerCinema);
       // spinner.setPrompt("hello");
       // cinemaList = new ArrayList<>();
       // cinemaList.add("< Select cinema here>");
      //  GetCinema getCinema = new GetCinema();
     //   getCinema.execute();
       // cinemaAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, cinemaList);
       // spinner.setAdapter(cinemaAdapter);
        updateSpinner(spinner);
        // datapicker select
        final DatePicker datePicker = findViewById(R.id.datePicker);
        Button btnDatePicker = findViewById(R.id.btnDate);
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth()+1;
                int day = datePicker.getDayOfMonth();
                TextView textDate = findViewById(R.id.textView2);
                textDate.setText(year+"-"+month+"-"+day);
            }
        });
        //add cinema
        Button btnAddCinema = findViewById(R.id.btnAddCinema);
        final EditText editCinema = findViewById(R.id.editCinema);
        btnAddCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] newCinema = editCinema.getText().toString().split(" ");
                AddCinemaTask addCinemaTask = new AddCinemaTask();
                int i = Integer.parseInt(cinemaCount)+1;
                String id = Integer.toString(i);
                addCinemaTask.execute(id,newCinema[0],newCinema[1]);
                updateSpinner(spinner);
            }
        });
        //add rating
        final RatingBar ratingBar = findViewById(R.id.rating);
        Button btnRatting = findViewById(R.id.btnRating);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                score= Float.toString(rating);
            }
        });

        //add Memoir
        Button btnAddMemoir = findViewById(R.id.btnAddMemoir);
        final SharedPreferencesFile sp = SharedPreferencesFile.getInstance(this);
        btnAddMemoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(memoirCount)+1;
                String id = Integer.toString(i);
                TextView textWatch = findViewById(R.id.textView2);
                String watchDate = textWatch.getText().toString();
                EditText editTime = findViewById(R.id.timeText);
                String watchTime = editTime.getText().toString();
                String watchDateTime =watchDate + "T" +watchTime + "+08:00";//2008-05-06T18:00:00+08:00
                String release =releaseDate;
                String t =movie;
                release = release +"T18:00:00+08:00";
                EditText editComment = findViewById(R.id.editComment);
                String comment = editComment.getText().toString();
                String pid = Long.toString(sp.getLong("personId", (long) 0));
 //               String[] c = spinner.getSelectedItem().toString().split(" ");
                cid = Integer.toString(spinner.getSelectedItemPosition());
                AddMemoirTask addMemoirTask = new AddMemoirTask();
                addMemoirTask.execute(id, DateRelated.makeUp(watchDateTime),t,DateRelated.makeUp(release),comment,score,pid,cid);

            }
        });





    }




    private void updateSpinner(Spinner spinner){
        spinner.setPrompt("hello");
        cinemaList = new ArrayList<>();
        cinemaList.add("< Select cinema here>");
        GetCinema getCinema = new GetCinema();
        getCinema.execute();
        cinemaAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, cinemaList);
        spinner.setAdapter(cinemaAdapter);
        CountCinema countno = new CountCinema();
        countno.execute();
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



    private class CountCinema extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            NetworkConnection networkConnection = new NetworkConnection();
            return networkConnection.countCinema();

        }

        @Override
        protected void onPostExecute(String info) {
          cinemaCount = info;
        }
    }

    private class AddCinemaTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            NetworkConnection networkConnection = new NetworkConnection();
            return networkConnection.addCinema(params);
        }
        @Override
        protected void onPostExecute(String result) {
            TextView tv = findViewById(R.id.tvRe);
            tv.setText("add: " + result);

        }
    }

    private class AddMemoirTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            NetworkConnection networkConnection = new NetworkConnection();
            return networkConnection.addMemoir(params);
        }
        @Override
        protected void onPostExecute(String result) {
            TextView tv = findViewById(R.id.tvRe);
            tv.setText("add: " + result);

        }
    }
    private class CountMemoir extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            NetworkConnection networkConnection = new NetworkConnection();
            return networkConnection.countMemoir();

        }

        @Override
        protected void onPostExecute(String info) {
            memoirCount = info;
        }
    }

    private class GetCid extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            NetworkConnection networkConnection = new NetworkConnection();
            return networkConnection.getCinema(params[0],params[1]);

        }

        @Override
        protected void onPostExecute(String info) {
            try {
                JSONArray jsonArray = new JSONArray(info);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                cid = Integer.toString(jsonObject.getInt("cinemaId"));
            } catch (JSONException e) {
                e.printStackTrace();
                cid = "0";
            }
        }
    }

































}

