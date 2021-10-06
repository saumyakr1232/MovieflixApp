package com.example.movie2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.movie2.Adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private Utils utils;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private ViewPagerAdapter adapter;


    private boolean isBound = false;
    //private SampleService mService;

//    private ServiceConnection serviceConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            SampleService.LocalBinder binder =
//                    (SampleService.LocalBinder) service;
//            mService = binder.getBinder();
//
//            isBound = true;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            isBound = false;
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        utils = new Utils(this);
        initView();

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), 3);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        initBottomNavigation();

    }

    public void toWatchListFragment() {
        tabLayout.selectTab(tabLayout.getTabAt(2));
    }

    @Override
    protected void onStart() {
        super.onStart();

//        Intent intent = new Intent(this, SampleService.class);
//        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    private void initBottomNavigation() {
        Log.d(TAG, "initBottomNavigation: created");
        bottomNavigationView.setSelectedItemId(R.id.homeActivity);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search:
                        Toast.makeText(HomeActivity.this, "search clicked", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(HomeActivity.this, SearchActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.myList:
                        Toast.makeText(HomeActivity.this, "my list", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomeActivity.this, WantToWatch.class);
                        startActivity(intent);

                        break;
                    case R.id.homeActivity:
                        Toast.makeText(HomeActivity.this, "home clicked", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }


    private void initView() {
        Log.d(TAG, "initView: started");
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }


    @Override
    protected void onStop() {
        super.onStop();

        if (isBound) {
            //unbindService(serviceConnection);
        }
    }
}
