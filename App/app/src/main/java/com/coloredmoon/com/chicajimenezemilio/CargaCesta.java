package com.coloredmoon.com.chicajimenezemilio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import adapter.AdapterCesta;
import adapter.AdapterProductos;
import basedatos.ConsultaBBDD;
import dao.CestaDAO;
import dao.InventarioDAO;
import dao.ReservaDAO;
import hebras.HConsultaReservas;
import modelo.ItemCesta;
import modelo.ItemInventario;
import modelo.LineaReserva;
import modelo.Reserva;
import modelo.Usuario;
import sesion.Sesion;
import utilidades.MiToolBar;
import com.coloredmoon.com.chicajimenezemilio.R;

public class CargaCesta extends AppCompatActivity {
    Context context = this;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_cesta);
        MiToolBar toolBar = new MiToolBar(this,this);
        toolBar.inicializarToolbar(R.menu.main,"CARRITO");

        final ArrayList<ItemCesta> productosEnlaCesta = CestaDAO.getInstance().getCesta();
        if(productosEnlaCesta.size()>0) {
            AdapterCesta listAdapter = new AdapterCesta(this, productosEnlaCesta);
            final ListView listview = (ListView) findViewById(R.id.ListView);
            // setting list adapter
            listview.setAdapter(listAdapter);
            TextView total = (TextView) findViewById(R.id.precioTotal);
            float totalCesta=0;
            totalCesta=CestaDAO.getInstance().calculaPrecioTotal();

            total.setText("Cantidad Total a pagar: "+totalCesta+" €");
            Button reservar =(Button)findViewById(R.id.reservarCesta);
            reservar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Usuario usuario = Sesion.getInstance(context).getUserSesion();
                    if(usuario!=null && usuario.getId()!=-1) {

                        Reserva reserva = new Reserva();

                        Calendar c = Calendar.getInstance();
                        reserva.setFecha(new Timestamp(c.getTime().getTime()));
                        reserva.setUsuario(usuario);

                        for (int i = 0; i < productosEnlaCesta.size(); ++i) {
                            ItemCesta ic = productosEnlaCesta.get(i);
                            reserva.addLinea(new LineaReserva(i, ic.getProducto(), ic.getFarmacia(), ic.getCantidad()));
                        }
                        //Pongo esta comprobación fuera del método ReservaDAO.getInstance(context).addReserva(reserva) porque
                        //el método addReserva lo utilizo para hacer el bulk de la base de datos y no se tiene que comprobar
                        //si se puede realizar la reserva, por lo tanto como sólo se hace una vez esta comprobación cuando
                        //se va a reservar la cesta, esta comprobación la hago fuera.
                        try {
                            new HConsultaReservas(context,reserva).execute().get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        //Una vez insertada en la base de datos la reserva trae un ID del servidor REST, si ese ID es -1 significa
                        //que ningun producto se ha podido reservar porque no hay stock, la reserva se hace con besteffort, es decir,
                        //se va reservar lo que se pueda y la cantidad que se pueda dentro de la cesta
                        if(reserva.getId()!=-1) {
                            ReservaDAO.getInstance(context).addReserva(reserva);
                            CestaDAO.getInstance().getCesta().clear();
                            listview.deferNotifyDataSetChanged();
                            Toast.makeText(context,"Productos Reservados",Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else
                            Toast.makeText(context,"No hay stock de ningun producto de la cesta, no se ha efectuado la reserva",Toast.LENGTH_LONG).show();

                    }else
                    {
                        Intent intent = new Intent(context,CargaInicioSesion.class);
                        startActivity(intent);
                    }

                }
            });

        }else{
            Toast.makeText(this, "La cesta esta vacía, compre algun producto", Toast.LENGTH_LONG).show();
            this.finish();
        }

    }

}
