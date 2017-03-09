package hebras;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.view.Gravity;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import basedatos.ConsultaBBDD;
import dao.DepartamentoDAO;
import dao.FarmaciaDAO;
import dao.InventarioDAO;
import dao.PedidoDAO;
import dao.ProductoDAO;
import dao.ReservaDAO;
import dao.UsuarioDAO;
import modelo.Departamento;
import modelo.Farmacia;
import modelo.ItemInventario;
import modelo.LineaReserva;
import modelo.Pedido;
import modelo.Producto;
import modelo.Reserva;
import modelo.Usuario;


/**
 * Created by NeN on 31/10/2015.
 */
public class HConsultaReservas extends AsyncTask<Void, Integer, JSONObject> {

        Context context;
        String resultado;
        ProgressDialog pDialog;
        Reserva reserva;


        public HConsultaReservas(Context c,Reserva reserva) {
            context=c;
            this.reserva = reserva;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            String reservaJSON = "";
            try {
                reservaJSON = mapper.writeValueAsString(reserva);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            resultado = ConsultaBBDD.realizarConsulta(ConsultaBBDD.consultaDisponibilidad, reservaJSON, "POST");
            JSONObject res =null;
            try {
                if(resultado!=null) {
                    res = new JSONObject(resultado);
                    ///GUARDAR Inventario EN SQLITE
                    if (!res.isNull("inventario")) {
                        JSONArray inventario = res.getJSONArray("inventario");

                        for (int j = 0; j < inventario.length(); ++j) {
                            JSONObject item = inventario.getJSONObject(j);
                            ItemInventario itemInventario = null;
                            try {
                                itemInventario = mapper.readValue(item.toString(), ItemInventario.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ///GUARDAR Inventario EN SQLITE
                            //////
                            if (itemInventario != null) {
                                InventarioDAO inventarioDAO = InventarioDAO.getInstance(context);
                                inventarioDAO.addItem(itemInventario);
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // durante la ejecucion -- para la barra
        }
    @Override
    protected void onPostExecute(JSONObject resultado) {

        if (resultado==null) {
            Toast toast = Toast.makeText(context,
                    "Problemas al recoger las reservas",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
            pDialog.dismiss();
            //((Activity) context).finish();
        }else{

            try {
                if(resultado.getLong("reserva")!=-1) {
                    JSONObject disponibles = resultado.getJSONObject("disponibles");
                    List<LineaReserva> lineaReservas = reserva.getLineaReservas();
                    for (int i = 0; i < lineaReservas.size(); ++i) {
                        if(disponibles.getInt(String.valueOf(lineaReservas.get(i).getProducto().getId()))==-1)
                        {
                            lineaReservas.remove(i);
                            i--;
                        }

                    }
                }
                reserva.setId(resultado.getLong("reserva"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        pDialog.dismiss();
    }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Cargando Datos");
            pDialog.setCancelable(false);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();
        }



        @Override
        protected void onCancelled() {

        }




}

