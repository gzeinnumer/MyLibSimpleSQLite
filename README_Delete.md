### Delete
```java
public class Table1_OLD {

    ...

    public boolean delete(){
        try{
            String query = "DELETE FROM table1 WHERE id='1';";
            GblVariabel.myDb.execSQL(query);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "deleted failed " + e.getMessage());
            return false;
        }
    }
}
```

---

```
Copyright 2020 M. Fadli Zein
```