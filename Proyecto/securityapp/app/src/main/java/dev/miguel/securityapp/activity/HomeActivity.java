package dev.miguel.securityapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import dev.miguel.securityapp.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById(R.id.storage_management_button).setOnClickListener( e -> startActivity(
                new Intent(HomeActivity.this, StorageManagementActivity.class)));

        findViewById(R.id.log_out_button).setOnClickListener( e -> startActivity(
                new Intent(HomeActivity.this, AuthActivity.class)));
    }
}