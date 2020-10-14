package com.example.timer.HomePage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.timer.CreateFragment;
import com.example.timer.DatabaseHelper;
import com.example.timer.EditPage.CreateActivity;
import com.example.timer.EditPage.EditActivity;
import com.example.timer.MainViewModel;
import com.example.timer.R;
import com.example.timer.Timer;
import com.example.timer.TimerPage.TimerActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity{

    private ListView listView;
    private static final int MENU_ITEM_VIEW = 111;
    private static final int MENU_ITEM_EDIT = 222;
    private static final int MENU_ITEM_CREATE = 333;
    private static final int MENU_ITEM_DELETE = 444;

    private static final int MY_REQUEST_CODE = 1000;

    private final List<Timer> timerList = new ArrayList<Timer>();
    private ArrayAdapter<Timer> listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get ListView object from xml
        this.listView = (ListView) findViewById(R.id.listView);

        DatabaseHelper db = new DatabaseHelper(this);
        db.createDefaultTimersIfNeed();

        List<Timer> list =  db.getAllTimer();
        this.timerList.addAll(list);

        // Define a new Adapter
        // 1 - Context
        // 2 - Layout for the row
        // 3 - ID of the TextView to which the data is written
        // 4 - the List of data

        this.listViewAdapter = new ArrayAdapter<Timer>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, this.timerList);
        // Assign adapter to ListView
        this.listView.setAdapter(this.listViewAdapter);
        // Register the ListView for Context menu
        registerForContextMenu(this.listView);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo)    {

        super.onCreateContextMenu(menu, view, menuInfo);
        menu.setHeaderTitle("Select The Action");

        // groupId, itemId, order, title
        menu.add(0, MENU_ITEM_VIEW , 0, "View Timer");
        menu.add(0, MENU_ITEM_CREATE , 1, "Create Timer");
        menu.add(0, MENU_ITEM_EDIT , 2, "Edit Timer");
        menu.add(0, MENU_ITEM_DELETE, 4, "Delete Timer");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Timer selectedTimer = (Timer) this.listView.getItemAtPosition(info.position);

        if(item.getItemId() == MENU_ITEM_VIEW){
            Intent intent = new Intent(this, TimerActivity.class);
            intent.putExtra("timer", selectedTimer);
            this.startActivityForResult(intent, MY_REQUEST_CODE);
        }
        else if(item.getItemId() == MENU_ITEM_CREATE){
            Intent intent = new Intent(this, CreateActivity.class);
            this.startActivityForResult(intent, MY_REQUEST_CODE);
        }
        else if(item.getItemId() == MENU_ITEM_EDIT ){
            Intent intent = new Intent(this, CreateActivity.class);
            intent.putExtra("timer", selectedTimer);
            this.startActivityForResult(intent, MY_REQUEST_CODE);
        }
        else if(item.getItemId() == MENU_ITEM_DELETE){
            // Ask before deleting.
            new AlertDialog.Builder(this)
                    .setMessage(selectedTimer.getTimerName()+". Are you sure you want to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            deleteTimer(selectedTimer);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        else {
            return false;
        }
        return true;
    }

    // Delete a record
    private void deleteTimer(Timer timer)  {
        DatabaseHelper db = new DatabaseHelper(this);
        db.deleteTimer(timer);
        this.timerList.remove(timer);
        // Refresh ListView.
        this.listViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == MY_REQUEST_CODE) {
            boolean needRefresh = data.getBooleanExtra("needRefresh", true);
            // Refresh ListView
            if (needRefresh) {
                this.timerList.clear();
                DatabaseHelper db = new DatabaseHelper(this);
                List<Timer> list = db.getAllTimer();
                this.timerList.addAll(list);
                // Notify the data change (To refresh the ListView).
                this.listViewAdapter.notifyDataSetChanged();
            }
        }
    }
}