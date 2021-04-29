package com.example.mymoviememoir.ui.watchlist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.daterelated.DateRelated;
import com.example.mymoviememoir.facebook.FbShare;

import com.example.mymoviememoir.room.dao.database.entity.Movie;
import com.example.mymoviememoir.ui.moviesearch.MovieView;

import java.util.ArrayList;


public class WatchList_Fragment extends Fragment {

    private WatchListViewModel watchListViewModel;
    private Button btnRead;
    private Button btnInsert;
    private TextView textView;
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        watchListViewModel = ViewModelProviders.of(this).get(WatchListViewModel.class);
        watchListViewModel.initDatabase(getContext());
        final View root = inflater.inflate(R.layout.fragment_watch_list_, container, false);


            final ListView listView = root.findViewById(R.id.mList);
            final TextView textViewR = root.findViewById(R.id.textR);
            Button btnDelete = root.findViewById(R.id.deleteButton);

            textView = root.findViewById(R.id.textView);
            btnRead = root.findViewById(R.id.readButton);
            btnRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    watchListViewModel.readDb();
                    watchListViewModel.getMovieList().observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            String[] movielist = s.split(",");
                            ArrayList<String> items = new ArrayList<>();
                            for (String i : movielist) {
                                items.add(i);
                            }
                            listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, items));

                        }
                    });
                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    watchListViewModel.getMovieList().observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            String[] movielist = s.split(",");
                            textViewR.setText(movielist[position]);

                        }
                    });


                }
            });

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(getContext()).setIcon(R.drawable.ic_menu_camera)
                                .setTitle("confirm")
                                .setMessage("comfirm to delete")
                                .setNegativeButton("back", null)
                                .setNeutralButton("delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String list = textViewR.getText().toString();
                                        String[] a = list.split("\n");
                                        String no;
                                        if (a.length == 1) {
                                            String[] i = a[0].split(" ");
                                            no = i[0];
                                        } else {
                                            String[] i = a[1].split(" ");
                                            no = i[0];
                                        }
                                        try {
                                            int id = Integer.parseInt(no);
                                            watchListViewModel.deleteList(id);
                                        }catch (Exception e){}
                                        watchListViewModel.readDb();
                                        watchListViewModel.getMovieList().observe(getViewLifecycleOwner(), new Observer<String>() {
                                            @Override
                                            public void onChanged(String s) {
                                                String[] movielist = s.split(",");
                                                ArrayList<String> items = new ArrayList<>();
                                                for (String i : movielist) {
                                                    items.add(i);
                                                }
                                                listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, items));

                                            }
                                        });
                                    }
                                }).show();
                    }


                });

            //view movie selected
                Button btnView = root.findViewById(R.id.btnView);
                btnView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String movie = textViewR.getText().toString();
                        String[] a = movie.split("\n");
                        String movieName;
                        try{
                        if (a.length == 1) {
                            String[] i = a[0].split(" ");
                            movieName = i[1];
                        } else {
                            String[] i = a[1].split(" ");
                            movieName = i[1];
                        }
                        Intent intent = new Intent(getActivity(), MovieView.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("movie", movieName);
                        intent.putExtras(bundle);
                        startActivity(intent);}catch (Exception e){}
                    }
                });
                //share
          Button btnShare = root.findViewById(R.id.shareButton);
            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {


                        String text = textViewR.getText().toString();
                        Intent intent = new Intent(getActivity(), FbShare.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("message", text);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }catch (Exception e){}

                }
            });




        return root;
    }}


