package com.example.retocomercial;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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
