package com.coloredmoon.com.chicajimenezemilio;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;


import android.app.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;


import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.Gravity;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;


import basedatos.ConsultaBBDD;

import hebras.HObtenerBD;

import sesion.Sesion;
import utilidades.ConexionInternet;

public class SplashScreen extends Activity implements
		GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

	GoogleApiClient mGoogleApiClient;
	Location mLastLocation;


	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		Toast.makeText(this, "Failed to connect...", Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onConnected(Bundle arg0) {

		if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
					mGoogleApiClient);
			if(mLastLocation!=null) {
				lat = mLastLocation.getLatitude();
				longi = mLastLocation.getLongitude();
			}
		}
	}

		@Override
		public void onConnectionSuspended(int arg0) {
			Toast.makeText(this, "Connection suspended...", Toast.LENGTH_SHORT).show();
		}

	protected synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API)
				.build();
	}



	Context c=this;
	double lat = Double.MAX_VALUE;
	double longi=Double.MAX_VALUE;
	private static final long SPLASH_SCREEN_DELAY = 3000;
	//a poder ser traer toda la carga inicial a esta pagina para que lo cargue aqui
	//y transladarlo a la siguiente pagina para que lo muestre directamente sin mas cargas
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash_screen);

		if (!ConexionInternet.estaConectado(this)) {
			Toast toast = Toast
					.makeText(
							SplashScreen.this,
							"No dispone de conexión a internet\n",
							Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();
		}
		buildGoogleApiClient();

		if(mGoogleApiClient!= null){
			mGoogleApiClient.connect();
		}
		else
			Toast.makeText(this, "No tiene la ubicación activada...", Toast.LENGTH_SHORT).show();
			//Esta función SÓLO esta para las pruebas y la rápida corrección de la práctica
			//Carga la IP del servidor REST
			//Sesion.getInstance(this).cargarServidor();
			//Esta función SÓLO esta para las pruebas y la rápida corrección de la práctica
			//Permite elegir la IP del servidor REST
			//if(ConsultaBBDD.IP == null || ConsultaBBDD.IP.equals("")) {
				//eligeServidorREST();
				//ConsultaBBDD.IP="104.40.61.63";
				//ConsultaBBDD.IP="farmasearch8531.cloudapp.net";
				//Sesion.getInstance(c).guardarServidor();
				//continuar();
			//}else
				continuar();
	}
	//Esta función SÓLO esta para las pruebas y la rápida corrección de la práctica
	//Permite elegir la IP del servidor REST
	private void eligeServidorREST(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Elige la IP del servidor REST:");
		final EditText input = new EditText(this);
		builder.setView(input);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//Si no se elige IP del servidor no se puede continuar la ejecución
				ConsultaBBDD.IP = input.getText().toString();
				if(!ConsultaBBDD.IP.equals("")) {
					Sesion.getInstance(c).guardarServidor();
					continuar();
				}
				else
					finish();

			}
		});
		builder.show();
	}

	private void continuar(){
		////CARGA EL USUARIO SI ES QUE SE HA REGISTRADO PREVIAMENTE SINO, TENDRÁ VALORES POR DEFECTO
		Sesion.getInstance(this).cargarSesion();

		/*GeoLocalizacion geo;
		Location localizacion;
		geo = new GeoLocalizacion(this); ////////////////////LOCALIZACION
		localizacion = geo.updatePosicion();*/

		/*if(localizacion!=null) {
			lat = localizacion.getLatitude();
			longi = localizacion.getLongitude();
		}*/
		new HObtenerBD(this, lat, longi).execute();

		TimerTask task = new TimerTask() {
			@Override
			public void run() {

				Intent mainIntent = new Intent().setClass(
						SplashScreen.this, MainActivity.class);
				mainIntent.putExtra("lat", lat);
				mainIntent.putExtra("long", longi);
				startActivity(mainIntent);
				// Close the activity so the user won't able to go back this
				// activity pressing Back button

				finish();
			}
		};

		// Simulate a long loading process on application startup.
		Timer timer = new Timer();
		timer.schedule(task, SPLASH_SCREEN_DELAY);
	}

}
