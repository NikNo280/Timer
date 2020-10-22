package com.example.timer.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;

import com.example.timer.DBHelper.DatabaseHelper;
import com.example.timer.MainPage.MainActivity;
import com.example.timer.R;
import com.example.timer.ViewModel.MainViewModel;

import java.util.Locale;

public class SplashScreen extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private long ms=0;
    private long splashTime=2000;
    private boolean splashActive = true;
    private boolean paused=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(prefs.getString("theme", "D").equals("D"))
        {
            setTheme(R.style.AppThemeDark);
        }
        else if(prefs.getString("theme", "D").equals("L"))
        {
            setTheme(R.style.AppThemeLite);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        prefs.registerOnSharedPreferenceChangeListener(this);
        float coef = (float)prefs.getInt("size", 1);
        Locale locale = new Locale(prefs.getString("language", "eu-US"));
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        configuration.fontScale = coef / 10;
        getBaseContext().getResources().updateConfiguration(configuration, null);
        Thread mythread = new Thread() {
            public void run() {
                try {
                    while (splashActive && ms < splashTime) {
                        if(!paused)
                            ms=ms+100;
                        sleep(100);
                    }
                } catch(Exception e) {}
                finally {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        mythread.start();
    }

    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        Locale locale = new Locale(prefs.getString("language", "eu-US"));
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, null);

    }
}