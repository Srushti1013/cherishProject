package com.example.cherishprototype;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {
    String title, body;
    @Override
    public void onReceive(Context context, Intent intent){
        Bundle extras = intent.getExtras();
        body = extras.getString("body");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "My Notification")
                .setSmallIcon(R.drawable.weak)
                .setContentTitle("Cherish Reminder")
                .setContentText("A friendly reminder to text " + body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, builder.build());
    }

}
