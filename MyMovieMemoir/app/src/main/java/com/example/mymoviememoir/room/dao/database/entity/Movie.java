package com.example.mymoviememoir.room.dao.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Movie {
    @PrimaryKey(autoGenerate = true)
    public int uid ;
    @ColumnInfo(name = "movie_name")
    public String movieName;
    @ColumnInfo(name = "release_date")
    public Date releaseDate;
    @ColumnInfo(name = "add_date")
    public Date addDate;



    public Movie(int uid, String movieName, Date releaseDate, Date addDate)
    {
        this.uid = uid;
        this.movieName = movieName;
        this.releaseDate = releaseDate;
        this.addDate = addDate;
    }


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }


































}
