package br.com.alois.aloismobile.ui.view.route.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.maps.model.LatLng;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.application.preference.GeneralPreferences_;
import br.com.alois.aloismobile.ui.view.home.PatientHomeActivity;
import br.com.alois.aloismobile.ui.view.patient.PatientDetailActivity;
import br.com.alois.aloismobile.ui.view.route.adapter.RouteListAdapter;
import br.com.alois.domain.entity.route.Route;
import br.com.alois.domain.entity.route.Step;
import br.com.alois.domain.entity.user.Patient;
import br.com.alois.domain.entity.user.UserType;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_route_list)
public class RouteListFragment extends Fragment
{
    //=====================================ATTRIBUTES=======================================

    @ViewById(R.id.routeList)
    ListView routeList;

    @FragmentArg("patient")
    Patient patient;

    @ViewById(R.id.fab_add_route)
    FloatingActionButton fabAddRoute;

    RouteFormFragment routeFormFragment;
    //======================================================================================

    //=====================================INJECTIONS=======================================
    @Bean
    RouteListAdapter routeListAdapter;

    @Pref
    GeneralPreferences_ generalPreferences;

    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public RouteListFragment()
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
        this.routeList.setAdapter(this.routeListAdapter);

        if(this.generalPreferences.loggedUserType().get().equals(UserType.CAREGIVER.ordinal()))
        {
            ((PatientDetailActivity) this.getActivity()).listPatientroutes(this.patient.getId());
        }
        else
        {
            ((PatientHomeActivity) this.getActivity()).listPatientRoutes(this.patient.getId());
            this.fabAddRoute.setVisibility(View.INVISIBLE);
        }
    }

    @Click(R.id.fab_add_route)
    public void onFabAddRouteClick()
    {
        this.routeFormFragment = RouteFormFragment_.builder()
                .patient(this.patient)
                .build();

        this.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.patientDetailFrame, this.routeFormFragment)
                .addToBackStack("routeFormFragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @ItemClick(R.id.routeList)
    public void onRouteListItemClick(Route route)
    {
        RouteDetailFragment routeDetailFragment = RouteDetailFragment_
                .builder()
                .route(route)
                .build();

        if(this.generalPreferences.loggedUserType().get().equals(UserType.CAREGIVER.ordinal()))
        {
            this.getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.patientDetailFrame, routeDetailFragment)
                    .addToBackStack("routeDetailFragment")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
        else
        {
            this.getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.patient_home_frame_layout, routeDetailFragment)
                    .addToBackStack("routeDetailFragment")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
    }

    public void setPatientRouteList(List<Route> patientRouteList)
    {
        this.routeListAdapter.setRouteList(patientRouteList);
    }

    public void drawRouteFormPolyline(List<LatLng> line)
    {
        this.routeFormFragment.drawRouteFormPolyline(line);
    }

    public void setRouteSteps(List<Step> routeSteps)
    {
        this.routeFormFragment.setRouteSteps(routeSteps);
    }

    public void onAddRoute(Route route) {
        this.routeListAdapter.onAddRoute(route);
    }

    public void onUpdateRoute(Route route)
    {
        this.routeListAdapter.onUpdateRoute(route);
    }

    public void setRouteFormFragment(RouteFormFragment routeFormFragment)
    {
        this.routeFormFragment = routeFormFragment;
    }

    public void onDeleteRoute(Route route)
    {
        this.routeListAdapter.onDeleteRoute(route);
    }
    //======================================================================================

}
