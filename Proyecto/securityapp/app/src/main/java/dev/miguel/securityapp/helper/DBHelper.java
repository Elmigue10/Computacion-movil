package dev.miguel.securityapp.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "securityapp.db";
    public static final String TABLE_USER = "user";
    public static final String TABLE_PERSON = "person";
    public static final String TABLE_VEHICLE = "vehicle";
    public static final String TABLE_RECORD = "record";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' " +
                "AND name='" + TABLE_USER + "';", null);

        if (cursor.getCount() == 0) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USER + "(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT NOT NULL," +
                    "password TEXT NOT NULL" + ")");

            db.execSQL("INSERT INTO " + TABLE_USER + " (username, password) " +
                    "VALUES ('admin', 'admin');");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void open () { this.getWritableDatabase(); }

    public void close () { this.close(); }
}
