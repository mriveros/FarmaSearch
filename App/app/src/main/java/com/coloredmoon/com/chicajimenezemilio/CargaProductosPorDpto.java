package com.coloredmoon.com.chicajimenezemilio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import adapter.AdapterProductos;
import dao.InventarioDAO;
import modelo.ItemInventario;
import utilidades.MiToolBar;
import com.coloredmoon.com.chicajimenezemilio.R;
public class CargaProductosPorDpto extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_productos);
        MiToolBar toolBar = new MiToolBar(this,this);
        toolBar.inicializarToolbar(R.menu.main,"PRODUCTOS");
        Bundle extras = this.getIntent().getExtras();
        long idD=extras.getLong("idD");
        List<ItemInventario> productosInventariados = InventarioDAO.getInstance(this).getItemsPorDpt(idD);
        if(productosInventariados.size()>0) {
            AdapterProductos listAdapter = new AdapterProductos(this, productosInventariados);
            ListView listview = (ListView) findViewById(R.id.ListView);
            // setting list adapter
            listview.setAdapter(listAdapter);

        }else{
            Toast.makeText(this,"El departamento no tiene productos aun",Toast.LENGTH_LONG);
            this.finish();
        }

    }

}
