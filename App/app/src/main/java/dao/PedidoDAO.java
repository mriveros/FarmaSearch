package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import basedatos.DBHelper;
import modelo.Farmacia;
import modelo.LineaPedido;
import modelo.Pedido;
import modelo.Producto;
import modelo.Usuario;

/**
 * Created by Emilio Chica Jimenez on 05/04/2016.
 */
public class PedidoDAO extends DBHelper {
    private Context context;
    //Para implementar el patron singleton
    private static PedidoDAO sInstance;
    private SimpleDateFormat parser =new SimpleDateFormat("yyyy-MM-dd");

    public static synchronized PedidoDAO getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PedidoDAO(context.getApplicationContext());
        }
        return sInstance;
    }
    //Constructor privado para seguir con el patron singleton
    private PedidoDAO(Context context) {
        super(context);
        this.context=context;
    }


    // Insert a post into the database
    public void addPedido(Pedido pedido){
        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put("id", pedido.getId());
            values.put("id_usuario", pedido.getUsuario().getId());
            values.put("formapago", pedido.getFormaPago());
            values.put("fecha", parser.format(pedido.getFecha()));
            db.replaceOrThrow("pedido", null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d("DB", "Error while trying to add pedido to database");
        } finally {
            db.endTransaction();
            for(int i=0;i<pedido.getLineaPedidos().size();++i){
                actualizaLineaPedido(pedido.getId(),pedido.getLineaPedidos().get(i));
            }
        }
    }
    private void actualizaLineaPedido(long idP,LineaPedido lp){
        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put("id", lp.getId());
            values.put("id_producto", lp.getProducto().getId());
            values.put("id_farmacia", lp.getFarmacia().getId());
            values.put("cantidad", lp.getCantidad());
            values.put("id_pedido",idP );

            db.replaceOrThrow("lineapedido", null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d("DB", "Error while trying to add pedido to database");
        } finally {
            db.endTransaction();
        }
    }

    // Get all farmacias in the database
    public List<Pedido> getPedidosUsuario(long idU) {

        ///PRESUPONGO QUE NO CAMBIAN LOS DEPARTAMENTOS DURANTE LA EJECUCION
        ////
        final List<Pedido> pedidos = new ArrayList<>();
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s where id_usuario= %d"
                        ,"pedido",
                        idU);
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Pedido pedido = new Pedido();
                    pedido.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    Usuario u =UsuarioDAO.getInstance(context).getUsuario(cursor.getLong(cursor.getColumnIndex("id_usuario")));
                    pedido.setUsuario(u);
                    pedido.setFormaPago(cursor.getString(cursor.getColumnIndex("formapago")));
                    Timestamp ts = new Timestamp(parser.parse(cursor.getString(cursor.getColumnIndex("fecha"))).getTime());
                    pedido.setFecha(ts);
                    setLineaPedidos(pedido);

                    pedidos.add(pedido);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB", "Error while trying to get pedidos from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return pedidos;
    }
    // Get all farmacias in the database
    public List<Pedido> getPedidos() {

        ///PRESUPONGO QUE NO CAMBIAN LOS DEPARTAMENTOS DURANTE LA EJECUCION
        ////
        final List<Pedido> pedidos = new ArrayList<>();
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s"
                        ,"pedido");
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Pedido pedido = new Pedido();
                    pedido.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    Usuario u =UsuarioDAO.getInstance(context).getUsuario(cursor.getLong(cursor.getColumnIndex("id_usuario")));
                    pedido.setUsuario(u);
                    pedido.setFormaPago(cursor.getString(cursor.getColumnIndex("formapago")));
                    Timestamp ts = new Timestamp(parser.parse(cursor.getString(cursor.getColumnIndex("fecha"))).getTime());
                    pedido.setFecha(ts);
                    setLineaPedidos(pedido);

                    pedidos.add(pedido);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB", "Error while trying to get pedidos from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return pedidos;
    }
    private void setLineaPedidos(Pedido pedido){
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s where id_pedido= %d"
                        ,"lineapedido",
                        pedido.getId());
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    LineaPedido lineaPedido = new LineaPedido();
                    lineaPedido.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    lineaPedido.setCantidad(cursor.getInt(cursor.getColumnIndex("cantidad")));
                    Farmacia f = FarmaciaDAO.getInstance(context).getFarmacia(cursor.getLong(cursor.getColumnIndex("id_farmacia")));
                    lineaPedido.setFarmacia(f);
                    Producto p = ProductoDAO.getInstance(context).getProducto(cursor.getLong(cursor.getColumnIndex("id_producto")));
                    lineaPedido.setProducto(p);
                    pedido.addLinea(lineaPedido);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB", "Error while trying to get pedidos from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    public Pedido getPedidoUsuario(long idU,Timestamp fecha){
        String fech = parser.format(fecha);

        Pedido pedido=null;
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s " +
                                "where id_usuario= %d " +
                                "and strftime(\"%Y-%m-%d\",fecha)= \"%s\""
                        , "pedido",
                        idU,fech);
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    pedido = new Pedido();
                    pedido.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    Usuario u =UsuarioDAO.getInstance(context).getUsuario(cursor.getLong(cursor.getColumnIndex("id_usuario")));
                    pedido.setUsuario(u);
                    pedido.setFormaPago(cursor.getString(cursor.getColumnIndex("formapago")));
                    Timestamp ts = new Timestamp(parser.parse(cursor.getString(cursor.getColumnIndex("fecha"))).getTime());
                    pedido.setFecha(ts);
                    setLineaPedidos(pedido);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB", "Error while trying to get pedido from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return pedido;
    }

    public Pedido getPedido(long idP){

        Pedido pedido=null;
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s where id= %d"
                        , "pedido",
                        idP);
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    pedido = new Pedido();
                    pedido.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    Usuario u =UsuarioDAO.getInstance(context).getUsuario(cursor.getLong(cursor.getColumnIndex("id_usuario")));
                    pedido.setUsuario(u);
                    pedido.setFormaPago(cursor.getString(cursor.getColumnIndex("formapago")));
                    Timestamp ts = new Timestamp(parser.parse(cursor.getString(cursor.getColumnIndex("fecha"))).getTime());
                    pedido.setFecha(ts);
                    setLineaPedidos(pedido);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB", "Error while trying to get pedido from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return pedido;
    }



    public void delPedidosUsuario(long idU) {
        db.beginTransaction();
        try {

            db.delete("pedido",
                    "id__usuario = ?",
                    new String[]{String.valueOf(idU)});
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d("DB","Error while trying to delete itemInventario to database");
        } finally {
            db.endTransaction();
        }
    }

    public void delPedido(long id) {
        db.beginTransaction();
        try {

            db.delete("pedido",
                    "id = ?",
                    new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d("DB","Error while trying to delete itemInventario to database");
        } finally {
            db.endTransaction();
        }
    }


}
