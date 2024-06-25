package com.example.bingebox;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bingebox.adapters.MovieAdapter;
import com.example.bingebox.api_service.MovieDetails;
import com.example.bingebox.viewmodel.View_Model;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MovieAdapter adapter;
    private View_Model view_model;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        searchEditText = findViewById(R.id.searchEditText);

        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        adapter = new MovieAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        view_model = new View_Model(getApplication());

        setupSearch();
        performSearch("avengers");
    }

    private void setupSearch() {
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                performSearch(searchEditText.getText().toString());
                return true;
            }
            return false;
        });
    }

    private void performSearch(String query) {
        LiveData<List<MovieDetails>> movieDetailsLiveData = view_model.getMovieDetails(query);
        movieDetailsLiveData.observe(this, movieDetails -> {
            adapter.updateMovies(movieDetails);
        });
    }
}