package com.example.bingebox;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bingebox.adapters.MovieAdapter;
import com.example.bingebox.api_service.MovieDetails;
import com.example.bingebox.database.Entity_Movie;
import com.example.bingebox.viewmodel.View_Model;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RVInterface {

    private MovieAdapter adapter;
    private View_Model view_model;
    private EditText searchEditText;
    private ProgressBar progressBar;
    private List<MovieDetails> movies;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        searchEditText = findViewById(R.id.searchEditText);
        progressBar = findViewById(R.id.progressBar);

        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        adapter = new MovieAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        view_model = new View_Model(getApplication());

        setupSearch();
        performSearch("avengers");
    }

    private void setupSearch() {
        searchEditText.setOnClickListener(view -> {
            searchEditText.setFocusableInTouchMode(true);
            searchEditText.requestFocus();
            showKeyboard(searchEditText);
        });

        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getAction() == KeyEvent.ACTION_DOWN &&
                            event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                performSearch(searchEditText.getText().toString());
                hideKeyboard(searchEditText);
                searchEditText.clearFocus();
                return true;
            }
            return false;
        });
    }

    private void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void performSearch(String query) {
        showProgressBar();
        LiveData<List<MovieDetails>> movieDetailsLiveData = view_model.getMovieDetails(query);
        movieDetailsLiveData.observe(this, movieDetails -> {
            hideProgressBar();
            if (movieDetails != null) {
                movies = movieDetails;
                adapter.updateMovies(movies);
            } else {
                movies = new ArrayList<>();
                adapter.updateMovies(movies);
                // Optionally show a message to the user
            }
        });
    }

    public MovieDetails getMovieAt(int position) {
        return movies.get(position);
    }

    @Override
    public void onItemClick(int position) {
        MovieDetails movie = getMovieAt(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_movie_details, null);

        ImageView dialogMovieImage = dialogView.findViewById(R.id.dialogMovieImage);
        TextView dialogMovieTitle = dialogView.findViewById(R.id.dialogMovieTitle);
        TextView dialogMovieYear = dialogView.findViewById(R.id.dialogMovieYear);
        TextView dialogMovieGenre = dialogView.findViewById(R.id.dialogMovieGenre);
        Button dialogAddToLibraryButton = dialogView.findViewById(R.id.dialogAddToLibraryButton);

        dialogMovieTitle.setText(movie.getTitle());
        dialogMovieYear.setText(movie.getYear());
        dialogMovieGenre.setText(movie.getType().toUpperCase());

        // Load image using Glide
        if (movie.getImage() != null && movie.getImage().getImageUrl() != null) {
            Glide.with(this)
                    .load(movie.getImage().getImageUrl())
                    .placeholder(R.drawable.default_movie_poster)
                    .error(R.drawable.default_movie_poster)
                    .into(dialogMovieImage);
        } else {
            dialogMovieImage.setImageResource(R.drawable.default_movie_poster);
        }

        dialogAddToLibraryButton.setOnClickListener(v -> {
            Entity_Movie entityMovie = new Entity_Movie(movie.getImage().getImageUrl(), movie.getTitle(), movie.getType(), movie.getYear());
            Log.d("MainActivity", "Adding to Library: " + movie.getTitle());
            view_model.insertLB(entityMovie);
            Toast.makeText(this, "Added to Library", Toast.LENGTH_SHORT).show();
        });

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
