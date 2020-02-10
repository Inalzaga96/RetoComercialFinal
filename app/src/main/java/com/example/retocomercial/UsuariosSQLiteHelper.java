package com.example.retocomercial;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UsuariosSQLiteHelper extends SQLiteOpenHelper {


    private String sql = "CREATE TABLE Partners(" +
            "idPartner Integer PRIMARY KEY AUTOINCREMENT," +
            "id_comercial Integer," +
            "nombre varchar(40)," +
            "apellidos varchar(60)," +
            "empresa varchar(50)," +
            "direccion varchar(80)," +
            "telefono varchar(15)," +
            "poblacion varchar(40)," +
            "email varchar(50)," +
            "FOREIGN KEY(id_comercial) REFERENCES usuarios(id_comercial));";


    private String lineas = "create table lineas(" +
            "cod Integer PRIMARY KEY AUTOINCREMENT," +
            "cod_producto Integer," +
            "cod_pedido Integer," +
            "unidad Integer," +
            "importe Integer," +
            "FOREIGN KEY(cod_pedido) REFERENCES pedidos(cod),"+
            "FOREIGN KEY(cod_producto) REFERENCES articulos(id_articulo))";

    private String pedidos="create table pedidos("+
            "cod Integer PRIMARY KEY AUTOINCREMENT,"+
            "id_partner Integer,"+
            "fecha varchar(100),"+
            "FOREIGN KEY(id_partner) REFERENCES Partners(idPartner))";

    private String login="create table usuarios("+
            "id_comercial Integer PRIMARY KEY AUTOINCREMENT,"+
            "usuario varchar(100) UNIQUE,"+
            "pwd varchar(100))";

    private String eventos="create table eventos("+
            "id_eventos Integer PRIMARY KEY AUTOINCREMENT,"+
            "id_comercial Integer,"+
            "nombre varchar(40),"+
            "descripcion varchar(100)," +
            "hora varchar(6)," +
            "fecha varchar(30),"+
            "FOREIGN KEY(id_comercial) REFERENCES usuarios(id_comercial))";

    private String articulos="create table articulos("+
            "id_articulo Integer PRIMARY KEY AUTOINCREMENT,"+
            "descripcion varchar(30),"+
            "precio Integer ," +
            "imagen varchar(50))";

    //private String crearUsuarios = "INSERT INTO usuarios(id_comercial,usuario,pwd) VALUES(9223372036854775807,'mikel', '1234');";
    private String crearUsuarios2= "INSERT INTO usuarios(usuario,pwd) VALUES('Kerman', '1234'), ('Iñaki','1234');";
    private String crearPartners= "INSERT INTO partners(id_comercial,nombre,apellidos,empresa,direccion,telefono,poblacion,email) VALUES(1,'Kerman','sorarrain','Azules','calle','943268456','Tolosa','kerman@gmail.com'),(1,'Iñaki','Alzaga','Pasaban','Txomin San Sebastian','660889733','Astigarraga','inalzaga@gmail.com');";
    private String crearEventos="INSERT INTO EVENTOS(id_comercial,NOMBRE,DESCRIPCION,HORA,FECHA) VALUES(1,'Ir a casa','mañana','16:20','02/08/19')";
    private String crearEventos2="INSERT INTO EVENTOS(id_comercial,NOMBRE,DESCRIPCION,HORA,FECHA) VALUES(2,'Salir de casa','hoy','15:09','06/08/19')";
    private String crearPedidos="INSERT INTO PEDIDOS(ID_PARTNER,FECHA) VALUES(1,'02/06/2019'),(1,'28/02/2019'),(1,'30/04/2019'),(1,'15/07/2019'),(1,'12/11/2019')";
    private String crearLineas="INSERT INTO lineas VALUES(1,2,1,20,500),(2,4,1,20,500),(3,1,2,20,500),(4,5,2,20,500),(5,1,3,20,500),(6,5,3,20,500),(7,1,4,20,500),(8,5,4,20,500),(9,1,5,30,700),(10,5,5,45,1000)";
    private String crearLineas2="INSERT INTO lineas VALUES(11,5,6,10,7500),(12,2,7,1,350),(13,3,8,10,1500),(14,4,9,21,2300)";
    private String crearPedidos2="INSERT INTO PEDIDOS(ID_PARTNER,FECHA) VALUES(2,'03/08/2019'),(2,'12/03/2019'),(2,'01/10/2019'),(2,'12/12/2019')";
    private String creaArticulo="INSERT INTO articulos(id_articulo,descripcion,precio,imagen) VALUES(1,'Mancuernas 5kg',17,'mancuernas5')";
    private String creaArticulo2="INSERT INTO articulos(id_articulo,descripcion,precio,imagen) VALUES(2,'Mancuernas 10kg',25,'mancuernas10')";
    private String creaArticulo3="INSERT INTO articulos(id_articulo,descripcion,precio,imagen) VALUES(3,'Norditrack C300',150,'norditrack')";
    private String creaArticulo4="INSERT INTO articulos(id_articulo,descripcion,precio,imagen) VALUES(4,'Fitfiu Besp-22 Bicicleta Indoor',300,'spinning')";
    private String creaArticulo5="INSERT INTO articulos(id_articulo,descripcion,precio,imagen) VALUES(5,'Maquina Gimnasio Multitension',250,'multitension')";



    //private String crearUsuarios2= "INSERT INTO usuarios(usuario,pwd) VALUES('mikel01', '1234')";
    public UsuariosSQLiteHelper(Context contexto, String nombre,
                                SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
        db.execSQL(lineas);
        db.execSQL(pedidos);
        db.execSQL(login);
        db.execSQL(eventos);
        db.execSQL(articulos);
        db.execSQL(crearUsuarios2);
        db.execSQL(crearPartners);
        db.execSQL(crearEventos);
        db.execSQL(crearEventos2);
        db.execSQL(crearPedidos);
        db.execSQL(crearLineas);
        db.execSQL(crearLineas2);
        db.execSQL(crearPedidos2);
        db.execSQL(creaArticulo);
        db.execSQL(creaArticulo2);
        db.execSQL(creaArticulo3);
        db.execSQL(creaArticulo4);
        db.execSQL(creaArticulo5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS COMERCIALES");

        //Se crea la nueva versión de la tabla
        //db.execSQL(sqlCreate);
    }
}