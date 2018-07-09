package com.seismosoft.pentaclock.activity;

import android.Manifest;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.seismosoft.dualclock.R;
import com.seismosoft.pentaclock.db.TinyDB;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    TextView clockTextView1;
    TextView clockTextView2;
    TextView clockTextView3;
    TextView clockTextView4;
    TextView clockTextView5;

    TextView clockzoneTextView1;
    TextView clockzoneTextView2;
    TextView clockzoneTextView3;
    TextView clockzoneTextView4;
    TextView clockzoneTextView5;

    LinearLayout clock1_ll;
    LinearLayout clock2_ll;
    LinearLayout clock3_ll;
    LinearLayout clock4_ll;
    LinearLayout clock5_ll;

    Date currentTime;
    DateFormat formatter1;
    DateFormat formatter2;
    DateFormat formatter3;
    DateFormat formatter4;
    DateFormat formatter5;

    ScrollView main_ll;

    final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 200;

    final Handler handler = new Handler();
    final int ACTIVITY_RESULT_TAG_TIMEZONE1_REQ_CODE = 2001;
    final int ACTIVITY_RESULT_TAG_TIMEZONE2_REQ_CODE = 2002;
    final int ACTIVITY_RESULT_TAG_TIMEZONE3_REQ_CODE = 2003;
    final int ACTIVITY_RESULT_TAG_TIMEZONE4_REQ_CODE = 2004;
    final int ACTIVITY_RESULT_TAG_TIMEZONE5_REQ_CODE = 2005;

    TinyDB tinydb;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
        currentTime = Calendar.getInstance().getTime();

        clockTextView1.setText(formatter1.format(currentTime));
        clockTextView2.setText(formatter2.format(currentTime));
        clockTextView3.setText(formatter3.format(currentTime));
        clockTextView4.setText(formatter4.format(currentTime));
        clockTextView5.setText(formatter5.format(currentTime));

        handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_ll = findViewById(R.id.main_ll);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,  new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            final WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
            final Drawable wallpaperDrawable = wallpaperManager.getDrawable();
            main_ll.setBackground(wallpaperDrawable);
        }

        clockTextView1 = (TextView)findViewById(R.id.clock1);
        clockTextView2 = (TextView)findViewById(R.id.clock2);
        clockTextView3 = (TextView)findViewById(R.id.clock3);
        clockTextView4 = (TextView)findViewById(R.id.clock4);
        clockTextView5 = (TextView)findViewById(R.id.clock5);

        clockzoneTextView1 = (TextView)findViewById(R.id.clockzone1);
        clockzoneTextView2 = (TextView)findViewById(R.id.clockzone2);
        clockzoneTextView3 = (TextView)findViewById(R.id.clockzone3);
        clockzoneTextView4 = (TextView)findViewById(R.id.clockzone4);
        clockzoneTextView5 = (TextView)findViewById(R.id.clockzone5);

        clock1_ll = findViewById(R.id.clock_1_ll);
        clock2_ll = findViewById(R.id.clock_2_ll);
        clock3_ll = findViewById(R.id.clock_3_ll);
        clock4_ll = findViewById(R.id.clock_4_ll);
        clock5_ll = findViewById(R.id.clock_5_ll);

        clock1_ll.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selectTimezone(v);
                return true;
            }
        });

        clock2_ll.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selectTimezone(v);
                return true;
            }
        });

        clock3_ll.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selectTimezone(v);
                return true;
            }
        });

        clock4_ll.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selectTimezone(v);
                return true;
            }
        });

        clock5_ll.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selectTimezone(v);
                return true;
            }
        });

        formatter1 = new SimpleDateFormat("E, dd-MM-yyyy\nhh:mm:ss a");
        formatter2 = new SimpleDateFormat("E, dd-MM-yyyy\nhh:mm:ss a");
        formatter3 = new SimpleDateFormat("E, dd-MM-yyyy\nhh:mm:ss a");
        formatter4 = new SimpleDateFormat("E, dd-MM-yyyy\nhh:mm:ss a");
        formatter5 = new SimpleDateFormat("E, dd-MM-yyyy\nhh:mm:ss a");

        tinydb = new TinyDB(getApplicationContext());
        if (tinydb.getBoolean("NOTFIRSTRUN") == false) {
            tinydb.putBoolean("NOTFIRSTRUN", true);
            tinydb.putString("CLOCKTZDB1", "Asia/Kolkata");
            tinydb.putString("CLOCKTZDB2", "Australia/Sydney");
            tinydb.putString("CLOCKTZDB3", "PDT");
            tinydb.putString("CLOCKTZDB4", "Europe/London");
            tinydb.putString("CLOCKDTZB5", "Europe/Paris");
            tinydb.putString("CLOCKLABELDB1", "India");
            tinydb.putString("CLOCKLABELDB2", "Kempsey");
            tinydb.putString("CLOCKLABELDB3", "San Jose");
            tinydb.putString("CLOCKLABELDB4", "London");
            tinydb.putString("CLOCKLABELDB5", "Paris");
        }

        formatter1.setTimeZone(TimeZone.getTimeZone(tinydb.getString("CLOCKTZDB1")));
        formatter2.setTimeZone(TimeZone.getTimeZone(tinydb.getString("CLOCKTZDB2")));
        formatter3.setTimeZone(TimeZone.getTimeZone(tinydb.getString("CLOCKTZDB3")));
        formatter4.setTimeZone(TimeZone.getTimeZone(tinydb.getString("CLOCKTZDB4")));
        formatter5.setTimeZone(TimeZone.getTimeZone(tinydb.getString("CLOCKTZDB5")));

        clockzoneTextView1.setText(tinydb.getString("CLOCKLABELDB1"));
        clockzoneTextView2.setText(tinydb.getString("CLOCKLABELDB2"));
        clockzoneTextView3.setText(tinydb.getString("CLOCKLABELDB3"));
        clockzoneTextView4.setText(tinydb.getString("CLOCKLABELDB4"));
        clockzoneTextView5.setText(tinydb.getString("CLOCKLABELDB5"));


        clockThread();
    }


    private void clockThread() {
        handler.post(runnable);
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();

    }

    @Override
    protected void onResume() {
        handler.post(runnable);
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    final WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
                    final Drawable wallpaperDrawable = wallpaperManager.getDrawable();
                    main_ll.setBackground(wallpaperDrawable);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void selectTimezone(View view) {
        Intent intent = new Intent(this, TimezoneSelectorActivity.class);

        switch (view.getId()) {
            case R.id.clock_1_ll: {
                startActivityForResult(intent, ACTIVITY_RESULT_TAG_TIMEZONE1_REQ_CODE);
                break;
            }

            case R.id.clock_2_ll: {
                startActivityForResult(intent, ACTIVITY_RESULT_TAG_TIMEZONE2_REQ_CODE);
                break;
            }

            case R.id.clock_3_ll: {
                startActivityForResult(intent, ACTIVITY_RESULT_TAG_TIMEZONE3_REQ_CODE);
                break;
            }

            case R.id.clock_4_ll: {
                startActivityForResult(intent, ACTIVITY_RESULT_TAG_TIMEZONE4_REQ_CODE);
                break;
            }

            case R.id.clock_5_ll: {
                startActivityForResult(intent, ACTIVITY_RESULT_TAG_TIMEZONE5_REQ_CODE);
                break;
            }

            default: {

            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);

        if(resultCode != Activity.RESULT_OK) {
            return;
        }
        Bundle extras = intent.getExtras();
        String message = extras.getString("TIMEZONE");
        String label = extras.getString("LABEL");

        // check if the request code is same as what is passed  here it is 2
        switch (requestCode) {
            case ACTIVITY_RESULT_TAG_TIMEZONE1_REQ_CODE: {
                formatter1.setTimeZone(TimeZone.getTimeZone(message));
                clockzoneTextView1.setText(label);
                tinydb.putString("CLOCKTZDB1", message);
                tinydb.putString("CLOCKLABELDB1", label);
                break;
            }

            case ACTIVITY_RESULT_TAG_TIMEZONE2_REQ_CODE:{
                formatter2.setTimeZone(TimeZone.getTimeZone(message));
                clockzoneTextView2.setText(label);
                tinydb.putString("CLOCKTZDB2", message);
                tinydb.putString("CLOCKLABELDB2", label);
                break;
            }

            case ACTIVITY_RESULT_TAG_TIMEZONE3_REQ_CODE: {
                formatter3.setTimeZone(TimeZone.getTimeZone(message));
                clockzoneTextView3.setText(label);
                tinydb.putString("CLOCKTZDB3", message);
                tinydb.putString("CLOCKLABELDB3", label);
                break;
            }

            case ACTIVITY_RESULT_TAG_TIMEZONE4_REQ_CODE: {
                formatter4.setTimeZone(TimeZone.getTimeZone(message));
                clockzoneTextView4.setText(label);
                tinydb.putString("CLOCKTZDB4", message);
                tinydb.putString("CLOCKLABELDB4", label);
                break;
            }

            case ACTIVITY_RESULT_TAG_TIMEZONE5_REQ_CODE: {
                formatter5.setTimeZone(TimeZone.getTimeZone(message));
                clockzoneTextView5.setText(label);
                tinydb.putString("CLOCKTZDB5", message);
                tinydb.putString("CLOCKLABELDB5", label);
                break;
            }

            default: {
            }
        }
    }
}