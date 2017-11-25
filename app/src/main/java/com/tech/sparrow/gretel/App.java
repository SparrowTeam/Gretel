package com.tech.sparrow.gretel;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;

import com.tech.sparrow.gretel.API.HanselService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Denis on 25.11.2017.
 */

public class App extends Application {

    private static String endpoint_url = "http://10.100.16.26:8080/";
    private static HanselService hanselService;
    private static Retrofit retrofit;

    private static SharedPreferences sPref;
    private static App application;
    private static String SAVED_TOKEN = "SAVED_TOKEN";

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl(endpoint_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        hanselService = retrofit.create(HanselService.class);
        application = this;
    }

    public static HanselService getApi() {
        return hanselService;
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static void saveToken(String token) {
        sPref = PreferenceManager.getDefaultSharedPreferences(application);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TOKEN, token);
        ed.commit();
    }

    public static String loadToken() {
        sPref = PreferenceManager.getDefaultSharedPreferences(application);
        return sPref.getString(SAVED_TOKEN, null);
    }

    public static String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
}
