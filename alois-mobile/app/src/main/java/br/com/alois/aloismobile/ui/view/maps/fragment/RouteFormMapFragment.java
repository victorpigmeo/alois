package br.com.alois.aloismobile.ui.view.maps.fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.widget.Toast;

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

        List<LatLng> pointsToGenerate = this.points;

        if(this.points.size() > 1)
        {
            ((PatientDetailActivity) this.getActivity()).generateGoogleRoute(pointsToGenerate);
        }
    }

    public List<Step> getSteps()
    {

        if(this.points.size() < 2)
        {
            Toast.makeText(this.getContext(), this.getActivity().getResources().getString(R.string.select_2_or_more_points), Toast.LENGTH_SHORT).show();
            return null;
        }
        else
        {
            List<Step> steps = new ArrayList<Step>();

            Step step = new Step();
            for(LatLng point: this.points)
            {
                if(step.getStartPoint() == null)
                {
                    step.setStartPoint(new AloisLatLng(point.latitude, point.longitude));
                }
                else
                {
                    if(step.getEndPoint() == null)
                    {
                        step.setEndPoint(new AloisLatLng(point.latitude, point.longitude));
                        steps.add(step);
                        step = new Step();
                        step.setStartPoint(new AloisLatLng(point.latitude, point.longitude));
                    }
                }
            }
            return steps;
        }

    }

    public void drawRouteFormPolyline(List<LatLng> line)
    {
        this.map.addPolyline(new PolylineOptions().addAll(line));
    }

    //======================================================================================


}
