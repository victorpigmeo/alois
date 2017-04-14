package br.com.alois.aloismobile.application.service;

import android.Manifest;
import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.concurrent.TimeUnit;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.application.api.patient.PatientClient;
import br.com.alois.aloismobile.application.preference.GeneralPreferences_;
import br.com.alois.aloismobile.application.preference.ServerConfiguration;
import br.com.alois.api.jackson.JacksonDecoder;
import br.com.alois.api.jackson.JacksonEncoder;
import br.com.alois.domain.entity.route.Point;
import feign.Feign;
import feign.FeignException;

/**
 * Created by victor on 05/04/17.
 */
@EService
public class LastLocationService extends IntentService implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
{
    //=====================================ATTRIBUTES=======================================
    private GoogleApiClient googleApiClient;

    Location lastLocation;

    private LocationRequest locationRequest;

    private LocationSettingsRequest locationSettingsRequest;

    LocationListener locationListener;

    //======================================================================================

    //=====================================INJECTIONS=======================================
    @Pref
    GeneralPreferences_ generalPreferences;

    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public LastLocationService()
    {
        super("LastLocationService");
    }

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @Override
    protected void onHandleIntent(@Nullable Intent intent)
    {
        // Create an instance of GoogleAPIClient.
        if (this.googleApiClient == null)
        {
            this.googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        //Connect to google play services
        this.googleApiClient.connect();

        this.locationRequest = new LocationRequest();
        this.locationRequest.setInterval(5000); //Set the prefered interval this can suffer influence of the availability of the providers
        this.locationRequest.setFastestInterval(5000);//Set the fastest interval
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(this.locationRequest);
        this.locationSettingsRequest = builder.build();

        try
        {
            Notification.Builder notification = new Notification.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher))
                    .setContentTitle(this.getResources().getString(R.string.alois))
                    .setContentText(this.getResources().getString(R.string.alois_tracking_active));

            this.startForeground(1, notification.build());
            while (true)
            {
                this.updateLastLocation();
                //Sets the time between verifications
                TimeUnit.SECONDS.sleep(30);
            }
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Background
    public void updateLastLocation()
    {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }

        this.locationListener = new LocationListener()
        {
            @Override
            public void onLocationChanged(Location location)
            {
                lastLocation = location;
            }

        };

        LocationServices.SettingsApi.checkLocationSettings(
                this.googleApiClient,
                this.locationSettingsRequest
        ).setResultCallback(new ResultCallback<LocationSettingsResult>()
                            {
                                @Override
                                public void onResult(LocationSettingsResult locationSettingsResult)
                                {
                                    final Status status = locationSettingsResult.getStatus();
                                    switch (status.getStatusCode())
                                    {
                                        case 0:
                                            try
                                            {
                                                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, locationListener);
                                            }catch (SecurityException e)
                                            {
                                                e.printStackTrace();
                                            }
                                            break;
                                    }
                                }
                            });

        if (this.lastLocation != null)
        {
            PatientClient routeClient = Feign.builder()
                    .encoder(new JacksonEncoder())
                    .decoder(new JacksonDecoder())
                    .target(PatientClient.class, ServerConfiguration.API_ENDPOINT);

            Point lastLocationPoint = new Point(this.lastLocation.getLatitude(), this.lastLocation.getLongitude());
            String teste  = "LastKnow:"+this.lastLocation.getLatitude()+"|"+this.lastLocation.getLongitude();
            try
            {
                routeClient.updateLastLocation(
                        lastLocationPoint,
                        this.generalPreferences.loggedUserId().get(),
                        this.generalPreferences.loggedUserAuthToken().get()
                );
                this.updateLastLocationHandleSuccess(teste);
            }
            catch (FeignException e)
            {
                e.printStackTrace();
                this.updateLastLocationHandleFail();
            }
        }

    }

    @UiThread
    public void updateLastLocationHandleSuccess(String teste)
    {
        Log.i("ALOIS-LOCATION", "Alois patient last location updated!");
        Toast.makeText(this.getApplicationContext(), teste, Toast.LENGTH_SHORT).show();
    }

    @UiThread
    public void updateLastLocationHandleFail()
    {
        Toast.makeText(this.getApplicationContext(), "Erro ao atualizar localização do paciente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        Toast.makeText(this.getApplicationContext(), this.getResources().getString(R.string.google_play_services_connected), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i)
    {
        Toast.makeText(this.getApplicationContext(), this.getResources().getString(R.string.google_play_services_suspended), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        Toast.makeText(this.getApplicationContext(), this.getResources().getString(R.string.google_play_services_connection_failed), Toast.LENGTH_SHORT).show();
    }
    //======================================================================================

}
