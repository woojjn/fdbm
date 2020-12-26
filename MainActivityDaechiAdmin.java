package org.foodbankmarket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivityDaechiAdmin extends AppCompatActivity {

    private long backBtnTime = 0;

    ConstraintLayout navigation_layout, rest_layout;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d3_main_daechi_admin);

        navigation_layout = findViewById(R.id.navigation_layout);
        rest_layout = findViewById(R.id.rest_layout);
        viewFlipper = findViewById(R.id.view_flipper);

        sharedPreferences = getSharedPreferences("market", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public  void previousView(View v) {
        Animation to_left = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.righttoleft);
        navigation_layout.startAnimation(to_left);

        Animation fastout = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fastout);
        rest_layout.startAnimation(fastout);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewFlipper.showPrevious();
            }
        },195);
    }

    public  void nextView(View v) {
        viewFlipper.showNext();
        Animation to_right = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.lefttoright);
        navigation_layout.startAnimation(to_right);

        Animation fastin = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fastin);
        rest_layout.startAnimation(fastin);
    }

    public void upload_view(View view) {
        if (navigation_layout.isShown()) {
            viewFlipper.showPrevious();
        }
        Intent intent = new Intent(getApplicationContext(), UploadDaechi.class);

        startActivity(intent);
    }

    public void market_view(View view) {
        if (navigation_layout.isShown()) {
            viewFlipper.showPrevious();
        }
        Intent intent = new Intent(getApplicationContext(), InMarketDaechiAdmin.class);

        startActivity(intent);
    }

    public void how_view(View view) {
        if (navigation_layout.isShown()) {
            viewFlipper.showPrevious();
        }
        Intent intent = new Intent(getApplicationContext(), HowActivity.class);

        startActivity(intent);
    }

    public void time_view(View view) {
        if (navigation_layout.isShown()) {
            viewFlipper.showPrevious();
        }
        Intent intent = new Intent(getApplicationContext(), TimeActivity.class);

        startActivity(intent);
    }

    public void road_view(View view) {
        if (navigation_layout.isShown()) {
            viewFlipper.showPrevious();
        }
        Intent intent = new Intent(getApplicationContext(), RoadMapDaechi.class);

        startActivity(intent);
    }

    public void phone_view(View view) {
        if (navigation_layout.isShown()) {
            viewFlipper.showPrevious();
        }
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:02-565-0377"));

        startActivity(intent);
    }

    public void signout(View view) {
        editor.remove("market_no");
        editor.remove("is_admin");
        editor.commit();

        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if (navigation_layout.isShown()) {
            Animation to_left = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.righttoleft);
            navigation_layout.startAnimation(to_left);

            Animation fastout = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fastout);
            rest_layout.startAnimation(fastout);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewFlipper.showPrevious();
                }
            },195);
        } else {
            if (0 <= gapTime && 2000 >= gapTime) {
                super.onBackPressed();
            } else {
                backBtnTime = curTime;
                Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
