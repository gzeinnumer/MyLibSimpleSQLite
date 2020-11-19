package com.gzeinnumer.mylibsimplesqlite.entity;

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

//    @PrimaryKeyTypeData(autoGenerate = false)
    @PrimaryKeyTypeData
    private int id;
    @VarcharTypeData
    private String name;
    @DecimalTypeData
    private double rating;
    @TextTypeData
    private String desc;
    @IntegerTypeData
    private int flag_active;
    @TimeStampTypeData
    private String created_at;

//    @JoinColumn(withTable = "table2", columnName = "name")
    @JoinColumn(withTable = "table2", columnName = "name", alias = "table2_name")
    public String table2_name;

    public Table1() {}

    public Table1(String name, double rating, String desc, int flag_active, String created_at) {
        this.name = name;
        this.rating = rating;
        this.desc = desc;
        this.flag_active = flag_active;
        this.created_at = created_at;
    }

    public Table1(int id, String name, double rating, String desc, int flag_active, String created_at) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.desc = desc;
        this.flag_active = flag_active;
        this.created_at = created_at;
    }

    public boolean insert(Table1 data) {
        return insertData(Table1.class, GblVariabel.myDb, data);
    }

    public boolean update(Table1 data) {
        String condition = "id='500'";                        //for single condition
        //String condition = "id='1' AND flag_Active='1'";    //for multi condition
        return updatedData(Table1.class, GblVariabel.myDb, data, condition);  // return true/false
    }

    public boolean delete() {
        String condition = "id='500'";                        //for single condition
        //String condition = "id='1' AND flag_Active='1'";    //for multi condition
        return deleteData(Table1.class, GblVariabel.myDb, condition);
    }

    public int count() {
//        String condition = "id='1'";
//        return countData(Table1.class, GblVariabel.myDb, condition);
        return countData(Table1.class, GblVariabel.myDb);
    }

    public List<Table1> read() {
//        String condition = "id='1'";
//        return readData(Table1.class, GblVariabel.myDb, condition);
        return readData(Table1.class, GblVariabel.myDb);
    }

    public List<Table1> query(){
        String query ="SELECT table1.*, table2.name AS table2_name FROM table1 JOIN table2 ON table2.id_table1 = table1.id;";
        return queryData(Table1.class, GblVariabel.myDb, query);
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
