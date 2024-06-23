package com.example.bingebox;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // Change to GridLayoutManager
        int numberOfColumns = 2; // You can adjust this number as needed
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        adapter = new MovieAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        View_Model view_model = new View_Model(getApplication());
        LiveData<List<MovieDetails>> movieDetailsLiveData = view_model.getMovieDetails("avengers");
        movieDetailsLiveData.observe(this, movieDetails -> {
            adapter.updateMovies(movieDetails);
        });
    }
}