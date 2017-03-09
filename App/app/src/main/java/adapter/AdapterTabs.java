package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import tabfarmacia.TabDepartamentosFarmacia;
import tabfarmacia.TabInicioFarmacia;

/**
 * Created by Tri_2 on 02/09/2015.
 */
public class
AdapterTabs extends FragmentStatePagerAdapter{
    CharSequence Titles[];
    int NumbOfTabs;
    long idF;

    public AdapterTabs(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb,long idF) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.idF = idF;
    }


    @Override
    public Fragment getItem(int position) {
        if(position == 0) // if the position is 0 we are returning the First tab
        {
            TabInicioFarmacia tabInicioFarmacia = new TabInicioFarmacia();
            tabInicioFarmacia.setIdF(idF);
            return tabInicioFarmacia;
        }
        else           // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            TabDepartamentosFarmacia tabDepartamentosFarmacia = new TabDepartamentosFarmacia();
            tabDepartamentosFarmacia.setIdF(idF);
            return tabDepartamentosFarmacia;
        }
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
