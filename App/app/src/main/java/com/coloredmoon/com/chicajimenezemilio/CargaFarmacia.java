package com.coloredmoon.com.chicajimenezemilio;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import adapter.AdapterTabs;
import utilidades.MiToolBar;
import utilidades.SlidingTabLayout;
import com.coloredmoon.com.chicajimenezemilio.R;
public class CargaFarmacia extends AppCompatActivity {
    ViewPager pager;
    AdapterTabs adapterTabs;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Inicio","Departamentos"};
    int Numboftabs =2;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_farmacia);
        MiToolBar toolBar = new MiToolBar(this,this);
        toolBar.inicializarToolbar(R.menu.main,"FARMACIA");
        Bundle extras = this.getIntent().getExtras();

        adapterTabs = new AdapterTabs(getSupportFragmentManager(),Titles,Numboftabs,extras.getLong("idFarmacia"));
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapterTabs);
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        tabs.setViewPager(pager);
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

}
