package com.example.timer.EditPage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.timer.DatabaseHelper;
import com.example.timer.R;
import com.example.timer.Timer;

public class CreateActivity extends AppCompatActivity {

    private static final int MODE_CREATE = 1;
    private static final int MODE_EDIT = 2;

    private EditText editName, editPreparation, editWarm, editWork, editRelaxationTime, editCycleCount, editSetCount, editPause;
    private Button buttonApply;
    private Timer timer;
    private boolean needRefresh;
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        this.editName = (EditText)this.findViewById(R.id.edit_name);
        this.editPreparation = (EditText)this.findViewById(R.id.edit_preparation);
        this.editWarm = (EditText)this.findViewById(R.id.edit_warm);
        this.editWork = (EditText)this.findViewById(R.id.edit_work);
        this.editRelaxationTime = (EditText)this.findViewById(R.id.edit_relaxation);
        this.editCycleCount = (EditText)this.findViewById(R.id.edit_cycle);
        this.editSetCount = (EditText)this.findViewById(R.id.edit_set);
        this.editPause = (EditText)this.findViewById(R.id.edit_pause);

        this.buttonApply = (Button)findViewById(R.id.button_apply);

        this.buttonApply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)  {
                buttonSaveClicked();
                buttonCancelClicked();
            }
        });


        Intent intent = this.getIntent();
        this.timer = (Timer) intent.getSerializableExtra("timer");
        if(timer == null)  {
            this.mode = MODE_CREATE;
        } else{
            this.mode = MODE_EDIT;
            this.editName.setText(timer.getTimerName());
            this.editPreparation.setText(timer.getPreparationTime());
            this.editWarm.setText(timer.getWarmTime());
            this.editWork.setText(timer.getWorkTime());
            this.editRelaxationTime.setText(timer.getRelaxationTime());
            this.editCycleCount.setText(timer.getCycleCount());
            this.editSetCount.setText(timer.getSetCount());
            this.editPause.setText(timer.getPauseTime());
        }
    }

    // User Click on the Save button.
    public void buttonSaveClicked()  {
        DatabaseHelper db = new DatabaseHelper(this);

        String name = this.editName.getText().toString();
        String preparation = this.editPreparation.getText().toString();
        String warm = this.editWarm.getText().toString();
        String work = this.editWork.getText().toString();
        String relaxation = this.editRelaxationTime.getText().toString();
        String cycle = this.editCycleCount.getText().toString();
        String set = this.editSetCount.getText().toString();
        String pause = this.editPause.getText().toString();

        if(name.equals("") || preparation.equals("") || warm.equals("")
                || work.equals("") || relaxation.equals("") || cycle.equals("")
                || set.equals("") || pause.equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Please enter empty edit", Toast.LENGTH_SHORT).show();
            return;
        }

        if(mode == MODE_CREATE ) {
            this.timer= new Timer(name, preparation, warm, work, relaxation, cycle, set, pause);
            db.addTimer(timer);
        } else  {
            this.timer.setTimerName(name);
            this.timer.setPreparationTime(preparation);
            this.timer.setWarmTime(warm);
            this.timer.setWorkTime(work);
            this.timer.setRelaxationTime(relaxation);
            this.timer.setCycleCount(cycle);
            this.timer.setSetCount(set);
            this.timer.setPauseTime(pause);
            db.updateTimer(timer);
        }

        this.needRefresh = true;

        // Back to MainActivity.
        this.onBackPressed();
    }

    // User Click on the Cancel button.
    public void buttonCancelClicked()  {
        // Do nothing, back MainActivity.
        this.onBackPressed();
    }

    // When completed this Activity,
    // Send feedback to the Activity called it.
    @Override
    public void finish() {

        // Create Intent
        Intent data = new Intent();

        // Request MainActivity refresh its ListView (or not).
        data.putExtra("needRefresh", needRefresh);

        // Set Result
        this.setResult(Activity.RESULT_OK, data);
        super.finish();
    }
}