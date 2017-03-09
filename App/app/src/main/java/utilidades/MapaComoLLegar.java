package utilidades;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.coloredmoon.com.chicajimenezemilio.R;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;
import com.coloredmoon.com.chicajimenezemilio.R;
public class MapaComoLLegar extends AppCompatActivity {

    WebView mimapa;
    ProgressBar progress;
    //Button comoLlegar;
    TextView distancia;
    GeoLocalizacion geo;
    String nombre;
    private double latori,lngori,latdes,lngdes;
    Location location=null;

    public static boolean isAvailable(Context ctx, Intent intent) {
        final PackageManager mgr = ctx.getPackageManager();
        List<ResolveInfo> list =
                mgr.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_mapa_como_llegar);

        Bundle bundle = this.getIntent().getExtras();
        MiToolBar miToolBar = new MiToolBar(this, this);
        miToolBar.inicializarToolbar(R.menu.main, bundle.getString("nombre"));

        geo = new GeoLocalizacion(this);
        location = geo.updatePosicion();

        if (location != null) {
            float lat_empresa = bundle.getFloat("latitud");
            float long_empresa = bundle.getFloat("longitud");
            Intent mapIntent = null;
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + lat_empresa + "," + long_empresa + "");
            mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            if (mapIntent != null && isAvailable(this, mapIntent)) {/////SI TIENE NAVIGATOR LO LANZO
                startActivity(mapIntent);
            } else {////////SINO CREO LA ACTIVITY CON EL MAPA DE GOOGLE
                mimapa = (WebView) findViewById(R.id.wVMimapa);
                mimapa.setWebViewClient(new MyWebViewClient());
                progress = (ProgressBar) findViewById(R.id.progressBar);
                progress.setMax(100);
                latori = geo.getLat();
                lngori = geo.getLng();
                WebSettings webSettings = mimapa.getSettings();
                webSettings.setJavaScriptEnabled(true);
                mimapa.loadUrl("https://www.google.es/maps/dir/" + latori + "," + lngori + "/" + lat_empresa + "," + long_empresa + "/");
                MapaComoLLegar.this.progress.setProgress(0);
            }
        } else//////////SINO CARGAMOS EL MAPA CON AL DIRECCION SIMPLEMENTE
        {
            Toast.makeText(this, "No conocemos tu ubicación exacta", Toast.LENGTH_SHORT).show();
        }

    }

    private class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            progress.setVisibility(View.GONE);
            MapaComoLLegar.this.progress.setProgress(100);
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progress.setVisibility(View.VISIBLE);
            MapaComoLLegar.this.progress.setProgress(0);
            super.onPageStarted(view, url, favicon);
        }
    }
}