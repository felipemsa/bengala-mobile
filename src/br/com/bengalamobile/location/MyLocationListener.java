package br.com.bengalamobile.location;

import static java.lang.String.valueOf;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import br.com.bengalamobile.GravarPin;
import br.com.bengalamobile.MainActivity;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class MyLocationListener implements LocationListener {

	public MyLocationListener() {}

	@Override
	public void onLocationChanged(Location loc) {
		List<NameValuePair> basicPairs = new ArrayList<NameValuePair>();
		basicPairs.add(new BasicNameValuePair("token",valueOf(MainActivity.IMEI)));
		basicPairs.add(new BasicNameValuePair("latitude",valueOf(loc.getLatitude())));
		basicPairs.add(new BasicNameValuePair("longitude",valueOf(loc.getLongitude())));
		
		GravarPin.addTokenWithCoordinates(basicPairs);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO do something?
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO do something?
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO do something?
	}

}
