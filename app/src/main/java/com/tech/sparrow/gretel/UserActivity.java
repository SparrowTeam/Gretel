package com.tech.sparrow.gretel;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.provider.MediaStore;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.SortedList;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==3 && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            String filePath = getRealPathFromURIPath(selectedImage, this);
            File file = new File(filePath);

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");

            retrofit2.Call<okhttp3.ResponseBody> req = App.getApi().postImage(App.loadToken(), body, name);
            req.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.d(TAG, "Response: " + response.toString());
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    public void handleTagId(final String tagId) {
        Intent i = new Intent(getBaseContext(), NewMark.class);
        i.putExtra("EXTRA_TAG_ID", tagId);
        startActivity(i);
    }

    public void showUserTags(View v)
    {
        Call<UserInfo> req = App.getApi().info(App.loadToken());
        req.enqueue(new Callback<UserInfo>() {
                @Override
                public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                    Log.d(TAG, "Response: " + response.toString());
                    final UserInfo info = response.body();
                    Call<List<MarkInfo>> req_mark_info = App.getApi().listMarksByUserId(App.loadToken(), "");
                    req_mark_info.enqueue(new Callback<List<MarkInfo>>() {
                        @Override
                        public void onResponse(Call<List<MarkInfo>> call, Response<List<MarkInfo>> response) {
                            Log.d(TAG, "Response: " + response.toString());
                            List<MarkInfo> marks = response.body();

                            Intent map_user_tags_activity_intent = new Intent(UserActivity.this, MapUserTagsActivity.class);
                            map_user_tags_activity_intent.putExtra("info", info);
                            map_user_tags_activity_intent.putExtra("marks",new ArrayList(marks));
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
}
