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
import modelo.ItemCesta;
import modelo.ItemInventario;
import modelo.Producto;


/**
 * Created by NeN on 29/09/2015.
 */
public class AdapterBusqueda extends BaseAdapter {

    private Context context;
    ItemCesta itemCesta = new ItemCesta();
    private List<ItemInventario> inventario;

    public AdapterBusqueda(Context context,  List<ItemInventario> inventario) {
        this.context = context;
        this.inventario = inventario;
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
        return inventario.size();
    }

    @Override
    public Object getItem(int position) {
        return inventario.get(position);
    }

    @Override
    public long getItemId(int position) {
        return inventario.get(position).getProducto().getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ItemInventario itemInventario= inventario.get(position);
        final Producto p = itemInventario.getProducto();
        final int stock = itemInventario.getStock();
        final float precio = itemInventario.getPrecio();

        itemCesta.setPrecio(precio);
        itemCesta.setProducto(p);
        itemCesta.setFarmacia(itemInventario.getFarmacia());

        LayoutInflater inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.item_productos_busqueda_listview, null);

        TextView nombre = (TextView)rowView.findViewById(R.id.nombreProducto);
        nombre.setText(p.getNombre());
        TextView stockProd = (TextView)rowView.findViewById(R.id.stockProducto);
        TextView precioProd = (TextView)rowView.findViewById(R.id.precioProducto);
        if (!CestaDAO.getInstance().estaEnCesta(itemCesta)) {
            CestaDAO.getInstance().addItem(itemCesta);
            stockProd.setText(String.valueOf(stock));
        }else {
            itemCesta = CestaDAO.getInstance().getItemAntiguoCesta(itemCesta);
            stockProd.setText(String.valueOf(stock -itemCesta.getCantidad()));
        }

        precioProd.setText(String.valueOf(precio+" €"));
        ImageView carrito = (ImageView) rowView.findViewById(R.id.addCarrito);

        carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CestaDAO.getInstance().estaEnCesta(itemCesta))
                    CestaDAO.getInstance().addItem(itemCesta);
                else
                    itemCesta = CestaDAO.getInstance().getItemAntiguoCesta(itemCesta);
                if(stock>itemCesta.getCantidad()){
                    itemCesta.addCantidad();
                    TextView stockProd = (TextView) rowView.findViewById(R.id.stockProducto);
                    stockProd.setText(String.valueOf(stock-itemCesta.getCantidad()));
                    Toast.makeText(context, "Añadido al carrito", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(context, "No hay suficientes productos", Toast.LENGTH_LONG).show();
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
        return inventario.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}