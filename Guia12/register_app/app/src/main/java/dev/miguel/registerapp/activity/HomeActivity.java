package dev.miguel.registerapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import dev.miguel.registerapp.R;

public class HomeActivity extends AppCompatActivity {

    TextView textViewName, textViewPhone, textViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle extras = getIntent().getExtras();
        textViewName = findViewById(R.id.textViewName);
        textViewPhone = findViewById(R.id.textViewPhone);
        textViewEmail = findViewById(R.id.textViewEmail);

        textViewName.setText("👋 WELCOME "+ extras.get("name") + "!");
        textViewPhone.setText("📞 Phone: " + extras.getString("phone"));
        textViewEmail.setText("📧️ Email: " + extras.getString("email"));

        findViewById(R.id.log_out_button).setOnClickListener( e ->
                startActivity(new Intent(HomeActivity.this, MainActivity.class)));
    }
}