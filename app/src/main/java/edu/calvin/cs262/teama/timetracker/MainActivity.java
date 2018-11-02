package edu.calvin.cs262.teama.timetracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Spinner spinActivities;
    private ArrayList<String> activitiesList = new ArrayList<String>();


    private int seconds;
    private boolean timerIsRunning;
    private ImageView playPause;
    private TextView timerText;
    private Date timeStarted;

    public static CSVImportExport csv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create data storage csv object


        // Read information from last time into current application


        //Create temp items for array
        activitiesList.add("Project Alpha");
        activitiesList.add("Project Beta");
        activitiesList.add("Project Gamma");
        activitiesList.add("Project Zeta");

        setContentView(R.layout.activity_main);
        startSpinner();
        timerText = (TextView)findViewById(R.id.timerText);
        timerIsRunning = false;
        playPause = (ImageView)findViewById(R.id.play);
        timeStarted = new Date();
        runTimer();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.add_project) {
            Intent intent = new Intent(this, addProject.class);
            boolean x = true;
            int b = 0;
            while(x){
                if (activitiesList.get(b) == null) {
                    x = false;
                } else {
                    intent.putExtra("activitiesList" + b, activitiesList.get(b));
                }
            }

            startActivityForResult(intent, 2);

        } else if (id == R.id.remove_project) {

        } else if (id == R.id.manual_time_entry) {

        } else if (id == R.id.manual_time_removal) {

        } else if (id == R.id.dark_theme_switch) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void startSpinner() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "COMING SOON!", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        spinActivities = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, activitiesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinActivities.setAdapter(adapter);
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    public void showStartMsg(View view){
        toggleTimerRunning(null);
        showMsg("Start time!");
    }

    public void showMsg(String message) {
        displayToast(message);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void startTimer() {
        playPause.setImageResource(R.drawable.start);
        timeStarted = new Date();
        timerIsRunning = true;

    }

    private void stopTimer() {
        playPause.setImageResource(R.drawable.play);
        timerIsRunning = false;
    }

    public void toggleTimerRunning(View view){
        if (timerIsRunning) {
            // Stop timer
            stopTimer();
        } else {
            // Start timer
            startTimer();
        }
    }

    private String getElapsedTime() {
        long millis = new Date().getTime() - timeStarted.getTime();
        int seconds_passed_total = ((int) millis) / 1000;
        int seconds_passed_partial = seconds_passed_total % 60;
        int minutes_passed_total = seconds_passed_total / 60;
        int minutes_passed_partial = minutes_passed_total % 60;
        int hours_passed = seconds_passed_total / 60;
        return String.format("%d:%02d:%02d", hours_passed, minutes_passed_partial, seconds_passed_partial);
    }

    public void runTimer(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(timerIsRunning)
                    timerText.setText(getElapsedTime());
                handler.post(this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if((requestCode==2) & (resultCode==2))
        {
            String newProjName = data.getExtras().get("MESSAGE").toString();
            if (newProjName.isEmpty()) {
                displayToast("Can't Add An Empty Project");
            } else {
                displayToast("New Project Added: " + newProjName);
                activitiesList.add(newProjName);
            }
        } else if((requestCode==2) & (resultCode==3)) {

        }
    }
}

