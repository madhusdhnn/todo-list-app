package com.thetechmaddy.todoapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thetechmaddy.todoapp.fragments.DashboardFragment;
import com.thetechmaddy.todoapp.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new DashboardFragment());

        BottomNavigationView todoAppBottomNavigationView = findViewById(R.id.todo_app_bottom_navigation_view);
        todoAppBottomNavigationView.setOnNavigationItemSelectedListener((MenuItem menuItem) -> {
            switch (menuItem.getItemId()) {
                case R.id.action_dashboard:
                    loadFragment(new DashboardFragment());
                    return true;
                case R.id.action_user_settings:
                    loadFragment(new SettingsFragment());
                    return true;
            }
            return false;
        });
    }

    private <T extends Fragment> void loadFragment(T fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
