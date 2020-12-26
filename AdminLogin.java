package org.foodbankmarket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminLogin extends AppCompatActivity {

    EditText text_id, text_pw;
    int market_no;
    int is_admin;
    String id, pw;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b2_admin_login);

        text_id = findViewById(R.id.text_id);
        text_pw = findViewById(R.id.text_pw);

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
            Intent intent = new Intent(getApplicationContext(), MainActivityIrwonAdmin.class);
            startActivity(intent);
            finish();
        }
    }

    public void irwon(View view) {
        id = text_id.getText().toString();
        pw = text_pw.getText().toString();
        if (id.trim().equals("gnmiso") && pw.trim().equals("1234")) {
            editor.putInt("is_admin", 1);
            editor.putInt("market_no", 1);
            editor.commit();
            MARKET();
        }
    }

    public void daechi(View view) {
        id = text_id.getText().toString();
        pw = text_pw.getText().toString();
        if (id.trim().equals("gnmiso") && pw.trim().equals("1234")) {
            editor.putInt("is_admin", 1);
            editor.putInt("market_no", 2);
            editor.commit();
            MARKET();
        } else {
            Toast.makeText(getApplicationContext(), "입력된 정보가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }
    public void home_view(View view) {
        onBackPressed();
    }
}