package com.seismosoft.pentaclock.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.seismosoft.dualclock.R;
import com.seismosoft.pentaclock.adapter.TimezoneAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

public class TimezoneSelectorActivity extends AppCompatActivity {

    TimezoneAdapter tzAdapter;
    List<String> tz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timezone_selector);

        RecyclerView recyclerview = findViewById(R.id.timezone_list_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerview.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerview.getContext(), linearLayoutManager.getOrientation());
        recyclerview.addItemDecoration(dividerItemDecoration);

        String [] ids = TimeZone.getAvailableIDs();
        tz = Arrays.asList(ids);

        tzAdapter = new TimezoneAdapter(this, tz);
        recyclerview.setAdapter(tzAdapter);
    }

}
