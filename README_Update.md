### Update
```java
public class Table1_OLD {

    ...

    //UPDATE table1 SET name='Name Update', desc='Desc Update', flag_active='0' WHERE id='1';
    public boolean update(){
        Table1_OLD data = new Table1_OLD();
        data.setName("Zein");
        data.setDesc("Android Programmer");
        data.setFlag_active(0);

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, data.getName());
        contentValues.put(KEY_DESC, data.getDesc());
        contentValues.put(KEY_FLAG_ACTIVE, data.getFlag_active());

        String whereCondition = "id = '1'";

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