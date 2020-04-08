package com.example.badge.xiaomi;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

import com.example.badge.BadgeUtil;
import com.example.badge.IBadge;
import com.example.badge.NotifiationUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.content.Intent;
import android.os.Handler;

/**
 *  https://dev.mi.com/console/doc/detail?pId=939
 *  MIUI 6 至 MIUI 11
 */

public class XiaoMi implements IBadge {

    @Override
    public void showBadge(Context context, String launcherClassName, int badgeNum) {
        try{
            final NotificationManager _manager = ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE));
            final Notification notification = NotifiationUtil.getNotification(context, badgeNum);
            Field field = notification.getClass().getDeclaredField("extraNotification");
            Object extraNotification = field.get(notification);
            Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
            method.invoke(extraNotification, badgeNum);
            try {
                //关闭上一次的notification
                _manager.cancel(BadgeUtil.NOTIFICATION_TAG, BadgeUtil.NOTIFICATION_ID);
            } catch (Exception e){
                e.printStackTrace();
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    _manager.notify(BadgeUtil.NOTIFICATION_TAG, BadgeUtil.NOTIFICATION_ID, notification);
                }
            }, 1000);
        } catch (Exception e){
            e.printStackTrace();
            // 网上找的据说是miui 6之前的版本,没有miui6之前版本的小米手机不知道有没有效
            Intent localIntent = new Intent("android.intent.action.APPLICATION_MESSAGE_UPDATE");
            localIntent.putExtra("android.intent.extra.update_application_component_name", context.getPackageName() + "/" + launcherClassName);
            localIntent.putExtra("android.intent.extra.update_application_message_text", String.valueOf(badgeNum == 0 ? "" : badgeNum));
            context.sendBroadcast(localIntent);
        }
    }

}
