package br.com.bengalamobile.location;

import static java.lang.String.valueOf;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import br.com.bengalamobile.GravarPin;
import br.com.bengalamobile.MainActivity;

public class LocationService extends Service {

	private LocationManager locManager;
	private long scheduleTime = 3*60*1000;
	private Timer timer;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		timer = new Timer();
		timer.schedule(new Task(), 0, scheduleTime);
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		timer.cancel();
		super.onDestroy();
	}
	
	class Task extends TimerTask {
		@Override
		public void run() {
			Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location != null) {
				List<NameValuePair> basicPairs = new ArrayList<NameValuePair>();
				basicPairs.add(new BasicNameValuePair("token",valueOf(MainActivity.IMEI)));
				basicPairs.add(new BasicNameValuePair("latitude",valueOf(location.getLatitude())));
				basicPairs.add(new BasicNameValuePair("longitude",valueOf(location.getLongitude())));
				
				GravarPin.addTokenWithCoordinates(basicPairs);
			}
		}
	}
}