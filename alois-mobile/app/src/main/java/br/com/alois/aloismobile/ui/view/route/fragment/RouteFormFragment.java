package br.com.alois.aloismobile.ui.view.route.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.model.LatLng;
import com.mobsandgeeks.saripaar.AnnotationRule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.ValidateUsing;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.ui.view.maps.fragment.RouteFormMapFragment;
import br.com.alois.aloismobile.ui.view.maps.fragment.RouteFormMapFragment_;
import br.com.alois.aloismobile.ui.view.patient.PatientDetailActivity;
import br.com.alois.domain.entity.route.Route;
import br.com.alois.domain.entity.route.Step;
import br.com.alois.domain.entity.user.Patient;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_route_form)
public class RouteFormFragment extends Fragment implements Validator.ValidationListener
{
    //=====================================ATTRIBUTES=======================================
    @ViewById(R.id.routeFormEditName)
    @NotEmpty(messageResId = R.string.route_name_is_required)
    EditText routeFormEditName;

    @ViewById(R.id.routeFormEditDescription)
    EditText routeFormEditDescription;

    RouteFormMapFragment routeFormMapFragment;

    Validator validator = new Validator(this);
    //======================================================================================

    //=====================================INJECTIONS=======================================
    @FragmentArg("route")
    Route route;

    @FragmentArg("patient")
    Patient patient;
    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public RouteFormFragment()
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
        this.validator.setValidationListener(this);

        this.routeFormMapFragment = RouteFormMapFragment_.builder().build();

        this.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.routeAddFrame, this.routeFormMapFragment)
                .commit();
    }

    @Click(R.id.routeFormSaveButton)
    public void onRouteFormSaveButtonClick()
    {
        List<Step> routeSteps = this.routeFormMapFragment.getSteps();

        if(routeSteps.size() > 0)
        {
            if(this.route == null)
            {
                this.route = new Route();
            }

            this.route.setSteps(routeSteps);
            this.validator.validate();
        }
        else
        {
            Toast.makeText(this.getContext(), this.getActivity().getResources().getString(R.string.select_2_or_more_points), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onValidationSucceeded()
    {
        this.route.setName(this.routeFormEditName.getText().toString());
        this.route.setDescription(this.routeFormEditDescription.getText().toString());
        //TODO verificar porque o id do paciente nao está indo
        this.route.setPatient( this.patient );

        ((PatientDetailActivity) this.getActivity()).addRoute(route);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors)
    {
        for (ValidationError error : errors)
        {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this.getContext());

            // Display error messages ;)
            if (view instanceof EditText)
            {
                ((EditText) view).setError(message);
            }
            else
            {
                Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void drawRouteFormPolyline(List<LatLng> line)
    {
        this.routeFormMapFragment.drawRouteFormPolyline(line);
    }

    public void setRouteSteps(List<Step> routeSteps)
    {
        this.routeFormMapFragment.setSteps(routeSteps);
    }
    //======================================================================================



}
