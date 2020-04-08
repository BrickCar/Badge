package com.example.badge;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotifiationUtil {

    private static final String CHANNEL = "badge";

    private static int getSmallIcon(){
        return R.mipmap.ic_launcher;
    }

    public static Notification getNotification(Context context, int badgeNum){
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            notification = getNotification26(context, badgeNum).build();
        else
            notification = getNotification25(context, badgeNum).build();
        return notification;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static Notification.Builder getNotification26(Context context, int badgeNum){
        return new Notification.Builder(context, CHANNEL)
                .setSmallIcon(getSmallIcon())
                .setTicker("")
                .setContentTitle("badge title")
                .setContentText("badge body")
        //        .setContentIntent(pIntent)
        //        .setCustomContentView(initNotification(context))
                .setWhen(System.currentTimeMillis())
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setNumber(badgeNum)
                .setSound(null)
                .setAutoCancel(true)
                .setOngoing(true);
    }

    private static NotificationCompat.Builder getNotification25(Context context, int badgeNum){
        return new NotificationCompat.Builder(context, CHANNEL)
                .setSmallIcon(getSmallIcon())
                .setTicker("")
                .setContentTitle("badge title")
                .setContentText("badge body")
       //         .setContentIntent(pIntent)
                .setWhen(System.currentTimeMillis())
        //        .setCustomContentView(initNotification(context))
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setNumber(badgeNum)
                .setSound(null)
                .setAutoCancel(true)
                .setOngoing(true);
    }


}
