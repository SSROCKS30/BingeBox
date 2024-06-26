package com.example.bingebox.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Entity_Movie.class}, version = 1)
public abstract class MovieDB extends RoomDatabase {
    public abstract MovieDao movieDao();
    private static MovieDB instance;
    public static synchronized MovieDB getInstance(Context context) { // To ensure only one instance is created
        if (instance == null) {
            instance = Room.databaseBuilder(context, MovieDB.class, "movie_database").fallbackToDestructiveMigration().build();
            //.fallbackToDestructiveMigration() is used to delete the table and recreate it according to the new schema if the version is changed
        }
        return instance;
    }
}
