package br.com.alois.aloismobile.ui.view.maps.fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.ui.view.patient.PatientDetailActivity;
import br.com.alois.domain.entity.route.AloisLatLng;
import br.com.alois.domain.entity.route.Step;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_route_form_map)
public class RouteFormMapFragment extends Fragment implements
        OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener
{
    //=====================================ATTRIBUTES=======================================
    GoogleMap map;

    List<LatLng> points = new ArrayList<LatLng>();

    List<Step> steps = new ArrayList<Step>();

    Polyline polyline;
    //======================================================================================

    //=====================================INJECTIONS=======================================

    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public RouteFormMapFragment()
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
        ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.routeFormMap)).getMapAsync(this);

        try
        {
            MapsInitializer.initialize(this.getContext());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Click(R.id.routeFormMapClearButton)
    public void onRouteFormClearButtonClick()
    {
        this.points = new ArrayList<LatLng>();
        this.steps = new ArrayList<Step>();
        this.map.clear();
//        this.polyline.remove();
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        this.map = googleMap;

        if (ActivityCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        this.map.setMyLocationEnabled(true);
        this.map.setOnMapLongClickListener(this);
        if(this.steps != null && this.steps.size() != 0)
        {
            this.drawRouteFormPolyline(this.points);
            LatLng initialPosition = new LatLng(this.steps.get(0).getStartPoint().getLatitude(), this.steps.get(0).getStartPoint().getLongitude());
            this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(initialPosition, 15));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onMapLongClick(LatLng latLng)
    {
        this.map.addMarker(new MarkerOptions().position(latLng));
        this.points.add(latLng);

        if(this.points.size() > 1)
        {
            ((PatientDetailActivity) this.getActivity()).generateGoogleRoute(this.points);
        }
    }

    public void drawRouteFormPolyline(List<LatLng> line)
    {
        if(this.polyline != null)
        {
            this.polyline.remove();
        }

        this.polyline = this.map.addPolyline(new PolylineOptions().addAll(line));
    }

    public void setSteps(List<Step> steps)
    {
        this.steps = steps;
    }

    public List<Step> getSteps()
    {
        return this.steps;
    }

    public void setPoints(List<LatLng> points) {
        this.points = points;
    }

    //======================================================================================


}
