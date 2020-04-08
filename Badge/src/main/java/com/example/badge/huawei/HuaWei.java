package com.example.badge.huawei;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.badge.IBadge;

/**
 *   华为文档 https://developer.huawei.com/consumer/cn/doc/30802
 */

public class HuaWei implements IBadge {

    @Override
    public void showBadge(Context context, String launcherClassName, int badgeNum) {
        try {
            Bundle bunlde = new Bundle();
            bunlde.putString("package", context.getPackageName()); // com.test.badge is your package name
            bunlde.putString("class", launcherClassName); // com.test. badge.MainActivity is your apk main activity
            bunlde.putInt("badgenumber", badgeNum);
            context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, bunlde);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
