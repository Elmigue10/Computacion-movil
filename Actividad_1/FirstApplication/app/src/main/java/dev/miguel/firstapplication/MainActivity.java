package dev.miguel.firstapplication;

import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;
import java.util.HashMap;
import java.util.Map;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    // Creating EditText.
    EditText FullName, Email, Password ;
    // Creating button;
    Button Register;

    TextView LabelResponse;
    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    // Create string variable to hold the EditText Value.
    String NameHolder, EmailHolder, PasswordHolder ;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    // Storing server url into String variable.
    String HttpUrl = "http://192.168.20.22:8080/api/v1/save";
    Boolean CheckEditText ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Assigning ID's to EditText.
        FullName = (EditText) findViewById(R.id.EditTextFullName);
        Email = (EditText) findViewById(R.id.EditTextEmail);
        Password = (EditText) findViewById(R.id.EditTextPassword);
        // Assigning ID's to Button.
        Register = (Button) findViewById(R.id.ButtonRegister);
        LabelResponse = (TextView) findViewById(R.id.LabelResponse);
        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(MainActivity.this);
        // Adding click listener to button.
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextIsEmptyOrNot();
                if(CheckEditText){
                    UserRegistration();
                }
                else {
                    Toast.makeText(MainActivity.this, "Please fill all form fields.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void UserRegistration() {
        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("FullName", NameHolder);
            jsonBody.put("Email", EmailHolder);
            jsonBody.put("Password", PasswordHolder);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(HttpUrl, jsonBody,
                response -> {
                    Log.d("TAG", response.toString());

                    try {
                        String name = response.getString("FullName");
                        String email = response.getString("Email");
                        LabelResponse.setText("Name : " + name + "\n" + "Email : " + email
                                + "\n" + "Thank You!");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Log.e("TAG", error.getMessage(), error));


        queue.add(jsonObjectRequest);
    }
    public void CheckEditTextIsEmptyOrNot(){
        // Getting values from EditText.
        NameHolder = FullName.getText().toString().trim();
        EmailHolder = Email.getText().toString().trim();
        PasswordHolder = Password.getText().toString().trim();
        // Checking whether EditText value is empty or not.
        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(EmailHolder) ||
                TextUtils.isEmpty(PasswordHolder))
        {
            // If any of EditText is empty then set variable value as False.
            CheckEditText = false;
        }
        else{
            // If any of EditText is filled then set variable value as True.
            CheckEditText = true ;
        }
    }
    public void showMessage(String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        builder.show();
    }
}