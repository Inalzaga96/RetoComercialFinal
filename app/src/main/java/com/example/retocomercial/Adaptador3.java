package com.example.retocomercial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador3 extends BaseAdapter {
    private Context context;
    private ArrayList<Pedido> listItems;

    public Adaptador3(Context context, ArrayList<Pedido> listItems) {
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
        Pedido item = (Pedido) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.item2, null);
        TextView tvTitulo = (TextView) convertView.findViewById(R.id.TvTitulo);
        TextView tvContenido = (TextView) convertView.findViewById(R.id.TvContenido);
        TextView tvContenido2 = (TextView) convertView.findViewById(R.id.TvContenido2);
        tvTitulo.setText(item.getTitulo());
        tvContenido.setText(item.getContenido());
        tvContenido2.setText(item.getContenido2());

        return convertView;
    }
}
