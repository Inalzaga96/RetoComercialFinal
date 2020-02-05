package com.example.retocomercial;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import androidx.appcompat.app.AppCompatActivity;

public class Partner extends AppCompatActivity {
    private ListView lPartners;
    private ArrayList<String> aParners=new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partners);
        lPartners =findViewById(R.id.partners);
        UsuariosSQLiteHelper usdbh =new UsuariosSQLiteHelper(this, "BaseDatosIkeya", null, 1);

        SQLiteDatabase db = usdbh.getReadableDatabase();

        ArrayAdapter<String> adapter;
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,aParners);
        lPartners.setAdapter(adapter);

        Cursor c = db.rawQuery(" SELECT nombre,apellidos,empresa FROM partners", null);

        if (c.moveToFirst()) {
            do {
                String codigo= c.getString(0);
                String nombre = c.getString(1);
                String empresa = c.getString(2);
                aParners.add(codigo+" "+nombre + " ("+empresa+") " );
            } while(c.moveToNext());
        }

        //  BOTONES
        Button btnVisualiza = (Button) findViewById(R.id.btnVisualiza);
        Button home = (Button) findViewById(R.id.btnHome2);
        Button nuevo = (Button) findViewById(R.id.btnNuevo);
        Button enviar = (Button) findViewById(R.id.btnEnviar);
        home.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                vuelve(null, 1234);
            }
        });
        nuevo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                NuevoPart(null);
            }
        });
        btnVisualiza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visualizaPart(null);
            }
        });
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviaXML();
            }
        });
    }
    private void vuelve(View view,int num){
        Intent intent = new Intent(this, MainActivity.class);
        setResult(RESULT_OK,intent);
        finish();
    }
    public void NuevoPart(View view) {
        Intent intent = new Intent(this, NuevoPartner.class);
        startActivityForResult(intent,3456);
    }
    public void crearPartner(String[] datos) {
        UsuariosSQLiteHelper bd = new UsuariosSQLiteHelper(getApplication(), "BaseDatosIkeya", null, 1);
        SQLiteDatabase db = bd.getWritableDatabase();
        Cursor cursor = db.rawQuery("select Max(idPartner) from Partners" , null);
        cursor.moveToFirst();
        int count= cursor.getInt(0);
        String sql = String.format("INSERT INTO Partners(idPartner,id_Comercial,nombre, apellidos, empresa, direccion, telefono, poblacion, email) "
                + "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s','%s');",(count+1),datos[0], datos[1], datos[2], datos[3], datos[4], datos[5], datos[6],datos[7]);
        try {
            db.execSQL(sql);
            finish();
            startActivity(getIntent());
        } catch (Exception e) {
            Toast aviso = Toast.makeText(getApplicationContext(), "INSERT ERRONEO!", Toast.LENGTH_SHORT);
            aviso.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
            aviso.show();
            e.getMessage();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent _data) {
            String result = _data.getStringExtra("partner");
            String[] datos = result.split("\\|");
            crearPartner(datos);
    }
    public void visualizaPart(View view) {
        Intent intent = new Intent(this, VisualizaPartner.class);
        startActivityForResult(intent,3456);

    }
    public void escribeXML(){
        UsuariosSQLiteHelper usdbh =new UsuariosSQLiteHelper(this, "BaseDatosIkeya", null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();

        Cursor c = db.rawQuery(" SELECT idPartner,id_comercial,nombre,apellidos,empresa,direccion,telefono,poblacion,email FROM partners", null);

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbs = dbf.newDocumentBuilder();
            Document doc = dbs.newDocument();
            //<PARTNERS>
            Element eParts = doc.createElement("Partners");
            doc.appendChild(eParts);

            if (c.moveToFirst()) {
                do {
                    Integer idPart = c.getInt(c.getColumnIndex("idPartner"));
                    Integer idComer = c.getInt(c.getColumnIndex("id_comercial"));
                    String nom = c.getString(c.getColumnIndex("nombre"));
                    String apell = c.getString(c.getColumnIndex("apellidos"));
                    String empr = c.getString(c.getColumnIndex("empresa"));
                    String direc = c.getString(c.getColumnIndex("direccion"));
                    String telef = c.getString(c.getColumnIndex("telefono"));
                    String pobla = c.getString(c.getColumnIndex("poblacion"));
                    String mail = c.getString(c.getColumnIndex("email"));
                    //<PARTNER>
                    Element ePart = doc.createElement("Partner");
                    eParts.appendChild(ePart);
                        //IDPARTNER
                    Element eIDPart = doc.createElement("IDPartner");
                    eIDPart.appendChild(doc.createTextNode(String.valueOf(idPart)));
                    ePart.appendChild(eIDPart);
                       //IDCOMER
                    Element eIDComer = doc.createElement("IDComer");
                    eIDComer.appendChild(doc.createTextNode(String.valueOf(idComer)));
                    ePart.appendChild(eIDComer);
                        //NOMBRE
                    Element eNombre = doc.createElement("Nombre");
                    eNombre.appendChild(doc.createTextNode(nom));
                    ePart.appendChild(eNombre);
                        //APELLIDO1
                    Element eApell1 = doc.createElement("apellido");
                    eApell1.appendChild(doc.createTextNode(apell));
                    ePart.appendChild(eApell1);
                        //EMPRESA
                    Element eEmpresa = doc.createElement("Empresa");
                    eEmpresa.appendChild(doc.createTextNode(empr));
                    ePart.appendChild(eEmpresa);
                        //DIRECCION
                    Element eDirec1 = doc.createElement("Direccion");
                    eDirec1.appendChild(doc.createTextNode(direc));
                    ePart.appendChild(eDirec1);
                        //TELEFONO
                    Element eTelef = doc.createElement("telefono");
                    eTelef.appendChild(doc.createTextNode(telef));
                    ePart.appendChild(eTelef);
                        //POBLACION
                    Element ePobla = doc.createElement("Poblacion");
                    ePobla.appendChild(doc.createTextNode(pobla));
                    ePart.appendChild(ePobla);
                } while (c.moveToNext());
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer t = transformerFactory.newTransformer();
                t.setOutputProperty(OutputKeys.INDENT, "yes");
                t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("/data/data/"+BuildConfig.APPLICATION_ID+"/Partners.xml"));
                t.transform(source, result);
            }
        } catch(Exception e) {
            e.getMessage();
            Toast aviso = Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT);
            aviso.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
            aviso.show();
        }
    }

    private void enviaXML(){
            escribeXML();

        File file = new File("/data/data/"+BuildConfig.APPLICATION_ID+"/Partners.xml");
        Uri path = Uri.fromFile(file);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // set the type to 'email'
        emailIntent.setType("vnd.android.cursor.dir/email");
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        String to[] = {"ikeya@ikeya.com"};
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        // the attachment
        emailIntent.putExtra(Intent.EXTRA_STREAM, path);
        // the mail subject
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Importar desde Android");
        startActivity(Intent.createChooser(emailIntent , "Enviar Partners..."));
    }
}
