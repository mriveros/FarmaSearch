package com.coloredmoon.com.chicajimenezemilio;


import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.widget.TextView;
import utilidades.MiToolBar;
import com.coloredmoon.com.chicajimenezemilio.R;


public class CargaContacto extends AppCompatActivity {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_carga_contacto);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		MiToolBar miToolBar = new MiToolBar(this,this);
		miToolBar.inicializarToolbar(R.menu.main);

		TextView textView = (TextView) findViewById(R.id.textView1);
		SpannableString sa = new SpannableString(textView.getText());

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			textView.setCompoundDrawablesWithIntrinsicBounds(
					android.R.drawable.ic_menu_call, 0, 0, 0);
		}
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
	}


}
