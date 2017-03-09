package hebras;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import com.coloredmoon.com.chicajimenezemilio.CargaProducto;
import dao.InventarioDAO;
import modelo.ItemInventario;
import utilidades.ComparadorSortBusqueda;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import adapter.AdapterBusqueda;

/**
 * Created by NeN on 29/10/2015.
 */
public class HBusqueda extends AsyncTask<ListView, Integer, List<ItemInventario>> {
        Context context;
        List<ItemInventario> productoArrayList = new ArrayList<ItemInventario>();
        ListView listB;
        private String query;
        private double latitudORI, longitudORI;
        ListView listBus;
        SearchView barra_busqueda;


        public HBusqueda(Context context, String query, ListView listBus,  SearchView barra_busqueda,double latOri,double longOri) {
            this.context = context;
            this.query = query;
            this.listBus = listBus;
            this.barra_busqueda =barra_busqueda;
            latitudORI = latOri;
            longitudORI = longOri;
        }

        @Override
        protected List<ItemInventario> doInBackground(ListView... params) {

            listB = params[0];
            List<ItemInventario> items = InventarioDAO.getInstance(context).getItems();
            for(int i=0;i<items.size();i++){
                if(items.get(i).getProducto().getNombre().toLowerCase().contains(query.toLowerCase()))
                    productoArrayList.add(items.get(i));
            }
            return productoArrayList;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // durante la ejecucion -- para la barra
        }

        @Override
        protected void onPreExecute() {
        }


        @Override
        protected void onPostExecute(List<ItemInventario> result) {
            if(barra_busqueda.getQuery() == "")
                listBus.setVisibility(View.GONE);
            if (result.isEmpty()) {

            } else {

                //Si estamos geolocalizados ordenamos por distancia
                if (latitudORI != Double.MAX_VALUE && longitudORI !=Double.MAX_VALUE) {
                    Collections.sort(result, new ComparadorSortBusqueda());
                }

                listB.setAdapter(new AdapterBusqueda(context, result));

                // se ejecuta cuando acaba la hebra principal
                listB.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        // TODO Auto-generated method stub
                        Intent producto = new Intent(context, CargaProducto.class);
                        producto.putExtra("idF", productoArrayList.get(position).getFarmacia().getId());
                        producto.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        context.startActivity(producto);
                    }
                });

                listBus.setVisibility(View.VISIBLE);


            }
        }

}

