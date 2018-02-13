package com.example.nngo1.remindme.Event;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by nngo1 on 18-Dec-17.
 */

public class CustomBroadcastReceiver extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "potato";
    public static String NOTIFICATION = "xd";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int notificationID = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(notificationID, notification);
    }
}
