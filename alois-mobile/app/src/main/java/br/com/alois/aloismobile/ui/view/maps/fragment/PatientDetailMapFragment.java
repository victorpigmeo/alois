package br.com.alois.aloismobile.ui.view.maps.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import br.com.alois.aloismobile.R;
import br.com.alois.domain.entity.user.Patient;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_patient_detail_map)
public class PatientDetailMapFragment extends Fragment implements OnMapReadyCallback
{
    //=====================================ATTRIBUTES=======================================
    @FragmentArg("patient")
    Patient patient;

    GoogleMap map;
    //======================================================================================

    //=====================================INJECTIONS=======================================

    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public PatientDetailMapFragment()
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
        ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.patientDetailMap)).getMapAsync(this);

        try {
            MapsInitializer.initialize(this.getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        this.map = googleMap;

        if(this.patient.getLastLocation() != null)
        {
            LatLng patientLastLocation = new LatLng(this.patient.getLastLocation().getLatitude(), this.patient.getLastLocation().getLongitude());

            this.map.addMarker(new MarkerOptions().position(patientLastLocation).title(this.patient.getName()));
            this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(patientLastLocation, 15));
        }
        else
        {
            Toast.makeText(this.getContext(), this.getActivity().getResources().getString(R.string.patient_without_last_location), Toast.LENGTH_SHORT).show();
        }
    }
    //======================================================================================



}
