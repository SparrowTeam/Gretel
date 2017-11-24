package com.tech.sparrow.gretel;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
        mCardReader = new CardReader();
        if (nfc != null) {
            nfc.enableReaderMode(this, mCardReader, READER_FLAGS, null);
        }
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            resolveIntent(intent);
        }
    }

    private void resolveIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag != null) {
            byte[] tagId = tag.getId();
            String tagIdStr = String.format("%02X %02X %02X %02X",
                tagId[0],
                tagId[1],
                tagId[2],
                tagId[3]
            );
            Log.i(TAG, "ID: " + tagIdStr);
            setContentView(R.layout.activity_main);
            TextView markTextView = findViewById(R.id.markIDtextView);
            markTextView.setText(tagIdStr);
        }
    }

    public void handleClickMap(View view) {
        Intent i = new Intent(getBaseContext(), MapsActivity.class);
        startActivity(i);
    }

    public void handleClickLogin(View view) {
        Intent i = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(i);
    }
}
