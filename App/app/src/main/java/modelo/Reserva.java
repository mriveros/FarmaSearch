package modelo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Emilio Chica Jimenez on 05/04/2016.
 */
public class Reserva {
    private long id;
    private Timestamp fecha;
    private List<LineaReserva> lineaReservas = new ArrayList<>();
    private Usuario usuario;

    public void setLineaReservas(List<LineaReserva> lineaReservas) {
        for(int i=0;i<lineaReservas.size();++i){
            lineaReservas.get(i).setReserva(this);
        }
        this.lineaReservas = lineaReservas;
    }

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

    public Reserva() {
    }
    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public List<LineaReserva> getLineaReservas() {
        return Collections.unmodifiableList(lineaReservas);
    }

    public void addLinea(LineaReserva l){
        lineaReservas.add(l);
    }
    public void delLinea(LineaReserva l){
        lineaReservas.remove(l);
    }
    public LineaReserva getLinea(long id){
        LineaReserva l=null;
        for(int i=0;i<lineaReservas.size();i++)
        {
            if(lineaReservas.get(i).getId()==id)
                l=lineaReservas.get(i);
        }
        return l;
    }

    public Reserva(long id, Usuario usuario, Timestamp fecha) {
        this.id = id;
        this.usuario = usuario;
        this.fecha = fecha;
    }
}
