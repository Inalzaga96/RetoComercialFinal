package com.example.retocomercial;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class VisualizaLineas extends AppCompatActivity {
    private Pedido item;
    private Spinner lineas;
    private ArrayList<String> listaArraySpinner;
    private ArrayList<Integer> codLineas;
    EditText codProdu;
    EditText codPed;
    EditText Cantidad;
    EditText importe;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizalineas);
        item = (Pedido) getIntent().getSerializableExtra("objetoData");
        lineas=findViewById(R.id.spnLineas);
        listaArraySpinner=new ArrayList<String>();
        codLineas=new ArrayList<Integer>();
        codProdu= (EditText)findViewById(R.id.codProdu);
        codPed= (EditText)findViewById(R.id.CodPed);
        Cantidad= (EditText)findViewById(R.id.Cantidad);
        importe= (EditText)findViewById(R.id.Importe);
        Button btnVolver = (Button) findViewById(R.id.btnVolver);
        Button btnGuardar = (Button) findViewById(R.id.btnGuardar);
        Button btnBorraLinea = (Button) findViewById(R.id.btnBorraLinea);
        Button btnNueva= (Button) findViewById(R.id.btnNuevo);
        Button btnBorraPed = (Button) findViewById(R.id.btnBorraPed);


        UsuariosSQLiteHelper usdbh =new UsuariosSQLiteHelper(this, "BaseDatosIkeya", null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT Max(cod) FROM lineas WHERE cod_pedido = "+item.getTitulo(),null);
        c.moveToFirst();
        int count= c.getInt(0);

        if(count == 0){
            codProdu.setFocusable(false);
            codPed.setFocusable(false);
            Cantidad.setFocusable(false);
            importe.setFocusable(false);
            btnGuardar.setEnabled(false);
            btnBorraLinea.setEnabled(false);
        }

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

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vuelve();
            }
        });
        btnBorraLinea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borra();
            }
        });
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guarda();
            }
        });
        btnNueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VisualizaLineas.this,NuevaLinea.class);
                intent.putExtra("objetoData2",item.getTitulo());
                startActivity(intent);
                finish();
            }
        });
        btnBorraPed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsuariosSQLiteHelper bd = new UsuariosSQLiteHelper(getApplication(), "BaseDatosIkeya", null, 1);
                SQLiteDatabase db = bd.getWritableDatabase();
                String sql = String.format("DELETE FROM lineas WHERE cod_pedido = '%s'",item.getTitulo());
                String sql2 = String.format("DELETE FROM pedidos WHERE cod = '%s'",item.getTitulo());
                try{
                    db.execSQL(sql);
                    db.execSQL(sql2);
                    Toast aviso = Toast.makeText(getApplicationContext(), "DELETE EXITOSO!", Toast.LENGTH_SHORT);
                    aviso.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    aviso.show();
                    vuelve();
                }catch(Exception e){
                    e.getMessage();
                    Toast aviso = Toast.makeText(getApplicationContext(), "DELETE ERRONEO!", Toast.LENGTH_SHORT);
                    aviso.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    aviso.show();
                }
                db.close();
            }
        });
    }
    private void vuelve(){
        Intent intent = new Intent(this, Pedidos.class);
        startActivityForResult(intent,3456);
        finish();
    }
    private void borra(){
        UsuariosSQLiteHelper bd =new UsuariosSQLiteHelper(this, "BaseDatosIkeya", null, 1);
        SQLiteDatabase db = bd.getWritableDatabase();
        String sql = String.format("DELETE FROM lineas WHERE cod = '%s' AND cod_pedido = '%s'",codLineas.get(lineas.getSelectedItemPosition()),item.getTitulo());
        try{
            db.execSQL(sql);
            Toast aviso = Toast.makeText(getApplicationContext(), "DELETE EXITOSO!", Toast.LENGTH_SHORT);
            aviso.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
            aviso.show();
            vuelve();
        }catch(Exception e){
            e.getMessage();
            Toast aviso = Toast.makeText(getApplicationContext(), "DELETE ERRONEO!", Toast.LENGTH_SHORT);
            aviso.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
            aviso.show();
        }
        db.close();
    }
    private void guarda(){
        UsuariosSQLiteHelper bd = new UsuariosSQLiteHelper(getApplication(), "BaseDatosIkeya", null, 1);
        SQLiteDatabase db = bd.getWritableDatabase();
        String sql = String.format("UPDATE lineas SET cod_producto = '%s', cod_pedido = '%s', unidad = '%s', importe = '%s' WHERE cod = '%s' AND cod_pedido = '%s'",codProdu.getText(),codPed.getText(),Cantidad.getText(),importe.getText(),codLineas.get(lineas.getSelectedItemPosition()),item.getTitulo());
        try{
            db.execSQL(sql);
            Toast aviso = Toast.makeText(getApplicationContext(), "UPDATE EXITOSO!", Toast.LENGTH_SHORT);
            aviso.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
            aviso.show();
            vuelve();
        }catch(Exception e){
            e.getMessage();
            Toast aviso = Toast.makeText(getApplicationContext(), "UPDATE ERRONEO!", Toast.LENGTH_SHORT);
            aviso.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
            aviso.show();
        }
        db.close();
    }
}
