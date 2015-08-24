package com.wattersnathen.popularmovies;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {

            MainActivityFragment fragment = new MainActivityFragment();

            mFragmentManager.beginTransaction()
                    .add(R.id.fragment, fragment)
                    .commit();
        }
    }
}
