package com.example.bingebox.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert
    void insert(Entity_Movie entityClass);
    @Update
    void update(Entity_Movie entityClass);
    @Delete
    void delete(Entity_Movie entityClass);
    @Query("Select * from movie_table")
    LiveData<List<Entity_Movie>> getAllMovies();
}
