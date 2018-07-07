package com.seismosoft.pentaclock.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.seismosoft.dualclock.R;

import java.util.List;

public class TimezoneAdapter extends RecyclerView.Adapter<TimezoneAdapter.ViewHolder> {

    private List<String> timezone_list;
    private Activity timezone_activity;
    private int last_checked_pos = -1;

    public TimezoneAdapter(Activity mActivity, List<String> tz) {
        this.timezone_list = tz;
        this.timezone_activity = mActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.timezone_list_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TimezoneAdapter.ViewHolder holder, final int position) {
        holder.tz_textView.setText(timezone_list.get(position));

        if(last_checked_pos == position) {
            holder.tz_radioButton.setChecked(true);
        } else {
            holder.tz_radioButton.setChecked(false);
        }
        holder.tz_radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tz_radioButton.setChecked(true);
                last_checked_pos = position;

                Toast.makeText(timezone_activity.getApplicationContext(), "Selected: " + timezone_list.get(position), Toast.LENGTH_SHORT).show();

                LayoutInflater inflater = timezone_activity.getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.label_popup, null);
                final EditText clock_label =  (EditText) alertLayout.findViewById(R.id.label);
                clock_label.setHint(timezone_list.get(position));

                final AlertDialog.Builder alert = new AlertDialog.Builder(timezone_activity);
                alert.setView(alertLayout);
                alert.setCancelable(false);
                alert.setTitle("Enter a Label");

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                        public void onClick(DialogInterface dialog, int which) {
                        holder.tz_radioButton.setChecked(false);
                    }
                });
                alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent =  new Intent();
                                Bundle mBundle = new Bundle();
                                mBundle.putString("TIMEZONE", timezone_list.get(position));
                                if(clock_label.getText().toString().isEmpty()) {
                                    mBundle.putString("LABEL", timezone_list.get(position));
                                } else {
                                    mBundle.putString("LABEL", clock_label.getText().toString());
                                }
                                intent.putExtras(mBundle);
                                timezone_activity.setResult(Activity.RESULT_OK, intent);
                                timezone_activity.finish();
                            }
                });

                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return timezone_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tz_textView;
        public RadioButton tz_radioButton;

        public ViewHolder(View itemView) {
            super(itemView);
            tz_textView = (TextView) itemView.findViewById(R.id.tz_text);
            tz_radioButton = (RadioButton) itemView.findViewById(R.id.tz_radio);
        }
    }
}

