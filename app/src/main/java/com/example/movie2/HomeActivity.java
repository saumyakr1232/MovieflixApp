package com.example.movie2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

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
    private SampleService mService;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SampleService.LocalBinder binder =
                    (SampleService.LocalBinder) service;
            mService = binder.getBinder();

            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        utils = new Utils(this);
        initView();

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), 3);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this, SampleService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

    }


//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.myList:
//                Toast.makeText(this, "myList Selected", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(HomeActivity.this, WantToWatch.class);
//                startActivity(intent);
//                break;
//            case R.id.genre:
//                Toast.makeText(this, "genre selected", Toast.LENGTH_SHORT).show();
//                Intent intent1 = new Intent(HomeActivity.this, GenreItemsActivity.class);
//                startActivity(intent1);
//                break;
//            case R.id.about:
//                Toast.makeText(this, "about Selected", Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder builder = new AlertDialog.Builder(this)
//                        .setTitle("About My Library App")
//                        .setMessage("Build and published by lol\n" +
//                                "\n" +
//                                "If you want to hire me or \n" +
//                                "if you want to check my other works\n" +
//                                "take a look at:\n" +
//                                "lol.org")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent = new Intent(HomeActivity.this, AboutWebActivity.class);
//                                intent.putExtra("url", "http://www.youtube.com");
//                                startActivity(intent);
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        });
//                builder.create().show();
//                break;
//            case R.id.terms:
//                Toast.makeText(this, "terms Selected", Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder builder2 = new AlertDialog.Builder(this)
//                        .setTitle("Terms")
//                        .setMessage("No Terms\n" +
//                                "\n" +
//                                "are you really interested in  \n" +
//                                "Reading Terms \n"
//                        )
//                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        });
//                builder2.create().show();
//                break;
//            case R.id.licenses:
//                Toast.makeText(this, "licenses Selected", Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder builder3 = new AlertDialog.Builder(this)
//                        .setTitle("Licenses")
//                        .setMessage("No licenses\n")
//                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        });
//                builder3.create().show();
//                break;
//            case R.id.map:
//                Toast.makeText(this, "map selected", Toast.LENGTH_SHORT).show();
//                Intent intent4 = new Intent(HomeActivity.this, MapsActivity.class);
//                startActivity(intent4);
//                break;
//
//            case R.id.logOut:
//                Intent intent3 = new Intent(HomeActivity.this, LoginActivity.class);
//                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent3);
//                utils.setSignedIn(false);
//            default:
//                break;
//        }
//        return false;
//    }

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
            unbindService(serviceConnection);
        }
    }
}
