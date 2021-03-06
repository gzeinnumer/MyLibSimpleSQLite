package com.gzeinnumer.mylibsimplesqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.gzeinnumer.mylibsimplesqlite.helper.InterfaceDaoSQLite;
import com.gzeinnumer.mylibsimplesqlite.struck.JoinColumn;
import com.gzeinnumer.mylibsimplesqlite.struck.SQLiteTable;
import com.gzeinnumer.mylibsimplesqlite.typeData.DecimalTypeData;
import com.gzeinnumer.mylibsimplesqlite.typeData.IntegerTypeData;
import com.gzeinnumer.mylibsimplesqlite.typeData.PrimaryKeyTypeData;
import com.gzeinnumer.mylibsimplesqlite.typeData.TextTypeData;
import com.gzeinnumer.mylibsimplesqlite.typeData.TimeStampTypeData;
import com.gzeinnumer.mylibsimplesqlite.typeData.VarcharTypeData;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class SQLiteLIB<T> implements InterfaceDaoSQLite<T> {

    private static final String TAG = "SQLiteLIB";

    @Override
    public int countData(Class<T> clss, SQLiteDatabase myDb) {
        return countData(clss, myDb, "");
    }

    @Override
    public int countData(Class<T> clss, SQLiteDatabase myDb, String whereCondition) {
        int count = 0;

        StringBuilder query = new StringBuilder("SELECT * FROM ");

        String tableName = "";
        if (clss.isAnnotationPresent(SQLiteTable.class)) {
            SQLiteTable SQLiteTable = clss.getAnnotation(SQLiteTable.class);
            if (SQLiteTable == null) {
                logD("countData: Annotation SQLiteTable Not Found");
                return count;
            } else {
                tableName = SQLiteTable.tableName();
            }
        } else {
            logD("countData: Annotation SQLiteTable Not Found");
            return count;
        }
        query.append(tableName);

        if (clss.getDeclaredFields().length == 0){
            logD("countData: Annotation Entity Not Found");
            return count;
        }

        if (myDb == null){
            logD("countData: SQLiteDatabase is null object references");
            return count;
        }

        if (whereCondition.length() > 0) {
            query.append(" ").append(whereCondition);
        }
        query.append(";");

        logDQuery(TAG, tableName+"_countData: "+query);

        Cursor cursor;
        try {
            cursor = myDb.rawQuery(query.toString(), null);
            count = cursor.getCount();
        } catch (Exception e) {
            e.printStackTrace();
            logD("countData: "+e.getMessage());
            return count;
        }
        cursor.close();
        return count;
    }

    @Override
    public List<T> readData(Class<T> clss, SQLiteDatabase myDb) {
        return readData(clss, myDb, "");
    }

    @Override
    @SuppressLint("Recycle")
    public List<T> readData(Class<T> clss, SQLiteDatabase myDb, String whereCondition) {
        List<T> list = new ArrayList<>();

        StringBuilder query = new StringBuilder("SELECT ");

        String tableName = "";
        if (clss.isAnnotationPresent(SQLiteTable.class)) {
            SQLiteTable SQLiteTable = clss.getAnnotation(SQLiteTable.class);
            if (SQLiteTable == null) {
                logD("readData: Annotation SQLiteTable Not Found");
                return list;
            } else {
                tableName = SQLiteTable.tableName();
            }
        } else {
            logD("readData: Annotation SQLiteTable Not Found");
            return list;
        }

        if (clss.getDeclaredFields().length == 0){
            logD("readData: Annotation Entity Not Found");
            return list;
        }

        if (myDb == null){
            logD("readData: SQLiteDatabase is null object references");
            return list;
        }

        String field = "";
        for (Field f : clss.getDeclaredFields()) {
            f.setAccessible(true);
            PrimaryKeyTypeData primaryKeyTypeData = f.getAnnotation(PrimaryKeyTypeData.class);
            if (primaryKeyTypeData != null) {
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
            IntegerTypeData _int = f.getAnnotation(IntegerTypeData.class);
            if (_int != null) {
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
            VarcharTypeData varcharTypeData = f.getAnnotation(VarcharTypeData.class);
            if (varcharTypeData != null) {
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
            TimeStampTypeData timestamp = f.getAnnotation(TimeStampTypeData.class);
            if (timestamp != null) {
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
            DecimalTypeData decimalTypeData = f.getAnnotation(DecimalTypeData.class);
            if (decimalTypeData != null){
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
            TextTypeData textTypeData = f.getAnnotation(TextTypeData.class);
            if (textTypeData != null){
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
        }

        if (query.length() > 0) {
            query.setLength(query.length() - 2);
        }

        query.append(" FROM ").append(tableName);

        if (whereCondition.length() > 0) {
            query.append(" ").append(whereCondition);
        }

        query.append(";");

        logDQuery(TAG, tableName+"_readData: "+query);

        Cursor cursor;

        cursor = myDb.rawQuery(query.toString(), null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                HashMap<String, Object> hashMap = new HashMap<>();
                for (Field f : clss.getDeclaredFields()) {
                    f.setAccessible(true);
                    PrimaryKeyTypeData primaryKeyTypeData = f.getAnnotation(PrimaryKeyTypeData.class);
                    if (primaryKeyTypeData != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getInt(cursor.getColumnIndex(field)));
                    }
                    IntegerTypeData _int = f.getAnnotation(IntegerTypeData.class);
                    if (_int != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getInt(cursor.getColumnIndex(field)));
                    }
                    VarcharTypeData varcharTypeData = f.getAnnotation(VarcharTypeData.class);
                    if (varcharTypeData != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getString(cursor.getColumnIndex(field)));
                    }
                    TimeStampTypeData timestamp = f.getAnnotation(TimeStampTypeData.class);
                    if (timestamp != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getString(cursor.getColumnIndex(field)));
                    }
                    DecimalTypeData decimalTypeData = f.getAnnotation(DecimalTypeData.class);
                    if (decimalTypeData != null){
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getDouble(cursor.getColumnIndex(field)));
                    }
                    TextTypeData textTypeData = f.getAnnotation(TextTypeData.class);
                    if (textTypeData != null){
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getString(cursor.getColumnIndex(field)));
                    }
                }

                Gson gson = new Gson();

                JsonElement jsonElement = gson.toJsonTree(hashMap);
                list.add(gson.fromJson(jsonElement, (Type) clss));
            }
        }

        cursor.close();
        return list;
    }

    @Override
    @SuppressLint("Recycle")
    public List<T> queryData(Class<T> clss, SQLiteDatabase myDb, String query) {

        List<T> list = new ArrayList<>();
        String tableName = "";
        if (clss.isAnnotationPresent(SQLiteTable.class)) {
            SQLiteTable SQLiteTable = clss.getAnnotation(SQLiteTable.class);
            if (SQLiteTable == null) {
                logD("queryData: Annotation SQLiteTable Not Found");
                return list;
            } else {
                tableName = SQLiteTable.tableName();
            }
        } else {
            logD("countData: Annotation SQLiteTable Not Found");
            return list;
        }

        if (clss.getDeclaredFields().length == 0){
            logD("queryData: Annotation Entity Not Found");
            return list;
        }

        if (myDb == null){
            logD("queryData: SQLiteDatabase is null object references");
            return list;
        }

        Cursor cursor;

        cursor = myDb.rawQuery(query, null);

        String field = "";

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                HashMap<String, Object> hashMap = new HashMap<>();
                for (Field f : clss.getDeclaredFields()) {
                    f.setAccessible(true);
                    PrimaryKeyTypeData primaryKeyTypeData = f.getAnnotation(PrimaryKeyTypeData.class);
                    if (primaryKeyTypeData != null) {
                        field = removeLast(press(f.toString()));
                        if (query.contains(field) || query.contains(tableName + ".*"))
                            hashMap.put(field, cursor.getInt(cursor.getColumnIndex(field)));
                    }
                    IntegerTypeData _int = f.getAnnotation(IntegerTypeData.class);
                    if (_int != null) {
                        field = removeLast(press(f.toString()));
                        if (query.contains(field) || query.contains(tableName + ".*"))
                            hashMap.put(field, cursor.getInt(cursor.getColumnIndex(field)));
                    }
                    VarcharTypeData varcharTypeData = f.getAnnotation(VarcharTypeData.class);
                    if (varcharTypeData != null) {
                        field = removeLast(press(f.toString()));
                        if (query.contains(field) || query.contains(tableName + ".*"))
                            hashMap.put(field, cursor.getString(cursor.getColumnIndex(field)));
                    }
                    TimeStampTypeData timestamp = f.getAnnotation(TimeStampTypeData.class);
                    if (timestamp != null) {
                        field = removeLast(press(f.toString()));
                        if (query.contains(field) || query.contains(tableName + ".*"))
                            hashMap.put(field, cursor.getString(cursor.getColumnIndex(field)));
                    }
                    DecimalTypeData decimalTypeData = f.getAnnotation(DecimalTypeData.class);
                    if (decimalTypeData != null){
                        field = removeLast(press(f.toString()));
                        if (query.contains(field) || query.contains(tableName + ".*"))
                            hashMap.put(field, cursor.getDouble(cursor.getColumnIndex(field)));
                    }
                    TextTypeData textTypeData = f.getAnnotation(TextTypeData.class);
                    if (textTypeData != null){
                        field = removeLast(press(f.toString()));
                        if (query.contains(field) || query.contains(tableName + ".*"))
                            hashMap.put(field, cursor.getString(cursor.getColumnIndex(field)));
                    }
                    JoinColumn joinColumn = f.getAnnotation(JoinColumn.class);
                    if (joinColumn != null) {
                        String withTable = joinColumn.withTable();
                        if (query.contains(withTable)) {
                            String columnName = joinColumn.columnName();
                            String alias = joinColumn.alias();

                            String modelName = removeLast(press(f.toString()));

                            if (alias.length() > 0) {
                                hashMap.put(modelName, cursor.getString(cursor.getColumnIndex(alias)));
                            } else {
                                hashMap.put(modelName, cursor.getString(cursor.getColumnIndex(columnName)));
                            }
                        }
                    }
                }

                Gson gson = new Gson();
                JsonElement jsonElement = gson.toJsonTree(hashMap);
                list.add(gson.fromJson(jsonElement, (Type) clss));
            }
        }

        cursor.close();
        return list;
    }

    @Override
    public boolean queryResult(Class<T> clss, SQLiteDatabase myDb, String query) {
        if (clss.isAnnotationPresent(SQLiteTable.class)) {
            SQLiteTable SQLiteTable = clss.getAnnotation(SQLiteTable.class);
            if (SQLiteTable == null) {
                logD("queryResult: Annotation SQLiteTable Not Found");
                return false;
            }
        } else {
            logD("queryResult: Annotation SQLiteTable Not Found");
            return false;
        }

        if (clss.getDeclaredFields().length == 0){
            logD("queryResult: Annotation Entity Not Found");
            return false;
        }

        if (myDb == null){
            logD("queryResult: SQLiteDatabase is null object references");
            return false;
        }

        try {
            myDb.execSQL(query);
            return true;
        } catch (Exception e){
            logD("queryResult: "+e.getMessage());
            return false;
        }
    }

    @Override
    public int queryCount(Class<T> clss, SQLiteDatabase myDb, String query) {
        int count = 0;
        if (clss.isAnnotationPresent(SQLiteTable.class)) {
            SQLiteTable SQLiteTable = clss.getAnnotation(SQLiteTable.class);
            if (SQLiteTable == null) {
                logD("countData: Annotation SQLiteTable Not Found");
                return count;
            }
        } else {
            logD("countData: Annotation SQLiteTable Not Found");
            return count;
        }

        if (clss.getDeclaredFields().length == 0){
            logD("countData: Annotation Entity Not Found");
            return count;
        }

        if (myDb == null){
            logD("countData: SQLiteDatabase is null object references");
            return count;
        }

        return (int) DatabaseUtils.longForQuery(myDb, query, null);
    }

    @Override
    public boolean deleteData(Class<T> clss, SQLiteDatabase myDb, String whereCondition) {
        String tableName = "";
        if (clss.isAnnotationPresent(SQLiteTable.class)) {
            SQLiteTable SQLiteTable = clss.getAnnotation(SQLiteTable.class);
            if (SQLiteTable == null){
                logD("deleteData: Annotation SQLiteTable Not Found");
                return false;
            } else {
                tableName = SQLiteTable.tableName();
            }
        } else {
            logD("deleteData: Annotation SQLiteTable Not Found");
            return false;
        }

        if (clss.getDeclaredFields().length == 0){
            logD("deleteData: Annotation Entity Not Found");
            return false;
        }

        if(whereCondition.length()==0){
            whereCondition = "1";
        }

        whereCondition = removeWhere(whereCondition);

        if (myDb == null){
            logD("deleteData: SQLiteDatabase is null object references");
            return false;
        }

        try {
            long res = myDb.delete(tableName, whereCondition, new String[]{});
            return res > 0;
        } catch (Exception e) {
            e.printStackTrace();
            logD("deleteData: "+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean insertData(Class<T> clss, SQLiteDatabase myDb, T data) {
        String tableName = "";
        if (clss.isAnnotationPresent(SQLiteTable.class)) {
            SQLiteTable SQLiteTable = clss.getAnnotation(SQLiteTable.class);
            if (SQLiteTable == null){
                logD("insertData: Annotation SQLiteTable Not Found");
                return false;
            } else {
                tableName = SQLiteTable.tableName();
            }
        } else {
            logD("countData: Annotation SQLiteTable Not Found");
            return false;
        }

        if (clss.getDeclaredFields().length == 0){
            logD("insertData: Annotation Entity Not Found");
            return false;
        }

        if (myDb == null){
            logD("insertData: SQLiteDatabase is null object references");
            return false;
        }

        List<String> value = new ArrayList<>();
        List<String> key = new ArrayList<>();

        String field = "";
        for (Field f : clss.getDeclaredFields()) {
            f.setAccessible(true);
            PrimaryKeyTypeData primaryKeyTypeData = f.getAnnotation(PrimaryKeyTypeData.class);
            if (primaryKeyTypeData != null) {
                if (!primaryKeyTypeData.autoGenerate()){
                    field = removeLast(press(f.toString()));
                    key.add(field);
                    try {
                        if (f.get(data) != null)
                            value.add(String.valueOf(f.get(data)));
                        else
                            value.add(null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        logD("insertData: "+e.getMessage());
                    }
                }
            }
            IntegerTypeData _int = f.getAnnotation(IntegerTypeData.class);
            if (_int != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    if (f.get(data) != null)
                        value.add(String.valueOf(f.get(data)));
                    else
                        value.add(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertData: "+e.getMessage());
                }
            }
            VarcharTypeData varcharTypeData = f.getAnnotation(VarcharTypeData.class);
            if (varcharTypeData != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    if (f.get(data) != null)
                        value.add(String.valueOf(f.get(data)));
                    else
                        value.add(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertData: "+e.getMessage());
                }
            }
            TimeStampTypeData timestamp = f.getAnnotation(TimeStampTypeData.class);
            if (timestamp != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    if (f.get(data) != null)
                        value.add(String.valueOf(f.get(data)));
                    else
                        value.add(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertData: "+e.getMessage());
                }
            }
            DecimalTypeData decimalTypeData = f.getAnnotation(DecimalTypeData.class);
            if (decimalTypeData != null){
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    if (f.get(data) != null)
                        value.add(String.valueOf(f.get(data)));
                    else
                        value.add(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertData: "+e.getMessage());
                }
            }
            TextTypeData textTypeData = f.getAnnotation(TextTypeData.class);
            if (textTypeData != null){
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    if (f.get(data) != null)
                        value.add(String.valueOf(f.get(data)));
                    else
                        value.add(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD("insertData: "+e.getMessage());
                }
            }
        }

        try {
            ContentValues values = new ContentValues();
            for (int i = 0; i < key.size(); i++) {
                values.put(key.get(i), value.get(i));
            }

            long res = myDb.insert(tableName, null, values);
            return res>0;
        } catch (Exception e) {
            e.printStackTrace();
            logD("insertData: "+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updatedData(Class<T> clss, SQLiteDatabase myDb, T data, String whereCondition, String[] fieldToUpdate) {
        String tableName = "";
        if (clss.isAnnotationPresent(SQLiteTable.class)) {
            SQLiteTable SQLiteTable = clss.getAnnotation(SQLiteTable.class);
            if (SQLiteTable == null){
                logD("updatedData: Annotation SQLiteTable Not Found");
                return false;
            } else {
                tableName = SQLiteTable.tableName();
            }
        } else {
            logD("updatedData: Annotation SQLiteTable Not Found");
            return false;
        }

        if (clss.getDeclaredFields().length == 0){
            logD("updatedData: Annotation Entity Not Found");
            return false;
        }

        if (myDb == null){
            logD("updatedData: SQLiteDatabase is null object references");
            return false;
        }

        if (whereCondition.length()==0){
            whereCondition = " 1";
        }

        whereCondition = removeWhere(whereCondition);

        ArrayList<String> fields = new ArrayList<>(Arrays.asList(fieldToUpdate));

        List<String> value = new ArrayList<>();
        List<String> key = new ArrayList<>();
        String field = "";
        for (Field f : clss.getDeclaredFields()) {
            f.setAccessible(true);
            IntegerTypeData _int = f.getAnnotation(IntegerTypeData.class);
            if (_int != null) {
                field = removeLast(press(f.toString()));
                if (fields.contains(field)){
                    key.add(field);
                    try {
                        if (f.get(data) != null)
                            value.add(String.valueOf(f.get(data)));
                        else
                            value.add(null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        logD("updatedData: "+e.getMessage());
                    }
                }
            }
            VarcharTypeData varcharTypeData = f.getAnnotation(VarcharTypeData.class);
            if (varcharTypeData != null) {
                field = removeLast(press(f.toString()));
                if (fields.contains(field)) {
                    key.add(field);
                    try {
                        if (f.get(data) != null)
                            value.add(String.valueOf(f.get(data)));
                        else
                            value.add(null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        logD("updatedData: "+e.getMessage());
                    }
                }
            }
            TimeStampTypeData timestamp = f.getAnnotation(TimeStampTypeData.class);
            if (timestamp != null) {
                field = removeLast(press(f.toString()));
                if (fields.contains(field)) {
                    key.add(field);
                    try {
                        if (f.get(data) != null)
                            value.add(String.valueOf(f.get(data)));
                        else
                            value.add(null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        logD("updatedData: "+e.getMessage());
                    }
                }
            }
            DecimalTypeData decimalTypeData = f.getAnnotation(DecimalTypeData.class);
            if (decimalTypeData != null){
                field = removeLast(press(f.toString()));
                if (fields.contains(field)) {
                    key.add(field);
                    try {
                        if (f.get(data) != null)
                            value.add(String.valueOf(f.get(data)));
                        else
                            value.add(null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        logD("updatedData: "+e.getMessage());
                    }
                }
            }
            TextTypeData textTypeData = f.getAnnotation(TextTypeData.class);
            if (textTypeData != null){
                field = removeLast(press(f.toString()));
                if (fields.contains(field)) {
                    key.add(field);
                    try {
                        if (f.get(data) != null)
                            value.add(String.valueOf(f.get(data)));
                        else
                            value.add(null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        logD("updatedData: "+e.getMessage());
                    }
                }
            }
        }

        try {
            ContentValues values = new ContentValues();
            for (int i = 0; i < key.size(); i++) {
                values.put(key.get(i), value.get(i));
            }
            long res = myDb.update(tableName, values,whereCondition, new String[]{});
            return res>0;
        } catch (Exception e) {
            e.printStackTrace();
            logD("updatedData: "+e.getMessage());
            return false;
        }
    }

    private String press(String s) {
        return s.substring(s.lastIndexOf('.') + 1) + ", ";
    }

    private String removeLast(String s) {
        return s.substring(0, s.length() - 2);
    }

    private void logD(String msg){
        Log.e(TAG, "logD: "+msg, null);
    }

    private void logDQuery(String TAG, String msg){
        Log.d(TAG, msg);
    }

    private String removeWhere(String a){
        String strTemp = a.toUpperCase();
        String toRemove = "WHERE";
        int x = strTemp.indexOf(toRemove);
        if (x != -1) a = a.substring(0,x) + a.substring(x+toRemove.length(),a.length());
        return a;
    }
}