package com.example.badge.vivo;

import android.content.Context;
import android.content.Intent;

import com.example.badge.IBadge;


/**
 *   无效 需要官网申请
 */

public class Vivo implements IBadge {

    @Override
    public void showBadge(Context context, String launcherClassName, int badgeNum) {
        try{
            Intent intent = new Intent("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
            intent.putExtra("packageName", context.getPackageName());
            intent.putExtra("className", launcherClassName);
            intent.putExtra("notificationNum", badgeNum);
            context.sendBroadcast(intent);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
