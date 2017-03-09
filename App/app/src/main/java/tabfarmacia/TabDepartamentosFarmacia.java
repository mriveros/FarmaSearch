package tabfarmacia;


import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.coloredmoon.com.chicajimenezemilio.R;

import java.util.List;

import adapter.AdapterDepartamentos;
import dao.DepartamentoDAO;
import modelo.Departamento;


/**
 * Created by Tri_2 on 02/09/2015.
 */
public class TabDepartamentosFarmacia extends Fragment {

    ListView listview;
    long idF;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_tab_departamentos_farmacia,container,false);

        // get the listview
        listview = (ListView) v.findViewById(R.id.ListView);
        List<Departamento> departamentos = DepartamentoDAO.getInstance(v.getContext()).getDepartamentosPorFarmacia(idF);
        if(departamentos.size()>0) {
            AdapterDepartamentos listAdapter = new AdapterDepartamentos(v.getContext(), idF, departamentos);
            // setting list adapter
            listview.setAdapter(listAdapter);
        }else{
            Toast.makeText(v.getContext(),"La farmacia no tiene departamentso aun",Toast.LENGTH_LONG);
        }
        return v;
    }

    public void setIdF(long idF){
        this.idF = idF;
    }
}