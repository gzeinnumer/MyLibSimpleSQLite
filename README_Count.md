### Count
```java
public int count(){
    int count = 0;
    try {
        Cursor cursor = GblVariabel.myDb.rawQuery("SELECT id FROM table1 WHERE is_uploaded = '0'", null);
        count = cursor.getCount();
        cursor.close();
    } catch (Exception e){
        Log.d(TAG, "count: "+e.getMessage());
    }
    return count;
}
```

---

```
Copyright 2020 M. Fadli Zein
```