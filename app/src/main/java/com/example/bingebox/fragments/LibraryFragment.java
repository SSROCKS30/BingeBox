package com.example.bingebox.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bingebox.dialogBox.Lib_dialog;
import com.example.bingebox.R;
import com.example.bingebox.RVInterface;
import com.example.bingebox.adapters.LibraryAdapter;
import com.example.bingebox.database.Entity_Movie;
import com.example.bingebox.viewmodel.View_Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryFragment extends Fragment implements RVInterface {

    private View_Model viewModel;
    private LibraryAdapter adapter;
    private RecyclerView recyclerView;
    private Spinner statusSpinner;
    private ProgressBar progressBar;
    private List<String> statusOptions = Arrays.asList("All", "Plan To Watch", "Dropped", "Watching", "Completed");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        statusSpinner = view.findViewById(R.id.statusSpinner);
        progressBar = view.findViewById(R.id.progressBar);
        viewModel = new ViewModelProvider(this).get(View_Model.class);

        setupStatusSpinner();
        setupRecyclerView();
        loadAllLibraryItems();
    }
    public LibraryFragment newInstance() {
        return new LibraryFragment();
    }

    private void setupStatusSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, statusOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter);

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedStatus = statusOptions.get(position);
                filterMoviesByStatus(selectedStatus);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }


    private void setupRecyclerView() {
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        adapter = new LibraryAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);
    }

    private void filterMoviesByStatus(String status) {
        if (status.equals("All")) {
            loadAllLibraryItems();
        } else {
            viewModel.getLibMovies().observe(getViewLifecycleOwner(), libraryItems -> {
                List<Entity_Movie> filteredItems = new ArrayList<>();
                for (Entity_Movie item : libraryItems) {
                    if (item.getStatus().equals(status)) {
                        filteredItems.add(item);
                    }
                }
                adapter.updateItems(filteredItems);
            });
        }
    }

    private void loadAllLibraryItems() {
        showProgressBar();
        viewModel.getLibMovies().observe(getViewLifecycleOwner(), libraryItems -> {
            hideProgressBar();
            if (libraryItems != null && !libraryItems.isEmpty()) {
                adapter.updateItems(libraryItems);
            } else {
                Toast.makeText(requireContext(), "Your library is empty", Toast.LENGTH_SHORT).show();
                adapter.updateItems(new ArrayList<>());
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Entity_Movie movie = adapter.getItemAt(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_movie_lib_details, null);

        Lib_dialog dialogBox = new Lib_dialog(requireContext(), dialogView, movie, viewModel);
        dialogBox.display_dialog_box();
    }

    public void performSearchOnLibrary(String query) {
        if (query == null || query.isEmpty()) {
            loadAllLibraryItems();
            return;
        }

        showProgressBar();
        viewModel.getLibMovies().observe(getViewLifecycleOwner(), libMovieDetails -> {
            hideProgressBar();
            if (libMovieDetails != null && !libMovieDetails.isEmpty()) {
                List<Entity_Movie> movies = libMovieDetails.stream()
                        .filter(movie -> movie.getTitle().toLowerCase().contains(query.toLowerCase()))
                        .collect(Collectors.toList());

                if (!movies.isEmpty()) {
                    adapter.updateItems(movies);
                } else {
                    Toast.makeText(requireContext(), "No results found", Toast.LENGTH_SHORT).show();
                    adapter.updateItems(new ArrayList<>());
                }
            } else {
                Toast.makeText(requireContext(), "No movies in library", Toast.LENGTH_SHORT).show();
                adapter.updateItems(new ArrayList<>());
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
}