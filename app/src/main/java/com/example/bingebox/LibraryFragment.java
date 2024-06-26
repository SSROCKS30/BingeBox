package com.example.bingebox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bingebox.adapters.LibraryAdapter;
import com.example.bingebox.database.Entity_Movie;
import com.example.bingebox.viewmodel.View_Model;

import java.util.ArrayList;

public class LibraryFragment extends Fragment implements RVInterface {

    private View_Model viewModel;
    private LibraryAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(View_Model.class);

        setupRecyclerView();
        loadLibraryItems();
    }

    private void setupRecyclerView() {
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        adapter = new LibraryAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);
    }

    private void loadLibraryItems() {
        viewModel.getLibMovies().observe(getViewLifecycleOwner(), libraryItems -> {
            adapter.updateItems(libraryItems);
        });
    }

    @Override
    public void onItemClick(int position) {
        Entity_Movie item = adapter.getItemAt(position);
        // Show library item details
    }
}