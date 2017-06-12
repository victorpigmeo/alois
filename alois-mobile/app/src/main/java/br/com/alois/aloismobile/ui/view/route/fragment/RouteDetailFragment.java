package br.com.alois.aloismobile.ui.view.route.fragment;


import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.domain.entity.route.Route;
import br.com.alois.domain.entity.route.Step;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_route_detail)
public class RouteDetailFragment extends Fragment implements OnMapReadyCallback
{
    //=====================================ATTRIBUTES=======================================
    @ViewById(R.id.routeDetailName)
    TextView routeDetailName;

    @ViewById(R.id.routeDetailDescription)
    TextView routeDetailDescription;

    GoogleMap map;

    //======================================================================================

    //=====================================INJECTIONS=======================================
    @FragmentArg("route")
    Route route;

    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public RouteDetailFragment()
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
        this.routeDetailName.setText(this.route.getName());
        this.routeDetailDescription.setText(this.route.getDescription());

        ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.routeDetailMap)).getMapAsync(this);

        try
        {
            MapsInitializer.initialize(this.getContext());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Click(R.id.routeDetailBackButton)
    public void onRouteDetailBackButtonClick()
    {
        this.getActivity().getSupportFragmentManager()
                .popBackStack();
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        this.map = googleMap;

        if(this.route.getSteps() != null && this.route.getSteps().size() != 0)
        {
            this.drawRouteFormPolyline(this.route.getSteps());

            LatLng initialPosition = new LatLng(this.route.getSteps().get(0).getStartPoint().getLatitude(), this.route.getSteps().get(0).getStartPoint().getLongitude());
            this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(initialPosition, 15));
        }
    }

    public void drawRouteFormPolyline(List<Step> steps)
    {
        List<LatLng> line = new ArrayList<LatLng>();
        for(Step step: steps)
        {
            LatLng latLngStart = new LatLng(step.getStartPoint().getLatitude(), step.getStartPoint().getLongitude());
            line.add(latLngStart);
            LatLng latLngEnd = new LatLng(step.getEndPoint().getLatitude(), step.getEndPoint().getLongitude());
            line.add(latLngEnd);
        }

        this.map.addPolyline(new PolylineOptions().addAll(line));
    }
    //======================================================================================




}
