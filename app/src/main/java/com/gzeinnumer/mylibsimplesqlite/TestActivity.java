package com.gzeinnumer.mylibsimplesqlite;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.gzeinnumer.mylibsimplesqlite.entity.Table1;
import com.gzeinnumer.mylibsimplesqlite.helper.GblVariabel;

import java.util.ArrayList;
import java.util.List;


public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        GblVariabel.initDb(getApplicationContext());

        Table1 table1 = new Table1();

//        boolean istrue = table1.insert();
//        Log.d(TAG, "onCreate1: "+istrue);

//        boolean istrue2 = table1.update();
//        Log.d(TAG, "onCreate2: "+istrue2);

//        boolean istrue3 = table1.delete();
//        Log.d(TAG, "onCreate3: "+istrue3);

//        int count = table1.count();
//        Log.d(TAG, "onCreate4: "+count);

//        int count2 = table1.count2();
//        Log.d(TAG, "onCreate5: "+count2);

//        List<Table1> read = table1.read();
//        Log.d(TAG, "onCreate6: "+read.get(0).getName());
//        Log.d(TAG, "onCreate6: "+read.size());

//        List<Table1> read2 = table1.read2();
//        Log.d(TAG, "onCreate7: "+read2.get(0).getName());
//        Log.d(TAG, "onCreate7: "+read2.size());

//        List<Table1> listQuery = table1.query();
//        Log.d(TAG, "onCreate8: "+listQuery.get(0).getName());
//        Log.d(TAG, "onCreate8: "+listQuery.get(0).getTable2_name());
//        Log.d(TAG, "onCreate8: "+listQuery.size());

//        boolean queryUpdate = table1.queryResultUpdate();
//        Log.d(TAG, "onCreate9: "+queryUpdate);
    }
}