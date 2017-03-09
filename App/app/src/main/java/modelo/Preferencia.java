package modelo;

import java.io.Serializable;

/**
 * Created by Emilio Chica Jimenez on 19/04/2016.
 */
public class Preferencia implements Serializable {
    private Producto producto;
    private Farmacia farmacia;
    private Departamento departamento;

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Farmacia getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(Farmacia farmacia) {
        this.farmacia = farmacia;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Preferencia() {
    }

    public Preferencia(Producto producto, Farmacia farmacia, Departamento departamento) {
        this.producto = producto;
        this.farmacia = farmacia;
        this.departamento = departamento;
    }
}
