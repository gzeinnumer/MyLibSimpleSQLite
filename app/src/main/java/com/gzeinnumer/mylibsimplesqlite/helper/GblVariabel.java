package com.gzeinnumer.mylibsimplesqlite.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class GblVariabel {
    private static final String TAG = "GblVariabel";

    public static SQLiteDatabase myDb = null;

    public static void initDb(Context context) {
        DatabaseHelperOLD dbHelper;
        try {
            dbHelper = new DatabaseHelperOLD(context);
            if (dbHelper.checkDatabase()) {
                GblVariabel.myDb = dbHelper.openDataBase();
            } else {
                Log.e(TAG, "initDb:  database kosong");
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            Log.e(TAG, "initDb: " + throwable.getMessage());
        }
    }
}
