package br.com.bengalamobile.contatos;

import static br.com.bengalamobile.Acentuacao.retirarAcentos;
import static br.com.bengalamobile.Constantes.EXTRA_NOME_CONTATO;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import br.com.bengalamobile.R;

public class ContatosActivity extends Activity
{

	ArrayList<Contato> listaContatos = new ArrayList<Contato>();
	ContatoAdapter contatoAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contatos);

		ListView listview = (ListView) findViewById(R.id.contactsList);

		Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
		while (phones.moveToNext()) {
			listaContatos.add(new Contato(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
					phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));
		}

		contatoAdapter = new ContatoAdapter(this, listaContatos);
		listview.setAdapter(contatoAdapter);

		listview.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Contato c = listaContatos.get(position);
				Intent i = new Intent(getApplicationContext(), TranslationService.class);
				stopService(i);

				i.putExtra(EXTRA_NOME_CONTATO, c.getNome());
				startService(i);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		listview.setOnItemClickListener(new OnItemClickListener()
		{

			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				final Contato c = listaContatos.get(position);
				 Intent i = new Intent(getApplicationContext(),
				 TranslationService.class);
				 stopService(i);
				
				 i.putExtra(EXTRA_NOME_CONTATO, c.getNome());
				 startService(i);
/*
				final MediaPlayer player = new MediaPlayer();

				try {
					final File f = new File(Environment.getExternalStorageDirectory(), "BengalaMobile/" + retirarAcentos(c.getNome(), true) + ".mp3");

					player.setDataSource(ContatosActivity.this, Uri.parse("android.resource://br.com.bengalamobile/raw/tts_chamando"));
					player.prepare();
					player.start();
					player.setOnCompletionListener(new OnCompletionListener()
					{
						@Override
						public void onCompletion(MediaPlayer mp) {
							try {
								MediaPlayer player2 = new MediaPlayer();
								player2.setDataSource(ContatosActivity.this, Uri.parse(f.getPath()));
								player2.prepare();
								player2.start();
								player2.setOnCompletionListener(new OnCompletionListener()
								{
									@Override
									public void onCompletion(MediaPlayer mp) {
										Uri uri = Uri.parse("tel://" + c.getTel());
										Intent i = new Intent(Intent.ACTION_CALL, uri);
										startActivity(i);
									}
								});
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					});

				} catch (Exception ex) {
					ex.printStackTrace();
				}
*/
			}
		});
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}

}
