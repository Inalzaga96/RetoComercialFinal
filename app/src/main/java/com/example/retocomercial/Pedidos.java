package com.example.retocomercial;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;

public class Pedidos extends AppCompatActivity {
    private Spinner partners;
    private ListView pedidos;
    private ArrayList<String> listaArray;
    private ArrayList<String> listaArraySpinner;
    private ArrayList<Integer> codPartner;
    private Button anadir;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedidos);
        partners=findViewById(R.id.spnPartners);
        pedidos=findViewById(R.id.listPedidos);
        listaArraySpinner=new ArrayList<String>();
        codPartner=new ArrayList<Integer>();
        listaArray=new ArrayList<String>();
        anadir=findViewById(R.id.btnAnadir);
        Button home = (Button) findViewById(R.id.btnHome);
        Button nuevo = (Button) findViewById(R.id.btnAnadir);
        home.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                vuelve(null, 1234);
            }
        });
        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NuevoPedido(null);
            }
        });

        UsuariosSQLiteHelper usdbh =new UsuariosSQLiteHelper(this, "BaseDatosIkeya", null, 1);

        final SQLiteDatabase db = usdbh.getReadableDatabase();

        Cursor c0 = db.rawQuery(" SELECT empresa, idPartner FROM Partners", null);

        if (c0.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String empresa= c0.getString(0);
                listaArraySpinner.add(empresa);
                codPartner.add(c0.getInt(1));
            } while(c0.moveToNext());
        }
        ArrayAdapter<String> adapter2;
        adapter2=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listaArraySpinner);
        partners.setAdapter(adapter2);


        partners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listaArray.clear();
                Cursor c = db.rawQuery("SELECT cod, fecha FROM pedidos WHERE id_partner="+codPartner.get(partners.getSelectedItemPosition()), null);
                if (c.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {
                        listaArray.add(c.getInt(c.getColumnIndex("cod"))+"  -  "+c.getString(1));
                    } while(c.moveToNext());
                }
                addPedido();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void NuevoPedido(View view) {
        //Intent intent = new Intent(this, InsertUpdatePedidos.class);
        Bundle bnd=new Bundle();
        bnd.putInt("cod",codPartner.get(partners.getSelectedItemPosition()));
        //startActivityForResult(intent,3456);
    }
    private void vuelve(View view,int num){
        Intent intent = new Intent(this, MainActivity.class);
        setResult(RESULT_OK,intent);
        finish();
    }
    private void addPedido(){
        ArrayAdapter<String> adapter;
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listaArray);
        pedidos.setAdapter(adapter);
    }
}