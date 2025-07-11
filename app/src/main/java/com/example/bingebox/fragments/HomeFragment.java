package com.example.bingebox.fragments;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bingebox.SharedViewModel;
import com.example.bingebox.dialogBox.Home_dialog;
import com.example.bingebox.R;
import com.example.bingebox.RVInterface;
import com.example.bingebox.adapters.MovieAdapter;
import com.example.bingebox.api_service.MovieDetails;
import com.example.bingebox.viewmodel.View_Model;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements RVInterface {

    private View_Model viewModel;
    private MovieAdapter adapter;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private List<MovieDetails> movies;
    private SharedViewModel sharedViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(View_Model.class);

        setupRecyclerView();
        loadSavedDataOrPerformInitialSearch();
    }
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    private void setupRecyclerView() {
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        adapter = new MovieAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);
    }

    private void loadSavedDataOrPerformInitialSearch() {
        String lastQuery = sharedViewModel.getLastSearchQuery().getValue();
        List<MovieDetails> lastResults = sharedViewModel.getLastMovieResults().getValue();

        if (lastQuery != null && lastResults != null) {
            movies = lastResults;
            adapter.updateMovies(movies);
        } else {
            performInitialSearch();
        }
    }

    private void performInitialSearch() {
        performSearch("Harry Potter");
    }

    public void performSearch(String query) {
        if (query == null || query.trim().isEmpty()) {
            Toast.makeText(requireContext(), "Please enter a search query", Toast.LENGTH_SHORT).show();
            return;
        }
        showProgressBar();
        viewModel.getMovieDetails(query).observe(getViewLifecycleOwner(), movieDetails -> {
            hideProgressBar();
            if (movieDetails != null) {
                movies = movieDetails;
                adapter.updateMovies(movies);
                sharedViewModel.setLastSearchQuery(query);
                sharedViewModel.setLastMovieResults(movies);
            } else {
                movies = new ArrayList<>();
                adapter.updateMovies(movies);
                Toast.makeText(requireContext(), "No results found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public MovieDetails getMovieAt(int position) {
        return movies.get(position);
    }

    @Override
    public void onItemClick(int position) {
        if (movies == null || position >= movies.size()) {
            return;
        }
        MovieDetails movie = getMovieAt(position);
        if (movie == null || getContext() == null) {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_movie_details, null);

        Home_dialog dialogBox = new Home_dialog(requireContext(), dialogView, movie, viewModel);
        dialogBox.display_dialog_box();
    }
}