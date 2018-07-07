package com.seismosoft.pentaclock.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        formatter1 = new SimpleDateFormat("hh:mm:ss a,\n dd-MM-yyyy");
        formatter2 = new SimpleDateFormat("hh:mm:ss a,\n dd-MM-yyyy");
        formatter3 = new SimpleDateFormat("hh:mm:ss a,\n dd-MM-yyyy");
        formatter4 = new SimpleDateFormat("hh:mm:ss a,\n dd-MM-yyyy");
        formatter5 = new SimpleDateFormat("hh:mm:ss a,\n dd-MM-yyyy");

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

    public void selectTimezone(View view) {
        Intent intent = new Intent(this, TimezoneSelectorActivity.class);

        switch (view.getId()) {
            case R.id.button1: {
                startActivityForResult(intent, ACTIVITY_RESULT_TAG_TIMEZONE1_REQ_CODE);
                break;
            }

            case R.id.button2: {
                startActivityForResult(intent, ACTIVITY_RESULT_TAG_TIMEZONE2_REQ_CODE);
                break;
            }

            case R.id.button3: {
                startActivityForResult(intent, ACTIVITY_RESULT_TAG_TIMEZONE3_REQ_CODE);
                break;
            }

            case R.id.button4: {
                startActivityForResult(intent, ACTIVITY_RESULT_TAG_TIMEZONE4_REQ_CODE);
                break;
            }

            case R.id.button5: {
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