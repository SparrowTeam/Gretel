package com.tech.sparrow.gretel;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public CardReader mCardReader;
    public static int READER_FLAGS =
            NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(this);

        mCardReader = new CardReader(this);
        if (nfc != null) {
            nfc.enableReaderMode(this, mCardReader, READER_FLAGS, null);
        }
        setContentView(R.layout.activity_main);
    }

    public void handleClickMap(View view) {
        Intent i = new Intent(getBaseContext(), MapsActivity.class);
        startActivity(i);
    }

    public void handleClickLogin(View view) {
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
