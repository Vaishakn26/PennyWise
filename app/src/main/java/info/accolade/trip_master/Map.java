package info.accolade.trip_master;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends AppCompatActivity {
    String lat1,long1,name1;
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        retrieve();
    }
    public void retrieve(){
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        // Showing status
        if(status!= ConnectionResult.SUCCESS){ // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, getParent(), requestCode);
            dialog.show();

        }else { // Google Play Services are available

            // Getting reference to the SupportMapFragment of activity_main.xml
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

            // Getting GoogleMap object from the fragment
//            googleMap = fm.getMap();
//
//            // Enabling MyLocation Layer of Google Map
//            googleMap.setMyLocationEnabled(true);

            // LatLng object to store user input coordinates
//            LatLng point = new LatLng();

            // Drawing the marker at the coordinates
//            drawMarker(point);
        }

    }


    private void drawMarker(LatLng point){
        // Clears all the existing coordinates
        googleMap.clear();

        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting latitude and longitude for the marker
        markerOptions.position(point);

        // Setting title for the InfoWindow


        markerOptions.title(name1);

        // Adding marker on the Google Map
        googleMap.addMarker(markerOptions);

        // Moving CameraPosition to the user input coordinates
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(point));

    }





}