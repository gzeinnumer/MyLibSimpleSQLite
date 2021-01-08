package com.gzeinnumer.mylibsimplesqlite.entity;

import android.database.sqlite.SQLiteDatabase;

import com.gzeinnumer.mylibsimplesqlite.SQLiteLIB;
import com.gzeinnumer.mylibsimplesqlite.helper.GblVariabel;
import com.gzeinnumer.mylibsimplesqlite.struck.JoinColumn;
import com.gzeinnumer.mylibsimplesqlite.struck.SQLiteTable;
import com.gzeinnumer.mylibsimplesqlite.typeData.DecimalTypeData;
import com.gzeinnumer.mylibsimplesqlite.typeData.IntegerTypeData;
import com.gzeinnumer.mylibsimplesqlite.typeData.PrimaryKeyTypeData;
import com.gzeinnumer.mylibsimplesqlite.typeData.TextTypeData;
import com.gzeinnumer.mylibsimplesqlite.typeData.TimeStampTypeData;
import com.gzeinnumer.mylibsimplesqlite.typeData.VarcharTypeData;

import java.util.List;

@SQLiteTable(tableName = "table1")
public class Table1 extends SQLiteLIB<Table1> {
    @PrimaryKeyTypeData private int id;                 // for Primary key
    // Default AutoIncrement true
    // @PrimaryKeyTypeData(autoGenerate = false) to disable
    @VarcharTypeData    private String name;            // for Varchar
    @DecimalTypeData    private double rating;          // for Decimal/Real
    @TextTypeData       private String desc;            // for String
    @IntegerTypeData    private int flag_active;        // for Integer
    @TimeStampTypeData  private String created_at;      // for String

    // for join column from other table
    //@JoinColumn(withTable = "table2", columnName = "name")
    @JoinColumn(withTable = "table2", columnName = "name", alias = "table2_name")
    private String table2_name;

    private SQLiteDatabase sqLiteDatabase;

    public Table1() {}

    public Table1(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    //INSERT INTO table1 (name, rating, desc, flag_active, created_at) VALUES ('Zein', '10.0.', 'Android Programmer', '1', '12-12-2020');
    public boolean insert() {
        Table1 data = new Table1();
        data.setName("Zein");
        data.setRating(10.0);
        data.setDesc("Android Programmer");
        data.setFlag_active(1);
        data.setCreated_at("12-12-2020");

        return insertData(Table1.class, sqLiteDatabase, data);
    }

    //UPDATE table1 SET name='Name Update', desc='Desc Update', flag_active='0' WHERE id='1';
    public boolean update() {
        //set your value to update
        Table1 data = new Table1();
        data.setName("Name Update1");
        data.setDesc("Desc Update");
        data.setFlag_active(0);

        //String condition = "";                                    //to update all data
        //String condition = "WHERE 1";                             //to update all data
        String condition = "WHERE id='1'";                          //for single condition
        //String condition = "WHERE id='1' AND flag_Active='1'";    //for multi condition

        String[] fieldToUpdate = new String[]{
                "name",
                "desc",
                "flag_active"
        }; // put all field that you want to update

        return updatedData(Table1.class, sqLiteDatabase, data, condition, fieldToUpdate);  // return true/false
    }

    //DELETE FROM table1 WHERE id='1';
    public boolean delete() {
        //String condition = "";                                    //to delete all data
        //String condition = "WHERE 1";                             //to delete all data
        String condition = "WHERE id='1'";                          //for single condition
        //String condition = "WHERE id='1' AND flag_Active='1'";    //for multi condition

        return deleteData(Table1.class, sqLiteDatabase, condition);
    }

    //type 1 SELECT COUNT(*) FROM table1;
    public int count() {
        return countData(Table1.class, sqLiteDatabase);
    }

    //type 2 SELECT COUNT(*) FROM table1 WHERE flag_Active='1';
    public int count2() {
        //String condition = "WHERE 1";                             //count all
        String condition = "WHERE flag_active='1'";                 //for single condition
        //String condition = "WHERE id='1' AND flag_Active='1'";    //for multi condition
        return countData(Table1.class, sqLiteDatabase, condition);
    }

    //type 3 Your Custom Query
    // SELECT COUNT(id) FROM table1;
    public int queryCount() {
        String query = "SELECT COUNT(id) FROM table1;";
        return queryCount(Table1.class, sqLiteDatabase, query);
    }

    //type 1 SElECT * FROM table1;
    public List<Table1> read() {
        return readData(Table1.class, sqLiteDatabase);
    }

    //type 2 SELECT * FROM table1 WHERE flag_active='1';
    public List<Table1> read2() {
        //String condition = "";                                      //read all
        //String condition = "WHERE 1";                             //read all
        String condition = "WHERE flag_active='1'";                 //for single condition
        //String condition = "WHERE id='1' AND flag_Active='1'";    //for multi condition

        return readData(Table1.class, sqLiteDatabase, condition);
    }

    public List<Table1> query(){
        String query ="SELECT table1.*, table2.name AS table2_name FROM table1 JOIN table2 ON table2.id_table1 = table1.id;";
        return queryData(Table1.class, sqLiteDatabase, query);
    }

    public boolean queryResultUpdate() {
        String query = "UPDATE table1 SET flag_Active='2' WHERE id='1'";
        return queryResult(Table1.class, sqLiteDatabase, query);
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
