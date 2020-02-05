package com.example.retocomercial;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class VisualizaPartner extends AppCompatActivity {
    TextView empresa,IDComer,nombre,apellidos,empr,direc,pobla,telef,email;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizapartner);
        empresa = (TextView) findViewById(R.id.txtBusca);
        IDComer = (TextView) findViewById(R.id.txtIDComer);
        nombre = (TextView) findViewById(R.id.txtNom);
        apellidos = (TextView) findViewById(R.id.txtApell);
        empr = (TextView) findViewById(R.id.txtEmpr);
        direc = (TextView) findViewById(R.id.txtDirec);
        pobla = (TextView) findViewById(R.id.txtPobla);
        telef = (TextView) findViewById(R.id.txtTelef);
        email = (TextView) findViewById(R.id.txtEmail);
        Button buscador = (Button) findViewById(R.id.btnBuscador);
        Button guardar = (Button) findViewById(R.id.btnGuardar);
        Button volver = (Button) findViewById(R.id.btnVolver);
        Button borrar = (Button) findViewById(R.id.btnBorrar);

        buscador.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                visualiza();
            }
        });
        guardar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                guarda();
            }
        });
        volver.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                vuelve();
            }
        });
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borra();
            }
        });
    }

    private void visualiza(){
        UsuariosSQLiteHelper usdbh =new UsuariosSQLiteHelper(this, "BaseDatosIkeya", null, 1);

        SQLiteDatabase db = usdbh.getReadableDatabase();

        try{
        Cursor c = db.rawQuery("SELECT * FROM Partners WHERE empresa='"+ empresa.getText() +"'", null);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            Integer idComer = c.getInt(c.getColumnIndex("id_comercial"));
            String nom = c.getString(c.getColumnIndex("nombre"));
            String apelli = c.getString(c.getColumnIndex("apellidos"));
            String empre = c.getString(c.getColumnIndex("empresa"));
            String direcc = c.getString(c.getColumnIndex("direccion"));
            String telefo = c.getString(c.getColumnIndex("telefono"));
            String poblaci = c.getString(c.getColumnIndex("poblacion"));
            String em = c.getString(c.getColumnIndex("email"));

            IDComer.setText(String.valueOf(idComer));
            nombre.setText(nom);
            apellidos.setText(apelli);
            empr.setText(empre);
            direc.setText(direcc);
            telef.setText(telefo);
            pobla.setText(poblaci);
            email.setText(em);
        }
        }catch(Exception e){
            Toast aviso = Toast.makeText(getApplicationContext(), "ESTA EMPRESA NO EXISTE!", Toast.LENGTH_SHORT);
            aviso.setGravity(Gravity.BOTTOM, 0, 0);
            aviso.show();

        }
    }
    private void guarda(){
        UsuariosSQLiteHelper bd = new UsuariosSQLiteHelper(getApplication(), "BaseDatosIkeya", null, 1);
        SQLiteDatabase db = bd.getWritableDatabase();
        String sql = String.format("UPDATE Partners SET id_comercial = '%s', nombre = '%s', apellidos = '%s', empresa = '%s', direccion = '%s', telefono = '%s', poblacion = '%s', email = '%s' WHERE empresa = '%s'",IDComer.getText(),nombre.getText(),apellidos.getText(),empr.getText(),direc.getText(),telef.getText(),pobla.getText(),email.getText(),empresa.getText());
        try{
            db.execSQL(sql);
            Toast aviso = Toast.makeText(getApplicationContext(), "UPDATE EXITOSO!", Toast.LENGTH_SHORT);
            aviso.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
            aviso.show();
            finish();
        }catch(Exception e){
            e.getMessage();
            Toast aviso = Toast.makeText(getApplicationContext(), "UPDATE ERRONEO!", Toast.LENGTH_SHORT);
            aviso.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
            aviso.show();
        }
    }
    private void vuelve() {
        Intent intent = new Intent(this, Partner.class);
        setResult(RESULT_OK, intent);
        finish();
    }
    private void borra(){
        UsuariosSQLiteHelper bd =new UsuariosSQLiteHelper(this, "BaseDatosIkeya", null, 1);
        SQLiteDatabase db = bd.getWritableDatabase();
        String sql = String.format("DELETE FROM Partners WHERE empresa = '%s'",empresa.getText());

        try{
            db.execSQL(sql);
            Toast aviso = Toast.makeText(getApplicationContext(), "DELETE EXITOSO!", Toast.LENGTH_SHORT);
            aviso.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
            aviso.show();
        }catch(Exception e){
            e.getMessage();
            Toast aviso = Toast.makeText(getApplicationContext(), "DELETE ERRONEO!", Toast.LENGTH_SHORT);
            aviso.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
            aviso.show();
        }
    }
}
