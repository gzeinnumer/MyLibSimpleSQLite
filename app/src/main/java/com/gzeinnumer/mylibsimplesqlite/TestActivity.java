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

//        List<Table1> list = table1.read();
//        Log.d(TAG, "onCreate1: "+list.get(9).getFlag_active());
//        if (list.get(9).getFlag_active() == 1){
//            Log.d(TAG, "onCreate1: integer");
//        } else {
//            Log.d(TAG, "onCreate1: Bukan Integer");
//        }
//        Log.d(TAG, "onCreate1: "+list.size());
//
//        if (list.get(0).getName() == null){
//            Log.d(TAG, "onCreate1: tidak  ada kutip");
//        } else {
//            Log.d(TAG, "onCreate1: ada kutip");
//        }
//        List<Table1> listQuery = table1.query();
//        Log.d(TAG, "onCreate2: "+listQuery.get(0).getName());
//        Log.d(TAG, "onCreate2: "+listQuery.get(0).getTable2_name());

//        boolean istrue = table1.insert(new Table1(500, "Zein", 1.3, "Decs", 1,"2020-12-12"));
//        boolean istrue = table1.insert(new Table1(null, 1.3, "Decs", 1,"2020-12-12"));
//        boolean istrue = table1.insert(new Table1(null, 0, "Decs", 1,"2020-12-12"));
//        Log.d(TAG, "onCreate3: "+istrue);

//        boolean istrue2 = table1.update(new Table1(500, "Zein", 1.6, "Decs", 1,"2020-11-11"));
//        Log.d(TAG, "onCreate4: "+istrue2);

//        int count = table1.count();
//        Log.d(TAG, "onCreate5: "+count);
      
//        boolean istrue3 = table1.delete();
//        Log.d(TAG, "onCreate: "+istrue3);

//        Table1 data = new Table1();
//        data.setName("Name Update");
//        data.setDesc("Desc Update");
//        data.setFlag_active(1);
//        table1.update(data);

//        boolean isTrue4 = table1.queryResultUpdate();
//        Log.d(TAG, "onCreate6: "+isTrue4);
//
//        Table1 data = table1.getSingleData();
//        Log.d(TAG, "onCreate7: "+data.getName());
//        Log.d(TAG, "onCreate7: "+data.getDesc());
    }
}