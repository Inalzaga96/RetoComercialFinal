package com.example.retocomercial;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Catalogo extends AppCompatActivity {
    private ListView lvItems;
    private Adaptador adaptador;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalogo);

        lvItems = (ListView) findViewById(R.id.lvItems);
        adaptador = new Adaptador(this,GetArrayItems());
        lvItems.setAdapter(adaptador);
    }

    private ArrayList<Articulo> GetArrayItems(){
        ArrayList<Articulo> listItems = new ArrayList<>();
        listItems.add(new Articulo(R.drawable.ikeya,"Articulo 1","Precio: 27,99"));
        listItems.add(new Articulo(R.drawable.ikeya,"Articulo 2","Precio: 35,67"));
        listItems.add(new Articulo(R.drawable.ikeya,"Articulo 3","Precio: 24,99"));
        listItems.add(new Articulo(R.drawable.ikeya,"Articulo 4","Precio: 13,99"));
        listItems.add(new Articulo(R.drawable.ikeya,"Articulo 5","Precio: 56,99"));

        return listItems;
    }
}
