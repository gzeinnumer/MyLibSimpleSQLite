package com.gzeinnumer.mylibsimplesqlite.entity.old;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.gzeinnumer.mylibsimplesqlite.helper.GblVariabel;

import java.util.ArrayList;
import java.util.List;

public class Table_1 {

    private final String TABLE = "table1";
    private final String KEY_ID = "id";
    private final String KEY_NAME = "name";
    private final String KEY_RATING = "rating";
    private final String KEY_DESC = "desc";
    private final String KEY_FLAG_ACTIVE = "flag_active";
    private final String KEY_CREATED_AT = "created_at";
    private final String KEY_TABLE2_NAME = "table2_name";

    private int id;
    private String name;
    private double rating;
    private String desc;
    private int flag_active;
    private String created_at;
    private String table2_name;

    private static final String TAG = "M_Category_Trainer";

    public Table_1() {}

    public Table_1(int id, String name, double rating, String desc, int flag_active, String created_at) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.desc = desc;
        this.flag_active = flag_active;
        this.created_at = created_at;
    }

    public boolean insert(Table_1 data){
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_ID, data.getId());
            values.put(KEY_NAME, data.getName());
            values.put(KEY_RATING, data.getRating());
            values.put(KEY_DESC, data.getDesc());
            values.put(KEY_FLAG_ACTIVE, data.getFlag_active());
            values.put(KEY_CREATED_AT, data.getCreated_at());
            GblVariabel.myDb.insert(TABLE, null, values);
            return true;
        } catch (Exception e) {
            Log.d(TAG, "insert: " + e.getMessage());
            return false;
        }
    }

    public boolean update(Table_1 data){
        String whereCondition = "id = '500'";
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, data.getName());
        contentValues.put(KEY_RATING, data.getRating());
        contentValues.put(KEY_DESC, data.getDesc());
        contentValues.put(KEY_FLAG_ACTIVE, data.getFlag_active());
        contentValues.put(KEY_CREATED_AT, data.getCreated_at());
        long row = GblVariabel.myDb.update(TABLE, contentValues, whereCondition, null);
        if (row > 0) {
            Log.d(TAG, "update: success");
            return true;
        } else {
            Log.d(TAG, "update: failed");
            return false;
        }
    }

    public boolean delete(){
        String whereCondition = "WHERE id='500';";
        try{
            String query = "DELETE FROM " + TABLE + whereCondition;
            GblVariabel.myDb.execSQL(query);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "deleted failed " + e.getMessage());
            return false;
        }
    }

    public int count(){
        int count = 0;
        try {
            Cursor cursor = GblVariabel.myDb.rawQuery("SELECT id FROM "+TABLE+" WHERE is_uploaded = '0'", null);
            count = cursor.getCount();
            cursor.close();
        } catch (Exception e){
            Log.d(TAG, "count: "+e.getMessage());
        }
        return count;
    }

    public ArrayList<Table_1> read(){
        Cursor cursor;
        ArrayList<Table_1> data = new ArrayList<>();
        cursor = GblVariabel.myDb.rawQuery("SELECT * FROM " + TABLE +" WHERE flag_active = '1'", null);
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                Table_1 current = new Table_1();
                current.id = cursor.getInt(cursor.getColumnIndex(this.KEY_ID));
                current.name = cursor.getString(cursor.getColumnIndex(this.KEY_NAME));
                current.rating = cursor.getInt(cursor.getColumnIndex(this.KEY_RATING));
                current.desc = cursor.getString(cursor.getColumnIndex(this.KEY_DESC));
                current.flag_active = cursor.getInt(cursor.getColumnIndex(this.KEY_FLAG_ACTIVE));
                current.created_at = cursor.getString(cursor.getColumnIndex(this.KEY_CREATED_AT));
                data.add(current);
            }
        }
        cursor.close();
        return data;
    }

    @SuppressLint("Recycle")
    public List<Table_1> query(){
        List<Table_1> current = new ArrayList<>();
        String query ="SELECT table1.*, table2.name AS table2_name FROM table1 JOIN table2 ON table2.id_table1 = table1.id;";
        Cursor cursor = GblVariabel.myDb.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (cursor.moveToNext()){
                Table_1 data = new Table_1();
                data.id = cursor.getInt(cursor.getColumnIndex(this.KEY_ID));
                data.name = cursor.getString(cursor.getColumnIndex(this.KEY_NAME));
                data.rating = cursor.getInt(cursor.getColumnIndex(this.KEY_RATING));
                data.desc = cursor.getString(cursor.getColumnIndex(this.KEY_DESC));
                data.flag_active = cursor.getInt(cursor.getColumnIndex(this.KEY_FLAG_ACTIVE));
                data.created_at = cursor.getString(cursor.getColumnIndex(this.KEY_CREATED_AT));
                data.table2_name = cursor.getString(cursor.getColumnIndex(this.KEY_TABLE2_NAME));
                current.add(data);
            }
        }
        return current;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getFlag_active() {
        return flag_active;
    }

    public void setFlag_active(int flag_active) {
        this.flag_active = flag_active;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTable2_name() {
        return table2_name;
    }

    public void setTable2_name(String table2_name) {
        this.table2_name = table2_name;
    }
}

