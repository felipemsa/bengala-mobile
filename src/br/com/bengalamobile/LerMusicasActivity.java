package br.com.bengalamobile;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import br.com.bengalamobile.R;


public class LerMusicasActivity extends Activity {

	 public void onCreate(Bundle savedInstanceState) {
		 
	        super.onCreate(savedInstanceState);
	 
	        setContentView(R.layout.lermusicas); 
	        
	        //getAllSongs(0
	        
	       //apos chamar o getall songs , ligar outro adapter para
	        //fazer
	        
	 }
	 @SuppressWarnings({ "deprecation", "unused" })
	public void getAllSongs() {

		    Uri allsongsuri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		    String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

		    Cursor cursor = managedQuery(allsongsuri, null, selection, null, null);
		    String song_name = "";
		    String fullpath = "";
		    String album_name= "";
		    String artist_name= "";
		    String fullsongpath= "";
		    
		    if (cursor != null) {
		        if (cursor.moveToFirst()) {
		            do {
		            	song_name = cursor
		                        .getString(cursor
		                                .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
		                int song_id = cursor.getInt(cursor
		                        .getColumnIndex(MediaStore.Audio.Media._ID));

		                fullpath = cursor.getString(cursor
		                        .getColumnIndex(MediaStore.Audio.Media.DATA));
		                fullsongpath= fullpath;

		                album_name = cursor.getString(cursor
		                        .getColumnIndex(MediaStore.Audio.Media.ALBUM));
		                int album_id = cursor.getInt(cursor
		                        .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));

		                artist_name = cursor.getString(cursor
		                        .getColumnIndex(MediaStore.Audio.Media.ARTIST));
		                int artist_id = cursor.getInt(cursor
		                        .getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));


		            } while (cursor.moveToNext());

		        }
		        cursor.close();
		    }
		}
	
}
