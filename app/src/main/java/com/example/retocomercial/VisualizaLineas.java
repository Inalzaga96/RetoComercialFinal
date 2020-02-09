package com.example.retocomercial;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class VisualizaLineas extends AppCompatActivity {
    private Pedido item;
    private Spinner lineas;
    private ArrayList<String> listaArraySpinner;
    private ArrayList<Integer> codLineas;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizalineas);
        item = (Pedido) getIntent().getSerializableExtra("objetoData2");
        lineas=findViewById(R.id.spnLineas);
        listaArraySpinner=new ArrayList<String>();
        codLineas=new ArrayList<Integer>();
        final EditText codProdu= (EditText)findViewById(R.id.codProdu);
        final EditText codPed= (EditText)findViewById(R.id.CodPed);
        final EditText Cantidad= (EditText)findViewById(R.id.Cantidad);
        final EditText importe= (EditText)findViewById(R.id.Importe);

        UsuariosSQLiteHelper usdbh =new UsuariosSQLiteHelper(this, "BaseDatosIkeya", null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        Cursor c0 = db.rawQuery(" SELECT cod FROM lineas WHERE cod_pedido = "+item.getTitulo(), null);
        if (c0.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                Integer codigo= c0.getInt(0);
                listaArraySpinner.add(codigo.toString());
                codLineas.add(codigo);
            } while(c0.moveToNext());
        }
        ArrayAdapter<String> adapter2;
        adapter2=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listaArraySpinner);
        lineas.setAdapter(adapter2);
        db.close();

        lineas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UsuariosSQLiteHelper usdbh =new UsuariosSQLiteHelper(VisualizaLineas.this, "BaseDatosIkeya", null, 1);
                SQLiteDatabase db = usdbh.getReadableDatabase();
                Cursor c = db.rawQuery("SELECT cod_producto,cod_pedido,unidad,importe FROM lineas WHERE cod="+codLineas.get(lineas.getSelectedItemPosition())+" AND cod_pedido = "+item.getTitulo(), null);
                if (c.moveToFirst()) {
                        Integer codProducto= c.getInt(0);
                        Integer codPedido = c.getInt(1);
                        Integer unidadvar= c.getInt(2);
                        Integer importevar = c.getInt(3);
                        codProdu.setText(String.valueOf(codProducto));
                        codPed.setText(String.valueOf(codPedido));
                        Cantidad.setText(String.valueOf(unidadvar));
                        importe.setText(String.valueOf(importevar));
                }
                db.close();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
