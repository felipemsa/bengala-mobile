package br.com.bengalamobile;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;
import static br.com.bengalamobile.GravarPin.addToken;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import br.com.bengalamobile.R;
import br.com.bengalamobile.contatos.ContatosActivity;
import br.com.bengalamobile.location.LocationService;


public class MainActivity extends Activity {
	
	public static long IMEI;
	
	private static final String PREFERENCES = "preferencias";
	
	private LocationManager locManager;

	//protected PowerManager.WakeLock mWakeLock;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        IMEI = Long.parseLong(telephonyManager.getDeviceId());
        
        new Thread() {
        	public void run() {
        		SharedPreferences pref = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        		SharedPreferences.Editor editor = pref.edit();
        		if (!pref.getBoolean("tokenGravado", false)) {
        			if (addToken(IMEI)) {
        				makeText(getApplicationContext(), "Token gravado com sucesso", LENGTH_LONG).show();
        				editor.putBoolean("tokenGravado", true);
        				editor.commit();
        			} else {
        				editor.putBoolean("tokenGravado", false);
        				editor.commit();
        			}
        		}
        	}
        }.start();
        if (/*pref.getBoolean("gravarLatLon", false)*/true) {
			locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				buildAlertMessageNoGps();
			}
        	startService(new Intent(this,LocationService.class));
        }
        
        /* This code together with the one in onDestroy() 
         * will make the screen be always on until this Activity gets destroyed. 
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(View.KEEP_SCREEN_ON, "My Tag");
        this.mWakeLock.acquire();*/
    }
    
    @Override
    protected void onDestroy() {
    	//stopService(null);
    	super.onDestroy();
    }
    
    @Override
    protected void onPause() {
    	//mWakeLock.release(); can be used on method onDestroy()
    	super.onPause();
    }

    public void openMusicasActivity(View v) {
    	startActivity(new Intent(this,LerMusicasActivity.class));
    }
    
    public void openContatosActivity(View v) {
    	startActivity(new Intent(this,ContatosActivity.class));
    }
    
    public void buildAlertMessageNoGps() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"O GPS deve estar ativado para esta funcionalidade.\nDeseja ativar?")
				.setCancelable(false)
				.setPositiveButton("Sim",
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog,
									final int id) {
								startActivity(new Intent(
										android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
							}
						})
				.setNegativeButton("N�o", new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog,
							final int id) {
						dialog.cancel();
					}
				});
		final AlertDialog alert = builder.create();
		alert.show();
	}
    
}
