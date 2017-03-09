package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Emilio Chica Jimenez on 05/04/2016.
 */
public class Departamento {
    private long id;
    private String nombre;
    private String descripcion;
    private String imagen;
    private final List<Farmacia> farmacias = new ArrayList<>();

    public Departamento() {
    }

    public List<Farmacia> getFarmacias() {
        return Collections.unmodifiableList(farmacias);
    }
    public void addFarmacias(Farmacia f){
        farmacias.add(f);
    }
    public Farmacia getFarmacia(long id){
        Farmacia f=null;
        for (int i=0;i<farmacias.size();++i){
            if(farmacias.get(i).getId()==id){
                f=farmacias.get(i);
            }
        }
        return f;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Departamento(long id, String nombre, String descripcion, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

}

