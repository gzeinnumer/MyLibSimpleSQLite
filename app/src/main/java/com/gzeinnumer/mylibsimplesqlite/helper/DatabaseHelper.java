package com.gzeinnumer.mylibsimplesqlite.helper;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    protected static final String DATABASE_NAME = "MyLibSQLiteSimple.db";
    protected static final String DATABASE_NAME_ASSET = "MyLibSQLiteSimple.db";
    protected static String DB_PATH = "";
    protected static final int DATABASE_VERSION = 4;
    protected final Context context;
    protected SQLiteDatabase myDataBase;

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: ");
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

        DB_PATH = Environment.getExternalStorageDirectory().toString() + "/MyLibSQLite/";

        Log.d(TAG, "DatabaseHelper: Database exist " + DB_PATH);

        String myPath = DB_PATH + DATABASE_NAME;
        File dbFile = new File(myPath);

        if (dbFile.exists()) {
            Log.d(TAG, "DatabaseHelper: Database exist");
        } else {
            if (!checkDatabase()) {
                try {
                    getReadableDatabase();
                    copyDatabase();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean checkDatabase() {
        boolean statusDB = false;
        SQLiteDatabase checkDB = null;
        String myPath = DB_PATH + DATABASE_NAME;
        try {
            if (new File(myPath).exists()) {
                try {
                    checkDB = SQLiteDatabase.openDatabase(myPath, null, 0);
                    statusDB = true;
                } catch (Exception e) {
                    statusDB = false;
                }
            } else {
                statusDB = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return statusDB;
    }

    public void createDatabase() throws IOException {
        if (!checkDatabase()) {
            getReadableDatabase();
            copyDatabase();
        }
    }

    private void copyDatabase() throws IOException {
        InputStream myInput = this.context.getAssets().open(DATABASE_NAME_ASSET);
        String copyPath = DB_PATH + DATABASE_NAME;
        File outFile = this.context.getDatabasePath(copyPath);
        outFile.delete();
        OutputStream myOutput = new FileOutputStream(outFile);
        byte[] buffer = new byte[1204];
        while (true) {
            int length = myInput.read(buffer);
            if (length <= 0) {
                myOutput.flush();
                myOutput.close();
                myInput.close();
                
                return;
            }
            myOutput.write(buffer, 0, length);
        }
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        String openPath = DB_PATH + DATABASE_NAME;
        this.myDataBase = SQLiteDatabase.openDatabase(openPath, null, 0);

        return this.myDataBase;
    }

    public boolean deleteDatabase() {
        String path = DB_PATH + DATABASE_NAME;
        File file = new File(path);

        return file.delete();
    }
}
