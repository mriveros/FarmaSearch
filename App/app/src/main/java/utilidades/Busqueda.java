package utilidades;


import java.util.ArrayList;

import hebras.HBusqueda;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.SearchView;

import com.coloredmoon.com.chicajimenezemilio.R;


public class Busqueda extends AppCompatActivity {
	ListView listBus;
	private double latitudORI = Double.MAX_VALUE, longitudORI = Double.MAX_VALUE;
	SearchView barra_busqueda;
	Context nContext = this;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_busqueda);
		listBus = (ListView) findViewById(R.id.listB);
		barra_busqueda = (SearchView) findViewById(R.id.searchView2);

		GeoLocalizacion geo;
		Location localizacion;
		geo= new GeoLocalizacion(this); ////////////////////LOCALIZACION
		localizacion= geo.updatePosicion();
		if(localizacion!=null) {
			longitudORI = localizacion.getLongitude();
			latitudORI = localizacion.getLatitude();
		}

		barra_busqueda.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				if (query != null && query.compareTo("") != 0 && query.length()>=3) {
					new HBusqueda(nContext,query,listBus,barra_busqueda,latitudORI,longitudORI).execute(listBus);
				}
				return true;
			}
			@Override
			public boolean onQueryTextChange(String newText) {
				String query = newText;

				if (query != null && query.compareTo("") != 0 && query.length()>=3 ) {
					new HBusqueda(nContext,query,listBus,barra_busqueda,latitudORI,longitudORI).execute(listBus);
				}
				return true;
			}

		});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Busqueda.this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}

