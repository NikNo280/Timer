package com.example.timer.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.example.timer.DBHelper.DatabaseHelper;
import com.example.timer.MainPage.MainActivity;
import com.example.timer.R;
import com.example.timer.ViewModel.MainViewModel;

import java.util.Locale;

public class SplashScreen extends AppCompatActivity {

    private long ms=0;
    private long splashTime=2000;
    private boolean splashActive = true;
    private boolean paused=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        DatabaseHelper db = new DatabaseHelper(getApplication());
        Locale locale = new Locale(db.getLanguage());
        Log.d("LG onCreate", db.getLanguage());
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
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

}