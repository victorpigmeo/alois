package br.com.alois.aloismobile.ui.view.requests.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 5/31/17.
 */
public class RequestsPagerAdapter extends FragmentPagerAdapter
{
    //=====================================ATTRIBUTES=======================================
    private List<Fragment> fragmentList = new ArrayList<Fragment>();

    private List<String> fragmentTitleList = new ArrayList<String>();
    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public RequestsPagerAdapter(FragmentManager manager)
    {
        super(manager);
    }

    //======================================================================================

    //==================================GETTERS/SETTERS=====================================

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @Override
    public Fragment getItem(int position)
    {
        return this.fragmentList.get(position);
    }

    @Override
    public int getCount()
    {
        return this.fragmentList.size();
    }

    public void addFragment(Fragment fragment, String title)
    {
        this.fragmentList.add(fragment);
        this.fragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return this.fragmentTitleList.get(position);
    }

    //======================================================================================
}
