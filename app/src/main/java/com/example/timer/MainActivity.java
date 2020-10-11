package com.example.timer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.timer.EditPage.EditActivity;
import com.example.timer.HomePage.HomeActivity;
import com.example.timer.SettingsPage.SettingsActivity;
import com.example.timer.TimerPage.TimerActivity;

public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.home_page:
                intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.timer_page:
                intent = new Intent(MainActivity.this, TimerActivity.class);
                startActivity(intent);
                break;
            case R.id.edit_page:
                intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
                break;
            case R.id.settings_page:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
    }
}