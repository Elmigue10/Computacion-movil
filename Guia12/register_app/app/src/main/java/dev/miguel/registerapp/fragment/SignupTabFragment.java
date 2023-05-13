package dev.miguel.registerapp.fragment;

import static dev.miguel.registerapp.helper.DBHelper.TABLE_USER;
import static dev.miguel.registerapp.util.EncryptUtil.cifrarPassword;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import dev.miguel.registerapp.R;
import dev.miguel.registerapp.activity.MainActivity;
import dev.miguel.registerapp.helper.DBHelper;

public class SignupTabFragment extends Fragment {

    EditText signUpName, signUpPhone, signUpEmail, signUpPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DBHelper dbHelper = new DBHelper(view.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        view.findViewById(R.id.signup_button).setOnClickListener(e -> {
            signUpName = view.findViewById(R.id.signup_name);
            signUpPhone = view.findViewById(R.id.signup_phone);
            signUpEmail = view.findViewById(R.id.signup_email);
            signUpPassword = view.findViewById(R.id.signup_password);
            String name = signUpName.getText().toString();
            String phone = signUpPhone.getText().toString();
            String email = signUpEmail.getText().toString();
            String password = signUpPassword.getText().toString();
            try {
                String mensaje = validarDatos(name, phone, email, password);
                if (mensaje.equals("OK")) {
                    db.execSQL("INSERT INTO " + TABLE_USER + " (name, phone, email, password) " +
                            "VALUES ('" + signUpName.getText().toString() + "','" +
                            signUpPhone.getText().toString() + "','" +
                            signUpEmail.getText().toString() + "','" +
                            cifrarPassword(signUpPassword.getText().toString()) + "')");

                    startActivity(new Intent(view.getContext(), MainActivity.class));
                } else {
                    Toast.makeText(view.getContext(),
                                    mensaje,
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            } catch (Exception ex){
                Toast.makeText(view.getContext(),
                                "Error al crear el usuario, intente de nuevo",
                                Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private String validarDatos(String name, String phone, String email, String password) {
        if (name == null || name.isEmpty()){
            return "El campo 'Name' es obligatorio";
        }
        if (phone == null || phone.isEmpty()){
            return "El campo 'Phone' es obligatorio";
        }
        if (email == null || email.isEmpty()){
            return "El campo 'Email' es obligatorio";
        }
        if (password == null || password.isEmpty()){
            return "El campo 'Password' es obligatorio";
        }

        if (phone.length() < 9){
            return "El campo 'Phone' debe tener 10 digitos";
        }

        String regexEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern patternEmail = Pattern.compile(regexEmail);
        Matcher matcherEmail = patternEmail.matcher(email);
        if (!matcherEmail.matches()){
            return "El 'Email' tiene un formato invalido";
        }

        String regexPassword = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        Pattern patternPassword = Pattern.compile(regexPassword);
        Matcher matcherPassword = patternPassword.matcher(password);
        if (!matcherPassword.matches()){
            return "La contraseña debe contener por lo menos 8 caracteres, una letra y un número";
        }

        return "OK";
    }
}