package com.example.mymoviememoir.room.dao.database.entity;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.mymoviememoir.daterelated.DateConverts;

@Database(entities = {Movie.class}, version = 2, exportSchema = false)
@TypeConverters({DateConverts.class})
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDAO movieDAO();
    private static MovieDatabase INSTANCE;

    public static synchronized MovieDatabase getInstance(final Context
                                                                    context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    MovieDatabase.class, "MovieDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }











}
