package br.com.bengalamobile.contatos;

import static br.com.bengalamobile.Acentuacao.retirarAcentos;
import static br.com.bengalamobile.Constantes.EXTRA_NOME_CONTATO;
import static br.com.bengalamobile.Constantes.URL_GOOGLE_TRANSLATOR;

import java.net.URL;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

public class TranslationService extends Service {

	MediaPlayer player;
	public final static String CHARSET = "UTF-8";
	
	@Override
	public void onCreate() {
		Log.d("service", "Create service");
		player = new MediaPlayer();;
		super.onCreate();
	}
	
	@Override
	public int onStartCommand (Intent intent, int flags, int startId) {
		try {
			String nomeContato = intent.getStringExtra(EXTRA_NOME_CONTATO);
			
			String url = URL_GOOGLE_TRANSLATOR + retirarAcentos(nomeContato, true);
			Log.d("translate", url);
			
			player.setDataSource(new URL(url).toString());
			player.prepare();
			player.start();
			
		} catch (Exception e) {
			try {
				player.setDataSource(this, Uri.parse("android.resource://br.com.bengalamobile/raw/tts_error"));
				player.prepare();
				player.start();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		Log.d("service", "Destroy service");
		if (player != null && player.isPlaying()) {
			player.stop();
			player.release();
		}
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
