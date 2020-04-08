package com.example.badge.Samsung;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.badge.BadgeUtil;
import com.example.badge.IBadge;

public class Samsung implements IBadge {

    @Override
    public void showBadge(Context context, String launcherClassName, int badgeNum) {
        Uri uri = Uri.parse("content://com.sec.badge/apps");
        String columnId = "_id";
        String columnPackage = "package";
        String columnClass = "class";
        String columnBadgeCount = "badgeCount";
        Cursor cursor = null;
        try {
            ContentResolver contentResolver = context.getContentResolver();
            cursor = contentResolver.query(uri, new String[]{columnId}, columnPackage + "=?", new String[]{context.getPackageName()}, null);

            if (cursor == null || !cursor.moveToFirst()) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(columnPackage, context.getPackageName());
                contentValues.put(columnClass, launcherClassName);
                contentValues.put(columnBadgeCount, badgeNum);
                contentResolver.insert(uri, contentValues);
            } else {
                int idColumnIndex = cursor.getColumnIndex(columnId);
                ContentValues contentValues = new ContentValues();
                contentValues.put(columnBadgeCount, badgeNum);
                contentResolver.update(uri, contentValues, columnId + "=?", new String[]{String.valueOf(cursor.getInt(idColumnIndex))});
            }
        } catch (Exception e) {
            BadgeUtil.getInstance().showDefaultBadge(context, badgeNum);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
