package com.example.product;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Profile extends AppCompatActivity implements View.OnClickListener {

    CrimeDbHelper crimeDbHelper;
    Context context = this;
    SQLiteDatabase sqLiteDatabase;

    private FirebaseAuth firebaseAuth;

    //private GPS
    private Button buttonlocation;
    private TextView tvlocation;

    //private Buttons
    private Button buttoncreate;


    //private Edit Text
    private TextView editTextname;
    private TextView editTextrefence;
    private TextView editTextdate;
    private TextView editTexttime;
    private TextView editTextlocation;
    private TextView editTextdescription;


    //private Spinner
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, loginActivity.class));
        }


        //firebase
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //textview
        editTextname = (TextView) findViewById(R.id.editTextname);
        editTextrefence = (TextView) findViewById(R.id.editTextreference);
        editTexttime = (TextView) findViewById(R.id.editTexttime);
        editTextdate = (TextView) findViewById(R.id.editTextdate);
        editTextlocation = (TextView) findViewById(R.id.editTextlocation);
        editTextdescription = (TextView) findViewById(R.id.editTextdescription);



        //spinner
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Profile.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.type));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter); // this will show the data on the drop box


        //buttons
        buttoncreate = (Button) findViewById(R.id.buttoncreate);



        //button listener

        buttoncreate.setOnClickListener(this);

        //GPS
        tvlocation = (TextView) findViewById(R.id.coordinates);
        buttonlocation = (Button)findViewById(R.id.buttonlocation);
        buttonlocation.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
    if(view == buttoncreate ){

        String name = editTextname.getText().toString();
        String reference = editTextrefence.getText().toString();
        String type = spinner.getSelectedItem().toString();
        String time = editTexttime.getText().toString();
        String date = editTextdate.getText().toString();
        String location = editTextlocation.getText().toString();
        String description = editTextdescription.getText().toString();

        crimeDbHelper = new CrimeDbHelper(context);

        sqLiteDatabase = crimeDbHelper.getWritableDatabase();

        crimeDbHelper.addinformation(name,reference,type,time,date,location,description,sqLiteDatabase);
        Toast.makeText(getBaseContext(),"Data Saved",Toast.LENGTH_LONG).show();
        crimeDbHelper.close();

    }
    if (view == buttonlocation){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED){

         requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);

        }else {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            try {
                String city = hereLocation(location.getLatitude(), location.getLongitude());
                tvlocation.setText(city);
            } catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
            }
        }
    }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1000:{
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    try {
                        String city = hereLocation(location.getLatitude(), location.getLongitude());
                        tvlocation.setText(city);
                    } catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
                    }
                }else {
                   Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }

        }
    }

    private String hereLocation(double lat, double lon){
      String cityname ="";


      Geocoder geocoder = new Geocoder(this, Locale.getDefault());
      List<Address> addresses;
      try {
          addresses = geocoder.getFromLocation(lat, lon, 10);
          if (addresses.size() > 0){
              for (Address adr: addresses){
                  if (adr.getLocality() != null && adr.getLocality().length() > 0){
                      cityname = adr.getLocality();
                      break;
                  }
              }
          }
      } catch (IOException e){
          e.printStackTrace();
      }

      return cityname;



    }

}