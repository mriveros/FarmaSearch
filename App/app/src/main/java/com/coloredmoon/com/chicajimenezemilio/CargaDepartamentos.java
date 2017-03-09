package com.coloredmoon.com.chicajimenezemilio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
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
public class CargaDepartamentos extends AppCompatActivity {
    ListView listview;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_departamentos);
        MiToolBar toolBar = new MiToolBar(this,this);
        toolBar.inicializarToolbar(R.menu.main,"DEPARTAMENTOS");
        listview = (ListView) findViewById(R.id.ListView);
        List<Departamento> departamentos = DepartamentoDAO.getInstance(this).getDepartamentos();
        if(departamentos.size()>0) {
            AdapterDepartamentos listAdapter = new AdapterDepartamentos(this, -1, departamentos);
            // setting list adapter
            listview.setAdapter(listAdapter);

        }else{
            Toast.makeText(this,"No hay departamentos aun",Toast.LENGTH_LONG);
            this.finish();
        }

    }

}
