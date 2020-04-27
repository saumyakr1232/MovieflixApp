package com.example.movie2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "HomeActivity";
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FrameLayout frameLayout;

    private androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.drawer_open, R.string.drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new MainFragment());
        transaction.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //TODO: write proper logic for different situations
            case R.id.myList:
                Toast.makeText(this, "myList Selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, WantToWatch.class);
                startActivity(intent);
                break;
            case R.id.genre:
                Toast.makeText(this, "genre selected", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(HomeActivity.this, GenreItemsActivity.class);
                startActivity(intent1);
                break;
            case R.id.about:
                Toast.makeText(this, "about Selected", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("About My Library App")
                        .setMessage("Build and published by lol\n" +
                                "\n" +
                                "If you want to hire me or \n" +
                                "if you want to check my other works\n" +
                                "take a look at:\n" +
                                "lol.org")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(HomeActivity.this, AboutWebActivity.class);
                                intent.putExtra("url", "http://www.youtube.com");
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create().show();
                break;
            case R.id.terms:
                Toast.makeText(this, "terms Selected", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this)
                        .setTitle("Terms")
                        .setMessage("No Terms\n" +
                                "\n" +
                                "are you really interested in  \n" +
                                "Reading Terms \n"
                        )
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder2.create().show();
                break;
            case R.id.licenses:
                Toast.makeText(this, "licenses Selected", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder3 = new AlertDialog.Builder(this)
                        .setTitle("Licenses")
                        .setMessage("No licenses\n")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder3.create().show();
                break;
            case R.id.map:
                Toast.makeText(this, "map selected", Toast.LENGTH_SHORT).show();

            default:
                break;
        }
        return false;
    }

    private void initView() {
        Log.d(TAG, "initView: started");
        toolbar = findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigationDrawer);

    }
}
