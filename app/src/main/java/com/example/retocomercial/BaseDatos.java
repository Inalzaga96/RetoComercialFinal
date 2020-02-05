package com.example.retocomercial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BaseDatos extends AppCompatActivity {
    private EditText nombre, contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_datos);

        nombre = findViewById(R.id.editNombre);
        contrasena = findViewById(R.id.editContraseña);
        Button bLogin = (Button) findViewById(R.id.bLogin);
        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        //UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 1);

        final UsuariosSQLiteHelper bdIkeya = new UsuariosSQLiteHelper(this, "BaseDatosIkeya", null, 1);
        final SQLiteDatabase db = bdIkeya.getWritableDatabase();
         bLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Cursor cursor = db.rawQuery("select count(*) from usuarios where usuario='" + nombre.getText().toString() + "' and pwd='" + contrasena.getText().toString() + "'", null);
                cursor.moveToFirst();
                int count= cursor.getInt(0);

                if(count == 0){
                    finish();
                    startActivity(getIntent());
                    Toast aviso = Toast.makeText(getApplicationContext(), "ESTE USUARIO NO EXISTE", Toast.LENGTH_SHORT);
                    aviso.setGravity(Gravity.BOTTOM, 0, 0);
                    aviso.show();
                }else{
                    finish();
                    Toast aviso = Toast.makeText(getApplicationContext(), "BIENVENIDO "+nombre.getText().toString().toUpperCase(), Toast.LENGTH_SHORT);
                    aviso.setGravity(Gravity.BOTTOM, 0, 0);
                    aviso.show();
                    vuelve();
                }
                    db.close();
            }
        });
    }
    private void vuelve(){
        Intent intent = new Intent(this, MainActivity.class);
        String nombreUsu=nombre.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("nombreUsuario", nombreUsu);
        intent.putExtras(bundle);
        startActivityForResult(intent,1234);
    }
}
//Cursor cursor = db.rawQuery("select * from comerciales where nombre='" + nombre.getText().toString() + "' and contrasena='" + contrasena.getText().toString()+"'", null);