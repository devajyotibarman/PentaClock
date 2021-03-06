package com.seismosoft.pentaclock.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
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

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clockTextView1 = findViewById(R.id.clock1);
        clockTextView2 = findViewById(R.id.clock2);
        clockTextView3 = findViewById(R.id.clock3);
        clockTextView4 = findViewById(R.id.clock4);
        clockTextView5 = findViewById(R.id.clock5);

        clockzoneTextView1 = findViewById(R.id.clockzone1);
        clockzoneTextView2 = findViewById(R.id.clockzone2);
        clockzoneTextView3 = findViewById(R.id.clockzone3);
        clockzoneTextView4 = findViewById(R.id.clockzone4);
        clockzoneTextView5 = findViewById(R.id.clockzone5);

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
        if (!tinydb.getBoolean("NOTFIRSTRUN")) {
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
            tinydb.putInt("CLOCKCOLORDB1", 0xFFe51c23);
            tinydb.putInt("CLOCKCOLORDB2", 0xFFe51c23);
            tinydb.putInt("CLOCKCOLORDB3", 0xFFe51c23);
            tinydb.putInt("CLOCKCOLORDB4", 0xFFe51c23);
            tinydb.putInt("CLOCKCOLORDB5", 0xFFe51c23);
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

        clockzoneTextView1.setTextColor(tinydb.getInt("CLOCKCOLORDB1"));
        clockzoneTextView2.setTextColor(tinydb.getInt("CLOCKCOLORDB2"));
        clockzoneTextView3.setTextColor(tinydb.getInt("CLOCKCOLORDB3"));
        clockzoneTextView4.setTextColor(tinydb.getInt("CLOCKCOLORDB4"));
        clockzoneTextView5.setTextColor(tinydb.getInt("CLOCKCOLORDB5"));

        clockTextView1.setTextColor(tinydb.getInt("CLOCKCOLORDB1"));
        clockTextView2.setTextColor(tinydb.getInt("CLOCKCOLORDB2"));
        clockTextView3.setTextColor(tinydb.getInt("CLOCKCOLORDB3"));
        clockTextView4.setTextColor(tinydb.getInt("CLOCKCOLORDB4"));
        clockTextView5.setTextColor(tinydb.getInt("CLOCKCOLORDB5"));


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
        assert extras != null;
        String message = extras.getString("TIMEZONE");
        String label = extras.getString("LABEL");
        int color = extras.getInt("COLOR");

        // check if the request code is same as what is passed  here it is 2
        switch (requestCode) {
            case ACTIVITY_RESULT_TAG_TIMEZONE1_REQ_CODE: {
                formatter1.setTimeZone(TimeZone.getTimeZone(message));
                clockzoneTextView1.setText(label);
                clockzoneTextView1.setTextColor(color);
                clockTextView1.setTextColor(color);
                tinydb.putString("CLOCKTZDB1", message);
                tinydb.putString("CLOCKLABELDB1", label);
                tinydb.putInt("CLOCKCOLORDB1", color);
                break;
            }

            case ACTIVITY_RESULT_TAG_TIMEZONE2_REQ_CODE:{
                formatter2.setTimeZone(TimeZone.getTimeZone(message));
                clockzoneTextView2.setText(label);
                clockzoneTextView2.setTextColor(color);
                clockTextView2.setTextColor(color);
                tinydb.putString("CLOCKTZDB2", message);
                tinydb.putString("CLOCKLABELDB2", label);
                tinydb.putInt("CLOCKCOLORDB2", color);
                break;
            }

            case ACTIVITY_RESULT_TAG_TIMEZONE3_REQ_CODE: {
                formatter3.setTimeZone(TimeZone.getTimeZone(message));
                clockzoneTextView3.setText(label);
                clockzoneTextView3.setTextColor(color);
                clockTextView3.setTextColor(color);
                tinydb.putString("CLOCKTZDB3", message);
                tinydb.putString("CLOCKLABELDB3", label);
                tinydb.putInt("CLOCKCOLORDB3", color);
                break;
            }

            case ACTIVITY_RESULT_TAG_TIMEZONE4_REQ_CODE: {
                formatter4.setTimeZone(TimeZone.getTimeZone(message));
                clockzoneTextView4.setText(label);
                clockzoneTextView4.setTextColor(color);
                clockTextView4.setTextColor(color);
                tinydb.putString("CLOCKTZDB4", message);
                tinydb.putString("CLOCKLABELDB4", label);
                tinydb.putInt("CLOCKCOLORDB4", color);
                break;
            }

            case ACTIVITY_RESULT_TAG_TIMEZONE5_REQ_CODE: {
                formatter5.setTimeZone(TimeZone.getTimeZone(message));
                clockzoneTextView5.setText(label);
                clockzoneTextView5.setTextColor(color);
                clockTextView5.setTextColor(color);
                tinydb.putString("CLOCKTZDB5", message);
                tinydb.putString("CLOCKLABELDB5", label);
                tinydb.putInt("CLOCKCOLORDB5", color);
                break;
            }

            default: {
            }
        }
    }
}