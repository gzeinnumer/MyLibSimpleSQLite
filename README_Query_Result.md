### Query Result
```java
public class Table1_OLD {

    ...

    @SuppressLint("Recycle")
    public boolean query(){
        String query = "UPDATE table1 SET flag_Active='2' where id='1'";
        try {
            myDb.execSQL(query);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
```

---

```
Copyright 2020 M. Fadli Zein
```