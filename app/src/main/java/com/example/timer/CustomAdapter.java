package com.example.timer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.timer.R;
import com.example.timer.Model.Timer;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Timer> {

    private LayoutInflater inflater;
    private int layout;
    private List<Timer> timers;

    public CustomAdapter(Context context, int resource, List<Timer> timers) {
        super(context, resource, timers);
        this.timers = timers;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        TextView name = (TextView) view.findViewById(R.id.textview_name_db);
        TextView preparation = (TextView) view.findViewById(R.id.textview_preparation_db);
        TextView warm = (TextView) view.findViewById(R.id.textview_warm_db);
        TextView work = (TextView) view.findViewById(R.id.textview_work_db);
        TextView relaxation = (TextView) view.findViewById(R.id.textview_relaxation_db);
        TextView cycle = (TextView) view.findViewById(R.id.textview_cycle_db);
        TextView set = (TextView) view.findViewById(R.id.textview_set_db);
        TextView pause = (TextView) view.findViewById(R.id.textview_pause_db);

        Timer timer = timers.get(position);

        name.setText(timer.getTimerName());
        preparation.setText(timer.getPreparationTime());
        warm.setText(timer.getWarmTime());
        work.setText(timer.getWorkTime());
        relaxation.setText(timer.getRelaxationTime());
        cycle.setText(timer.getCycleCount());
        set.setText(timer.getSetCount());
        pause.setText(timer.getPauseTime());
        return view;
    }
}
