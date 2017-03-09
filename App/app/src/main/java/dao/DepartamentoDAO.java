package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import basedatos.DBHelper;
import modelo.Departamento;
import modelo.Farmacia;

/**
 * Created by Emilio Chica Jimenez on 05/04/2016.
 */
public class DepartamentoDAO extends DBHelper {

    //Para implementar el patron singleton
    private static DepartamentoDAO sInstance;

    public static synchronized DepartamentoDAO getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DepartamentoDAO(context.getApplicationContext());
        }
        return sInstance;
    }
    //Constructor privado para seguir con el patron singleton
    private DepartamentoDAO(Context context) {
        super(context);
    }


    // Insert a post into the database
    public void addDepartameno(Departamento departamento){
        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put("id", departamento.getId());
            values.put("nombre", departamento.getNombre());
            values.put("descripcion", departamento.getDescripcion());
            values.put("imagen", departamento.getImagen());


            db.replaceOrThrow("departamento", null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d("DB","Error while trying to add departamento to database");
        } finally {
            db.endTransaction();
            for(int i=0;i<departamento.getFarmacias().size();++i){
                actualizaFarmaciaDpto(departamento.getId(), departamento.getFarmacias().get(i).getId());
            }
        }
    }
    private void actualizaFarmaciaDpto(long idDpto, long idFar){
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("id_departamento", idDpto);
            values.put("id_farmacia", idFar);
            db.replaceOrThrow("farmacia_departamento", null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("DB","Error while trying to add farmacia_departamento to database");
        } finally {
            db.endTransaction();
        }
    }

    // Get all departamentos in the database
    public List<Departamento> getDepartamentos() {

        ///PRESUPONGO QUE NO CAMBIAN LOS DEPARTAMENTOS DURANTE LA EJECUCION
        ////
        final List<Departamento> departamentos = new ArrayList<>();
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s"
                            ,"departamento");
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Departamento d=new Departamento();
                    d.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    d.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                    d.setDescripcion(cursor.getString(cursor.getColumnIndex("descripcion")));
                    d.setImagen(cursor.getString(cursor.getColumnIndex("imagen")));
                    List<Farmacia> farmacias = getFarmaciasDepartamento(d.getId());
                    for (int i=0;i<farmacias.size();++i)
                        d.addFarmacias(farmacias.get(i));
                    departamentos.add(d);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB", "Error while trying to get Departamentos from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return departamentos;
    }

    // Get all departamentos in the database
    public List<Departamento> getDepartamentosPorFarmacia(long idF) {
        final List<Departamento> departamentosPorFarmacia = new ArrayList<>();

        String POSTS_SELECT_QUERY =
                String.format("SELECT d.* FROM %s as d " +
                                "INNER JOIN %s as fd " +
                                "ON fd.%s = d.%s" +
                                " WHERE fd.id_farmacia=%d",
                        "departamento",
                        "farmacia_departamento",
                        "id_departamento",
                        "id",
                        idF);
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Departamento d=new Departamento();
                    d.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    d.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                    d.setDescripcion(cursor.getString(cursor.getColumnIndex("descripcion")));
                    d.setImagen(cursor.getString(cursor.getColumnIndex("imagen")));
                    List<Farmacia> farmacias = getFarmaciasDepartamento(d.getId());
                    for (int i=0;i<farmacias.size();++i)
                        d.addFarmacias(farmacias.get(i));
                    departamentosPorFarmacia.add(d);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB", "Error while trying to get Departamentos from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return departamentosPorFarmacia;
    }
    // Get all posts in the database
    private List<Farmacia> getFarmaciasDepartamento(long idD) {
        final List<Farmacia> farmacias = new ArrayList<>();
        String POSTS_SELECT_QUERY =
                String.format("SELECT f.* FROM %s as f " +
                                "INNER JOIN %s as fd " +
                                "ON fd.%s = f.%s" +
                                " WHERE fd.id_departamento=%d",
                        "farmacia",
                        "farmacia_departamento",
                        "id_farmacia",
                        "id",
                        idD);
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
            Log.d("DB", "Error while trying to get Farmacias from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return Collections.unmodifiableList(farmacias);
    }


    public Departamento getDepartamento(long idDepartamento){
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s where id=%d"
                        ,"departamento",
                        idDepartamento);
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        Departamento d=null;
        try {
            if (cursor.moveToFirst()) {
                do {
                    d=new Departamento();
                    d.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    d.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                    d.setDescripcion(cursor.getString(cursor.getColumnIndex("descripcion")));
                    d.setImagen(cursor.getString(cursor.getColumnIndex("imagen")));
                    List<Farmacia> farmacias = getFarmaciasDepartamento(d.getId());
                    for (int i=0;i<farmacias.size();++i)
                        d.addFarmacias(farmacias.get(i));

                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB", "Error while trying to get Departamentos from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return d;
    }

    public void delDepartamento(long id) {
        db.beginTransaction();
        try {

            db.delete("departamento",
                    "id = ?",
                    new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();
            /*for(int i=0;i<departamentos.size();++i)
                if(departamentos.get(i).getId()==id)
                    departamentos.remove(departamentos.get(i));*/

        } catch (Exception e) {
            Log.d("DB","Error while trying to add departamento to database");
        } finally {
            db.endTransaction();
        }
    }


}
