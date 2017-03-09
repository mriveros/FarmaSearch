package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import basedatos.DBHelper;
import modelo.Departamento;
import modelo.Farmacia;
import modelo.ItemInventario;
import modelo.Producto;

/**
 * Created by Emilio Chica Jiménez on 19/04/2016.
 */
public class InventarioDAO extends DBHelper {
    private Context context;
    //Para implementar el patron singleton
    private static InventarioDAO sInstance;

    public static synchronized InventarioDAO getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new InventarioDAO(context.getApplicationContext());
        }
        return sInstance;
    }
    //Constructor privado para seguir con el patron singleton
    private InventarioDAO(Context context) {
        super(context);
        this.context=context;
    }


    // Insert a post into the database
    public void addItem(ItemInventario itemInventario){
        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put("id", itemInventario.getId());
            values.put("id_producto", itemInventario.getProducto().getId());
            values.put("id_farmacia", itemInventario.getFarmacia().getId());
            values.put("id_departamento", itemInventario.getDepartamento().getId());
            values.put("stock", itemInventario.getStock());
            values.put("precio", itemInventario.getPrecio());


            db.replaceOrThrow("inventario", null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d("DB", "Error while trying to add itemInventario to database");
        } finally {
            db.endTransaction();
        }
    }


    // Get all items en el inventario in the database
    public List<ItemInventario> getItems() {

        ///PRESUPONGO QUE NO CAMBIAN LOS DEPARTAMENTOS DURANTE LA EJECUCION
        ////
        final List<ItemInventario> items = new ArrayList<>();
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s"
                        ,"inventario");
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    ItemInventario item = new ItemInventario();
                    item.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    Producto p =ProductoDAO.getInstance(context).getProducto(cursor.getLong(cursor.getColumnIndex("id_producto")));
                    item.setProducto(p);
                    Departamento d=DepartamentoDAO.getInstance(context).getDepartamento(cursor.getLong(cursor.getColumnIndex("id_departamento")));
                    item.setDepartamento(d);
                    Farmacia f=FarmaciaDAO.getInstance(context).getFarmacia(cursor.getLong(cursor.getColumnIndex("id_farmacia")));
                    item.setFarmacia(f);
                    item.setPrecio(cursor.getFloat(cursor.getColumnIndex("precio")));
                    item.setStock(cursor.getInt(cursor.getColumnIndex("stock")));
                    items.add(item);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB", "Error while trying to get Departamentos from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return items;
    }

    // Get all items en el inventario por farmacia y departamento in the database
    public List<ItemInventario> getItemsPorFarmaciaYDpto(long idF,long idD) {

        ///PRESUPONGO QUE NO CAMBIAN LOS DEPARTAMENTOS DURANTE LA EJECUCION
        ////
        final List<ItemInventario> items = new ArrayList<>();
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s as i WHERE i.id_farmacia=%d and i.id_departamento=%d"
                        ,"inventario",
                        idF,idD);
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    ItemInventario item = new ItemInventario();
                    item.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    Producto p =ProductoDAO.getInstance(context).getProducto(cursor.getLong(cursor.getColumnIndex("id_producto")));
                    item.setProducto(p);
                    Departamento d=DepartamentoDAO.getInstance(context).getDepartamento(cursor.getLong(cursor.getColumnIndex("id_departamento")));
                    item.setDepartamento(d);
                    Farmacia f=FarmaciaDAO.getInstance(context).getFarmacia(cursor.getLong(cursor.getColumnIndex("id_farmacia")));
                    item.setFarmacia(f);
                    item.setPrecio(cursor.getFloat(cursor.getColumnIndex("precio")));
                    item.setStock(cursor.getInt(cursor.getColumnIndex("stock")));
                    items.add(item);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB", "Error while trying to get Departamentos from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return items;
    }


    public ItemInventario getItemInventario(long idI){

        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s where id=%d"
                        ,"inventario",
                        idI);
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        ItemInventario item=null;
        try {
            if (cursor.moveToFirst()) {
                do {
                    item = new ItemInventario();
                    item.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    Producto p =ProductoDAO.getInstance(context).getProducto(cursor.getLong(cursor.getColumnIndex("id_producto")));
                    item.setProducto(p);
                    Departamento d=DepartamentoDAO.getInstance(context).getDepartamento(cursor.getLong(cursor.getColumnIndex("id_departamento")));
                    item.setDepartamento(d);
                    Farmacia f=FarmaciaDAO.getInstance(context).getFarmacia(cursor.getLong(cursor.getColumnIndex("id_farmacia")));
                    item.setFarmacia(f);
                    item.setPrecio(cursor.getFloat(cursor.getColumnIndex("precio")));
                    item.setStock(cursor.getInt(cursor.getColumnIndex("stock")));
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB", "Error while trying to get Departamentos from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return item;
    }
    public ItemInventario getItemPorIdProductoInventario(long idP){

        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s where id_producto=%d"
                        ,"inventario",
                        idP);
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        ItemInventario item=null;
        try {
            if (cursor.moveToFirst()) {
                do {
                    item = new ItemInventario();
                    item.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    Producto p =ProductoDAO.getInstance(context).getProducto(cursor.getLong(cursor.getColumnIndex("id_producto")));
                    item.setProducto(p);
                    Departamento d=DepartamentoDAO.getInstance(context).getDepartamento(cursor.getLong(cursor.getColumnIndex("id_departamento")));
                    item.setDepartamento(d);
                    Farmacia f=FarmaciaDAO.getInstance(context).getFarmacia(cursor.getLong(cursor.getColumnIndex("id_farmacia")));
                    item.setFarmacia(f);
                    item.setPrecio(cursor.getFloat(cursor.getColumnIndex("precio")));
                    item.setStock(cursor.getInt(cursor.getColumnIndex("stock")));
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB", "Error while trying to get Departamentos from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return item;
    }
    public void delItemInventario(long id) {
        db.beginTransaction();
        try {

            db.delete("inventario",
                    "id = ?",
                    new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d("DB","Error while trying to delete itemInventario to database");
        } finally {
            db.endTransaction();
        }
    }

    public ArrayList<ItemInventario> getItemsPorDpt(long idDpt){
        ArrayList<ItemInventario> itemPorDpt = new ArrayList<>();
        List<ItemInventario> inventario = getItems();
        for(int i=0;i<inventario.size();++i){
            if(inventario.get(i).getDepartamento().getId()==idDpt){
                itemPorDpt.add(inventario.get(i));
            }
        }
        return itemPorDpt;
    }
}
