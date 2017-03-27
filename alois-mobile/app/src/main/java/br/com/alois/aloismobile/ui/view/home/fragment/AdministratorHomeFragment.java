package br.com.alois.aloismobile.ui.view.home.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.ui.view.home.adapter.CaregiverListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_administrator_home)
public class AdministratorHomeFragment extends Fragment
{
    //=====================================ATTRIBUTES=======================================

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

    //======================================================================================



}
