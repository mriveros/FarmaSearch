package sesion;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.concurrent.ExecutionException;

import basedatos.ConsultaBBDD;
import dao.UsuarioDAO;
import hebras.HInsertaUsuarioEnBDExterna;
import modelo.Usuario;
import utilidades.Cifrador;

/**
 * Created by NeN on 07/11/2015.
 */
public class Sesion {
    Context context;
    public Usuario userSesion;
    private static Sesion sInstance;

    public static synchronized Sesion getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Sesion(context.getApplicationContext());
        }
        return sInstance;
    }
    //Constructor privado para seguir con el patron singleton
    private Sesion(Context context) {
        this.context = context;
    }

    public Usuario getUserSesion(){
        return userSesion;
    }
    public void cargarSesion(){

        SharedPreferences misprefe = context.getSharedPreferences("sesion", Context.MODE_PRIVATE);
        if(misprefe!=null) {
            userSesion = new Usuario();
            userSesion.setId(misprefe.getLong("id", -1));
            userSesion.setNombre(misprefe.getString("nombre", ""));
            userSesion.setApellidos(misprefe.getString("apellidos", ""));
            userSesion.setPassword(misprefe.getString("password", ""));
            userSesion.setEmail(misprefe.getString("email", ""));
            userSesion.setTipo(misprefe.getInt("tipo", -1));
        }
    }
    //guardar configuración aplicación Android usando SharedPreferences
    public void guardarSesion()
    {
        SharedPreferences prefs =
                context.getSharedPreferences("sesion", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("id", userSesion.getId());
        editor.putString("nombre", userSesion.getNombre());
        editor.putString("apellidos", userSesion.getApellidos());
        editor.putString("password", userSesion.getPassword());
        editor.putString("email", userSesion.getEmail());
        editor.putInt("tipo", userSesion.getTipo());
        editor.commit();
    }
    //Esta función SÓLO esta para las pruebas y la rápida corrección de la práctica
    //GUARDA la IP del servidor REST
    public void guardarServidor(){
        ConsultaBBDD.server="http://"+ConsultaBBDD.IP+ConsultaBBDD.REST;
        SharedPreferences prefs =
                context.getSharedPreferences("servidor", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("IP", ConsultaBBDD.IP);
        editor.commit();
    }
    //Esta función SÓLO esta para las pruebas y la rápida corrección de la práctica
    //Carga la IP del servidor REST
    public void cargarServidor(){
        SharedPreferences prefs =
                context.getSharedPreferences("servidor", Context.MODE_PRIVATE);
        if(prefs!=null) {
            ConsultaBBDD.IP = prefs.getString("IP", "");
            ConsultaBBDD.server="http://"+ConsultaBBDD.IP+ConsultaBBDD.REST;
        }
    }

    public void cerrarSesion(){
        SharedPreferences prefs = context.getSharedPreferences("sesion", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        userSesion = new Usuario();
        editor.putLong("id", -1);
        editor.putString("nombre", "");
        editor.putString("apellidos", "");
        editor.putString("password", "");
        editor.putString("email", "");
        editor.putString("tipo", "");
        editor.commit();
    }
    public boolean registrarUsuario(Usuario u){
        if(comprobarUsuario(u.getEmail())==null) ///El usuario no existe y hay que añadirlo a la base de datos y guardar su sesion
        {
            u.setTipo(2);
            userSesion = u;
            ///METER AL USUARIO EN LA BASE DE DATOS

            try {
                new HInsertaUsuarioEnBDExterna(context,u).execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return true;
        }else {
            iniciarSesion(u.getEmail(), u.getPassword());
            return false;//No he podido añadir al usuario porque ya existe
        }
    }
    public boolean iniciarSesion(String email,String password){
        Usuario u= comprobarUsuario(email,password);
        if(u!=null){
            userSesion=u;
            guardarSesion();
            return true;
        }
        return false;
    }
    private Usuario comprobarUsuario(String email){
        /////BUSCAR EL USUARIO EN LA BASE DE DATOS Ó EN USUARIOSDAO
        return UsuarioDAO.getInstance(context).getUsuarioPorEmail(email);
    }
    private Usuario comprobarUsuario(String email,String password){
        Usuario usuario =comprobarUsuario(email);
        if(usuario!=null){
            String passCifrado =Cifrador.cifrar(password,Cifrador.SHA1);
            if(!usuario.getPassword().equals(passCifrado))
                usuario=null;
            return usuario;
        }
        return null;
    }

}
