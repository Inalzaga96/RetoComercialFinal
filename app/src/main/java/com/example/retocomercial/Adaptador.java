package com.example.retocomercial;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
public class Adaptador extends BaseAdapter {
    private Context context;
    private ArrayList<Articulo> listItems;

    public Adaptador(Context context, ArrayList<Articulo> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    public int getCount(){
        return listItems.size();
    }
    public long getItemId(int position){
        return 0;
    }

    public Object getItem(int position){
        return listItems.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent){
      Articulo item = (Articulo) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.item, null);
        ImageView imgFoto = (ImageView) convertView.findViewById(R.id.imgFoto);
        TextView tvTitulo = (TextView) convertView.findViewById(R.id.TvTitulo);
        TextView tvContenido = (TextView) convertView.findViewById(R.id.TvContenido);

        imgFoto.setImageResource(item.getImgFoto());
        tvTitulo.setText(item.getTitulo());
        tvContenido.setText(item.getContenido());

      return convertView;
    }
}
