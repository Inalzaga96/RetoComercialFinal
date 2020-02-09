package com.example.retocomercial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.File;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mapa;
    String nombre_usuario="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ImageView enviar =(ImageView) findViewById(R.id.enviar);
        ImageView llamar = (ImageView) findViewById(R.id.llamar);
        Button btnCalen  = (Button) findViewById(R.id.btnCalen);
        Button btnPed  = (Button) findViewById(R.id.btnPartner);
        Button btnPart  = (Button) findViewById(R.id.btnPedido);

        Bundle bun = getIntent().getExtras();
        nombre_usuario= bun.getString("nombreUsuario");


        llamar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String phone = "943555261";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarCorreo();
            }
        });
        btnCalen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calen(null);
            }
        });
        btnPed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                part(null);
            }
        });
        btnPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ped(null);
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        LatLng cebanc = new LatLng(43.3045627, -2.0171193);
        googleMap.addMarker(new MarkerOptions().position(cebanc).title("Cebanc"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cebanc,15.0f));
    }
    private void enviarCorreo() {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            // set the type to 'email'
            emailIntent.setType("vnd.android.cursor.dir/email");
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            String to[] = {"ikeya@ikeya.com"};
            emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
            // the mail subject
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Enviar desde Android");
            startActivity(Intent.createChooser(emailIntent , "Enviar email..."));
    }
    public void Calen(View view) {
        Intent intent = new Intent(this, Calendario.class);
        Bundle bundle = new Bundle();
        bundle.putString("nombreUsuario", nombre_usuario);
        intent.putExtras(bundle);
        startActivityForResult(intent,1234);
    }
    public void part(View view) {
        Intent intent = new Intent(this, Partner.class);
        startActivityForResult(intent,1234);
    }
    public void ped(View view) {
        Intent intent = new Intent(this, Pedidos.class);
        startActivityForResult(intent,1234);
    }
}
