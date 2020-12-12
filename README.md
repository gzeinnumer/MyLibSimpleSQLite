
| ![](https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/master/preview/example1.JPG) | ![](https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/master/preview/example2.JPG) |
|---|---|
|Before|Simple Code|

| ![](https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/master/preview/example3.JPG) | ![](https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/master/preview/example4.JPG) |
|---|---|
|Before|Simple Code|

<h1 align="center">
  MyLibSimpleSQLite - Simple SQLite
</h1>

<div align="center">
    <a><img src="https://img.shields.io/badge/Version-2.0.5-brightgreen.svg?style=flat"></a>
    <a><img src="https://img.shields.io/badge/ID-gzeinnumer-blue.svg?style=flat"></a>
    <a><img src="https://img.shields.io/badge/Java-Suport-green?logo=java&style=flat"></a>
    <a><img src="https://img.shields.io/badge/Koltin-Suport-green?logo=kotlin&style=flat"></a>
    <a href="https://github.com/gzeinnumer"><img src="https://img.shields.io/github/followers/gzeinnumer?label=follow&style=social"></a>
    <br>
    <p>Simple way to use CRUD SQLite</p>
</div>

---

`I just try to make Cursor and ContentValues disappeared but its still in there, I hate always having Typo on my code, specially on Cursor and ContentValues again and again. And take time to debug and fix it. arghh...`

`Than I made this so I don't hate it anymore, hope you enjoy it :)`

`please tell me if you find error.`

## Download
Add maven `jitpack.io` and `dependencies` in `build.gradle (Project)` :
```gradle
// build.gradle project
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}

// build.gradle app/module
dependencies {
  ...
  implementation 'com.github.gzeinnumer:MyLibSimpleSQLite:version'
}
```

