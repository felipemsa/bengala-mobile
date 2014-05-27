package br.com.bengalamobile.contatos;

import static br.com.bengalamobile.Constantes.EXTRA_NOME_CONTATO;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import br.com.bengalamobile.R;


public class ContatosActivity extends Activity {
	
	ArrayList<Contato> listaContatos = new ArrayList<Contato>();
	ContatoAdapter contatoAdapter;
	MediaPlayer player;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contatos);
		
		 ListView listview = (ListView) findViewById(R.id.contactsList);
	
		 Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
	        while (phones.moveToNext()){
	        	listaContatos.add(new Contato(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
	        			phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));
	        }
		 
		 contatoAdapter = new ContatoAdapter(this, listaContatos);
		 listview.setAdapter(contatoAdapter);
		 
		 listview.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Contato c = listaContatos.get(position);
				Intent i = new Intent(getApplicationContext(), TranslationService.class);
				stopService(i);
				
				i.putExtra(EXTRA_NOME_CONTATO, c.getNome());
				startService(i);
				
				/*ConectarWebService.gerarAudioContato(c.getNome());
				SERVICE_AUDIO_URL + c.getNome()
				new Thread() {
					public void run() {
						if (player != null && player.isPlaying()) {
							player.stop();
						}
						try {
							player = new MediaPlayer();
							player.setDataSource("http://www.faulconer.com/mp3/DBZ-b1/Best1DBZtheme.mp3");
							player.prepare();
							player.start();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}.start();
				 */
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		 
		 listview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Contato c = listaContatos.get(position);
				Intent i = new Intent(getApplicationContext(), TranslationService.class);
				stopService(i);
				
				i.putExtra(EXTRA_NOME_CONTATO, c.getNome());
				startService(i);
				
				/*
				Uri uri = Uri.parse("tel://"+c.getTel());
				Intent i = new Intent(Intent.ACTION_CALL,uri);
				startActivity(i);
				*/
			}
		});
	}
	@Override
	protected void onDestroy() {
		if (player != null && player.isPlaying()) {
			player.stop();
		}
		super.onDestroy();
	}

}
