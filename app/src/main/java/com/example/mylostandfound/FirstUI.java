package com.example.mylostandfound;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class FirstUI extends AppCompatActivity {
    private DBHandler dbHandler;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_ui);
        EditText postName, phoneNumber, itemDescription, itemDate, lat, lon;

        Button save = (Button)findViewById(R.id.save) ;

        postName = findViewById(R.id.textView3);
        phoneNumber = findViewById(R.id.textView5);
        itemDescription = findViewById(R.id.textView7);
        itemDate = findViewById(R.id.textView9);
        lat = findViewById(R.id.lat);
        lon = findViewById(R.id.lon);
        TextView itemLoc = findViewById(R.id.textView11);


        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyBVlNuAJksUQZ6q25FoJL0m_EHpbgp1XTw", Locale.US);
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        final ActivityResultLauncher<Intent> startAutocomplete = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            Place place = Autocomplete.getPlaceFromIntent(intent);
                            itemLoc.setText(place.getName());
                            lat.setText(String.valueOf(place.getLatLng().latitude));
                            lon.setText(String.valueOf(place.getLatLng().longitude));
                            Log.i("TAG", "Place: ${place.getName()}, ${place.getId()}");
                        }
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        // The user canceled the operation.
                        Log.i("TAG", "User canceled autocomplete");
                    }
                });

        Button search = (Button)findViewById(R.id.search) ;

        Button currentLocation = (Button)findViewById(R.id.current) ;

        dbHandler = new DBHandler(this);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the fields to specify which types of place data to
                // return after the user has made a selection.
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                        .build(FirstUI.this);
                startAutocomplete.launch(intent);
            }
        });

        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    fusedLocationClient.getLastLocation()
                            .addOnSuccessListener(FirstUI.this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    // Got last known location. In some rare situations this can be null.
                                    if (location != null) {
                                        // Logic to handle location object
                                        itemLoc.setText("lat- " + location.getLatitude() + " long- " + location.getLongitude());
                                        lat.setText(String.valueOf(location.getLatitude()));
                                        lon.setText(String.valueOf(location.getLongitude()));

                                    }
                                }
                            });
                } catch (SecurityException e){

                }

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHandler.addNewItem(postName.getText().toString(), phoneNumber.getText().toString(), itemDescription.getText().toString(), itemDate.getText().toString(), itemLoc.getText().toString(),
                        Double.valueOf(lat.getText().toString()), Double.valueOf(lon.getText().toString()));
                Toast.makeText(FirstUI.this, "Saved Successfully!!", Toast.LENGTH_SHORT).show();            }
        });

    }
}