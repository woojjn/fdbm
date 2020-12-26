package org.foodbankmarket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SelectMarketActivity extends AppCompatActivity {

    int market_no;
    int is_admin;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private long backBtnTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_select_market);

        sharedPreferences = getSharedPreferences("market", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        MARKET();
    }

    public void MARKET() {

        market_no = sharedPreferences.getInt("market_no", 0);
        is_admin = sharedPreferences.getInt("is_admin", 0);

        if (is_admin == 1 && market_no == 1) {
            Intent intent = new Intent(getApplicationContext(), MainActivityIrwonAdmin.class);
            startActivity(intent);
            finish();
        } else if (is_admin == 1 && market_no == 2) {
            Intent intent = new Intent(getApplicationContext(), MainActivityDaechiAdmin.class);
            startActivity(intent);
            finish();
        } else if (is_admin == 2 && market_no == 1) {
            Intent intent = new Intent(getApplicationContext(), MainActivityI.class);
            startActivity(intent);
            finish();
        } else if (is_admin == 2 &&market_no == 2) {
            Intent intent = new Intent(getApplicationContext(), MainActivityD.class);
            startActivity(intent);
            finish();
        }
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

    public void irwon(View view) {
        editor.putInt("market_no", 1);
        editor.commit();
        MARKET();
    }

    public void daechi(View view) {
        editor.putInt("market_no", 2);
        editor.commit();
        MARKET();
    }
}