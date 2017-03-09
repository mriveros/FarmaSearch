package utilidades;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Emilio Chica Jimenez  on 11/12/2015.
 */
public class GeoLocalizacion extends Activity {
    LocationManager locationManager;
    Location localizacion, locaHasta, loc;
    LocationListener locationListener;
    boolean updat = false;
    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS
    };
    private static final String[] CAMERA_PERMS = {
            Manifest.permission.CAMERA
    };
    private static final String[] CONTACTS_PERMS = {
            Manifest.permission.READ_CONTACTS
    };
    private static final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final byte INITIAL_REQUEST = 13;
    private static final byte LOCATION_REQUEST = INITIAL_REQUEST + 3;

    //Geocoder geoCoder;
    List<String> direccion;
    Context context;


    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(context, perm));
    }

    private boolean canAccessLocation() {
        return (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case LOCATION_REQUEST:
                if (canAccessLocation()) {
                    doLocationThing(updat);
                }
                break;
        }
    }

    public void doLocationThing(boolean update) {
        // Register the listener with the Location Manager to receive location updates
        if (!update) {
            Log.d("VERSION5 5.0","DOLOACTIONTHING");
            if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                } else if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } else {
            if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            } else if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, LOCATION_PERMS, LOCATION_REQUEST);
                    loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
            }
        }
    }

    public GeoLocalizacion(final Context context) {

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                localizacion = location;
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        ////VERSION 5.0 ANDROID
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.d("VERSION5 5.0","Constructor GEOLOCALIZACION");
            updat = false;
            ActivityCompat.requestPermissions((Activity) context, LOCATION_PERMS, LOCATION_REQUEST);
            if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                else if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            else if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        // geoCoder = new Geocoder(context, Locale.getDefault());
        direccion = new ArrayList<>();
        this.context = context;
    }

    public Location updatePosicion() {
        loc = null;
        if (localizacion == null) {
            ////VERSION 5.0 ANDROID
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Log.d("VERSION5 5.0","updatePosicion");
                updat = true;
                ActivityCompat.requestPermissions((Activity) context, LOCATION_PERMS, LOCATION_REQUEST);
                if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    Log.d("VERSION5 5.0","updatePosicion------------------1");
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        Log.d("VERSION5 5.0","updatePosicion2----------------------------------------");
                    }
                } else if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        Log.d("VERSION5 5.0","updatePosicion3----------------------------------------");
                    }
                    Log.d("VERSION5 5.0","updatePosicion4-----------------------------------------------------------");
                }
            }
            else {
                if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                        loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                } else if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
            }

            localizacion = loc;
        }
        else
            loc = localizacion;
        return loc;

    }

    public Location getLocalizacion()
    {
        return  localizacion;
    }
    public List<String> getLocalizacion(double latitud,double longitud) throws IOException, ExecutionException, InterruptedException {
        GeoCoding g = new GeoCoding(latitud,longitud);
        final String TAG = GeoCoding.class.getSimpleName();
        g.execute(direccion);
        direccion = g.get();
        return direccion;
    }
    public float distanciaHasta(String ciudad) throws ExecutionException, InterruptedException {
        localizacion = updatePosicion();
        List<String> dir = new ArrayList<>();
        GeoCoding g = new GeoCoding(ciudad);
        g.execute(dir);
        dir = g.get();
        Location location = new Location("");
        float res=0;
        if(dir!=null && localizacion!=null) {
            Log.d("DONDE ESTOY LAAAAATTT", "" + localizacion.getLatitude());
            Log.d("DONDE ESTOY LOOOONGGG", "" + localizacion.getLongitude());
            String[] di = dir.get(0).split(":");
            location.setLatitude(Double.parseDouble(di[0]));
            location.setLongitude(Double.parseDouble(di[1]));
            locaHasta = location;
            Log.d("VOY A LAAAAATTT", "" + location.getLatitude());
            Log.d("VOY A LOOOONGGG", "" + location.getLongitude());
            res = (float)distanciaKM(localizacion.getLatitude(),location.getLatitude(),localizacion.getLongitude(),location.getLongitude());
        }
        return res;
    }

    public float distanciaHastaAux(String ciudad, Location locaaux) throws ExecutionException, InterruptedException {
        List<String> dir = new ArrayList<>();
        GeoCoding g = new GeoCoding(ciudad);
        g.execute(dir);
        dir = g.get();
        Location location = new Location("");
        float res=0;
        if(dir!=null && locaaux!=null) {
            Log.d("DONDE ESTOY LAAAAATTT", "" + locaaux.getLatitude());
            Log.d("DONDE ESTOY LOOOONGGG", "" + locaaux.getLongitude());
            String[] di = dir.get(0).split(":");
            location.setLatitude(Double.parseDouble(di[0]));
            location.setLongitude(Double.parseDouble(di[1]));
            locaHasta = location;
            Log.d("VOY A LAAAAATTT", "" + location.getLatitude());
            Log.d("VOY A LOOOONGGG", "" + location.getLongitude());
            res = (float)distanciaKM(locaaux.getLatitude(),location.getLatitude(),locaaux.getLongitude(),location.getLongitude());
        }
        return res;
    }

    public double getLatHast(){
        return locaHasta.getLatitude();
    }
    public double getLngHast(){
        return locaHasta.getLongitude();
    }
    public double getLat(){
        return localizacion.getLatitude();
    }
    public double getLng(){
        return localizacion.getLongitude();
    }
    public static double distanciaKMS(double latitud1,double latitud2,double longitud1, double longitud2)
    {
        double PI,lat1,lat2,long1,long2,distancia;
        PI=Math.PI;

        lat1=(latitud1*PI)/180;
        lat2=(latitud2*PI)/180;

        long1=(longitud1*PI)/180;
        long2=(longitud2*PI)/180;
        distancia = 6371 * Math.acos(Math.cos(lat1) * Math.cos(lat2) * Math.cos(long2 - long1) + Math.sin(lat1) * Math.sin(lat2));

        return distancia;
    }
    public double distanciaKM(double latitud1,double latitud2,double longitud1, double longitud2)
    {
        double PI,lat1,lat2,long1,long2,distancia;
        PI=Math.PI;

        lat1=(latitud1*PI)/180;
        lat2=(latitud2*PI)/180;

        long1=(longitud1*PI)/180;
        long2=(longitud2*PI)/180;
        distancia = 6371 * Math.acos(Math.cos(lat1) * Math.cos(lat2) * Math.cos(long2 - long1) + Math.sin(lat1) * Math.sin(lat2));

        return distancia;
    }
    public void comoLLegar(Location location){
       // updatePosicion();
        //https://www.google.es/maps/dir/37.1789023,-3.5986384/37.121269,-3.5843623/@37.1448449,-3.5776877,12.89z?hl=es
    }
    public void pararSincronizacion(){
        // Remove the listener you previously added
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        locationManager.removeUpdates(locationListener);
    }
    //GEOLOCALIZACION INVERSA: Le das una latitud y una longitud y te dice el pueblo en el que estas
    public class GeoCoding extends AsyncTask<List<String>, Integer, List<String>> {
        private final String TAG = GeoCoding.class.getSimpleName();
        JSONObject jsonObj;
        String ur;
        private String Address1 = "";
        private String City = "";
        private String Address2 = "";
        private String Country = "";
        private String County = "";
        private String PIN = "";
        private String Area="";
        private String State = "";
        private List<String> direccion;
        private String ciudad;
        private  double latitude, longitude;
        HttpURLConnection connection;
        BufferedReader br;
        StringBuilder sb ;

        public GeoCoding(double latitude, double longitude){
            this.latitude = latitude;
            this.longitude = longitude;
            direccion = new ArrayList<>();
        }

        public GeoCoding(String ciudad){
            latitude=Double.MAX_VALUE;
            longitude=Double.MAX_VALUE;
            this.ciudad = ciudad;
            direccion = new ArrayList<>();
        }

        public String getArea(){
            return Area;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // durante la ejecucion -- para la barra
        }
        public List<String> getAddress() {
            Address1 = "";
            Address2 = "";
            City = "";
            State = "";
            Country = "";
            County = "";
            PIN = "";
            Area ="";

            try {

                String Status = jsonObj.getString("status");
                if (Status.equalsIgnoreCase("OK")) {
                    JSONArray Results = jsonObj.getJSONArray("results");

                    if(longitude!=Double.MAX_VALUE && latitude !=Double.MAX_VALUE) {
                        JSONObject zero = Results.getJSONObject(0);
                        JSONArray address_components = zero.getJSONArray("address_components");
                            for (int i = 0; i < address_components.length(); i++) {
                                JSONObject zero2 = address_components.getJSONObject(i);
                                String long_name = zero2.getString("long_name");
                                JSONArray mtypes = zero2.getJSONArray("types");
                                String Type = mtypes.getString(0);
                                if (!TextUtils.isEmpty(long_name) || !long_name.equals(null) || long_name.length() > 0 || !long_name.equals("")) {
                                    if (Type.equalsIgnoreCase("street_number")) {
                                        Address1 = long_name + " ";
                                    } else if (Type.equalsIgnoreCase("route")) {
                                        Address1 = Address1 + long_name;
                                    } else if (Type.equalsIgnoreCase("sublocality")) {
                                        Address2 = long_name;
                                    } else if (Type.equalsIgnoreCase("locality")) {
                                        City = long_name;
                                    } else if (Type.equalsIgnoreCase("administrative_area_level_2")) {
                                        County = long_name;
                                    } else if (Type.equalsIgnoreCase("administrative_area_level_1")) {
                                        State = long_name;
                                    } else if (Type.equalsIgnoreCase("country")) {
                                        Country = long_name;
                                    } else if (Type.equalsIgnoreCase("postal_code")) {
                                        PIN = long_name;
                                    } else if (Type.equalsIgnoreCase("neighborhood")) {
                                        Area = long_name;
                                    }
                                }
                            }
                            direccion.add(0, Address1);
                            direccion.add(1, Address2);
                            direccion.add(2, City);
                            direccion.add(3, County);
                            direccion.add(4, State);
                            direccion.add(5, Country);
                            direccion.add(6, PIN);
                            direccion.add(7, Area);
                        }else
                        {
                            JSONObject zero = Results.getJSONObject(0);
                            JSONObject latlng = zero.getJSONObject("geometry");
                            JSONObject zero2 =latlng.getJSONObject("location");
                            direccion.add(0, zero2.getDouble("lat") + ":" +zero2.getDouble("lng") );
                            Log.d("LAAAAATTT SET", "" + zero2.getDouble("lat"));
                            Log.d("LOOOONGGG SET", "" + zero2.getDouble("lng"));
                        }
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return direccion;

        }

        public double[] getGeoPoint(){
            double[] latlng = new double[2];
            try{
                longitude = ((JSONArray)jsonObj.get("results")).getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location")
                        .getDouble("lng");
                latitude = ((JSONArray)jsonObj.get("results")).getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location")
                        .getDouble("lat");
                latlng[0]=latitude;
                latlng[1]=longitude;


            }catch (Exception e){
                e.printStackTrace();
            }
            return latlng;
        }


        @Override
        protected List<String> doInBackground(List<String>... params)  {
            try {
                StringBuilder urlStringBuilder = new StringBuilder("http://maps.google.com/maps/api/geocode/json");
                if(longitude!=Double.MAX_VALUE && latitude!=Double.MAX_VALUE)
                    urlStringBuilder.append("?latlng=" + URLEncoder.encode(latitude+","+longitude, "utf8"));
                else
                    urlStringBuilder.append("?address=" + URLEncoder.encode(ciudad, "utf8"));
                urlStringBuilder.append("&sensor=false");
                ur = urlStringBuilder.toString();
                Log.d(TAG, "URL: " + ur);

                URL url = new URL(ur);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb = sb.append(line + "\n");
                }
                jsonObj = new JSONObject(sb.toString());

            }catch (Exception e){e.printStackTrace(); }
            return getAddress();
        }

        @Override
        protected void onPostExecute(List<String>  direccion) {
            try {
                Log.d(TAG, "response code: " + connection.getResponseCode());
                Log.d(TAG, "JSON obj: " + jsonObj);
                Log.d(TAG, "area is: " + getArea());
                Log.d("latitude", "" + latitude);
                Log.d("longitude", "" + longitude);

        } catch (Exception e) {
            e.printStackTrace();
        }
       // super.onPostExecute(direccion);
    }
}

}
