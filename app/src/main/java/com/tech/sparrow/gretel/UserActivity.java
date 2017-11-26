package com.tech.sparrow.gretel;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.SortedList;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tech.sparrow.gretel.API.APIError;
import com.tech.sparrow.gretel.API.ErrorUtils;
import com.tech.sparrow.gretel.API.models.response.MarkDetailedInfo;
import com.tech.sparrow.gretel.API.models.response.MarkInfo;
import com.tech.sparrow.gretel.API.models.response.UserInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.view.View;
import android.widget.Toast;

import com.tech.sparrow.gretel.API.APIError;
import com.tech.sparrow.gretel.API.ErrorUtils;
import com.tech.sparrow.gretel.API.models.response.ImageInfo;
import com.tech.sparrow.gretel.API.models.response.MarkInfo;
import com.tech.sparrow.gretel.API.models.response.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

public class UserActivity extends AppCompatActivity {
    public static final String TAG = "UserActivity";
    public CardReader mCardReader;
    public static int READER_FLAGS =
            NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(this);

        mCardReader = new CardReader(this);
        if (nfc != null) {
            nfc.enableReaderMode(this, mCardReader, READER_FLAGS, null);
        }
    }

    public void onImageClick(View view) {
        startActivityForResult(
                new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI
                ),
                3
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==3 && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            final String filePath = App.getRealPathFromURIPath(selectedImage, this);
            File file = new File(filePath);

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");

            retrofit2.Call<ImageInfo> req = App.getApi().postUserPhoto(App.loadToken(), body, name);
            req.enqueue(new Callback<ImageInfo>() {
                @Override
                public void onResponse(Call<ImageInfo> call, Response<ImageInfo> response) {
                    Log.d(TAG, "Response: " + response.toString());
                    int code = response.code();
                    switch (code){
                        case 200:
                            // photo uploaded
                            //getApplicationContext().
                            //findViewById(android.view.View.);
                            break;
                    }
                }

                @Override
                public void onFailure(Call<ImageInfo> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    public void handleTagId(final String tagId) {
        final String tagIdWithoutSpaces = tagId.replace(" ","");
        Call<ResponseBody> call = App.getApi().getMarkStatus(App.loadToken(), tagIdWithoutSpaces);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                int code = response.code();
                switch (code){
                    case 200:
                        // mark is already known (another team)
                        //Toast t = Toast.makeText(getApplicationContext(), "You successfully conquered the mark!\nCongratulations!", Toast.LENGTH_LONG);
                        //TextView v = (TextView) t.getView().findViewById(android.R.id.message);
                        //if( v != null) v.setGravity(Gravity.CENTER);
                        //t.show();

                        Call<MarkDetailedInfo> call2 = App.getApi().getMarkInfo(App.loadToken(), tagId.replace(" ",""));

                        call2.enqueue(new Callback<MarkDetailedInfo>() {
                            @Override
                            public void onResponse(Call<MarkDetailedInfo> call, Response<MarkDetailedInfo> response2) {
                                Log.d(TAG, "Response: " + response2.code());
                                Intent i = new Intent(UserActivity.this, CapturedActivity.class);
                                MarkDetailedInfo info = response2.body();
                                MarkDetailedInfo.User user = info.getUsers().get(0);
                                i.putExtra("username", user.getName());
                                startActivity(i);
                            }
                            @Override
                            public void onFailure(Call<MarkDetailedInfo> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Connection failure", Toast.LENGTH_LONG).show();
                            }
                        });

                        break;
                    case 202:
                        // new mark
                        Intent i = new Intent(getBaseContext(), NewMark.class);
                        i.putExtra("EXTRA_TAG_ID", tagId);
                        startActivity(i);
                        break;
                    case 403:
                        // mark of your team
                        Toast t2 = Toast.makeText(getApplicationContext(), "This is the mark of your team.\nYou can't takeover it.", Toast.LENGTH_LONG);
                        TextView v2 = (TextView) t2.getView().findViewById(android.R.id.message);
                        if( v2 != null) v2.setGravity(Gravity.CENTER);
                        t2.show();
                        //displayMarkInfo(tagIdWithoutSpaces);
                        break;
                    case 201:
                        // mark of your team
                        Toast t3 = Toast.makeText(getApplicationContext(), "This is your mark.\nYou should be proud of it.", Toast.LENGTH_LONG);
                        TextView v3 = (TextView) t3.getView().findViewById(android.R.id.message);
                        if( v3 != null) v3.setGravity(Gravity.CENTER);
                        t3.show();
                        //displayMarkInfo(tagIdWithoutSpaces);
                        break;
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Connection failure", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void displayMarkInfo(String tag){
        Call<MarkDetailedInfo> call = App.getApi().getMarkInfo(App.loadToken(), tag);
        call.enqueue(new Callback<MarkDetailedInfo>() {
            @Override
            public void onResponse(Call<MarkDetailedInfo> call, Response<MarkDetailedInfo> response) {
                if(response.isSuccessful()){
                    MarkDetailedInfo info = response.body();
                    handleMarkInfoActivity(info);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Can't get mark info", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<MarkDetailedInfo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Connection failure", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showUserTags(View v)
    {
        Call<UserInfo> req = App.getApi().info(App.loadToken());
        req.enqueue(new Callback<UserInfo>() {
                @Override
                public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                    Log.d(TAG, "Response: " + response.toString());
                    final UserInfo info = response.body();
                    Call<List<MarkInfo>> req_mark_info = App.getApi().listMarksByUser(App.loadToken());
                    req_mark_info.enqueue(new Callback<List<MarkInfo>>() {
                        @Override
                        public void onResponse(Call<List<MarkInfo>> call, Response<List<MarkInfo>> response) {
                            Log.d(TAG, "Response: " + response.toString());
                            List<MarkInfo> marks;
                            if (response.body() == null) marks = new ArrayList<>();
                            else marks = response.body();

                            Intent map_user_tags_activity_intent = new Intent(UserActivity.this, MapUserTagsActivity.class);
                            map_user_tags_activity_intent.putExtra("info", info);
                            map_user_tags_activity_intent.putExtra("marks", new ArrayList(marks));
                            startActivity(map_user_tags_activity_intent);
                        }

                        @Override
                        public void onFailure(Call<List<MarkInfo>> call, Throwable t) {
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

    public void handleMarkInfoActivity(MarkDetailedInfo markDetailedInfo){
        Intent map_user_tags_activity_intent = new Intent(UserActivity.this, MarkInfoActivity.class);
        map_user_tags_activity_intent.putExtra("info", markDetailedInfo);
        startActivity(map_user_tags_activity_intent);
    }
}
