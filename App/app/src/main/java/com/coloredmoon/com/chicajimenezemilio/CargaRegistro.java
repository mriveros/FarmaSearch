package com.coloredmoon.com.chicajimenezemilio;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import modelo.Usuario;
import sesion.Sesion;
import utilidades.Cifrador;
import utilidades.MiToolBar;
import com.coloredmoon.com.chicajimenezemilio.R;
public class CargaRegistro extends AppCompatActivity {
    Context context = this;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_registro);
        MiToolBar toolBar = new MiToolBar(this,this);
        toolBar.inicializarToolbar(R.menu.main, "INICIAR SESIÓN");
        final EditText nombre = (EditText)findViewById(R.id.nombreEdit);
        final EditText apellidos = (EditText)findViewById(R.id.apellidosEdit);
        final EditText email = (EditText)findViewById(R.id.emailEdit);
        final EditText password = (EditText)findViewById(R.id.passwordEdit);
        Button registrarse = (Button)findViewById(R.id.registrarUsuario);
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = password.getText().toString();
                pass = Cifrador.cifrar(pass, Cifrador.SHA1);
                Usuario u = new Usuario();
                u.setNombre(nombre.getText().toString());
                u.setApellidos(apellidos.getText().toString());
                u.setEmail(email.getText().toString());
                u.setPassword(pass);
                if(!Sesion.getInstance(context).registrarUsuario(u)){
                    Toast.makeText(context, "No he podido registrarte o ya esta registrado", Toast.LENGTH_LONG).show();
                }else
                {
                    //Toast.makeText(context,"Registro con exito",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });


    }
}
