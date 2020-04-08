package com.example.badge.htc;

import android.content.Context;
import android.content.Intent;

import com.example.badge.BadgeUtil;
import com.example.badge.IBadge;

public class Htc implements IBadge {

    @Override
    public void showBadge(Context context, String launcherClassName, int badgeNum) {
        try {
            Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
            intent.putExtra("badge_count", badgeNum);
            intent.putExtra("badge_count_package_name", context.getPackageName());
            intent.putExtra("badge_count_class_name", launcherClassName);
            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
            BadgeUtil.getInstance().showDefaultBadge(context, badgeNum);
        }
    }

}
