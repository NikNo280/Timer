package com.example.timer.SettingsPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.timer.DBHelper.DatabaseHelper;
import com.example.timer.MainPage.MainActivity;
import com.example.timer.R;
import com.example.timer.ViewModel.MainViewModel;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    public MainViewModel mainViewModel;
    private boolean needRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mainViewModel  = ViewModelProviders.of(this).get(MainViewModel.class);
        Button btnClear = findViewById(R.id.button_clear);
        btnClear.setOnClickListener(item -> {
            mainViewModel.deleteTimers();
            needRefresh = true;
        });

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.spinner_language, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner_language = findViewById(R.id.spinner_language);
        spinner_language.setAdapter(adapter);
        spinner_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                if(selectedItemPosition == 1)
                {
                    Locale locale = new Locale("en-US");
                    Locale.setDefault(locale);
                    Configuration configuration = new Configuration();
                    configuration.locale = locale;
                    getBaseContext().getResources().updateConfiguration(configuration, null);
                    DatabaseHelper db = new DatabaseHelper(getApplication());
                    db.updateLanguage("en-US");
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
                else if(selectedItemPosition == 2)
                {
                    Locale locale = new Locale("ru");
                    Locale.setDefault(locale);
                    Configuration configuration = new Configuration();
                    configuration.locale = locale;
                    getBaseContext().getResources().updateConfiguration(configuration, null);
                    DatabaseHelper db = new DatabaseHelper(getApplication());
                    db.updateLanguage("ru");
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
                needRefresh = true;
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public void finish() {

        Intent data = new Intent();
        data.putExtra("needRefresh", needRefresh);
        this.setResult(Activity.RESULT_OK, data);
        super.finish();
    }
}