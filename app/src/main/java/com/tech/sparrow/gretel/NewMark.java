package com.tech.sparrow.gretel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NewMark extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mark);

        String s = getIntent().getStringExtra("EXTRA_TAG_ID");

        TextView idView = findViewById(R.id.markID_lbl);
        idView.append(s);
    }
}
