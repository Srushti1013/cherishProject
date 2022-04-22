package com.example.cherishprototype.contacts;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.cherishprototype.R;

/**
 * A reminderBroadcast used to create a notifcation for the alarm function of the app.
 */

public class ReminderBroadcast extends BroadcastReceiver {
    String body;
    @Override
    public void onReceive(Context context, Intent intent){
        Bundle extras = intent.getExtras();
        body = extras.getString("body");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "My Notification")
                .setSmallIcon(R.drawable.noty)
                .setContentTitle("Cherish Reminder")
                .setContentText("A friendly reminder to text " + body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(false);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, builder.build());
    }

}
