package hebras;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.Toast;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.util.List;

import javax.xml.transform.dom.DOMLocator;

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
import modelo.Pedido;
import modelo.Producto;
import modelo.Reserva;
import modelo.Usuario;
import utilidades.GeoLocalizacion;


/**
 * Created by NeN on 31/10/2015.
 */
public class HObtenerBD extends AsyncTask<Void, Integer, Boolean> {

        Context context;
        String resultado;
        ProgressDialog pDialog;
        double latitudORI,longitudORI;

        public HObtenerBD(Context c,double latitudORI,double longitudORI) {
            context=c;
            this.latitudORI = latitudORI;
            this.longitudORI = longitudORI;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean correcto=true;
            resultado= ConsultaBBDD.realizarConsulta(ConsultaBBDD.consultaBBDD, "","POST");

            JSONObject baseDatosJSON=null;
            if(resultado!=null) {
                try {
                    ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                    mapper.disable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE);
                    mapper.disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
                    baseDatosJSON = new JSONObject(resultado);
                    ///GUARDAR PRODUCTOS EN SQLITE
                    if(!baseDatosJSON.isNull("productos")) {
                        JSONArray productos = baseDatosJSON.getJSONArray("productos");

                        for (int i = 0; i < productos.length(); ++i) {
                            JSONObject producto = productos.getJSONObject(i);
                            Producto p = null;
                            try {
                                p = mapper.readValue(producto.toString(), Producto.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ///GUARDAR PRODUCTOS EN SQLITE
                            ////
                            if (p != null) {
                                ProductoDAO productoDAO = ProductoDAO.getInstance(context);
                                productoDAO.addProducto(p);
                            }
                        }
                    }
                    ///GUARDAR PRODUCTOS EN SQLITE

                    ///GUARDAR FARMACIAS EN SQLITE

                    if(!baseDatosJSON.isNull("farmacias")) {
                        JSONArray farmacias = baseDatosJSON.getJSONArray("farmacias");
                        for (int j = 0; j < farmacias.length(); ++j) {
                            JSONObject farmacia = farmacias.getJSONObject(j);
                            Farmacia f = null;
                            try {
                                f = mapper.readValue(farmacia.toString(), Farmacia.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ///GUARDAR FARMACIAS EN SQLITE
                            //////
                            if (f != null) {
                                FarmaciaDAO farmaciaDAO = FarmaciaDAO.getInstance(context);
                                if(latitudORI!=Double.MAX_VALUE && longitudORI!= Double.MAX_VALUE) {
                                    double p = GeoLocalizacion.distanciaKMS(latitudORI, f.getLatitud(), longitudORI, f.getLongitud());
                                    f.setDistancia(p);
                                }
                                farmaciaDAO.addFarmacia(f);
                            }

                        }
                    }
                    ///GUARDAR FARMACIAS EN SQLITE


                    ///GUARDAR DEPARTAMENTOS EN SQLITE
                    if(!baseDatosJSON.isNull("departamentos")) {
                        JSONArray departamentos = baseDatosJSON.getJSONArray("departamentos");

                        for (int i = 0; i < departamentos.length(); ++i) {
                            JSONObject departamento = departamentos.getJSONObject(i);
                            Departamento d = null;
                            try {
                                d = mapper.readValue(departamento.toString(), Departamento.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ///GUARDAR DEPARTAMENTOS EN SQLITE
                            /////
                            if (d != null) {
                                DepartamentoDAO departamentoDAO = DepartamentoDAO.getInstance(context);
                                departamentoDAO.addDepartameno(d);
                            }
                        }
                    }


                    ///GUARDAR Inventario EN SQLITE
                    if(!baseDatosJSON.isNull("inventario")) {
                        JSONArray inventario = baseDatosJSON.getJSONArray("inventario");

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
                    ///GUARDAR Inventario EN SQLITE


                    ///GUARDAR USUARIOS EN SQLITE
                    if(!baseDatosJSON.isNull("usuarios")) {
                        JSONArray usuarios = baseDatosJSON.getJSONArray("usuarios");
                        for (int j = 0; j < usuarios.length(); ++j) {
                            JSONObject usuario = usuarios.getJSONObject(j);
                            Usuario u = null;
                            try {
                                u = mapper.readValue(usuario.toString(), Usuario.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ///GUARDAR USUARIOS EN SQLITE
                            //////
                            if (u != null) {
                                UsuarioDAO usuarioDAO = UsuarioDAO.getInstance(context);
                                usuarioDAO.addUsuario(u);
                            }

                        }
                    }
                    ///GUARDAR USUARIOS EN SQLITE

                    ///GUARDAR RESERVAS EN SQLITE
                    if(!baseDatosJSON.isNull("reservas")) {
                        JSONArray reservas = baseDatosJSON.getJSONArray("reservas");
                        for (int j = 0; j < reservas.length(); ++j) {
                            JSONObject reserva = reservas.getJSONObject(j);
                            Reserva r = null;
                            try {
                                r = mapper.readValue(reserva.toString(), Reserva.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ///GUARDAR RESERVAS EN SQLITE
                            //////
                            if (r != null) {
                                ReservaDAO reservaDAO = ReservaDAO.getInstance(context);
                                reservaDAO.addReserva(r);
                            }

                        }
                    }
                    ///GUARDAR RESERVAS EN SQLITE

                    ///GUARDAR PEDIDOS EN SQLITE
                    if(!baseDatosJSON.isNull("pedidos")) {
                        JSONArray pedidos = baseDatosJSON.getJSONArray("pedidos");
                        for (int j = 0; j < pedidos.length(); ++j) {
                            JSONObject pedido = pedidos.getJSONObject(j);
                            Pedido p = null;
                            try {
                                p = mapper.readValue(pedido.toString(), Pedido.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ///GUARDAR PEDIDOS EN SQLITE
                            //////
                            if (p != null) {
                                PedidoDAO pedidoDAO = PedidoDAO.getInstance(context);
                                pedidoDAO.addPedido(p);
                            }
                        }
                    }
                    ///GUARDAR PEDIDOS EN SQLITE


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else
                correcto=false;
            return correcto;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // durante la ejecucion -- para la barra
        }
    @Override
    protected void onPostExecute(Boolean correcto) {

        if (!correcto) {
            Toast toast = Toast.makeText(context,
                    "No hay conexion con el servidor, trabajará en local",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }
    }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }



        @Override
        protected void onCancelled() {

        }




}

