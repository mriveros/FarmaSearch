package tabfarmacia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coloredmoon.com.chicajimenezemilio.R;
import com.squareup.picasso.Picasso;


import basedatos.ConsultaBBDD;
import dao.FarmaciaDAO;
import modelo.Farmacia;
import utilidades.MapaComoLLegar;

/**
 * Created by Tri_2 on 02/09/2015.
 */
public class TabInicioFarmacia extends Fragment {
    long idF;
    View v ;
    Farmacia f;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
         v =inflater.inflate(R.layout.activity_tab_inicio_farmacia,container,false);

        ImageView foto = (ImageView) v.findViewById(R.id.imageFarmacia);
        ImageView imageTelefono = (ImageView) v.findViewById(R.id.imageTlf);
        ImageView imageMapa = (ImageView) v.findViewById(R.id.imageMapa);
        TextView nombre = (TextView) v.findViewById(R.id.nombre);
        TextView descripcion = (TextView) v.findViewById(R.id.descripcion);
        TextView telefono = (TextView) v.findViewById(R.id.telefono);
        TextView email = (TextView) v.findViewById(R.id.email);
        f = FarmaciaDAO.getInstance(v.getContext()).getFarmacia(idF);

        nombre.setText(f.getNombre());
        descripcion.setText(f.getDescripcion());
        telefono.setText(f.getTelefono());
        email.setText(f.getEmail());

        imageMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button1();
            }
        });
        imageTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button2();
            }
        });
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        Picasso.with(getActivity()).load(ConsultaBBDD.server+ConsultaBBDD.imgFarmacias+f.getImagen()).into(foto);

        return v;
    }

    public void button1()
    {
        if(f.getLatitud()==0 && f.getLongitud()==0){
            Toast toast = Toast.makeText(v.getContext(), "Lo sentimos, no existe mapa de la farmacia seleccionada", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }else{
            Intent intmap2 = null;
            intmap2 = new Intent(v.getContext(), MapaComoLLegar.class);
            intmap2.putExtra("latitud",f.getLatitud());
            intmap2.putExtra("longitud",f.getLongitud());
            intmap2.putExtra("nombre", f.getNombre());
            startActivity(intmap2);
        }
    }

    public void button2()
    {
        if(f.getTelefono().equals("")){
            Toast toast = Toast.makeText(v.getContext(), "Lo sentimos, no existe teléfono e la farmaciaseleccionado", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }else{
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + f.getTelefono()));
            startActivity(intent);
        }
    }

    public void setIdF(long idF) {
        this.idF = idF;
    }


}
