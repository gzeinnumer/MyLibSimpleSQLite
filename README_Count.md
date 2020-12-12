### Count
```java
public class Table1_OLD {

    ...

    public int count(){
        int count = 0;
        try {
            Cursor cursor = GblVariabel.myDb.rawQuery("SELECT COUNT(*) FROM table1", null);
            count = cursor.getCount();
            cursor.close();
        } catch (Exception e){
            Log.d(TAG, "count: "+e.getMessage());
        }
        return count;
    }
}
```

---

```
Copyright 2020 M. Fadli Zein
```