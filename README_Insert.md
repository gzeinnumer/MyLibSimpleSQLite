### Insert
```java

public class Table1_OLD {

    ...

    //INSERT INTO table1 (name, rating, desc, flag_active, created_at)
    //VALUES ('Zein', '10.0', 'Android Programmer', '1', '12-12-2020');
    public boolean insert(){
        Table_1 data = new Table1_OLD();
        data.setName("Zein");
        data.setRating(10.0);
        data.setDesc("Android Programmer");
        data.setFlag_active(1);
        data.setCreated_at("12-12-2020");

        try {
            ContentValues values = new ContentValues();
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
}
```

---

```
Copyright 2020 M. Fadli Zein
```