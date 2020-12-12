### Update
```java
public class Table1_OLD {

    ...

    public boolean update(){
        Table1_OLD data = new Table1_OLD();
        data.setName("Zein");
        data.setRating(10.0);
        data.setDesc("Android Programmer");
        data.setFlag_active(1);
        data.setCreated_at("12-12-2020");

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, data.getName());
        contentValues.put(KEY_RATING, data.getRating());
        contentValues.put(KEY_DESC, data.getDesc());
        contentValues.put(KEY_FLAG_ACTIVE, data.getFlag_active());
        contentValues.put(KEY_CREATED_AT, data.getCreated_at());

        String whereCondition = "id = '500'";

        long row = GblVariabel.myDb.update(TABLE, contentValues, whereCondition, null);

        if (row > 0) {
            Log.d(TAG, "update: success");
            return true;
        } else {
            Log.d(TAG, "update: failed");
            return false;
        }
    }
}
```

---

```
Copyright 2020 M. Fadli Zein
```