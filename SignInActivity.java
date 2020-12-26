package org.foodbankmarket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {

    private long backBtnTime;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int is_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b1_login);

        sharedPreferences = getSharedPreferences("market", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        ADMIN();
    }

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if (0 <= gapTime && 2000 >= gapTime) {
            super.onBackPressed();
        } else {
            backBtnTime = curTime;
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void admin(View view) {
        Intent intent = new Intent(getApplicationContext(), AdminLogin.class);

        startActivity(intent);
    }

    public void skip(View view) {
        editor.putInt("is_admin", 2);
        editor.commit();
        ADMIN();
        finish();
    }

    public void ADMIN() {
        is_admin = sharedPreferences.getInt("is_admin", 0);

        if (!(is_admin == 0)) {
            Intent intent = new Intent(getApplicationContext(), SelectMarketActivity.class);

            startActivity(intent);
            finish();
        }
    }
}