package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coloredmoon.com.chicajimenezemilio.CargaProducto;
import com.coloredmoon.com.chicajimenezemilio.R;

import java.util.ArrayList;
import java.util.List;

import dao.CestaDAO;
import modelo.ItemCesta;
import modelo.ItemInventario;
import modelo.Producto;


/**
 * Created by NeN on 29/09/2015.
 */
public class AdapterCesta extends BaseAdapter {

    private Context context;
    private ArrayList<ItemCesta> cesta;

    public AdapterCesta(Context context,  ArrayList<ItemCesta> cesta) {
        this.context = context;
        this.cesta = cesta;
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
        return cesta.size();
    }

    @Override
    public Object getItem(int position) {
        return cesta.get(position);
    }

    @Override
    public long getItemId(int position) {
        return cesta.get(position).getProducto().getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ItemCesta itemCesta= cesta.get(position);
        final Producto p = itemCesta.getProducto();
        final int cantidad = itemCesta.getCantidad();
        final float precio = itemCesta.getPrecio();


        LayoutInflater inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.item_cesta_listview, null);

        TextView nombre = (TextView)rowView.findViewById(R.id.nombreProducto);
        nombre.setText(p.getNombre());
        final TextView stockProd = (TextView)rowView.findViewById(R.id.cantidadProducto);
        TextView precioProd = (TextView)rowView.findViewById(R.id.precioProducto);
        stockProd.setText(String.valueOf(cantidad));
        precioProd.setText(String.valueOf(precio+" €"));
        ImageView carrito = (ImageView) rowView.findViewById(R.id.delCarrito);


        carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCesta.restarUnoACantidad();
                if(itemCesta.getCantidad()==0) {
                    cesta.remove(itemCesta);
                    notifyDataSetChanged();
                }
                stockProd.setText(String.valueOf(itemCesta.getCantidad()));
                TextView total = (TextView) ((Activity) context).findViewById(R.id.precioTotal);
                float totalCesta = 0;
                for (int i = 0; i < cesta.size(); ++i) {
                    ItemCesta ic = cesta.get(i);
                    totalCesta += (ic.getCantidad() * ic.getPrecio());
                }
                total.setText("Cantidad Total a pagar: " + totalCesta + " €");

            }
        });
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cargaproducto = new Intent(context, CargaProducto.class);
                cargaproducto.putExtra("idP", p.getId());
                context.startActivity(cargaproducto);
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
        return cesta.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}