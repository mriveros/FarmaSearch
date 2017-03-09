package adapter;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;


import com.coloredmoon.com.chicajimenezemilio.CargaProductos;
import com.coloredmoon.com.chicajimenezemilio.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import basedatos.ConsultaBBDD;
import dao.DepartamentoDAO;
import modelo.Departamento;


/**
 * Created by NeN on 29/09/2015.
 */
public class AdapterDepartamentos extends BaseAdapter {


    private Context context;

    private List<Departamento> departamentos;
    long idF;

    public AdapterDepartamentos(Context context, long idF, List<Departamento> departamentos) {
        this.context = context;
        this.idF = idF;
        this.departamentos = departamentos;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return departamentos.size();
    }

    @Override
    public Object getItem(int position) {
        return departamentos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return departamentos.get(position).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final  Departamento d = departamentos.get(position);
        LayoutInflater inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.item_departamentos_listview, null);
        TextView nombre = (TextView)rowView.findViewById(R.id.nombreDpto);
        ImageView img = (ImageView)rowView.findViewById(R.id.imageDepartamento);
        nombre.setText(d.getNombre());
        Picasso.with(context).load(ConsultaBBDD.server+ConsultaBBDD.imgDepartamentos+d.getImagen()).into(img);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cargaproductos = new Intent(context, CargaProductos.class);
                cargaproductos.putExtra("idF", idF);
                cargaproductos.putExtra("idD", d.getId());
                context.startActivity(cargaproductos);
            }
        });
        return rowView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return departamentos.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}