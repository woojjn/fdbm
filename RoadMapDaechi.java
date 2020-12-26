package org.foodbankmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RoadMapDaechi extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    Button DaechiB, shuttle1B;


    FragmentShuttle1 fragmentShuttle1 = new FragmentShuttle1();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roadmapdaechi);

        DaechiB = findViewById(R.id.DaechiB);
        shuttle1B = findViewById(R.id.shuttle1B);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;

        LatLng gangnamFoodBankDaechi = new LatLng(37.491939, 127.073446);
        map.addMarker(new MarkerOptions().position(gangnamFoodBankDaechi).title("강남푸드뱅크마켓 대치점"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(gangnamFoodBankDaechi, 16F));


        DaechiB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                map = googleMap;

                LatLng gangnamFoodBankDaechi = new LatLng(37.502583, 127.059678);
                map.addMarker(new MarkerOptions().position(gangnamFoodBankDaechi).title("강남푸드뱅크마켓 대치점"));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(gangnamFoodBankDaechi, 16F));

                if (fragmentShuttle1.isAdded()) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.remove(fragmentShuttle1);
                    fragmentTransaction.commit();
                }
            }
        });

        shuttle1B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!fragmentShuttle1.isAdded()) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.map_fragment, fragmentShuttle1);
                    fragmentTransaction.commit();
                } else {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.remove(fragmentShuttle1);
                    fragmentTransaction.commit();
                }
            }
        });
    }
}
