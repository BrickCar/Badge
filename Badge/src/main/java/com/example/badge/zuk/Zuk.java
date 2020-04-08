package com.example.badge.zuk;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.badge.BadgeUtil;
import com.example.badge.IBadge;

/**
 *
 * http://developer.zuk.com/detail/12
 */

public class Zuk implements IBadge {

    @Override
    public void showBadge(Context context, String launcherClassName, int badgeNum) {
        try {
            Bundle extra = new Bundle();
            extra.putInt("app_badge_count", badgeNum);
            context.getContentResolver().call(Uri.parse("content://com.android.badge/badge"), "setAppBadgeCount", null, extra);
        } catch (Exception e){
            e.printStackTrace();
            BadgeUtil.getInstance().showDefaultBadge(context, badgeNum);
        }
    }

}
