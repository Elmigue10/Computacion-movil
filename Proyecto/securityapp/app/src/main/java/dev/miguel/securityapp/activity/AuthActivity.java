package dev.miguel.securityapp.activity;

import static dev.miguel.securityapp.helper.DBHelper.TABLE_USER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import dev.miguel.securityapp.R;
import dev.miguel.securityapp.helper.DBHelper;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if ( db == null ){
            Toast.makeText(this, "Error creating the DataBase...",
                    Toast.LENGTH_SHORT).show();
        }

        findViewById(R.id.log_in_button).setOnClickListener( e -> {
            dbHelper.open();
            EditText usernameInput = findViewById(R.id.username_input);
            EditText passwordInput = findViewById(R.id.password_input);

            if (validateUser(usernameInput.getText().toString(),
                    passwordInput.getText().toString(), db)) {
                startActivity(new Intent(AuthActivity.this, HomeActivity.class));
            } else {
                Toast.makeText(this, "Invalid Credentials...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateUser(String username, String password, SQLiteDatabase db) {
        String[] projection = {"id", "password"};
        String selection = "username = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_USER, projection, selection, selectionArgs, null,
                null, null);

        if (cursor.moveToFirst()) {
            String savedPassword =
                    cursor.getString(cursor.getColumnIndexOrThrow("password"));
            if (password.equals(savedPassword)) {
                cursor.close();
                db.close();
                return true;
            } else {
                cursor.close();
                db.close();
                return false;
            }
        }
        cursor.close();
        db.close();
        return false;
    }
}