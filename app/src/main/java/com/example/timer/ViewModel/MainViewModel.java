package com.example.timer.ViewModel;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.timer.DBHelper.DatabaseHelper;
import com.example.timer.Model.Timer;
import com.example.timer.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainViewModel extends AndroidViewModel {
    private final MutableLiveData<String> editTimer = new MutableLiveData<>("");
    public MainViewModel(@NonNull Application application) {
        super(application);
    }
    List<Integer> timerList = new ArrayList<Integer>();
    DatabaseHelper db = new DatabaseHelper(getApplication());



    public void addTimer(String timerName, String preparationTime,
                         String warmTime, String workTime,
                         String relaxationTime, String cycleCount,
                         String setCount, String pauseTime, String color)
    {
        Timer timer= new Timer(timerName, preparationTime, warmTime, workTime, relaxationTime, cycleCount, setCount, pauseTime, color);
        Log.d("Color", timer.getColor());
        db.addTimer(timer);
    }

    public void updateTimer(Timer timer, String timerName, String preparationTime,
                            String warmTime, String workTime,
                            String relaxationTime, String cycleCount,
                            String setCount, String pauseTime, String color)
    {
        timer.setTimerName(timerName);
        timer.setPreparationTime(preparationTime);
        timer.setWarmTime(warmTime);
        timer.setWorkTime(workTime);
        timer.setRelaxationTime(relaxationTime);
        timer.setCycleCount(cycleCount);
        timer.setSetCount(setCount);
        timer.setPauseTime(pauseTime);
        timer.setColor(color);
        Log.d("Color", timer.getColor());
        db.updateTimer(timer);
    }

    public List<Timer> getTimerList()
    {
        return db.getAllTimer();
    }

    public void deleteTimers()
    {
        db.deleteTimers();
    }

    public void deleteTimer(Timer timer)  {
        db.deleteTimer(timer);
        timerList.remove(timer);
    }

    public int[] getIntList()
    {
        int count = timerList.size();
        int[] timers = new int[count];
        for(int i = 0; i < count; i++)
        {
            timers[i] = timerList.get(i);
        }
        return timers;
    }

    public List<String> getTimerStringList(Timer timer)
    {
        List<String> stringTimerList = new ArrayList<String>();
        List<String> phase = new ArrayList<String>();
        switch (db.getLanguage())
        {
            case "ru":
                phase.add(". Подготовка : ");
                phase.add(". Разминка : ");
                phase.add(". Работа : ");
                phase.add(". Отдых : ");
                phase.add(". Пауза : ");
                break;
            case "en-US":
                phase.add(". Preparation : ");
                phase.add(". Warm : ");
                phase.add(". Work : ");
                phase.add(". Relaxation : ");
                phase.add(". PauseTime : ");
                break;
        }
        int countCycle = Integer.parseInt(timer.getCycleCount());
        int countSet = Integer.parseInt(timer.getSetCount());
        int count = 1;
        for(int i = 0; i < countSet; i++)
        {
            stringTimerList.add(count + phase.get(0) + timer.getPreparationTime());
            timerList.add(Integer.parseInt(timer.getPreparationTime()) * 1000);
            count++;
            for(int j = 0; j < countCycle; j++)
            {
                stringTimerList.add(count + phase.get(1) + timer.getWarmTime());
                timerList.add(Integer.parseInt(timer.getWarmTime()) * 1000);
                count++;
                stringTimerList.add(count + phase.get(2) + timer.getWorkTime());
                timerList.add(Integer.parseInt(timer.getWorkTime()) * 1000);
                count++;
                stringTimerList.add(count + phase.get(3)  + timer.getRelaxationTime());
                timerList.add(Integer.parseInt(timer.getRelaxationTime()) * 1000);
                count++;
            }
            stringTimerList.add(count + phase.get(4) + timer.getPauseTime());
            timerList.add(Integer.parseInt(timer.getPauseTime()) * 1000);
            count++;
        }
        return stringTimerList;
    }
}
