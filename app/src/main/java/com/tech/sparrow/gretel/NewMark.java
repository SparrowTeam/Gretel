package com.tech.sparrow.gretel;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.tech.sparrow.gretel.API.models.request.LoginRequest;
import com.tech.sparrow.gretel.API.models.request.MarkRequest;
import com.tech.sparrow.gretel.API.models.response.Coordinates;
import com.tech.sparrow.gretel.API.models.response.ImageInfo;
import com.tech.sparrow.gretel.API.models.response.Token;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewMark extends AppCompatActivity {
    public static final String TAG = "NewMark";

    private FusedLocationProviderClient mFusedLocationClient;
    private static int ACCESS_FINE_LOCATION_REQ = 5;
    private static int SELECT_IMAGE_REQ = 6;

    private Integer imagesCount = 0;
    private ArrayList<ImageInfo> images;
    private Location lastLocation;
    private String tagId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mark);

        String s = getIntent().getStringExtra("EXTRA_TAG_ID");

        TextView idView = findViewById(R.id.markID_lbl);
        idView.append(s);
        this.tagId = s;

        Date currentTime = Calendar.getInstance().getTime();
        TextView dateView = findViewById(R.id.tagDate_lbl);
        dateView.setText(currentTime.toString());

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        setLocation();
        imagesCount = 0;

        images = new ArrayList<ImageInfo>();
    }

    private void setLocation() {
        if (ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(
                    this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    ACCESS_FINE_LOCATION_REQ
            );
        } else {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                EditText locationEdit = findViewById(R.id.locationEdit);
                                //locationEdit.setText(location.toString());
                                lastLocation = location;
                            }
                        }
                    });
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == ACCESS_FINE_LOCATION_REQ) {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    setLocation();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
        }

        // other 'case' lines to check for other
        // permissions this app might request
    }

    public void handleAddPhoto(View view) {
        startActivityForResult(
                new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI
                ),
                SELECT_IMAGE_REQ
        );
    }

    private void incrementImagesCount() {
        TextView imagesCountView = findViewById(R.id.photoCount_lbl);
        imagesCountView.setText(imagesCount.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==SELECT_IMAGE_REQ && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            String filePath = App.getRealPathFromURIPath(selectedImage, this);
            File file = new File(filePath);

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");

            retrofit2.Call<ImageInfo> req = App.getApi().postImage(App.loadToken(), body, name);
            req.enqueue(new Callback<ImageInfo>() {
                @Override
                public void onResponse(Call<ImageInfo> call, Response<ImageInfo> response) {
                    Log.d(TAG, "Response: " + response.toString());
                    if (response.code() == 200) {
                        imagesCount += 1;
                        incrementImagesCount();
                        ImageInfo info = response.body();
                        Log.d(TAG, "Response: " + info.getImageId());
                        images.add(info);
                    }
                }

                @Override
                public void onFailure(Call<ImageInfo> call, Throwable t) {
                    t.printStackTrace();
                    Toast t2 = Toast.makeText(getApplicationContext(), "Failed to upload photo", Toast.LENGTH_LONG);
                    TextView v2 = (TextView) t2.getView().findViewById(android.R.id.message);
                    if( v2 != null) v2.setGravity(Gravity.CENTER);
                    t2.show();
                }
            });
        }
    }

    public void handleSubmit(View view) {
        if (imagesCount == 0) {
            Toast t = Toast.makeText(getApplicationContext(), "You should upload at least one photo", Toast.LENGTH_LONG);
            TextView v = (TextView) t.getView().findViewById(android.R.id.message);
            if( v != null) v.setGravity(Gravity.CENTER);
            t.show();
        }
        EditText locationView = findViewById(R.id.locationEdit);
        String locationDesc = locationView.getText().toString();

        if (locationDesc.isEmpty()) {
            locationDesc = "No description provided";
        }

        Call<ResponseBody> call = App.getApi().postMark(
                App.loadToken(), this.tagId.replace(" ",""),
                new MarkRequest(
                locationDesc,
                new Coordinates(
                    String.valueOf(lastLocation.getLongitude()),
                    String.valueOf(lastLocation.getLatitude()),
                    String.valueOf(lastLocation.getAltitude()),
                    ""
                ),
                images)
        );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "Response: " + response.toString());
                Intent i = new Intent(getBaseContext(), SuccessActivity.class);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
