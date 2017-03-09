package com.coloredmoon.com.chicajimenezemilio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.AdapterProductos;
import adapter.AdapterReservas;
import dao.InventarioDAO;
import dao.ReservaDAO;
import modelo.ItemInventario;
import modelo.LineaReserva;
import modelo.Reserva;
import sesion.Sesion;
import utilidades.MiToolBar;
import com.coloredmoon.com.chicajimenezemilio.R;

public class CargaReservas extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_reservas);
        MiToolBar toolBar = new MiToolBar(this,this);
        toolBar.inicializarToolbar(R.menu.main,"RESERVAS");
        if(Sesion.getInstance(this).getUserSesion()!=null) {
            long id = Sesion.getInstance(this).getUserSesion().getId();
            List<Reserva> reservas = ReservaDAO.getInstance(this).getReservasUsuario(id);

            List<LineaReserva> todasReservas = new ArrayList<>();

            if (reservas.size() > 0) {
                for(int i=0;i<reservas.size();++i)
                    for (int j=0;j<reservas.get(i).getLineaReservas().size();++j)
                    todasReservas.add(reservas.get(i).getLineaReservas().get(j));
                AdapterReservas listAdapter = new AdapterReservas(this, todasReservas);
                ListView listview = (ListView) findViewById(R.id.ListView);
                // setting list adapter
                listview.setAdapter(listAdapter);

            } else {
                Toast.makeText(this, "No ha realizado ninguna reserva aun", Toast.LENGTH_LONG).show();
            }
        }else
        {
            Toast.makeText(this, "No ha realizado ninguna reserva aun", Toast.LENGTH_LONG).show();
        }

    }

}
