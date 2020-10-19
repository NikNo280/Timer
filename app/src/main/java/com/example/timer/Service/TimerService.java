package com.example.timer.Service;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import com.example.timer.TimerPage.TimerActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimerService extends Service {

    final String LOG_TAG = "myLogs";
    int[] timerList;
    int position;
    int operationCode;
    CountDownTimer countDownTimer;

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "MyService onCreate");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "MyService onDestroy");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "MyService onStartCommand");
        timerList = intent.getIntArrayExtra("list");

        run();
        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void run() {
        countDownTimer = new CountDownTimer(timerList[position], 100) {
            @Override
            public void onTick(long l) {
                timerUpdate((int) l);
            }

            @Override
            public void onFinish() {
                if(timerList.length - 1> position)
                {
                    position++;
                    countDownTimer.cancel();
                    run();
                }
                else
                {
                    position = 0;
                    timerUpdate(0);
                }
            }
        }.start();
    }

    private void timerUpdate(int time){

        Intent intent = new Intent(TimerActivity.BROADCAST_ACTION);
        intent.putExtra(TimerActivity.PARAM_RESULT, time / 1000);
        sendBroadcast(intent);
    }
}
