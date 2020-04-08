package com.example.badge.sony;

import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.badge.IBadge;

/**
 * https://github.com/sonyxperiadev/home-badge
 */

public class Sony implements IBadge {

    @Override
    public void showBadge(Context context, String launcherClassName, int badgeNum) {
        AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(context.getContentResolver()){};
        try {
            //官方给出方法
            ContentValues contentValues = new ContentValues();
            contentValues.put("badge_count", badgeNum);
            contentValues.put("package_name", context.getPackageName());
            contentValues.put("activity_name", launcherClassName);
            asyncQueryHandler.startInsert(0, null,
                    Uri.parse("content://com.sonymobile.home.resourceprovider/badge"),
                    contentValues);
        } catch (Exception e) {
            //网上大部分使用方法
            Intent intent = new Intent("com.sonyericsson.home.action.UPDATE_BADGE");
            intent.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", badgeNum > 0);
            intent.putExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME", launcherClassName);
            intent.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", String.valueOf(badgeNum > 0 ? badgeNum : ""));
            intent.putExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME", context.getPackageName());
            context.sendBroadcast(intent);
        }
    }

}
