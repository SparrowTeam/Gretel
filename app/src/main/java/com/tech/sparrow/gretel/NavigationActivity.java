package com.tech.sparrow.gretel;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.tech.sparrow.gretel.API.models.response.MarkDetailedInfo;
import com.tech.sparrow.gretel.API.models.response.MarkInfo;
import com.tech.sparrow.gretel.API.models.response.UserInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "NavigationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        try {
            getActionBar().setTitle("O_o");
            getSupportActionBar().setTitle("O_o");
        } catch (NullPointerException ex) {
            Log.e(TAG, "No action bar!");
        }

        // redirect if we have token
        Menu nav_Menu = navigationView.getMenu();
        Boolean stay = getIntent().getBooleanExtra("stay", false);
        if (!stay) {
            String token = App.loadToken();
            if (token != null && !token.isEmpty()) {
                handleRedirectUser();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        String token = App.loadToken();
        if(token != null && !token.isEmpty()){
            // user authorized
            nav_Menu.findItem(R.id.nav_login).setVisible(false);
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);
            nav_Menu.findItem(R.id.nav_profile).setVisible(true);
        }
        else {
            // user unauthorized
            nav_Menu.findItem(R.id.nav_login).setVisible(true);
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
            nav_Menu.findItem(R.id.nav_profile).setVisible(false);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            handleClickLogin();
        } else if(id == R.id.nav_logout){
            handleClickLogout();
        } else if (id == R.id.nav_world) {
            handleClickMap();
        } else if (id == R.id.nav_profile){
            handleRedirectUser();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void handleClickMap() {

        Call<UserInfo> req = App.getApi().info(App.loadToken());
        req.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                Log.d(TAG, "Response: " + response.toString());
                final UserInfo info = response.body();
                Call<List<MarkDetailedInfo>> req_mark_info = App.getApi().listMarks(App.loadToken());
                req_mark_info.enqueue(new Callback<List<MarkDetailedInfo>>() {
                    @Override
                    public void onResponse(Call<List<MarkDetailedInfo>> call, Response<List<MarkDetailedInfo>> response) {
                        Log.d(TAG, "Response: " + response.toString());
                        List<MarkDetailedInfo> marks;
                        if (response.body() == null) marks = new ArrayList<>();
                        else marks = response.body();

                        Intent map_user_tags_activity_intent = new Intent(NavigationActivity.this, MapWorldTagsActivity.class);
                        map_user_tags_activity_intent.putExtra("info", info);
                        map_user_tags_activity_intent.putExtra("marks", new ArrayList(marks));
                        startActivity(map_user_tags_activity_intent);
                    }

                    @Override
                    public void onFailure(Call<List<MarkDetailedInfo>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void handleClickLogin() {
        Intent i = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(i);
    }

    public void handleClickLogout() {
        App.saveToken(null);
        recreate(); // restart activity (SDK >= 11)
    }

    public void handleRedirectUser(){
        Intent i = new Intent(getBaseContext(), UserActivity.class);
        startActivity(i);
    }

}
