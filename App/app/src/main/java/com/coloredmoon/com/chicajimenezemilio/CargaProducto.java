package com.coloredmoon.com.chicajimenezemilio;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import basedatos.ConsultaBBDD;
import dao.CestaDAO;
import dao.InventarioDAO;
import modelo.Farmacia;
import modelo.ItemCesta;
import modelo.ItemInventario;
import modelo.Producto;
import utilidades.MiToolBar;
import com.coloredmoon.com.chicajimenezemilio.R;
public class CargaProducto extends AppCompatActivity {
    int stock=0;
    Context context=this;
    ItemCesta itemCesta = new ItemCesta();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_producto);
        MiToolBar toolBar = new MiToolBar(this,this);
        toolBar.inicializarToolbar(R.menu.main,"PRODUCTO");
        final TextView stockProd = (TextView)findViewById(R.id.stockProducto);
        TextView nombre = (TextView)findViewById(R.id.nombreProducto);
        TextView precioProd = (TextView)findViewById(R.id.precioProducto);
        TextView farmaciaProducto = (TextView)findViewById(R.id.farmaciaProducto);
        TextView descripcionProd = (TextView)findViewById(R.id.descripcionProducto);
        ImageView imagenProd = (ImageView)findViewById(R.id.imageProducto);

        Bundle extras = this.getIntent().getExtras();
        long idP=extras.getLong("idP");
        ItemInventario i = InventarioDAO.getInstance(this).getItemPorIdProductoInventario(idP);

        stock = i.getStock();
        Producto p = i.getProducto();
        Farmacia f = i.getFarmacia();
        itemCesta.setProducto(p);
        itemCesta.setFarmacia(f);
        itemCesta.setPrecio(i.getPrecio());
        if(!CestaDAO.getInstance().estaEnCesta(itemCesta))
             stockProd.setText("Stock :" +String.valueOf(stock));
        else {
            itemCesta= CestaDAO.getInstance().getItemAntiguoCesta(itemCesta);
            stockProd.setText("Stock :" +String.valueOf(stock - itemCesta.getCantidad()));
        }


        farmaciaProducto.setText("En farmacia: "+f.getNombre());

        nombre.setText(p.getNombre());
        descripcionProd.setText(p.getDescripcion());

        Picasso.with(context).load(ConsultaBBDD.server+ ConsultaBBDD.imgProductos+p.getImagen()).into(imagenProd);
        precioProd.setText("Precio: " + String.valueOf(i.getPrecio() + " €"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!CestaDAO.getInstance().estaEnCesta(itemCesta)) {
                    CestaDAO.getInstance().addItem(itemCesta);
                } else {
                    itemCesta= CestaDAO.getInstance().getItemAntiguoCesta(itemCesta);
                }
                if (stock > itemCesta.getCantidad()) {
                    itemCesta.addCantidad();
                    stockProd.setText("Stock :" + String.valueOf(stock - itemCesta.getCantidad()));
                    Toast.makeText(context, "Añadido al carrito", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "No hay suficientes productos", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
