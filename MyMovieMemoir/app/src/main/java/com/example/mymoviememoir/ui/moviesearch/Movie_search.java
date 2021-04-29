package com.example.mymoviememoir.ui.moviesearch;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.googlesearch.SearchGoogleAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class Movie_search extends Fragment {

    private Movie_search context = this;
    private ImageView imageView;
    private MovieSearchViewModel movieSearchViewModel;
    private  String keyword;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        movieSearchViewModel = ViewModelProviders.of(this).get(MovieSearchViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_movie_search, container, false);
        final TextView tv= root.findViewById(R.id.tv_Result);
        final EditText editText= root.findViewById(R.id.ed_keyword) ;
        imageView = root.findViewById(R.id.imageView);
        final ListView listView = root.findViewById(R.id.listView);
        final String[] a = {""};
        Button btnSearch = root.findViewById(R.id.btn_search);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                keyword = editText.getText().toString();
              movieSearchViewModel.searchMovie(keyword);
              movieSearchViewModel.getNameText().observe(getViewLifecycleOwner(), new Observer<String>() {
                  @Override
                  public void onChanged(String s) {
                      tv.setText(s);
                      ArrayList<String> items = new ArrayList<>();
                      items.add(s);
                      listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1,items));
                      a[0] = s;

                  }
              });
              movieSearchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
                  @Override
                  public void onChanged(String s) {
                      Picasso.get()
                              .load(s)
                              .placeholder(R.mipmap.ic_launcher)
                              .resize(200, 200)
                              .centerInside()
                              .into(imageView);
                  }
              });

            }});
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        Intent intent = new Intent(getActivity(),MovieView.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("name",a[0]);
                        bundle.putString("movie",keyword);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                }

            }
        });






















        return root;

}}
