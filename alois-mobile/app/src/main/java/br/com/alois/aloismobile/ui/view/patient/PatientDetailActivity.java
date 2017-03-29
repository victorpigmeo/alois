package br.com.alois.aloismobile.ui.view.patient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InjectMenu;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.application.api.route.RouteTasks;
import br.com.alois.aloismobile.ui.view.login.LoginActivity;
import br.com.alois.aloismobile.ui.view.patient.fragment.PatientDetailFragment;
import br.com.alois.aloismobile.ui.view.patient.fragment.PatientDetailFragment_;
import br.com.alois.aloismobile.ui.view.route.fragment.RouteListFragment;
import br.com.alois.aloismobile.ui.view.route.fragment.RouteListFragment_;
import br.com.alois.domain.entity.route.Route;
import br.com.alois.domain.entity.route.Step;
import br.com.alois.domain.entity.user.Patient;

@EActivity(R.layout.activity_patient_detail)
@OptionsMenu(R.menu.patient_detail_popup_menu)
public class PatientDetailActivity extends AppCompatActivity
{
    //=====================================ATTRIBUTES=======================================
    public ProgressDialog progressDialog;

    RouteListFragment routeListFragment;
    //======================================================================================

    //=====================================INJECTIONS=======================================
    @Extra("patient")
    Patient patient;

    @InjectMenu
    Menu menu;

    @NonConfigurationInstance
    @Bean
    RouteTasks routeTasks;
    //======================================================================================

    //====================================CONSTRUCTORS======================================

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @AfterViews
    public void onAfterViews()
    {
        final PatientDetailFragment patientDetailFragment = PatientDetailFragment_
                .builder()
                .patient(patient)
                .build();

        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.patientDetailFrame, patientDetailFragment)
                .addToBackStack("patientDetailFragment")
                .commit();
    }

    @OptionsItem(R.id.patientDetailRoutesMenuButton)
    public void onPatientRoutesClick()
    {
        if(this.getSupportFragmentManager().getBackStackEntryCount() > 1 &&
                this.getSupportFragmentManager().getBackStackEntryAt(this.getSupportFragmentManager().getBackStackEntryCount() - 1).getName().equals("routeListFragment"))
        {
            System.out.println("mesmo fragment");
            return;
        }
        else {
            this.routeListFragment = RouteListFragment_
                    .builder()
                    .patient(this.patient)
                    .build();

            this.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.patientDetailFrame, routeListFragment)
                    .addToBackStack("routeListFragment")
                    .commit();
        }
    }

    public void listPatientroutes(Long patientId)
    {
        this.progressDialog = ProgressDialog.show(this,
                super.getString(R.string.loading_patients),
                super.getString(R.string.please_wait),
                true,//is indeterminate
                true//is cancelable
        );

        this.routeTasks.listRoutesByPatientId(patientId);
    }

    public void setPatientRouteList(List<Route> patientRouteList)
    {
        this.routeListFragment.setPatientRouteList(patientRouteList);
    }

    public void generateGoogleRoute(List<LatLng> points)
    {
        this.progressDialog = ProgressDialog.show(this,
                super.getString(R.string.loading_route),
                super.getString(R.string.please_wait),
                true,//is indeterminate
                false//is cancelable
        );

        this.routeTasks.generateGoogleRoute(points);
    }

    public void drawRouteFormPolyline(List<LatLng> line)
    {
        this.routeListFragment.drawRouteFormPolyline(line);
    }

    public void setRouteSteps(List<Step> routeSteps)
    {
        this.routeListFragment.setRouteSteps(routeSteps);
    }

    public void addRoute(Route route)
    {
        this.progressDialog = ProgressDialog.show(this,
                super.getString(R.string.saving_route),
                super.getString(R.string.please_wait),
                true,//is indeterminate
                true//is cancelable
        );

        this.routeTasks.addRoute(route);
    }

    public void onAddRoute(Route route) {
        this.routeListFragment.onAddRoute(route);
    }

    public void editRoute(Route route) {
        this.progressDialog = ProgressDialog.show(this,
                super.getString(R.string.saving_route),
                super.getString(R.string.please_wait),
                true,//is indeterminate
                true//is cancelable
        );

        this.routeTasks.updateRoute(route);
    }

    public void onUpdateRoute(Route route)
    {
        this.routeListFragment.onUpdateRoute(route);
    }

    @Override
    public void onBackPressed()
    {
        int fragments = this.getSupportFragmentManager().getBackStackEntryCount();

        if (fragments > 1)
        {
            getSupportFragmentManager().popBackStack();
        }
        else
        {
            finish();
        }
    }
    //======================================================================================
}
