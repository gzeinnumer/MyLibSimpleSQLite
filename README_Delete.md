### Delete
```java
public boolean delete(){
    String whereCondition = "WHERE id='500';";
    try{
        String query = "DELETE FROM table1 "+ whereCondition;
        GblVariabel.myDb.execSQL(query);
        return true;
    } catch (Exception e) {
        Log.e(TAG, "deleted failed " + e.getMessage());
        return false;
    }
}
```

---

```
Copyright 2020 M. Fadli Zein
```