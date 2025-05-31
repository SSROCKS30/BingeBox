package com.example.bingebox.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Entity_Movie.class}, version = 1)
public abstract class MovieDB extends RoomDatabase {
    public abstract MovieDao movieDao();
    private static MovieDB instance;
    public static synchronized MovieDB getInstance(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), 
                    MovieDB.class, 
                    "movie_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
