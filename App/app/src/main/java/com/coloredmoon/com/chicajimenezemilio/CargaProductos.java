package com.coloredmoon.com.chicajimenezemilio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import adapter.AdapterDepartamentos;
import adapter.AdapterProductos;
import dao.DepartamentoDAO;
import dao.InventarioDAO;
import modelo.Departamento;
import modelo.ItemInventario;
import utilidades.MiToolBar;
import com.coloredmoon.com.chicajimenezemilio.R;
public class CargaProductos extends AppCompatActivity {

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
        long idF=extras.getLong("idF");
        long idD=extras.getLong("idD");
        List<ItemInventario> productosInventariados;
        if(idF!=-1)
            productosInventariados = InventarioDAO.getInstance(this).getItemsPorFarmaciaYDpto(idF, idD);
        else
            productosInventariados = InventarioDAO.getInstance(this).getItemsPorDpt(idD);
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
