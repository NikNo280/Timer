package com.example.timer;

import android.app.Application;

import android.os.CountDownTimer;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private final MutableLiveData<String> editTimer = new MutableLiveData<>("");
    private CountDownTimer countDownTimer;
    public MainViewModel(@NonNull Application application) {
        super(application);
    }
    private int position = 0;
    private int timerBuffer;
    private boolean isTimerStart = false;
    private boolean isTimerStop = false;
    List<Integer> timerList = new ArrayList<Integer>();

    public LiveData<String> getEditTimer()
    {
        return editTimer;
    }

    public void setNewTimerPosition(int position) {
        if (isTimerStart) {
            countDownTimer.cancel();
            isTimerStop = false;
        }
        this.position = position;
        startTimer();
    }

    public void stopTimer() {
        if(isTimerStart || !editTimer.getValue().equals("Все"))
        {
            timerBuffer = Integer.parseInt(editTimer.getValue()) * 1000;
            countDownTimer.cancel();
            isTimerStop = true;
        }
        else
        {
            Toast toast = Toast.makeText(getApplication(), "Timer not started", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void startTimer()
    {
        int second;
        if(isTimerStop)
        {
            second = timerBuffer;
            isTimerStop = false;
        }
        else
        {
            second = timerList.get(position);
        }
        isTimerStart = true;
        countDownTimer = new CountDownTimer(second, 100) {
            @Override
            public void onTick(long l) {
                editTimer.setValue("" + l / 1000);
            }

            @Override
            public void onFinish() {
                if(timerList.size() - 1> position)
                {
                    position++;
                    countDownTimer.cancel();
                    startTimer();
                }
                else
                {
                    position = 0;
                    editTimer.setValue("Все");
                }
            }
        }.start();
    }

    public List<String> getTimerList(Timer timer)
    {
        List<String> stringTimerList = new ArrayList<String>();
        int countCycle = Integer.parseInt(timer.getCycleCount());
        int countSet = Integer.parseInt(timer.getSetCount());
        int count = 1;
        for(int i = 0; i < countSet; i++)
        {
            stringTimerList.add(count + ". Preparation : " + timer.getPreparationTime());
            timerList.add(Integer.parseInt(timer.getPreparationTime()) * 1000);
            count++;
            for(int j = 0; j < countCycle; j++)
            {
                stringTimerList.add(count + ". Warm : " + timer.getWarmTime());
                timerList.add(Integer.parseInt(timer.getWarmTime()) * 1000);
                count++;
                stringTimerList.add(count + ". Work : " + timer.getWorkTime());
                timerList.add(Integer.parseInt(timer.getWorkTime()) * 1000);
                count++;
                stringTimerList.add(count + ". Relaxation : " + timer.getRelaxationTime());
                timerList.add(Integer.parseInt(timer.getRelaxationTime()) * 1000);
                count++;
            }
            stringTimerList.add(count + ". PauseTime : " + timer.getPauseTime());
            timerList.add(Integer.parseInt(timer.getPauseTime()) * 1000);
            count++;
        }
        return stringTimerList;
    }
}