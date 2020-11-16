package com.gzeinnumer.mylibsimplesqlite.entity;

import com.gzeinnumer.mylibsqlitelib.SQLiteLIB;
import com.gzeinnumer.mylibsqlitelib.struck.Table;
import com.gzeinnumer.mylibsqlitelib.typeData.Int;
import com.gzeinnumer.mylibsqlitelib.typeData.PrimaryKey;
import com.gzeinnumer.mylibsqlitelib.typeData.Varchar;

import java.util.List;

@Table(tableName = "table2")
public class Table2 extends SQLiteLIB<Table2> {

    @PrimaryKey
    private int id;

    @Varchar
    private String name;

    @Int
    private int id_table1;

    public Table2() {}

    public Table2(int id, String name, int id_table1) {
        this.id = id;
        this.name = name;
        this.id_table1 = id_table1;
    }

    public boolean insert(Table2 data) {
        return false;
    }

    public boolean update(Table2 data, String whereCondition) {
        return false;
    }

    public boolean delete(String whereCondition) {
        return false;
    }

    public int count() {
        return 0;
    }

    public List<Table2> read() {
        return null;
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

    public int getId_table1() {
        return id_table1;
    }

    public void setId_table1(int id_table1) {
        this.id_table1 = id_table1;
    }
}
