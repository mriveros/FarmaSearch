package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import basedatos.DBHelper;
import modelo.Usuario;

/**
 * Created by Emilio Chica Jimenez on 05/04/2016.
 */
public class UsuarioDAO extends DBHelper {
    //Para implementar el patron singleton
    private static UsuarioDAO sInstance;

    public static synchronized UsuarioDAO getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new UsuarioDAO(context.getApplicationContext());
        }
        return sInstance;
    }
    //Constructor privado para seguir con el patron singleton
    private UsuarioDAO(Context context) {
        super(context);
    }


    // Insert a post into the database
    public void addUsuario(Usuario usuario){
        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put("id", usuario.getId());
            values.put("nombre", usuario.getNombre());
            values.put("apellidos", usuario.getApellidos());
            values.put("email", usuario.getEmail());
            values.put("password", usuario.getPassword());
            values.put("tipo",usuario.getTipo());


            db.replaceOrThrow("usuario", null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d("DB", "Error while trying to add usuario to database");
        } finally {
            db.endTransaction();
        }
    }


    // Get all ususarios in the database
    public List<Usuario> getUsuarios() {
        final List<Usuario> usuarios = new ArrayList<>();
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s"
                        ,"usuario");
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Usuario u = new Usuario();
                    u.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    u.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                    u.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                    u.setApellidos(cursor.getString(cursor.getColumnIndex("apellidos")));
                    u.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                    u.setTipo(cursor.getInt(cursor.getColumnIndex("tipo")));
                    usuarios.add(u);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB", "Error while trying to get usuarios from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return usuarios;
    }


    public Usuario getUsuario(long idU){

        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s where id=%d"
                        ,"usuario",
                        idU);
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        Usuario u=null;
        try {
            if (cursor.moveToFirst()) {
                do {
                    u = new Usuario();
                    u.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    u.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                    u.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                    u.setApellidos(cursor.getString(cursor.getColumnIndex("apellidos")));
                    u.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                    u.setTipo(cursor.getInt(cursor.getColumnIndex("tipo")));
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB", "Error while trying to get usuario from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return u;
    }

    public Usuario getUsuarioPorEmail(String email){

        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s where email='%s'"
                        ,"usuario",
                        email);
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        Usuario u=null;
        try {
            if (cursor.moveToFirst()) {
                do {
                    u = new Usuario();
                    u.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    u.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                    u.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                    u.setApellidos(cursor.getString(cursor.getColumnIndex("apellidos")));
                    u.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                    u.setTipo(cursor.getInt(cursor.getColumnIndex("tipo")));
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB", "Error while trying to get usuario from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return u;
    }


    public void delUsuario(long id) {
        db.beginTransaction();
        try {

            db.delete("usuario",
                    "id = ?",
                    new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d("DB","Error while trying to delete usuario to database");
        } finally {
            db.endTransaction();
        }
    }
}
