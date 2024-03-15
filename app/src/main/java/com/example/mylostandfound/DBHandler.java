package com.example.mylostandfound;

import android.content.ContentValues;
        import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "lostfounddb";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "items";
    private static final String POST_ID = "postId";

    private static final String POST_COL = "postType";
    private static final String NAME_COL = "name";
    private static final String PHONE_COL = "phone";
    private static final String DESCRIPTION_COL = "description";
    private static final String DATE_COL = "date";
    private static final String LOC_COL = "location";

    private static final String Latitude = "latitude";

    private static final String Longitude = "longitude";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + POST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + POST_COL + " TEXT,"
                + NAME_COL + " TEXT,"
                + PHONE_COL + " TEXT,"
                + DESCRIPTION_COL + " TEXT,"
                + DATE_COL + " TEXT,"
                + LOC_COL+ " TEXT,"
        + Latitude+ " DOUBLE,"
        + Longitude+ " DOUBLE)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewItem(String postName, String phoneNumber, String itemDescription, String itemDate, String itemLoc, Double lat, Double lon) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, postName);
        values.put(PHONE_COL, phoneNumber);
        values.put(DESCRIPTION_COL, itemDescription);
        values.put(DATE_COL, itemDate);
        values.put(LOC_COL, itemLoc);
        values.put(Latitude, lat);
        values.put(Longitude, lon);


        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public ArrayList<Post> getAllItems(){
    ArrayList<Post> list = new ArrayList<>();
    SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while(cursor.moveToNext()) {

            int id = cursor.getInt(0);
            String post = cursor.getString(1);
            String name = cursor.getString(2);
            String phone = cursor.getString(3);
            String desc = cursor.getString(4);
            String date = cursor.getString(5);
            String loc = cursor.getString(6);
            Double lat = cursor.getDouble(7);
            Double lon = cursor.getDouble(8);

            Post postDetails = new Post(id, post, name, phone, desc, date, loc, lat, lon);
            list.add(postDetails);

        }
        return list;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void removeItem(int id) {
        SQLiteDatabase db = getWritableDatabase();
     db.delete(TABLE_NAME, "postId="+id, null);
      db.close();
   // db.rawQuery("DELETE FROM " + TABLE_NAME+ " WHERE "+POST_ID+"="+id, null);
    }
}
