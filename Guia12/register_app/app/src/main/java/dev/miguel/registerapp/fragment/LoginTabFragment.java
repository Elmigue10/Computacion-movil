package dev.miguel.registerapp.fragment;

import static dev.miguel.registerapp.helper.DBHelper.TABLE_USER;
import static dev.miguel.registerapp.util.EncryptUtil.cifrarPassword;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dev.miguel.registerapp.R;
import dev.miguel.registerapp.activity.HomeActivity;
import dev.miguel.registerapp.helper.DBHelper;

public class LoginTabFragment extends Fragment {

    EditText loginEmail, loginPassword;
    Button loginButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DBHelper dbHelper = new DBHelper(view.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        loginButton = view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(e -> {
            loginEmail = view.findViewById(R.id.login_email);
            loginPassword = view.findViewById(R.id.login_password);

            try {
                if (validateUser(loginEmail.getText().toString(),
                        cifrarPassword(loginPassword.getText().toString()), db)) {
                    startActivity(new Intent(view.getContext(), HomeActivity.class));
                } else {
                    Toast.makeText(view.getContext(),
                                    "Credenciales incorrectas, intente de nuevo",
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            } catch (Exception ex){
                Toast.makeText(view.getContext(),
                                "Error al validar el usuario, intente de nuevo",
                                Toast.LENGTH_SHORT)
                        .show();
            }

        });
    }

    private boolean validateUser(String email, String password, SQLiteDatabase db) {
        String[] projection = {"id", "password"};
        String selection = "email = ?";
        String[] selectionArgs = {email};
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
        return false;
    }
}