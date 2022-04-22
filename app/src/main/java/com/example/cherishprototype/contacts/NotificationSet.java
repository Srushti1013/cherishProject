package com.example.cherishprototype.contacts;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cherishprototype.R;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The notification activity lets the user create notifications that will repeat, notifications
 * on holidays or redirect the user to the calendar activity to create specific notificatiions.
 */

public class NotificationSet extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final String TAG = "NotificationSet";
    private Button btnSaveNotif;
    private List<String> timeArray;
    Spinner spin;
    DatabaseHelper mDatabaseHelper;
    private String selectedName;
    ImageView btnCalendar;
    String[] timeIntervals = { "Daily", "Weekly", "Monthly", "Yearly", "No Reminder"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_set);
        btnCalendar = (ImageView) findViewById(R.id.btnCalendar);
        btnSaveNotif = (Button) findViewById(R.id.btnSaveNotif);
        spin =(Spinner) findViewById(R.id.spinner);
        mDatabaseHelper = new DatabaseHelper(this);
        timeArray = new ArrayList<String>();
        timeArray = Arrays.asList(timeIntervals);

        //get the intent extra from the ListDataActivity
        Intent receivedIntent = getIntent();

        //now get the name we passed as an extra
        selectedName = receivedIntent.getStringExtra("contact");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeArray);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spin.setAdapter(dataAdapter);
        spin.setOnItemSelectedListener(this);


        //Creates notifications based on the spinner item selection.
        btnSaveNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = spin.getSelectedItem().toString();

                createNotificationChannel();
                toastMessage("Notification set");
                Intent intent = new Intent(NotificationSet.this, ReminderBroadcast.class);
                intent.putExtra("body",selectedName);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),3,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                long timeAtButtonClick = System.currentTimeMillis();

                //for demo purposes this is set to 5 second intervals
                if(text.equals("Daily")){
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,timeAtButtonClick + 5000, 2500, pendingIntent);
                }
                else if(text.equals("Weekly")){
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,timeAtButtonClick + 5000, AlarmManager.INTERVAL_DAY * 7, pendingIntent);
                }
                else if(text.equals("Monthly")){
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,timeAtButtonClick + 5000, AlarmManager.INTERVAL_DAY * 30, pendingIntent);
                }
                else if(text.equals("Yearly")){
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,timeAtButtonClick + 5000, AlarmManager.INTERVAL_DAY * 365, pendingIntent);
                }
                else{
                    System.out.println("nothing");
                }
                Intent backIntent = new Intent(NotificationSet.this, EditDataActivity.class);
                backIntent.putExtra("name", selectedName);
                startActivity(backIntent);
            }
        });

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationSet.this, com.example.cherishprototype.calendar.Calendar.class);
                startActivity(intent);
            }
        });
    }


    void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel= new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

}