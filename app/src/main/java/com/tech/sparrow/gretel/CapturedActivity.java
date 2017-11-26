package com.tech.sparrow.gretel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CapturedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captured);
        String s = getIntent().getStringExtra("username");

        TextView nameView = findViewById(R.id.name_lbl3);
        nameView.setText(s);
    }
}
