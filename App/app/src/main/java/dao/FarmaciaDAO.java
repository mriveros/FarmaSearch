package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import basedatos.DBHelper;
import modelo.Departamento;
import modelo.Farmacia;

/**
 * Created by Emilio Chica Jimenez on 05/04/2016.
 */
public class FarmaciaDAO extends DBHelper {

    //Para implementar el patron singleton
    private static FarmaciaDAO sInstance;

    public static synchronized FarmaciaDAO getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new FarmaciaDAO(context.getApplicationContext());
        }
        return sInstance;
    }
    //Constructor privado para seguir con el patron singleton
    private FarmaciaDAO(Context context) {
        super(context);
    }


    // Insert a post into the database
    public void addFarmacia(Farmacia farmacia){
        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put("id", farmacia.getId());
            values.put("nombre", farmacia.getNombre());
            values.put("descripcion", farmacia.getDescripcion());
            values.put("imagen", farmacia.getImagen());
            values.put("email", farmacia.getEmail());
            values.put("latitud", farmacia.getLatitud());
            values.put("longitud", farmacia.getLongitud());
            values.put("telefono", farmacia.getTelefono());

            db.replaceOrThrow("farmacia", null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d("DB", "Error while trying to add farmacia to database");
        } finally {
            db.endTransaction();
        }
    }


    // Get all farmacias in the database
    public List<Farmacia> getFarmacias() {

        ///PRESUPONGO QUE NO CAMBIAN LOS DEPARTAMENTOS DURANTE LA EJECUCION
        ////
        final List<Farmacia> farmacias = new ArrayList<>();
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s"
                        ,"farmacia");
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Farmacia f = new Farmacia();
                    f.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    f.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                    f.setDescripcion(cursor.getString(cursor.getColumnIndex("descripcion")));
                    f.setImagen(cursor.getString(cursor.getColumnIndex("imagen")));
                    f.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                    f.setTelefono(cursor.getString(cursor.getColumnIndex("telefono")));
                    f.setLatitud(cursor.getFloat(cursor.getColumnIndex("latitud")));
                    f.setLongitud(cursor.getFloat(cursor.getColumnIndex("longitud")));
                    farmacias.add(f);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB", "Error while trying to get Departamentos from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return farmacias;
    }


    public Farmacia getFarmacia(long idF){

        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s where id=%d"
                        ,"farmacia",
                        idF);
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        Farmacia f=null;
        try {
            if (cursor.moveToFirst()) {
                do {
                    f = new Farmacia();
                    f.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    f.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                    f.setDescripcion(cursor.getString(cursor.getColumnIndex("descripcion")));
                    f.setImagen(cursor.getString(cursor.getColumnIndex("imagen")));
                    f.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                    f.setTelefono(cursor.getString(cursor.getColumnIndex("telefono")));
                    f.setLatitud(cursor.getFloat(cursor.getColumnIndex("latitud")));
                    f.setLongitud(cursor.getFloat(cursor.getColumnIndex("longitud")));
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB", "Error while trying to get Departamentos from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return f;
    }

    public void delFarmacia(long id) {
        db.beginTransaction();
        try {

            db.delete("farmacia",
                    "id = ?",
                    new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d("DB","Error while trying to add departamento to database");
        } finally {
            db.endTransaction();
        }
    }

}
