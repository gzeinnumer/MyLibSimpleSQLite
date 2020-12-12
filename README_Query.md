### Query Data
```java
public class Table1_OLD {

    ...

    @SuppressLint("Recycle")
    public List<Table1_OLD> query(){
        List<Table1_OLD> current = new ArrayList<>();
        String query ="SELECT table1.*, table2.name AS table2_name FROM table1 JOIN table2 ON table2.id_table1 = table1.id;";
        Cursor cursor = GblVariabel.myDb.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (cursor.moveToNext()){
                Table1_OLD data = new Table1_OLD();
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
}
```

---

```
Copyright 2020 M. Fadli Zein
```