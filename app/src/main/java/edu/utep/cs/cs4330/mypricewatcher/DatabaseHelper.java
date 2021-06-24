package edu.utep.cs.cs4330.mypricewatcher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

//databasehelper from class, of course updated to project specifications
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 3;
    private static final String DB_NAME = "todoDB";
    private static final String TODO_TABLE = "items";

    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "Name";
    private static final String KEY_URL = "URL";
    private static final String KEY_PRICE = "Price";
    private static final String KEY_NPRICE = "NPrice";
    private static final String KEY_PERCENT = "Change";

    public DatabaseHelper(Context context){
        super (context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table = "CREATE TABLE " + TODO_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, "
                + KEY_URL + " TEXT, "
                + KEY_PRICE + " TEXT, "
                + KEY_NPRICE + " TEXT, "
                + KEY_PERCENT + " TEXT " + ")";
        db.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        onCreate(database);
    }

    public void addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.Name()); // task name
        values.put(KEY_URL, item.URL());
        values.put(KEY_PRICE, item.Price());
        values.put(KEY_NPRICE, item.NPrice());
        values.put(KEY_PERCENT, item.Percent());
        long id = db.insert(TODO_TABLE, null, values);
        item.setId((int) id);
        db.close();
    }

    public List<Item> allItems() {
        List<Item> todoList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TODO_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String Name = cursor.getString(1);
                String URL = cursor.getString(2);
                String Price = cursor.getString(3);
                String NPrice = cursor.getString(4);
                String Percent = cursor.getString(5);
                Item task = new Item(id, Name, URL, Price, NPrice, Percent);
                todoList.add(task);
            } while (cursor.moveToNext());
        }
        return todoList;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TODO_TABLE, null, new String[]{});
        db.close();
    }

    public void delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TODO_TABLE, KEY_ID + " = ?", new String[] { Integer.toString(id) } );
        db.close();
    }

    public void update(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.Name());
        db.update(TODO_TABLE, values, KEY_ID + " = ?", new String[]{String.valueOf(item.id())});
        db.close();
    }

}