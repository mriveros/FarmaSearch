package hebras;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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
import dao.InventarioDAO;
import dao.UsuarioDAO;
import modelo.ItemInventario;
import modelo.LineaReserva;
import modelo.Reserva;
import modelo.Usuario;


/**
 * Created by NeN on 31/10/2015.
 */
public class HInsertaUsuarioEnBDExterna extends AsyncTask<Void, Integer, String> {

        Context context;
        String resultado;
        Usuario usuario;


        public HInsertaUsuarioEnBDExterna(Context c, Usuario usuario) {
            context=c;
            this.usuario = usuario;
        }

        @Override
        protected String doInBackground(Void... params) {
            ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            String usuarioJSON = "";
            try {
                usuarioJSON = mapper.writeValueAsString(usuario);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            resultado = ConsultaBBDD.realizarConsulta(ConsultaBBDD.insertaUserBBDD, usuarioJSON, "POST");
            return resultado;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // durante la ejecucion -- para la barra
        }
    @Override
    protected void onPostExecute(String resultado) {

        if (resultado==null || resultado.equals("")) {
            Toast toast = Toast.makeText(context,
                    "Problemas al insertar al usuario en la BBDD",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
            //((Activity) context).finish();
        }else{
            try {
                JSONObject jsonObject = new JSONObject(resultado);
                if(jsonObject.getLong("usuario")!=-1){
                    Toast toast = Toast.makeText(context,
                            "Registrado con éxito",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                    usuario.setId(jsonObject.getLong("usuario"));
                    if(jsonObject.getLong("usuario")!=-1) {
                        UsuarioDAO.getInstance(context).addUsuario(usuario);
                    }

                }else
                {
                    Toast toast = Toast.makeText(context,
                            "NO TE HEMOS PODIDO REGISTRAR, ¿FALTA CONEXION A INTERNET?",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                }
                //usuario.setId(jsonObject.getLong("usuario"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

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

