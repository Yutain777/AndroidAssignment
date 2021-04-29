package com.example.mymoviememoir.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mymoviememoir.dbentity.Person;

import java.util.logging.SocketHandler;

public class SharedPreferencesFile {
    private static SharedPreferences sharedPreferences;
    private static SharedPreferencesFile sharedPreferencesFile;

    public SharedPreferencesFile(Context context){
        sharedPreferences = context.getSharedPreferences("name",Context.MODE_PRIVATE);
    }
    public static SharedPreferencesFile getInstance(Context context){
        sharedPreferencesFile = new SharedPreferencesFile(context);
        return sharedPreferencesFile;
    }


    public void saveLong(String key, Long id){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key,id);
        editor.apply();
    }

    public Long getLong(String key, Long defaultId){
        return sharedPreferences.getLong(key,defaultId);
    }
    public void saveInt(String key, int i){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key,i);
        editor.apply();
    }
    public int getInt(String key,int defaultId){
        return sharedPreferences.getInt(key, defaultId);
    }

    public void saveString(String key,String str)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,str);
        editor.apply();
    }

    public String getString(String key, String defaultStr){
        return sharedPreferences.getString(key, defaultStr);
    }

    public void savePerson(String key, Person person)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,person.getFirstname());
        editor.apply();
    }
    public String getFirstname(String key, String defultStr){
        return sharedPreferences.getString(key, defultStr);
    }

    public void putStringArray(String key,String str){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, str);
    }

}