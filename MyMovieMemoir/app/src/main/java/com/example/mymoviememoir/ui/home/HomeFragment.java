package com.example.mymoviememoir.ui.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.daterelated.DateRelated;
import com.example.mymoviememoir.networkconnection.NetworkConnection;
import com.example.mymoviememoir.sharedpreferences.SharedPreferencesFile;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView rencentWatched;
    private  TextView welcome;
    private TextView currentDate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        homeViewModel.initContext(this);
         welcome = root.findViewById(R.id.welcomText);
        currentDate = root.findViewById(R.id.tv);
        SharedPreferencesFile sp = SharedPreferencesFile.getInstance(getActivity());
        String text1 = "welcome " + sp.getString("firstname", "no");
        welcome.setText(text1);

        rencentWatched = root.findViewById(R.id.recentText);
        GetTopFive getTopFive = new GetTopFive();
        getTopFive.execute(sp.getLong("personId", (long) 0).toString());

        TextView tv=root.findViewById(R.id.tv);
        tv.setText(DateRelated.currentDateString());

















        return root;
    }




    private class GetTopFive extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                NetworkConnection networkConnection = new NetworkConnection();
                String a = networkConnection.findTopFive(params[0]);
                //String b[] = a.split("\\[");
                String x[] = a.split("\\]");
                String d = x[0];
                String c[] = d.split("\\}");
                String f ="Rececent watched:\n";
                for (String e : c)
                {
                    f = f+e.substring(2) +"\n";
                } return f;

            }catch (Exception e){
                return  "no info";
            }




        }

        @Override
        protected void onPostExecute(String memoir) {
            rencentWatched.setText(memoir);
        }
    }











}
