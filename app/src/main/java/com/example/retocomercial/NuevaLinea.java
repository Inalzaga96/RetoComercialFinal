package com.example.retocomercial;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class NuevaLinea extends AppCompatActivity {
    private String codigoPedido;
    private EditText codProd;
    private EditText cantidad;
    private EditText importe;
    private Button btnCrear;
    private Button btnVolver;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevalinea);
        codigoPedido = getIntent().getStringExtra("objetoData2");
        codProd = (EditText) findViewById(R.id.codProdu3);
        cantidad = (EditText) findViewById(R.id.cantidad2);
        importe =  (EditText) findViewById(R.id.importe2);
        btnCrear = (Button) findViewById(R.id.btnCrear);
        btnVolver = (Button) findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NuevaLinea.this, VisualizaLineas.class);
                startActivityForResult(intent,3456);
            }
        });
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatos();
            }
        });
    }
    private void guardarDatos() {
        if (codProd.getText().length()==0){
            codProd.setError("El codigo de producto esta vacio");
        } else if (cantidad.getText().length()==0) {
            cantidad.setError("Introduce una cantidad");
        } else if (importe.getText().length()==0) {
            importe.setError("Introduce el importe");
        } else {
            UsuariosSQLiteHelper bd = new UsuariosSQLiteHelper(getApplication(), "BaseDatosIkeya", null, 1);
            SQLiteDatabase db = bd.getWritableDatabase();
            Cursor cursor = db.rawQuery("select Max(cod) from lineas WHERE cod_pedido = "+codigoPedido , null);
            cursor.moveToFirst();
            int count= cursor.getInt(0);
            String sql = String.format("INSERT INTO lineas VALUES('%s', '%s', '%s', '%s', '%s')",(count+1),codProd.getText(),codigoPedido,cantidad.getText(),importe.getText());
            try {
                db.execSQL(sql);
                Intent intent = new Intent(this, VisualizaLineas.class);
                startActivityForResult(intent,3456);
            } catch (Exception e) {
                Toast aviso = Toast.makeText(getApplicationContext(), "INSERT ERRONEO!", Toast.LENGTH_SHORT);
                aviso.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                aviso.show();
                e.getMessage();
            }
            db.close();
        }
    }
}

