package com.example.timer.TimerPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.timer.DatabaseHelper;
import com.example.timer.MainViewModel;
import com.example.timer.R;
import com.example.timer.Timer;

import java.util.ArrayList;
import java.util.List;

public class TimerActivity extends AppCompatActivity {

    private Timer timer;
    private ListView listView;
    private List<String> timerList;
    private ArrayAdapter<String> listViewAdapter;
    public MainViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        this.listView = (ListView) findViewById(R.id.listView);
        Intent intent = this.getIntent();
        this.timer = (Timer) intent.getSerializableExtra("timer");
        timerList = viewModel.getListToAdapter(timer);
        this.listViewAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, this.timerList);
        // Assign adapter to ListView
        this.listView.setAdapter(this.listViewAdapter);
    }
}