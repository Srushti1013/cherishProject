package com.example.cherishprototype;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class NotificationSet extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final String TAG = "NotificationSet";
    private Button btnCalendar,btnSaveNotif;
    private final int THOUSAND = 1000;
    private int time;
    private List<String> timeArray;
    Spinner spin;
    DatabaseHelper mDatabaseHelper;
    private String selectedName;
    String[] timeIntervals = { "20 Seconds", "10 Seconds", "5 Seconds", "No Repeating Reminder" };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_set);
        btnCalendar = (Button) findViewById(R.id.btnCalendar);
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


        btnSaveNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = spin.getSelectedItem().toString();

                createNotificationChannel();
                toastMessage("Notification set");
                Intent intent = new Intent(NotificationSet.this,ReminderBroadcast.class);
                intent.putExtra("body",selectedName);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),3,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                long timeAtButtonClick = System.currentTimeMillis();
                if(text.equals("10 Seconds")){
                    time = THOUSAND * 10;
                    alarmManager.set(AlarmManager.RTC_WAKEUP,timeAtButtonClick + time, pendingIntent);
                }
                else if(text.equals("5 Seconds")){
                    time = THOUSAND * 5;
                    alarmManager.set(AlarmManager.RTC_WAKEUP,timeAtButtonClick + time, pendingIntent);
                }
                else if(text.equals("20 Seconds")){
                    time = THOUSAND * 20;
                    alarmManager.set(AlarmManager.RTC_WAKEUP,timeAtButtonClick + time, pendingIntent);
                }
                else{
                    System.out.println("nothing");
                }
                Intent backIntent = new Intent(NotificationSet.this, EditDataActivity.class);
                backIntent.putExtra("name", selectedName);
                startActivity(backIntent);

                //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeAtButtonClick, timeAtButtonClick + time,pendingIntent);

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