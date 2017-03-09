package basedatos;

import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



/**
 * Created by Emilio Chica Jimenez on 27/10/2015.
 */
public class ConsultaBBDD {

    //Servidor
    public static final String REST=":8080/ChicaJimenezEmilio/";
    //Comente esta linea y descomente la de abajo con su IP
    //Recuerde desactivar el firewall del antivirus
    public static String IP = "farmasearch8531.cloudapp.net";
    //public static String IP = "192.168.2.3";
    public static String server ="http://"+IP+REST;


    //Ficheros para consultas

    public static final String consultaBBDD = "ServletBD";
    public static final String consultaDisponibilidad = "ServletReserva";
    public static final String insertaUserBBDD = "ServletUsuario";

    ///Ficheros para imagenes

    public static final String imgFarmacias = "imgFarmacias/";
    public static final String imgDepartamentos = "imgDepartamentos/";
    public static final String imgProductos = "imgProductos/";


    public static String realizarConsulta(String urlREST,String parametros,String metodo) {

            HttpURLConnection conexion = null;

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {

                System.setProperty("http.keepAlive", "false");
            }

            try {

                URL url = new URL(server + urlREST );

                conexion = (HttpURLConnection) url.openConnection();
                conexion.setConnectTimeout(5000);
                conexion.setReadTimeout(10000);
                conexion.setRequestMethod(metodo);
                conexion.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conexion.setDoInput(true);
                conexion.setDoOutput(true);
                OutputStream os = conexion.getOutputStream();
                os.write(parametros.getBytes());
                os.flush();
                conexion.connect();

                int responseCode = conexion.getResponseCode();

                Log.d(" reponseCode", String.valueOf(responseCode));

                if(responseCode == HttpURLConnection.HTTP_OK){

                    StringBuilder sb = new StringBuilder();
                    try{

                        BufferedReader br = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                        String linea;

                        while ((linea = br.readLine())!= null){

                            sb.append(linea);
                        }

                        return sb.toString();
                    }catch (Exception e){

                        e.printStackTrace();
                    }

                }else{

                    if(responseCode == HttpURLConnection.HTTP_CLIENT_TIMEOUT){
                        Log.d(" reponseCode",  conexion.getErrorStream().toString());
                    }
                }

            } catch (MalformedURLException e) {

                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            finally {
                    if(conexion!=null)
                        conexion.disconnect();
            }

            return null;
    }

}
