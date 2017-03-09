package com.coloredmoon.com.chicajimenezemilio;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import sesion.Sesion;
import utilidades.Cifrador;
import utilidades.MiToolBar;
import com.coloredmoon.com.chicajimenezemilio.R;
public class CargaInicioSesion extends AppCompatActivity {
    Context context = this;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_inicio_sesion);
        MiToolBar toolBar = new MiToolBar(this,this);
        toolBar.inicializarToolbar(R.menu.main, "INICIAR SESIÓN");
        final EditText email = (EditText)findViewById(R.id.emailEdit);
        final EditText password = (EditText)findViewById(R.id.passwordEdit);
        Button iniciarSesion = (Button)findViewById(R.id.iniciarSesion);
        Button registrarse = (Button)findViewById(R.id.registrarUsuario);
        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = password.getText().toString();
                Cifrador.cifrar(pass, Cifrador.SHA1);
                if(!Sesion.getInstance(context).iniciarSesion(email.getText().toString(),pass)){
                    Toast.makeText(context,"No he podido iniciar sesion",Toast.LENGTH_LONG).show();
                }else
                {
                    Toast.makeText(context,"Has iniciado sesion",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cargarRegistro = new Intent(context,CargaRegistro.class);
                startActivity(cargarRegistro);
            }
        });


    }
}
