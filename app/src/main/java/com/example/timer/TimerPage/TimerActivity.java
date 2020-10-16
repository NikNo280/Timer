package com.example.timer.TimerPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.timer.MainViewModel;
import com.example.timer.R;
import com.example.timer.Timer;

import java.util.List;

public class TimerActivity extends AppCompatActivity {

    private Timer timer;
    private ListView listView;
    private TextView textView_timer;
    private List<String> timerList;
    private ArrayAdapter<String> listViewAdapter;
    public MainViewModel mainViewModel ;
    Button btnStart, btnStop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        mainViewModel  = ViewModelProviders.of(this).get(MainViewModel.class);


        this.listView = (ListView) findViewById(R.id.listView);
        Intent intent = this.getIntent();
        this.timer = (Timer) intent.getSerializableExtra("timer");
        timerList = mainViewModel.getTimerList(timer);
        this.btnStart = findViewById(R.id.button_start);
        this.textView_timer = findViewById(R.id.textView_timer);
        this.btnStop = findViewById(R.id.button_stop);
        this.listViewAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, this.timerList);
        // Assign adapter to ListView
        mainViewModel.getEditTimer().observe(this, value -> textView_timer.setText(value));
        this.listView.setAdapter(this.listViewAdapter);
        btnStart.setOnClickListener(item -> mainViewModel.startTimer());
        btnStop.setOnClickListener(item -> mainViewModel.stopTimer());
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                mainViewModel.setNewTimerPosition(position);
            }
        });
    }
}