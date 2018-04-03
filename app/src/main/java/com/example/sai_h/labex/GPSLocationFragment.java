package com.example.sai_h.labex;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.w3c.dom.Text;

import java.util.Timer;

/*To use GoogleApiClient, add these lines in build.gradle (Module:app)
implementation 'com.google.android.gms:play-services-maps:11.8.0'
implementation 'com.google.android.gms:play-services-location:11.8.0'
implementation 'com.google.android.gms:play-services-places:11.8.0'*/

public class GPSLocationFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,com.google.android.gms.location.LocationListener{
    View v;
    TextView lt,ln;
    static double lat, lon;
    Location loc;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mRequest;
    Context c;
    Activity a;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*ViewGroup to be inflated into the activity
          This is not necessary in activity. In activity it is autogenerated
          super.onCreate(savedInstanceState);
          setContentView(R.layout.your_activity);
         */
        v = inflater.inflate(R.layout.fragment_gps_tracking,container,false);
        c = getContext();
        a = getActivity();
        //All the view elements of the fragment are identified using the viewgroup. Here viewgroup is stored in variable v
        lt = (TextView)v.findViewById(R.id.gpslat);
        ln = (TextView)v.findViewById(R.id.gpslon);
        this.buildGoogleapiclient();
        return v; //Returns the Viewgroup to the activity class for inflation
    }

    synchronized void buildGoogleapiclient() {
        /*Building a GoogleApiClient for LocationServices with
        ConnectionCallbacks, OnConnectionFailed Listener , onLocationChanged Listener*/
        mGoogleApiClient = new GoogleApiClient.Builder(c)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(a, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
            //Requesting location updates
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mRequest, this);
            //Getting last updated location
            loc = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }
        if (loc != null) {
            lat = loc.getLatitude();
            lon = loc.getLongitude();
        }
        update();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mRequest = LocationRequest.create();
        mRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mRequest.setMaxWaitTime(5000);
        mRequest.setInterval(10000);
        try {
            if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(a, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
            //Requesting location updates
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mRequest, (com.google.android.gms.location.LocationListener) this);
            //Getting last updated location
            loc = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }
        if (loc != null) {
            lat = loc.getLatitude();
            lon = loc.getLongitude();
        }
        update();
    }
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        buildGoogleapiclient();
    }
    public void update(){
        lt.setText(String.valueOf(lat));
        ln.setText(String.valueOf(lon));
    }

}
