package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import basedatos.DBHelper;
import modelo.Producto;

/**
 * Created by Emilio Chica Jimenez on 05/04/2016.
 */
public class ProductoDAO extends DBHelper {
    //Para implementar el patron singleton
    private static ProductoDAO sInstance;

    public static synchronized ProductoDAO getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ProductoDAO(context.getApplicationContext());
        }
        return sInstance;
    }
    //Constructor privado para seguir con el patron singleton
    private ProductoDAO(Context context) {
        super(context);
    }


    // Insert a post into the database
    public void addProducto(Producto producto){
        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put("id", producto.getId());
            values.put("nombre", producto.getNombre());
            values.put("descripcion", producto.getDescripcion());
            values.put("imagen", producto.getImagen());


            db.replaceOrThrow("producto", null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d("DB", "Error while trying to add producto to database");
        } finally {
            db.endTransaction();
        }
    }


    // Get all farmacias in the database
    public List<Producto> getProductos() {

        ///PRESUPONGO QUE NO CAMBIAN LOS DEPARTAMENTOS DURANTE LA EJECUCION
        ////
        final List<Producto> productos = new ArrayList<>();
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s"
                        ,"producto");
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Producto p = new Producto();
                    p.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    p.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                    p.setDescripcion(cursor.getString(cursor.getColumnIndex("descripcion")));
                    p.setImagen(cursor.getString(cursor.getColumnIndex("imagen")));

                    productos.add(p);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB", "Error while trying to get Productos from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return productos;
    }


    public Producto getProducto(long idP){

        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s where id=%d"
                        ,"producto",
                        idP);
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        Producto p=null;
        try {
            if (cursor.moveToFirst()) {
                do {
                    p = new Producto();
                    p.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    p.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                    p.setDescripcion(cursor.getString(cursor.getColumnIndex("descripcion")));
                    p.setImagen(cursor.getString(cursor.getColumnIndex("imagen")));
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB", "Error while trying to get Productos from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return p;
    }

    public void delProducto(long id) {
        db.beginTransaction();
        try {

            db.delete("producto",
                    "id = ?",
                    new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d("DB","Error while trying to delete Producto to database");
        } finally {
            db.endTransaction();
        }
    }
}
