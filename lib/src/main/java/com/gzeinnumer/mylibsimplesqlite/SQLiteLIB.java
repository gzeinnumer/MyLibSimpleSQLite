package com.gzeinnumer.mylibsimplesqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.gzeinnumer.mylibsimplesqlite.helper.InterfaceDaoSQLite;
import com.gzeinnumer.mylibsimplesqlite.struck.JoinColumn;
import com.gzeinnumer.mylibsimplesqlite.struck.Table;
import com.gzeinnumer.mylibsimplesqlite.typeData.Decimal;
import com.gzeinnumer.mylibsimplesqlite.typeData.Int;
import com.gzeinnumer.mylibsimplesqlite.typeData.PrimaryKey;
import com.gzeinnumer.mylibsimplesqlite.typeData.Text;
import com.gzeinnumer.mylibsimplesqlite.typeData.TimeStamp;
import com.gzeinnumer.mylibsimplesqlite.typeData.Varchar;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class SQLiteLIB<T> implements InterfaceDaoSQLite<T> {

    private static final String TAG = "SQLiteLIB";

    @Override
    public int countData(Class<T> clss, SQLiteDatabase myDb) {
        return countData(clss, myDb, "");
    }

    @Override
    public int countData(Class<T> clss, SQLiteDatabase myDb, String condition) {
        int count = 0;

        StringBuilder query = new StringBuilder("SELECT * FROM ");

        String tableName = "";
        if (clss.isAnnotationPresent(Table.class)) {
            Table table = clss.getAnnotation(Table.class);
            if (table == null) {
                logD("Annotation Table Not Found");
                return count;
            } else {
                tableName = table.tableName();
            }
        }
        query.append(tableName);

        if (clss.getDeclaredFields().length == 0){
            logD("Annotation Entity Not Found");
            return count;
        }

        if (condition.length() > 0) {
            query.append(" WHERE ").append(condition);
        }
        query.append(";");

        Cursor cursor;
        try {
            cursor = myDb.rawQuery(query.toString(), null);
            count = cursor.getCount();
        } catch (Exception e) {
            e.printStackTrace();
            logD(e.getMessage());
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
    public List<T> readData(Class<T> clss, SQLiteDatabase myDb, String condition) {
        List<T> list = new ArrayList<>();

        StringBuilder query = new StringBuilder("SELECT ");

        String tableName = "";
        if (clss.isAnnotationPresent(Table.class)) {
            Table table = clss.getAnnotation(Table.class);
            if (table == null) {
                logD("Annotation Table Not Found");
                return list;
            } else {
                tableName = table.tableName();
            }
        }

        if (clss.getDeclaredFields().length == 0){
            logD("Annotation Entity Not Found");
            return list;
        }

        String field = "";
        for (Field f : clss.getDeclaredFields()) {
            PrimaryKey primaryKey = f.getAnnotation(PrimaryKey.class);
            if (primaryKey != null) {
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
            Int _int = f.getAnnotation(Int.class);
            if (_int != null) {
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
            Varchar varchar = f.getAnnotation(Varchar.class);
            if (varchar != null) {
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
            TimeStamp timestamp = f.getAnnotation(TimeStamp.class);
            if (timestamp != null) {
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
            Decimal decimal = f.getAnnotation(Decimal.class);
            if (decimal != null){
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
            Text text = f.getAnnotation(Text.class);
            if (text != null){
                field = press(f.toString());
                query.append(tableName).append(".").append(field);
            }
        }

        if (query.length() > 0) {
            query.setLength(query.length() - 2);
        }

        query.append(" FROM ").append(tableName);

        if (condition.length() > 0) {
            query.append(" WHERE ").append(tableName).append(".").append(condition);
        }

        query.append(";");

        Cursor cursor;

        cursor = myDb.rawQuery(query.toString(), null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                HashMap<String, Object> hashMap = new HashMap<>();
                for (Field f : clss.getDeclaredFields()) {
                    PrimaryKey primaryKey = f.getAnnotation(PrimaryKey.class);
                    if (primaryKey != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getInt(cursor.getColumnIndex(field)));
                    }
                    Int _int = f.getAnnotation(Int.class);
                    if (_int != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getInt(cursor.getColumnIndex(field)));
                    }
                    Varchar varchar = f.getAnnotation(Varchar.class);
                    if (varchar != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getString(cursor.getColumnIndex(field)));
                    }
                    TimeStamp timestamp = f.getAnnotation(TimeStamp.class);
                    if (timestamp != null) {
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getString(cursor.getColumnIndex(field)));
                    }
                    Decimal decimal = f.getAnnotation(Decimal.class);
                    if (decimal != null){
                        field = removeLast(press(f.toString()));
                        hashMap.put(field, cursor.getDouble(cursor.getColumnIndex(field)));
                    }
                    Text text = f.getAnnotation(Text.class);
                    if (text != null){
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
        if (clss.isAnnotationPresent(Table.class)) {
            Table table = clss.getAnnotation(Table.class);
            if (table == null) {
                logD("Annotation Table Not Found");
                return list;
            } else {
                tableName = table.tableName();
            }
        }

        if (clss.getDeclaredFields().length == 0){
            logD("Annotation Entity Not Found");
            return list;
        }

        Cursor cursor;

        cursor = myDb.rawQuery(query, null);

        String field = "";

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                HashMap<String, Object> hashMap = new HashMap<>();
                for (Field f : clss.getDeclaredFields()) {
                    PrimaryKey primaryKey = f.getAnnotation(PrimaryKey.class);
                    if (primaryKey != null) {
                        field = removeLast(press(f.toString()));
                        if (query.contains(field) || query.contains(tableName + ".*"))
                            hashMap.put(field, cursor.getInt(cursor.getColumnIndex(field)));
                    }
                    Int _int = f.getAnnotation(Int.class);
                    if (_int != null) {
                        field = removeLast(press(f.toString()));
                        if (query.contains(field) || query.contains(tableName + ".*"))
                            hashMap.put(field, cursor.getInt(cursor.getColumnIndex(field)));
                    }
                    Varchar varchar = f.getAnnotation(Varchar.class);
                    if (varchar != null) {
                        field = removeLast(press(f.toString()));
                        if (query.contains(field) || query.contains(tableName + ".*"))
                            hashMap.put(field, cursor.getString(cursor.getColumnIndex(field)));
                    }
                    TimeStamp timestamp = f.getAnnotation(TimeStamp.class);
                    if (timestamp != null) {
                        field = removeLast(press(f.toString()));
                        if (query.contains(field) || query.contains(tableName + ".*"))
                            hashMap.put(field, cursor.getString(cursor.getColumnIndex(field)));
                    }
                    Decimal decimal = f.getAnnotation(Decimal.class);
                    if (decimal != null){
                        field = removeLast(press(f.toString()));
                        if (query.contains(field) || query.contains(tableName + ".*"))
                            hashMap.put(field, cursor.getDouble(cursor.getColumnIndex(field)));
                    }
                    Text text = f.getAnnotation(Text.class);
                    if (text != null){
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
    public boolean deleteData(Class<T> clss, SQLiteDatabase myDb, String condition) {
        StringBuilder query = new StringBuilder("DELETE FROM ");

        String tableName = "";
        if (clss.isAnnotationPresent(Table.class)) {
            Table table = clss.getAnnotation(Table.class);
            if (table == null){
                logD("Annotation Table Not Found");
                return false;
            } else {
                tableName = table.tableName();
            }
        }
        query.append(tableName);

        if (clss.getDeclaredFields().length == 0){
            logD("Annotation Entity Not Found");
            return false;
        }

        if (condition.length() > 0) {
            query.append(" WHERE ").append(condition);
        }
        query.append(";");

        try {
            myDb.execSQL(query.toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logD(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean insertData(Class<T> clss, SQLiteDatabase myDb, T data) {
        String tableName = "";
        if (clss.isAnnotationPresent(Table.class)) {
            Table table = clss.getAnnotation(Table.class);
            if (table == null){
                logD("Annotation Table Not Found");
                return false;
            } else {
                tableName = table.tableName();
            }
        }

        if (clss.getDeclaredFields().length == 0){
            logD("Annotation Entity Not Found");
            return false;
        }

        List<String> value = new ArrayList<>();
        List<String> key = new ArrayList<>();

        String field = "";
        for (Field f : clss.getDeclaredFields()) {
            PrimaryKey primaryKey = f.getAnnotation(PrimaryKey.class);
            if (primaryKey != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    value.add(String.valueOf(f.get(data)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD(e.getMessage());
                }
            }
            Int _int = f.getAnnotation(Int.class);
            if (_int != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    value.add(String.valueOf(f.get(data)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD(e.getMessage());
                }
            }
            Varchar varchar = f.getAnnotation(Varchar.class);
            if (varchar != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    value.add((String) f.get(data));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD(e.getMessage());
                }
            }
            TimeStamp timestamp = f.getAnnotation(TimeStamp.class);
            if (timestamp != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    value.add((String) f.get(data));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD(e.getMessage());
                }
            }
            Decimal decimal = f.getAnnotation(Decimal.class);
            if (decimal != null){
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    value.add(String.valueOf(f.get(data)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD(e.getMessage());
                }
            }
            Text text = f.getAnnotation(Text.class);
            if (text != null){
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    value.add((String) f.get(data));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD(e.getMessage());
                }
            }
        }

        logD(String.valueOf(key.size()));
        logD(String.valueOf(value.size()));

        try {
            ContentValues values = new ContentValues();
            for (int i = 0; i < key.size(); i++) {
                values.put(key.get(i), value.get(i));
            }
            myDb.insert(tableName, null, values);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logD(e.getMessage());
            return false;
        }
    }

    public boolean updatedData(Class<T> clss, SQLiteDatabase myDb, T data, String whereCondition) {
        String tableName = "";
        if (clss.isAnnotationPresent(Table.class)) {
            Table table = clss.getAnnotation(Table.class);
            if (table == null){
                logD("Annotation Table Not Found");
                return false;
            } else {
                tableName = table.tableName();
            }
        }

        if (clss.getDeclaredFields().length == 0){
            logD("Annotation Entity Not Found");
            return false;
        }

        List<String> value = new ArrayList<>();
        List<String> key = new ArrayList<>();String field = "";
        for (Field f : clss.getDeclaredFields()) {
            Int _int = f.getAnnotation(Int.class);
            if (_int != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    value.add(String.valueOf(f.get(data)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD(e.getMessage());
                }
            }
            Varchar varchar = f.getAnnotation(Varchar.class);
            if (varchar != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    value.add((String) f.get(data));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD(e.getMessage());
                }
            }
            TimeStamp timestamp = f.getAnnotation(TimeStamp.class);
            if (timestamp != null) {
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    value.add((String) f.get(data));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD(e.getMessage());
                }
            }
            Decimal decimal = f.getAnnotation(Decimal.class);
            if (decimal != null){
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    value.add(String.valueOf(f.get(data)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD(e.getMessage());
                }
            }
            Text text = f.getAnnotation(Text.class);
            if (text != null){
                field = removeLast(press(f.toString()));
                key.add(field);
                try {
                    value.add((String) f.get(data));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logD(e.getMessage());
                }
            }
        }

        try {
            ContentValues values = new ContentValues();
            for (int i = 0; i < key.size(); i++) {
                values.put(key.get(i), value.get(i));
            }
            myDb.update(tableName, values,whereCondition, new String[]{});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logD(e.getMessage());
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
}