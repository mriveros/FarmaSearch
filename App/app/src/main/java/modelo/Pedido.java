package modelo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Emilio Chica Jimenez on 05/04/2016.
 */
public class Pedido {
    private long id;
    private Usuario usuario;
    private String formaPago;
    private Timestamp fecha;
    private List<LineaPedido> lineasPedido = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public List<LineaPedido> getLineaPedidos() {
        return Collections.unmodifiableList(lineasPedido);
    }

    public void setLineasPedido(List<LineaPedido> lineasPedido) {
        for(int i=0;i<lineasPedido.size();++i){
            lineasPedido.get(i).setPedido(this);
        }
        this.lineasPedido = lineasPedido;
    }

    public void addLinea(LineaPedido l){
        lineasPedido.add(l);
     }
    public void delLinea(LineaPedido l){
        lineasPedido.remove(l);
    }
    public LineaPedido getLinea(long id){
        LineaPedido l=null;
        for(int i=0;i<lineasPedido.size();i++)
        {
            if(lineasPedido.get(i).getId()==id)
                l=lineasPedido.get(i);
        }
        return l;
    }

    public Pedido() {
    }

    public Pedido(long id, Usuario usuario, String formaPago, Timestamp fecha) {
        this.id = id;
        this.usuario = usuario;
        this.formaPago = formaPago;
        this.fecha = fecha;
    }
}
