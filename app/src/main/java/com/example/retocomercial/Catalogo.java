package com.example.retocomercial;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Catalogo extends AppCompatActivity {
    private ListView lvItems;
    private Adaptador adaptador;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalogo);
        Button Volver = (Button) findViewById(R.id.btnVolver5);
        lvItems = (ListView) findViewById(R.id.lvItems);
        adaptador = new Adaptador(this,GetArrayItems());
        lvItems.setAdapter(adaptador);
        Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Catalogo.this, Pedidos.class);
                startActivityForResult(intent,3456);
            }
        });


    }

    private ArrayList<Articulo> GetArrayItems(){
        ArrayList<Articulo> listItems = new ArrayList<>();

        UsuariosSQLiteHelper usdbh =new UsuariosSQLiteHelper(this, "BaseDatosIkeya", null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();

        Cursor c = db.rawQuery(" SELECT id_articulo,descripcion,precio,imagen FROM articulos", null);

        if (c.moveToFirst()) {
            do {
                Integer codigo= c.getInt(0);
                String descripcion = c.getString(1);
                Integer precio = c.getInt(2);
                String imagen = c.getString(3);
                int resID = getResources().getIdentifier(imagen , "drawable", getPackageName());
                listItems.add(new Articulo(resID,"Articulo "+codigo.toString(),descripcion + " , Precio: "+precio.toString()));
            } while(c.moveToNext());
        }
        return listItems;
    }
}
