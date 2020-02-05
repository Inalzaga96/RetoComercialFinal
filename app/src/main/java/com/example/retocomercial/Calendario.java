package com.example.retocomercial;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Calendario extends AppCompatActivity {

    private CalendarView calen;
    private ListView listaEventos;
    private ArrayList<String> listaArray;
    private ArrayAdapter<String> adaptadorArray;
    private String vuelta, vuelta2, vuelta3, vuelta4;
    private String str;
    String nombre_usuario = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendario);


        calen = findViewById(R.id.calendario);
        listaArray = new ArrayList<String>();
        adaptadorArray = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaArray);
        listaEventos = findViewById(R.id.listEventos);
        listaEventos.setAdapter(adaptadorArray);

        Bundle bun = getIntent().getExtras();
        nombre_usuario = bun.getString("nombreUsuario");

        calen.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Intent intencion = new Intent(Calendario.this, Evento.class);

                Bundle bundle = new Bundle();
                int ano = year, mes = month, dia = dayOfMonth;
                bundle.putInt("dia", dia);
                bundle.putInt("mes", mes);
                bundle.putInt("ano", ano);
                bundle.putString("nombreUsuario", nombre_usuario);
                intencion.putExtras(bundle);
                startActivityForResult(intencion, 1234);
            }
        });
//////////////////////////
        UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(this, "BaseDatosIkeya", null, 1);

        SQLiteDatabase db = usdbh.getReadableDatabase();

        Cursor c0 = db.rawQuery("SELECT id_comercial from usuarios where usuario='" + nombre_usuario + "'", null);
        Integer id_comercial = 0;
        if (c0.moveToFirst()) {
            do {
                id_comercial = c0.getInt(0);

            } while (c0.moveToNext());
        }

        Cursor c = db.rawQuery("SELECT nombre,descripcion,hora,fecha FROM eventos where id_comercial='" + id_comercial + "'", null);

        if (c.moveToFirst()) {
            do {
                String nombre = c.getString(0);
                String descripcion = c.getString(1);
                String hora = c.getString(2);
                String fecha = c.getString(3);
                listaArray.add(nombre + ", " + descripcion + ", Día: " + fecha + ", Hora:" + hora);
            } while (c.moveToNext());
        }

        SQLiteDatabase db1 = usdbh.getWritableDatabase();


        listaEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String evento="delete from eventos where id_eventos='"+id+"'";
                Toast aviso = Toast.makeText(getApplicationContext(), "Hola "+position+" "+id, Toast.LENGTH_SHORT);
                aviso.show();
                AlertDialog.Builder alerta = new AlertDialog.Builder(Calendario.this);
                alerta.setMessage("¿Desea eliminar el evento?")
                        .setCancelable(false)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast aviso = Toast.makeText(getApplicationContext(), "Hola ", Toast.LENGTH_SHORT);

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Salida");
                titulo.show();
            }

        });


        /*try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document documento = db.newDocument();

            //<eventos>
            Element elementoPrincipal = documento.createElement("eventos");
            documento.appendChild(elementoPrincipal);
            //<evento>
            Element elementos = documento.createElement("evento");
            elementoPrincipal.appendChild(elementos);
            //<evento2>
            Element elementos2 = documento.createElement("evento");
            elementoPrincipal.appendChild(elementos2);

            //nombre
            Element nombre=documento.createElement("nombre");
            elementos.appendChild(nombre);
            nombre.setTextContent("Evento prueba "+vuelta);

            //descripcion
            Element descrip=documento.createElement("descripcion");
            elementos.appendChild(descrip);
            descrip.setTextContent("ir a casa "+vuelta2);
            //hora(atributo)
            Attr atributo_hora = documento.createAttribute("hora");
            elementos.appendChild(atributo_hora);

            //fecha(atributo)
            Attr atributo_fecha = documento.createAttribute("fecha");
            elementos.appendChild(atributo_fecha);


            TransformerFactory fabricaTransformer = TransformerFactory.newInstance();
            Transformer transformer = fabricaTransformer.newTransformer();
            DOMSource recurso = new DOMSource(documento);
            StreamResult resultado = new StreamResult(new File("/data/data/" + BuildConfig.APPLICATION_ID + "/pruebaEventos.xml"));

            transformer.transform(recurso, resultado);


        } catch (Exception e) {

        }*/


        /*try {
            File archivo = new File("/data/data/" + BuildConfig.APPLICATION_ID + "/pruebaEventos.xml");
            //File archivo = new File("C:/Users/adminportatil/Desktop");

            DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
            DocumentBuilder constructor = fabrica.newDocumentBuilder();
            Document documento = constructor.parse(archivo);


            NodeList lista = documento.getElementsByTagName("eventos");

            for (int i = 0; i < lista.getLength(); i++) {
                Node nodo = lista.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodo;
                    str = "Evento: " + element.getElementsByTagName("evento").item(0).getTextContent()+" ";

                }
                listaArray.add(str);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        Button home = (Button) findViewById(R.id.btnHome3);
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vuelve(null, 1234);
            }
        });
    }

    private void vuelve(View view, int num) {
        Intent intent = new Intent(this, MainActivity.class);
        setResult(RESULT_OK, intent);
        finish();
    }

    /*protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234 && resultCode == RESULT_OK) {
            //String vuelta= data.getExtras().getString("nuevo");
            vuelta = data.getExtras().getString("nom");
            vuelta2 = data.getExtras().getString("desc");
            vuelta3 = data.getExtras().getString("hora");
            vuelta4 = data.getExtras().getString("fecha");
            listaArray.add(vuelta + "  " + vuelta3 + "  " + vuelta4 + "  " + vuelta2);
            adaptadorArray.notifyDataSetChanged();


        }
    }*/
}
