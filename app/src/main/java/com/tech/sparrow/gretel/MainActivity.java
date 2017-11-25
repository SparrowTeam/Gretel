package com.tech.sparrow.gretel;

import android.app.Fragment;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    public static final String TAG = "MainActivity";
    public CardReader mCardReader;
    public static int READER_FLAGS =
            NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(this);

        mCardReader = new CardReader(this);
        if (nfc != null) {
            nfc.enableReaderMode(this, mCardReader, READER_FLAGS, null);
        }
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.login) {
            handleClickLogin();
        }

        if (id == R.id.world) {
            handleClickMap();
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public void handleClickMap() {
        Intent i = new Intent(getBaseContext(), MapsActivity.class);
        startActivity(i);
    }

    public void handleClickLogin() {
        Intent i = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(i);
    }

    public void handleTagId(final String tagId) {
        final TextView markTextView = findViewById(R.id.markIDtextView);
        markTextView.post(new Runnable() {
            public void run() {
                markTextView.setText("ID\n" + tagId);
            }
        });
    }
}





