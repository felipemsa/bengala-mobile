package br.com.bengalamobile.contatos;

import java.util.ArrayList;

import br.com.bengalamobile.R;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContatoAdapter extends BaseAdapter {
	
	ArrayList<Contato> contatos;
	Activity activity;
	
	public ContatoAdapter(Activity activity, ArrayList<Contato> contatos){
		this.activity = activity;
		this.contatos = contatos;
	}
	

	public int getCount() {
		
		return contatos.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int arg0) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup arg2) {
		
		View view = convertView;
		if(view == null) {
			view = activity.getLayoutInflater().inflate(R.layout.linha_contato, null);
		}
		
		Contato c = contatos.get(position);
		
		TextView textNome = (TextView) view.findViewById(R.id.text_contato);
		TextView textTel = (TextView) view.findViewById(R.id.text_tele);
		
		textNome.setText(c.getNome());
		textTel.setText(c.getTel());
		
		return view;
	}

}
