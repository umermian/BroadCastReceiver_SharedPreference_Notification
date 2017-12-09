package com.example.popie.broadcastreceiver_sharedpreference_notification.receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.popie.broadcastreceiver_sharedpreference_notification.MainActivity;
import com.example.popie.broadcastreceiver_sharedpreference_notification.R;

/**
 * Created by popie on 12/9/2017.
 */

public class WiFiReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) {

            Intent intent1 = new Intent(context.getApplicationContext(), MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent1, 0);

            Notification notification = new Notification.Builder(context.getApplicationContext())
                    .setSmallIcon(R.drawable.wifi)
                    .setContentTitle("Wifi Notification")
                    .setContentText("Wifi state changed")
                    .setContentIntent(pendingIntent)
                    .build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notification);
        }
    }
}
