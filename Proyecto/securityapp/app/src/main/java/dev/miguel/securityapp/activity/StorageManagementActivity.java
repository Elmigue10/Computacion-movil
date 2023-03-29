package dev.miguel.securityapp.activity;

import static dev.miguel.securityapp.helper.DBHelper.TABLE_PERSON;
import static dev.miguel.securityapp.helper.DBHelper.TABLE_RECORD;
import static dev.miguel.securityapp.helper.DBHelper.TABLE_VEHICLE;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import dev.miguel.securityapp.R;
import dev.miguel.securityapp.helper.DBHelper;

public class StorageManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_management);
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        validateTables(db, dbHelper);

    }

    private void validateTables(SQLiteDatabase db, DBHelper dbHelper) {
        Cursor personCursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' " +
                "AND name='" + TABLE_PERSON + "';", null);
        Cursor vehicleCursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' " +
                "AND name='" + TABLE_VEHICLE + "';", null);
        Cursor recordCursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' " +
                "AND name='" + TABLE_RECORD + "';", null);

        if (personCursor.getCount() == 0 || vehicleCursor.getCount() == 0 ||
                recordCursor.getCount() == 0) {
            if (createTables(db, dbHelper)){
                Toast.makeText(this, "Correctly created tables...",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "It was not possible to create the tables...",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Tables already exists...",
                    Toast.LENGTH_SHORT).show();
        }

        personCursor.close();
        vehicleCursor.close();
        recordCursor.close();
        //db.close();
    }

    private boolean createTables(SQLiteDatabase db, DBHelper dbHelper) {
        try{
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PERSON + " ("
                    + "CEDULA INTEGER primary key ,"
                    + "NOMBRES TEXT,"
                    + "APELLIDOS TEXT,"
                    + "DIRECCION TEXT,"
                    + "TELEFONO TEXT,"
                    + "EMAIL TEXT,"
                    + "FOTO BLOB)");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_VEHICLE + " ("
                    + "TAG TEXT primary key ,"
                    + "PLACA TEXT,"
                    + "CEDULA INTEGER ,"
                    + "CLASE_VEHICULO TEXT,"
                    + "SERIAL TEXT,"
                    + "MARCA TEXT,"
                    + "COLOR TEXT,"
                    + "FOTO BLOB)");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_RECORD + " ("
                    + "CEDULA INTEGER,"
                    + "PLACA TEXT,"
                    + "DENUNCIA TEXT,"
                    + "SPOA TEXT,"
                    + "FISCALIA TEXT,"
                    + "JUZGADO TEXT,"
                    + "FECHA TEXT,"
                    + "OBSERVACION TEXT)");

            dbHelper.onUpgrade(db, 1, 2);

            return true;
        } catch (SQLException e){
            return false;
        }

    }
}