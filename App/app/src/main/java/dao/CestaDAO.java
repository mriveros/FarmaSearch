package dao;

import android.content.Context;

import java.util.ArrayList;

import modelo.ItemCesta;

/**
 * Created by NeN on 19/04/2016.
 */
public class CestaDAO {
    private static final ArrayList<ItemCesta> cesta = new ArrayList<>();

    //Para implementar el patron singleton
    private static CestaDAO sInstance;

    public static synchronized CestaDAO getInstance() {
        if (sInstance == null) {
            sInstance = new CestaDAO();
        }
        return sInstance;
    }
    //Constructor privado para seguir con el patron singleton
    private CestaDAO() {
    }


    public ArrayList<ItemCesta> getCesta() {
        return cesta;
    }

    public void addItem(ItemCesta item){
        cesta.add(item);
    }
    public ItemCesta getItem(Integer idItem){
        return cesta.get(idItem);
    }

    ///Este método sirve por si vuelve a cargar la activity donde esté y crea un itemCesta nuevo, que encuentre el que esta en la CestaDAO
    ///que es más antiguo y se actualize el itemNuevo con la referencia del antiguo
    public ItemCesta getItemAntiguoCesta(ItemCesta itemCesta){
        for (int i=0;i<cesta.size();++i){
            if(cesta.get(i).getFarmacia().getId()==itemCesta.getFarmacia().getId() && cesta.get(i).getProducto().getId()==itemCesta.getProducto().getId()){
                //Para no tener que volver a hacer la busqueda sobreescribo el item que me han pasado y así el item que es nuevo porque ha sido
                //creado por haber ejecutado la aplicacion de nuevo se convierte en el item que estaba en la cesta ya que la cesta se conserva
                //mientras la aplicacion esté en ejecucion
                return cesta.get(i);
            }
        }
        return null;
    }
    //Calculo el precio total de los productos en la cesta
    public float calculaPrecioTotal(){
        float total=0;
        for(int i=0;i<cesta.size();++i){
            ItemCesta ic = cesta.get(i);
            total+=(ic.getCantidad()*ic.getPrecio());
        }
        return total;
    }
    public boolean estaEnCesta(ItemCesta itemCesta){
        boolean flag=false;
        for (int i=0;i<cesta.size();++i){
            if(cesta.get(i).getFarmacia().getId()==itemCesta.getFarmacia().getId() && cesta.get(i).getProducto().getId()==itemCesta.getProducto().getId()){
                flag=true;
            }
        }
        return flag;
    }
    public void delItem(Integer idItem){
        cesta.remove(idItem);
    }
}
