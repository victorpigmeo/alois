package br.com.alois.aloismobile.ui.view.home.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.ui.view.home.adapter.CaregiverListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_administrator_home)
public class AdministratorHomeFragment extends Fragment
{
    //=====================================ATTRIBUTES=======================================

    @ViewById(R.id.administratorHomeCaregiverList)
    ListView administratorHomeCaregiverList;

    //======================================================================================

    //=====================================INJECTIONS=======================================
    @Bean
    CaregiverListAdapter caregiverListAdapter;
    //======================================================================================

    //====================================CONSTRUCTORS======================================
        public AdministratorHomeFragment()
        {
            // Required empty public constructor
        }

    //======================================================================================

    //==================================GETTERS/SETTERS=====================================

    //======================================================================================

    //=====================================BEHAVIOUR========================================

    @AfterViews
    public void onAfterViews()
    {

    }
    //======================================================================================



}
