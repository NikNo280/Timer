package com.example.timer.TimerPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.timer.ViewModel.MainViewModel;
import com.example.timer.R;
import com.example.timer.Service.TimerService;
import com.example.timer.Model.Timer;

import java.util.List;

public class TimerActivity extends AppCompatActivity {

    private Timer timer;
    private ListView listView;
    private TextView textView_timer;
    private List<String> timerList;
    private ArrayAdapter<String> listViewAdapter;
    public MainViewModel mainViewModel ;
    Button btnStart, btnStop;
    Intent intentService;

    BroadcastReceiver br;
    public final static String BROADCAST_ACTION = "ru.startandroid.develop.p0961servicebackbroadcast";
    public final static String PARAM_RESULT = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        mainViewModel  = ViewModelProviders.of(this).get(MainViewModel.class);
        this.listView = (ListView) findViewById(R.id.listView);
        Intent intent = this.getIntent();
        this.timer = (Timer) intent.getSerializableExtra("timer");
        listView.setBackgroundColor(Color.parseColor(timer.getColor()));
        timerList = mainViewModel.getTimerStringList(timer);
        this.btnStart = findViewById(R.id.button_start);
        this.textView_timer = findViewById(R.id.textView_timer);
        this.btnStop = findViewById(R.id.button_stop);
        this.listViewAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, this.timerList);
        this.listView.setAdapter(this.listViewAdapter);

        intentService = new Intent(this, TimerService.class).putExtra("list", mainViewModel.getIntList());

        btnStart.setOnClickListener(item -> {
            intentService.putExtra("operationCode", 1);
            startService(intentService);
        });

        btnStop.setOnClickListener(item -> {
            intentService.putExtra("operationCode", 2);
            startService(intentService);
        });

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                intentService.putExtra("operationCode", 3).putExtra("position", position);
                startService(intentService);
            }
        });
        br = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                int result = intent.getIntExtra(PARAM_RESULT, 0);
                textView_timer.setText("" + result);
            }
        };
        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(br, intFilt);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);
        stopService(intentService);
    }
}