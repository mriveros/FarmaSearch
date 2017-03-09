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
import modelo.Departamento;
import modelo.Farmacia;
import modelo.LineaPedido;
import modelo.LineaReserva;
import modelo.Pedido;
import modelo.Producto;
import modelo.Reserva;
import modelo.Usuario;

/**
 * Created by Emilio Chica Jimenez on 05/04/2016.
 */
public class ReservaDAO extends DBHelper {
    private Context context;
    //Para implementar el patron singleton
    private static ReservaDAO sInstance;
    private SimpleDateFormat parser =new SimpleDateFormat("yyyy-MM-dd");

    public static synchronized ReservaDAO getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ReservaDAO(context.getApplicationContext());
        }
        return sInstance;
    }
    //Constructor privado para seguir con el patron singleton
    private ReservaDAO(Context context) {
        super(context);
        this.context=context;
    }


    // Insert a post into the database
    public void addReserva(Reserva reserva){
        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put("id", reserva.getId());
            values.put("id_usuario", reserva.getUsuario().getId());
            values.put("fecha", parser.format(reserva.getFecha()));

            db.replaceOrThrow("reserva", null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d("DB", "Error while trying to add pedido to database");
        } finally {
            db.endTransaction();
            for(int i=0;i<reserva.getLineaReservas().size();++i)
                actualizaLineaReserva(reserva.getId(),reserva.getLineaReservas().get(i));
        }
    }

    private void actualizaLineaReserva(long idR, LineaReserva lr){
        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put("id", lr.getId());
            values.put("id_producto", lr.getProducto().getId());
            values.put("id_farmacia", lr.getFarmacia().getId());
            values.put("cantidad", lr.getCantidad());
            values.put("id_reserva",idR );

            db.replaceOrThrow("lineareserva", null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d("DB", "Error while trying to add pedido to database");
        } finally {
            db.endTransaction();
        }
    }
    // Get all farmacias in the database
    public List<Reserva> getReservasUsuario(long idU) {

        ///PRESUPONGO QUE NO CAMBIAN LOS DEPARTAMENTOS DURANTE LA EJECUCION
        ////
        final List<Reserva> reservas = new ArrayList<>();
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s where id_usuario= %d"
                        ,"reserva",
                        idU);
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Reserva reserva = new Reserva();
                    reserva.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    Usuario u =UsuarioDAO.getInstance(context).getUsuario(cursor.getLong(cursor.getColumnIndex("id_usuario")));
                    reserva.setUsuario(u);
                    Timestamp ts = new Timestamp(parser.parse(cursor.getString(cursor.getColumnIndex("fecha"))).getTime());
                    reserva.setFecha(ts);
                    setLineaReservas(reserva);

                    reservas.add(reserva);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB", "Error while trying to get reservas from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return reservas;
    }
    // Get all farmacias in the database
    public List<Reserva> getReservas() {
        final List<Reserva> reservas = new ArrayList<>();
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s"
                        ,"reserva");
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Reserva reserva = new Reserva();
                    reserva.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    Usuario u =UsuarioDAO.getInstance(context).getUsuario(cursor.getLong(cursor.getColumnIndex("id_usuario")));
                    reserva.setUsuario(u);
                    Timestamp ts = new Timestamp(parser.parse(cursor.getString(cursor.getColumnIndex("fecha"))).getTime());
                    reserva.setFecha(ts);
                    setLineaReservas(reserva);

                    reservas.add(reserva);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB", "Error while trying to get pedidos from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return reservas;
    }
    private void setLineaReservas(Reserva reserva){
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s where id_reserva= %d"
                        , "lineareserva",
                        reserva.getId());
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    LineaReserva lineaReserva = new LineaReserva();
                    lineaReserva.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    lineaReserva.setCantidad(cursor.getInt(cursor.getColumnIndex("cantidad")));
                    Farmacia f = FarmaciaDAO.getInstance(context).getFarmacia(cursor.getLong(cursor.getColumnIndex("id_farmacia")));
                    lineaReserva.setFarmacia(f);
                    Producto p = ProductoDAO.getInstance(context).getProducto(cursor.getLong(cursor.getColumnIndex("id_producto")));
                    lineaReserva.setProducto(p);
                    reserva.addLinea(lineaReserva);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB", "Error while trying to set lineas reservas from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    public Reserva getReservaUsuario(long idU,Timestamp fecha){
        String fech = parser.format(fecha);

        Reserva reserva=null;
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s " +
                                "where id_usuario= %d " +
                                "and strftime(\"%Y-%m-%d\",fecha)= \"%s\""
                        , "reserva",
                        idU,fech);
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    reserva = new Reserva();
                    reserva.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    Usuario u =UsuarioDAO.getInstance(context).getUsuario(cursor.getLong(cursor.getColumnIndex("id_usuario")));
                    reserva.setUsuario(u);
                    Timestamp ts = new Timestamp(parser.parse(cursor.getString(cursor.getColumnIndex("fecha"))).getTime());
                    reserva.setFecha(ts);
                    setLineaReservas(reserva);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB", "Error while trying to get reserva from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return reserva;
    }

    public Reserva getReserva(long idR){

        Reserva reserva=null;
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s where id= %d"
                        , "reserva",
                        idR);
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    reserva = new Reserva();
                    reserva.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    Usuario u =UsuarioDAO.getInstance(context).getUsuario(cursor.getLong(cursor.getColumnIndex("id_usuario")));
                    reserva.setUsuario(u);
                    Timestamp ts = new Timestamp(parser.parse(cursor.getString(cursor.getColumnIndex("fecha"))).getTime());
                    reserva.setFecha(ts);
                    setLineaReservas(reserva);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB", "Error while trying to get pedido from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return reserva;
    }



    public void delReservasUsuario(long idU) {
        db.beginTransaction();
        try {

            db.delete("reserva",
                    "id__usuario = ?",
                    new String[]{String.valueOf(idU)});
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d("DB","Error while trying to delete reserva to database");
        } finally {
            db.endTransaction();
        }
    }

    public void delReserva(long id) {
        db.beginTransaction();
        try {

            db.delete("reserva",
                    "id = ?",
                    new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d("DB","Error while trying to delete reserva to database");
        } finally {
            db.endTransaction();
        }
    }
}
