package com.example.badge.oppo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.badge.BadgeUtil;
import com.example.badge.IBadge;

/**
 *   oppo 需要官网申请
 */

public class Oppo implements IBadge {


    @Override
    public void showBadge(Context context, String launcherClassName, int badgeNum) {
        try {
            Bundle extras = new Bundle();
            extras.putInt("app_badge_count", badgeNum);
            context.getContentResolver().call(Uri.parse("content://com.android.badge/badge"), "setAppBadgeCount", null, extras);
        } catch (Exception e) {
            e.printStackTrace();
            BadgeUtil.getInstance().showDefaultBadge(context, badgeNum);
        }
    }
}
