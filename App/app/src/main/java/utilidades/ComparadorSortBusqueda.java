package utilidades;

import java.util.Comparator;;
import modelo.Farmacia;
import modelo.ItemInventario;

/**
 * Created by Emilio on 03/10/2015.
 */
public class ComparadorSortBusqueda implements Comparator<ItemInventario> {


    @Override
    public int compare(ItemInventario lhs, ItemInventario rhs) {

        if(lhs.getFarmacia().getDistancia() < rhs.getFarmacia().getDistancia())
        {
            return -1;
        }
        else
        {
            return 1;
        }

    }
}
