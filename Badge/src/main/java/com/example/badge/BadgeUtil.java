package com.example.badge;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;

import com.example.badge.Samsung.Samsung;
import com.example.badge.htc.Htc;
import com.example.badge.huawei.HuaWei;
import com.example.badge.oppo.Oppo;
import com.example.badge.sony.Sony;
import com.example.badge.vivo.Vivo;
import com.example.badge.xiaomi.XiaoMi;
import com.example.badge.zuk.Zuk;

import java.util.List;

public class BadgeUtil {

    public static final String NOTIFICATION_TAG = "badge";
    public static final int NOTIFICATION_ID = 101;

    private static BadgeUtil _instance = null;
    private BadgeUtil(){}
    public static BadgeUtil getInstance(){
        if (_instance == null)
            synchronized (BadgeUtil.class){
                if (_instance == null)
                    _instance = new BadgeUtil();
            }
        return _instance;
    }


    private String launcherClassName = null;

    /**
     *  其中 google 手机 文档地址 https://developer.android.com/training/notify-user/badges.html#java， 从 8.0 开始支持角标，暂无数字、颜色定义
     *
     * @param context
     * @param badgeNum
     */
    public void showBadge(Context context, int badgeNum){
        launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null)
            throw new NullPointerException("Launcher class name is null");
        if (launcherClassName.trim().length() == 0)
            throw new NullPointerException("Launcher class name is null");
        badgeNum = Math.min(badgeNum, 99); //设置badge最大为 99
        String manufacturer = Build.MANUFACTURER.toLowerCase(); //制造商
        if (manufacturer == null)
            throw new IllegalArgumentException("Build MANUFACTURER is null");
        if (manufacturer.trim().length() == 0)
            throw new IllegalArgumentException("Build MANUFACTURER is null");
        if (manufacturer.contains("huawei"))
            new HuaWei().showBadge(context, launcherClassName, badgeNum);
        else if (manufacturer.contains("xiaomi"))
            new XiaoMi().showBadge(context, launcherClassName, badgeNum);
        else if (manufacturer.contains("sony"))
            new Sony().showBadge(context, launcherClassName, badgeNum);
        else if (manufacturer.contains("zuk"))
            new Zuk().showBadge(context, launcherClassName, badgeNum);
        else if (manufacturer.contains("samsung"))
            new Samsung().showBadge(context, launcherClassName, badgeNum);
        else if (manufacturer.contains("htc"))
            new Htc().showBadge(context, launcherClassName, badgeNum);
        else if (manufacturer.contains("vivo"))
            new Vivo().showBadge(context, launcherClassName, badgeNum);
        else if (manufacturer.contains("oppo"))
            new Oppo().showBadge(context, launcherClassName, badgeNum);
        else
            showBadge();
    }

    private void showBadge(){}

    public void showDefaultBadge(Context context, int badgeNum){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final NotificationManager _manager = ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE));
            final Notification notification = NotifiationUtil.getNotification(context, badgeNum);
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
        } else {
            //参考其他开发者写法,不一定有效
            try {
                Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
                intent.putExtra("badge_count", badgeNum);
                intent.putExtra("badge_count_package_name", context.getPackageName());
                intent.putExtra("badge_count_class_name", launcherClassName);
                context.sendBroadcast(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getLauncherClassName(Context context) {
        if (!TextUtils.isEmpty(launcherClassName)) {
            return launcherClassName;
        }
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfoList) {
            if (context != null
                    && context.getPackageName().equalsIgnoreCase(resolveInfo.activityInfo.applicationInfo.packageName)) {
                launcherClassName = resolveInfo.activityInfo.name;
                break;
            }
        }
        return launcherClassName;
    }


}
