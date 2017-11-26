package com.tech.sparrow.gretel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
    }

    public void onBackClick(View view) {
        Intent i = new Intent(getBaseContext(), UserActivity.class);
        startActivity(i);
    }
}
