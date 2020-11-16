package com.gzeinnumer.mylibsimplesqlite.entity.old;

public class Table_2 {

    private final String TABLE = "table1";
    private final String KEY_ID = "id";
    private final String KEY_NAME = "name";
    private final String KEY_TABLE2_NAME = "table2_name";

    private int id;
    private String name;
    private String table2_name;

    public Table_2() {
    }

    public Table_2(int id, String name, String table2_name) {
        this.id = id;
        this.name = name;
        this.table2_name = table2_name;
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

    public String getTable2_name() {
        return table2_name;
    }

    public void setTable2_name(String table2_name) {
        this.table2_name = table2_name;
    }
}
