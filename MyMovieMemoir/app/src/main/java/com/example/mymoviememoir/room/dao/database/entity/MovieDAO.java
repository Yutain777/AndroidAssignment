package com.example.mymoviememoir.room.dao.database.entity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MovieDAO {
    @Query("select * from Movie")
    List<Movie> getAll();
    @Query("select * from Movie Where uid=:movieId LIMIT 1")
    Movie findByUid(int movieId);
    @Insert
    void insertAll(Movie... movies);
    @Insert
    long insert(Movie movie);
    @Delete
    void delete(Movie movie);
    @Update(onConflict = REPLACE)
    void updateMovies(Movie... movies);
    @Query("DELETE FROM movie")
    void deleteAll();










}
