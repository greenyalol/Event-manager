package com.pts.greenya.eventmanager.location;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

/**
 * Created by Greenya on 20.05.2017.
 */

public class MyLocationListener implements LocationListener {

    static Location imHere; // здесь будет всегда доступна самая последняя информация о местоположении пользователя.

    public static void SetUpLocationListener(Context context) {

        LocationManager locationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new MyLocationListener();
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return  ;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                10,
                locationListener); // здесь можно указать другие более подходящие вам параметры

        imHere = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onLocationChanged(Location loc) {
        imHere = loc;
    }
    @Override
    public void onProviderDisabled(String provider) {}
    @Override
    public void onProviderEnabled(String provider) {}
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}