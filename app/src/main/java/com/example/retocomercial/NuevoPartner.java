package com.example.retocomercial;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import org.w3c.dom.Document;
import org.w3c.dom.Element;


import androidx.appcompat.app.AppCompatActivity;

public class NuevoPartner extends AppCompatActivity {
    EditText empr,apell,nom,direc1,pobla,telef,email,comer;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevopartner);
        Button home = (Button) findViewById(R.id.btnHome5);
        Button nuevo = (Button) findViewById(R.id.btnNuevo2);
         empr = (EditText) findViewById(R.id.ETEmpresa);
         apell = (EditText) findViewById(R.id.ETApellidos);
         nom = (EditText) findViewById(R.id.ETNombre);
         direc1 = (EditText) findViewById(R.id.ETDireccion);
         pobla = (EditText) findViewById(R.id.ETPoblacion);
         telef = (EditText) findViewById(R.id.ETTelefono);
         email = (EditText) findViewById(R.id.ETTotal_Factura);
         comer = (EditText) findViewById(R.id.ETComercial);

        nuevo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                guardarDatos();

            }
        });

        home.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                vuelve(null, 1234);
            }
        });
    }
    private void vuelve(View view,int num){
        Intent intent = new Intent(this, Partner.class);
        setResult(RESULT_OK,intent);
        finish();
    }

    private void guardarDatos() {
        if (empr.getText().length()==0){
            empr.setError("La empresa está vacía.");
        } else if (apell.getText().length()==0) {
            apell.setError("Los apellidos están vacíos.");
        } else if (nom.getText().length()==0) {
            nom.setError("El nombre está vacío.");
        } else if (telef.getText().length()==0) {
            telef.setError("El telefono está vacío.");
        } else if (pobla.getText().length()==0) {
            pobla.setError("La población está vacía.");
        } else if (direc1.getText().length()==0) {
            direc1.setError("La dirección está vacía.");
        } else if (comer.getText().length()==0) {
            comer.setError("El comercial está vacio.");
        } else {
            String result = comer.getText()
                    + "|" + nom.getText()
                    + "|" + apell.getText()
                    + "|" + empr.getText()
                    + "|" + direc1.getText()
                    + "|" + telef.getText()
                    + "|" + pobla.getText()
                    + "|" + email.getText();

            Intent returnIntent = new Intent(this, Partner.class);
            returnIntent.putExtra("partner", result);
            setResult(Partner.RESULT_OK, returnIntent);
            finish();
        }
    }
}
