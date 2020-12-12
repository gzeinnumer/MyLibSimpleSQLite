### Read
```java
public class Table1_OLD {

    ...

    public List<Table1_OLD> read(){
        Cursor cursor;
        ArrayList<Table1_OLD> data = new ArrayList<>();
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
}
```

---

```
Copyright 2020 M. Fadli Zein
```