package adapter;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coloredmoon.com.chicajimenezemilio.CargaProducto;
import com.coloredmoon.com.chicajimenezemilio.R;

import java.util.List;

import dao.CestaDAO;
import modelo.Farmacia;
import modelo.ItemCesta;
import modelo.ItemInventario;
import modelo.LineaReserva;
import modelo.Producto;
import modelo.Reserva;


/**
 * Created by NeN on 29/09/2015.
 */
public class AdapterReservas extends BaseAdapter {

    private Context context;
    private List<LineaReserva> reservas;


    public AdapterReservas(Context context, List<LineaReserva> reservas) {
        this.context = context;
        this.reservas = reservas;
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
        return reservas.size();
    }

    @Override
    public Object getItem(int position) {
        return reservas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return reservas.get(position).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LineaReserva lineaReserva= reservas.get(position);
        Producto p = lineaReserva.getProducto();
        Farmacia f = lineaReserva.getFarmacia();
        int cantidad = lineaReserva.getCantidad();

        LayoutInflater inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_reservas_listview, null);

        TextView nombre = (TextView)rowView.findViewById(R.id.nombreProducto);
        nombre.setText(p.getNombre());
        TextView farmaProd = (TextView)rowView.findViewById(R.id.farmaciaProducto);
        farmaProd.setText(f.getNombre());
        TextView cantidadProd = (TextView)rowView.findViewById(R.id.cantidadProducto);
        cantidadProd.setText(String.valueOf(cantidad));



        return rowView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return reservas.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}