package com.example.bingebox;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bingebox.fragments.HomeFragment;
import com.example.bingebox.fragments.LibraryFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private EditText searchEditText;
    private HomeFragment homeFragment;
    private LibraryFragment libraryFragment;
    private SharedViewModel sharedViewModel;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        searchEditText = findViewById(R.id.searchEditText);
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        if (savedInstanceState == null) {
            homeFragment = HomeFragment.newInstance();
            libraryFragment = LibraryFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, homeFragment)
                    .commit();
        }

        setupBottomNavigation();
        setupSearch();
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, homeFragment)
                        .commit();
                return true;
            }
            else if (itemId == R.id.navigation_library) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, libraryFragment)
                        .commit();
                return true;
            }
            return false;
        });
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
                String query = searchEditText.getText().toString();
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (currentFragment instanceof HomeFragment) {
                    ((HomeFragment) currentFragment).performSearch(query);
                } else if (currentFragment instanceof LibraryFragment) {
                    ((LibraryFragment) currentFragment).performSearchOnLibrary(query);
                }
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Add any custom logic here if needed
    }
}