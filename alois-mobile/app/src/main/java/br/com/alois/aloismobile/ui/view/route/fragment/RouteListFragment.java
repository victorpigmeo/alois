package br.com.alois.aloismobile.ui.view.route.fragment;


import android.support.v4.app.Fragment;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.ui.view.patient.PatientDetailActivity;
import br.com.alois.aloismobile.ui.view.route.adapter.RouteListAdapter;
import br.com.alois.domain.entity.route.Route;
import br.com.alois.domain.entity.route.Step;
import br.com.alois.domain.entity.user.Patient;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_route_list)
public class RouteListFragment extends Fragment
{
    //=====================================ATTRIBUTES=======================================
    @ViewById(R.id.routeListPatientName)
    TextView routeListPatientName;

    @ViewById(R.id.routeList)
    ListView routeList;

    @FragmentArg("patient")
    Patient patient;

    RouteFormFragment routeFormFragment;
    //======================================================================================

    //=====================================INJECTIONS=======================================
    @Bean
    RouteListAdapter routeListAdapter;

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
        this.routeListPatientName.setText(this.patient.getName());

        this.routeList.setAdapter(this.routeListAdapter);

        ((PatientDetailActivity) this.getActivity()).listPatientroutes(this.patient.getId());
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
                .commit();
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
    //======================================================================================

}