## Feature List
- [x] [1. Table](#1-table)
- [x] [2. Entity](#2-entity)
- [x] [3. Insert](#3-insert)
- [x] [4. Update](#4-update)
- [x] [5. Delete](#5-delete)
- [x] [6. Count](#6-count)
- [x] [7. Read](#7-read)
- [x] [8. Query Data](#8-query-data) for Complex Query. return List.
- [x] [9. Query Result](#9-query-result) return true/false.
- [ ] Create Table

---

## Scenario Table
|<img src="https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/master/preview/example7.JPG" width="400"/>|<img src="https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/master/preview/example8.JPG" width="400"/>|
|---|---|

<p align="center">
  <img src="https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/master/preview/example5.JPG" width="400"/>
</p>

---
## USE
Please make sure you have access to your database with instance from `SQLiteDatabase`.
here is my global variable. i will sent `GblVariabel.myDb` to function that i made.
```java
public class GblVariabel {
    private static final String TAG = "GblVariabel";

    public static SQLiteDatabase myDb = null;

    @SuppressLint("StaticFieldLeak")
    public static DatabaseHelper dbHelper = null;

    public static void initDb(Context context) {
        try {
            dbHelper = new DatabaseHelper(context);
            if (dbHelper.checkDatabase()) {
                GblVariabel.myDb = dbHelper.openDataBase();
            } else {
                Log.e(TAG, "initDb:  database kosong");
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            Log.e(TAG, "initDb: " + throwable.getMessage());
        }
    }
}
```
Here is my [DatabaseHelper](https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/master/app/src/main/java/com/gzeinnumer/mylibsimplesqlite/helper/DatabaseHelper.java).

**Or you can use your own configuration to connect to Database, just make sure you have access to your `local database`. [ReadMore](https://developer.android.com/training/data-storage/sqlite?hl=id)**.

---

### 1. Table
You need to extends `SQLiteLIB<YourEntity>` to your `Entity Class`. And Use Annotation `@SQLiteTable(tableName = "your_table_name")` like this:
```java
@SQLiteTable(tableName = "table1")
public class Table1 extends SQLiteLIB<Table1> {

    ...

}
```

#
### 2. Entity
> Lets see [Boilerplate Code Entity](https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/dev/README_Entity.md)

> Simple Code

Declare Entity. You can make it more simple with this `Annotation`
- `@PrimaryKeyTypeData` or `@VarcharTypeData` or `@IntegerTypeData` or `@TimeStampTypeData` or `@TextTypeData` or `@DoubleTypeData` or `@JoinColumnTypeData`
```java
@SQLiteTable(tableName = "table1")
public class Table1 extends SQLiteLIB<Table1> {
    @PrimaryKeyTypeData private int id;              // for Primary key
                                                     // Default AutoIncrement true
                                                     // @PrimaryKeyTypeData(autoGenerate = false) to disable
    @VarcharTypeData    private String name;         // for Varchar
    @DecimalTypeData    private double rating;       // for Decimal/Real
    @TextTypeData       private String desc;         // for String
    @IntegerTypeData    private int flag_active;     // for Integer
    @TimeStampTypeData  private String created_at;   // for String

    // for join column from other table
    //@JoinColumn(withTable = "table2", columnName = "name")
    @JoinColumn(withTable = "table2", columnName = "name", alias = "table2_name")
    private String table2_name;

    //contructor
    //getter setter

    ...

}
```
**Notes :**
- `@PrimaryKeyTypeData` : Your variable type should `int`.
- `@VarcharTypeData` : Your variable type should `String`.
- `@IntegerTypeData` : Your variable type should `int`.
- `@TimeStampTypeData` : Your variable type should `String`.
- `@TextTypeData` : Your variable type should `String`.
- `@DoubleTypeData` : Your variable type should `double`.
- `@JoinColumnTypeData` :
  - `withTable` = other table to join with current table.
  - `columnName` = realname on other table.
  - `alias` = if your `first table` and `second table` haven't same `column name`. you can ignore it.
  - `alias` = if your `first table` and `second table` have same `column name`, you can use this as alias. example `SELECT table1.name, table2.name AS table2_name FROM table1 JOIN ... ;`.
    - make sure your `alias` same like your `variable name` and your `query` -> `AS table2_name`.

#
### 3. Insert
> Lets see [Boilerplate Code Insert Data](https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/dev/README_Insert.md)

> Simple Code

```java
@SQLiteTable(tableName = "table1")
public class Table1 extends SQLiteLIB<Table1> {

    ...

    //INSERT INTO table1 (name, rating, desc, flag_active, created_at)
    //VALUES ('Zein', '10.0', 'Android Programmer', '1', '12-12-2020');
    public boolean insert() {
        Table1 data = new Table1();
        data.setName("Zein");
        data.setRating(10.0);
        data.setDesc("Android Programmer");
        data.setFlag_active(1);
        data.setCreated_at("12-12-2020");

        return insertData(Table1.class, GblVariabel.myDb, data);
    }
}
```

#
### 4. Update
> Lets see [Boilerplate Code Update Data](https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/dev/README_Update.md)

> Simple Code
```java
@SQLiteTable(tableName = "table1")
public class Table1 extends SQLiteLIB<Table1> {

    ...

    //UPDATE table1 SET name='Name Update', desc='Desc Update', flag_active='0' WHERE id='1';
    public boolean update() {
        //set your value to update
        Table1 data = new Table1();
        data.setName("Name Update");
        data.setDesc("Desc Update");
        data.setFlag_active(0);

        //no need to write WHERE, i will write it for you, just type your condition
        String condition = "id='1'";                            //for single condition
        //String condition = "id='1' AND flag_Active='1'";      //for multi condition

        String[] fieldToUpdate = new String[]{
            "name",
            "desc",
            "flag_active"
        }; // put all field that you want to update

        return updatedData(Table1.class, GblVariabel.myDb, data, condition, fieldToUpdate);  // return true/false
    }
}
```

#
### 5. Delete
> Lets see [Boilerplate Code Delete Data](https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/dev/README_Delete.md)

> Simple Code
```java
@SQLiteTable(tableName = "table1")
public class Table1 extends SQLiteLIB<Table1> {

    ...

    //DELETE FROM table1 WHERE id='1';
    public boolean delete() {
        //no need to write WHERE, i will write it for you, just type your condition
        String condition = "id='1'";                          //for single condition
        //String condition = "id='1' AND flag_Active='1'";    //for multi condition
        //String condition = "1";                               //to delete all data
        return deleteData(Table1.class, GblVariabel.myDb, condition);
    }
}
```

#
### 6. Count
> Lets see [Boilerplate Code Count Data](https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/dev/README_Count.md)

> Simple Code
```java
@SQLiteTable(tableName = "table1")
public class Table1 extends SQLiteLIB<Table1> {

    ...

    //type 1 SELECT COUNT(*) FROM table1;
    public int count() {
        return countData(Table1.class, GblVariabel.myDb);
    }

    //type 2 SELECT COUNT(*) FROM table1 WHERE flag_Active='1';
    public int count() {
        //no need to write WHERE, i will write it for you, just type your condition
        String condition = "id='1'";                          //for single condition
        //String condition = "id='1' AND flag_Active='1'";    //for multi condition
        return countData(Table1.class, GblVariabel.myDb, condition);
    }
}
```

#
### 7. Read
> Lets see [Boilerplate Code Read Data](https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/dev/README_Read.md)

> Simple Code
```java
@SQLiteTable(tableName = "table1")
public class Table1 extends SQLiteLIB<Table1> {

    ...

    //type 1 SElECT * FROM table1;
    public List<Table1> read() {
        return readData(Table1.class, GblVariabel.myDb);
    }

    //type 2 SELECT * FROM table1 WHERE flag_active='1';
    public List<Table1> read() {
        //no need to write WHERE, i will write it for you, just type your condition
        String condition = "id='1'";                         //for single condition
        //String condition = "id='1' AND flag_Active='1'";    //for multi condition

        return readData(Table1.class, GblVariabel.myDb, condition);
    }
}
```

#
### 8. Query Data
> Lets see [Boilerplate Code Query Data](https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/dev/README_Query.md)

> Simple Code
```java
@SQLiteTable(tableName = "table1")
public class Table1 extends SQLiteLIB<Table1> {

    ...

    //dont forget to write this to
    @JoinColumn(withTable = "table2", columnName = "name", alias = "table2_name")
    private String table2_name;

    ...

    public List<Table1> query(){
        String query ="SELECT table1.*, table2.name AS table2_name FROM table1 JOIN table2 ON table2.id_table1 = table1.id;";
        return queryData(Table1.class, GblVariabel.myDb, query);
    }
}
```

#
### 8. Query Result
> Lets see [Boilerplate Code Query Result](https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/dev/README_Query_Result.md)

> Simple Code
```java
@SQLiteTable(tableName = "table1")
public class Table1 extends SQLiteLIB<Table1> {

    ...

    public boolean queryResultUpdate() {
        String query = "UPDATE table1 SET flag_Active='2' where id='1'";
        return queryResult(GblVariabel.myDb, query);
    }
}
```
**Notes :**
You can use it to excecute `update` or `delete` query and give you `true/false` as return.

---
Entity Old Verision
[Table1_OLD](https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/master/app/src/main/java/com/gzeinnumer/mylibsimplesqlite/entity/old/Table1_OLD.java)
 & [Table2_OLD](https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/master/app/src/main/java/com/gzeinnumer/mylibsimplesqlite/entity/old/Table2_OLD.java)

Entity New Verion
[Table1](https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/master/app/src/main/java/com/gzeinnumer/mylibsimplesqlite/entity/Table1.java)
 & [Table2](https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/master/app/src/main/java/com/gzeinnumer/mylibsimplesqlite/entity/Table2.java)

[DatabaseHelper](https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/master/app/src/main/java/com/gzeinnumer/mylibsimplesqlite/helper/DatabaseHelper.java)
 & [GblVariabel](https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/master/app/src/main/java/com/gzeinnumer/mylibsimplesqlite/helper/GblVariabel.java)

---

## Debug
<p align="center">
  <img src="https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/master/preview/example6.JPG" width="400"/>
</p>

---

**Example App [Java](https://github.com/gzeinnumer/MyLibSQLiteExample) & [Kotlin](https://github.com/gzeinnumer/MyLibSimpleSQLitekt)**

---

## Version
- **1.0.0**
  - First Release
- **1.0.6**
  - Kotlin Version
- **1.0.9**
  - Fixing Bug On Update
- **2.0.0**
  - Add Feature queryResult()
- **2.0.5**
  - Fixing Bug On Update Kotlin

---

## Contribution
You can sent your constibution to `branche` `open-pull`.

---

```
Copyright 2020 M. Fadli Zein
```