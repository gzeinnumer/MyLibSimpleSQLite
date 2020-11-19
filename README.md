
| ![](https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/master/preview/example1.JPG) | ![](https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/master/preview/example2.JPG) |
|---|---|
|Before|After|

| ![](https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/master/preview/example3.JPG) | ![](https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/master/preview/example4.JPG) |
|---|---|
|Before|After|

<h1 align="center">
  MyLibSimpleSQLite - Simple SQLite
</h1>

<div align="center">
    <a><img src="https://img.shields.io/badge/Version-1.0.6-brightgreen.svg?style=flat"></a>
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
- [x] [Entity](#entity)
- [x] [Insert](#insert)
- [x] [Update](#update)
- [x] [Delete](#delete)
- [x] [Count](#count)
- [x] [Read](#read)
- [x] [Query](#query) for Complex Query
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

You need to extends `SQLiteLIB<YourEntity>` to your `Entity Class`. And Use Annotation `@SQLiteTable(tableName = "you_table_name")` like this:
```java
@SQLiteTable(tableName = "table1")
public class Table1 extends SQLiteLIB<Table1> {

    ...

}
```

#
### Entity
> Before
```java
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

```
You can make it more simple with this `Annotation`
- `@PrimaryKeyTypeData` or `@VarcharTypeData` or `@IntegerTypeData` or `@TimeStampTypeData` or `@TextTypeData` or `@DoubleTypeData` or `@JoinColumnTypeData`
> After
```java
@PrimaryKeyTypeData private int id;              // for Primary key
@VarcharTypeData    private String name;         // for Varchar
@DecimalTypeData    private double rating;       // for Decimal/Real
@TextTypeData       private String desc;         // for String
@IntegerTypeData    private int flag_active;     // for Integer
@TimeStampTypeData  private String created_at;   // for String

// for join column from other table
//@JoinColumn(withTable = "table2", columnName = "name")
@JoinColumn(withTable = "table2", columnName = "name", alias = "table2_name")
private String table2_name;
```
**Notes :**
- `@PrimaryKeyTypeData` :
  - Your variable type should `int`.
- `@VarcharTypeData` :
  - Your variable type should `String`.
- `@IntegerTypeData` :
  - Your variable type should `int`.
- `@TimeStampTypeData` :
  - Your variable type should `String`.
- `@TextTypeData` :
  - Your variable type should `String`.
- `@DoubleTypeData` :
  - Your variable type should `double`.
- `@JoinColumnTypeData` :
  - `withTable` = other table to join with current table.
  - `columnName` = realname on other table.
  - `alias` = if your `first table` and `second table` haven't same `column name`. you can ignore it.
  - `alias` =  if your `first table` and `second table` have same `column name`, you can use this as alias. example `SELECT table1.name, table2.name AS table2_name FROM table1 JOIN ... ;`.
    - make sure your `alias` same like your `variable name` and your `query` -> `AS table2_name`.

#
### Insert
> Before
```java
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
```
> After
```java
public boolean insert(Table1 data) {
    return insertData(Table1.class, GblVariabel.myDb, data);
}
```

#
### Update
> Before
```java
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
```
> After
```java
//no need to write WHERE, i will write it for you, just type your condition
public boolean update(Table1 data) {
    String condition = "id='500'";                            //for single condition
    //String condition = "id='500' AND flag_Active='1'";      //for multi condition
    return updatedData(Table1.class, GblVariabel.myDb, data, condition);  // return true/false
}
```

#
### Delete
> Before
```java
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
```
> After
```java
//no need to write WHERE, i will write it for you, just type your condition
public boolean delete() {
    String condition = "id='1=500'";                        //for single condition
    //String condition = "id='500' AND flag_Active='1'";    //for multi condition
    return deleteData(Table1.class, GblVariabel.myDb, condition);
}
```

#
### Count
> Before
```java
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
```
> After
```java
//type 1
public int count() {
    return countData(Table1.class, GblVariabel.myDb);
}

//type 2 no need to write WHERE, i will write it for you, just type your condition
public int count() {
    String condition = "id='500'";                        //for single condition
    //String condition = "id='500' AND flag_Active='1'";    //for multi condition
    return countData(Table1.class, GblVariabel.myDb, condition);
}
```

#
### Read
> Before
```java
public ArrayList<Table_1> read(){
    Cursor cursor;
    ArrayList<Table_1> data = new ArrayList<>();
    cursor = GblVariabel.myDb.rawQuery("SELECT * FROM " + TABLE , null);
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
```
> After
```java
//type 1
public int count() {
    return readData(Table_1.class, GblVariabel.myDb);
}

//type 2 no need to write WHERE, i will write it for you, just type your condition
public int count() {
    String condition = "id='500'";                        //for single condition
    //String condition = "id='500' AND flag_Active='1'";    //for multi condition

    return readData(Table_1.class, GblVariabel.myDb, condition);
}
```

#
### Query
> Before
```java
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
```
> After
```java
//dont forget to write this to
@JoinColumn(withTable = "table2", columnName = "name", alias = "table2_name")
private String table2_name;

public List<Table1> query(){
    String query ="SELECT table1.*, table2.name AS table2_name FROM table1 JOIN table2 ON table2.id_table1 = table1.id;";
    return queryData(Table1.class, GblVariabel.myDb, query);
}
```
**Notes :**
If you not find something that you need on function that i prepare for you before. You can use this function, and make your own query like `ORDER BY`, `GROUP BY`, `LEFT JOIN`, `RIGHT JOIN`, `DISTINC`, etc.

---
Entity Old Verision
[Table_1](https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/master/app/src/main/java/com/gzeinnumer/mylibsimplesqlite/entity/old/Table_1.java)
 & [Table_2](https://github.com/gzeinnumer/MyLibSimpleSQLite/blob/master/app/src/main/java/com/gzeinnumer/mylibsimplesqlite/entity/old/Table_2.java)

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

---

## Contribution
You can sent your constibution to `branche` `open-pull`.

---

```
Copyright 2020 M. Fadli Zein
```