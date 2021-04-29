package com.example.mymoviememoir.count;

import android.os.AsyncTask;
import android.widget.TextView;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.networkconnection.NetworkConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Count {
    private static int i=20;
    public static String countId()
    {
        i++;
        return Integer.toString(i);
    }
}


