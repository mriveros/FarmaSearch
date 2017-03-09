package utilidades;

/**
 * Created by NeN on 02/10/2015.
 */

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import modelo.Preferencia;

/**
 * Created by Emilio Chica Jiménez on 18/09/2015.
 */
public enum  Serializar {
    INSTANCE;
    FileOutputStream fos;
    FileInputStream fis;
    ObjectOutputStream out;
    ObjectInputStream in;

    // Escribir el objeto en el fichero
    public void serializadoPreferencias(ArrayList<Preferencia> preferencias,Context context) throws IOException {

        fos = context.openFileOutput("pref.bin", Context.MODE_PRIVATE);
        out = new ObjectOutputStream(fos);
        out.writeObject(preferencias);
        out.close();
    }

    // Leer el objeto del fichero (en el mismo orden !!
    public  ArrayList<Preferencia> deserializadoPreferencias(Context context) throws IOException, ClassNotFoundException {

        fis = context.openFileInput("pref.bin");
        if(fis.available()>0) {
            in = new ObjectInputStream(fis);
        }
        ArrayList<Preferencia> aux=null;
        if(in!=null) {
            aux = (ArrayList<Preferencia>) in.readObject();
            in.close();
        }
        return aux;
    }



}
