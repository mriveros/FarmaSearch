package dao;

import java.util.ArrayList;

import modelo.Preferencia;

/**
 * Created by NeN on 19/04/2016.
 */
public class  PreferenciaDAO {

    private static final ArrayList<Preferencia> preferencias = new ArrayList<>();

    //Para implementar el patron singleton
    private static PreferenciaDAO sInstance;

    public static synchronized PreferenciaDAO getInstance() {
        if (sInstance == null) {
            sInstance = new PreferenciaDAO();
        }
        return sInstance;
    }
    //Constructor privado para seguir con el patron singleton
    private PreferenciaDAO() {
    }


    public ArrayList<Preferencia> getPreferencias() {
        return preferencias;
    }

    public void addPreferencia(Preferencia preferencia){
        preferencias.add(preferencia);
    }
    public Preferencia getPreferencia(Integer idP){
        return preferencias.get(idP);
    }
    public void delPreferencia(Integer idP){
        preferencias.remove(idP);
    }
}
