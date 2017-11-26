package com.tech.sparrow.gretel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tech.sparrow.gretel.API.models.response.MarkDetailedInfo;

/**
 * Created by Denis on 26.11.2017.
 */

public class MarkInfoActivity  extends AppCompatActivity {
    public static final String TAG = "MarkInfoActivity";

    private MarkDetailedInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markinfo);

        info = (MarkDetailedInfo) getIntent().getSerializableExtra("info");
        System.out.println(info);
    }


}
