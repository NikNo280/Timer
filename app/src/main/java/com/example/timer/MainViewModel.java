package com.example.timer;

import android.app.Application;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private final MutableLiveData<String> timerName = new MutableLiveData<>("");
    private final MutableLiveData<String> preparationTime = new MutableLiveData<>("");
    private final MutableLiveData<String> warmTime = new MutableLiveData<>("");
    private final MutableLiveData<String> workTime = new MutableLiveData<>("");
    private final MutableLiveData<String> relaxationTime = new MutableLiveData<>("");
    private final MutableLiveData<String> cycleCount = new MutableLiveData<>("");
    private final MutableLiveData<String> setCount = new MutableLiveData<>("");
    private final MutableLiveData<String> pauseTime = new MutableLiveData<>("");

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getTimerName(){return timerName;}

    public LiveData<String> getPreparationTime(){return preparationTime;}

    public LiveData<String> getWarmTime(){return warmTime;}

    public LiveData<String> getWorkTime(){return workTime;}

    public LiveData<String> getRelaxationTime(){return relaxationTime;}

    public LiveData<String> getCycleCount(){return cycleCount;}

    public LiveData<String> getSetCount(){return setCount;}

    public LiveData<String> getPauseTime(){return pauseTime;}


    public List<String> getListToAdapter(Timer timer)
    {
        List<String> timerList = new ArrayList<String>();
        int countCycle = Integer.parseInt(timer.getCycleCount());
        int countSet = Integer.parseInt(timer.getSetCount());
        for(int i = 0; i < countSet; i++)
        {
            timerList.add("Preparation : " + timer.getPreparationTime());
            for(int j = 0; j < countCycle; j++)
            {
                timerList.add("Warm : " + timer.getWarmTime());
                timerList.add("Work : " + timer.getWorkTime());
                timerList.add("Relaxation : " + timer.getRelaxationTime());
            }
            timerList.add("PauseTime : " + timer.getPauseTime());
        }
        return timerList;
    }
}
