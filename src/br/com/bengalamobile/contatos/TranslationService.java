package br.com.bengalamobile.contatos;

import static br.com.bengalamobile.Acentuacao.retirarAcentos;
import static br.com.bengalamobile.Constantes.EXTRA_NOME_CONTATO;
import static br.com.bengalamobile.Constantes.URL_GOOGLE_TRANSLATOR;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.http.Header;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;

public class TranslationService extends Service
{

	MediaPlayer player;
	public final static String CHARSET = "UTF-8";

	@Override
	public void onCreate() {
		player = new MediaPlayer();
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		File cacheDir = new File(Environment.getExternalStorageDirectory(), "BengalaMobile");
		if (!cacheDir.exists())
			cacheDir.mkdirs();

		String nomeContato = intent.getStringExtra(EXTRA_NOME_CONTATO);
		nomeContato = retirarAcentos(nomeContato, true);

		final File f = new File(cacheDir, nomeContato + ".mp3");

		if (f.exists()) {
			try {
				player.setDataSource(this, Uri.parse(f.getPath()));
				player.prepare();
				player.start();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {

			try {
				player.setDataSource(this, Uri.parse(f.getPath()));
				player.prepare();
				player.start();
			} catch (Exception ex) {

			}

			String url = URL_GOOGLE_TRANSLATOR + nomeContato;

			AsyncHttpClient client = new AsyncHttpClient();
			String[] allowedContentTypes = new String[] { "audio/mpeg" };
			client.get(url, new BinaryHttpResponseHandler(allowedContentTypes)
			{
				@Override
				public void onSuccess(byte[] fileData) {
					try {
						FileOutputStream fos = new FileOutputStream(f);
						fos.write(fileData);
						fos.close();

						player.setDataSource(TranslationService.this, Uri.parse(f.getPath()));
						player.prepare();
						player.start();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {
					try {
						player.setDataSource(TranslationService.this, Uri.parse("android.resource://br.com.bengalamobile/raw/tts_error"));
						player.prepare();
						player.start();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					super.onFailure(statusCode, headers, binaryData, error);
				}
			});
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
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
