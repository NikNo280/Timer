package com.example.timer;

import android.app.Application;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

}
