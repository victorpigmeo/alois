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
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.concurrent.TimeUnit;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.application.api.patient.PatientClient;
import br.com.alois.aloismobile.application.preference.GeneralPreferences_;
import br.com.alois.aloismobile.application.preference.ServerConfiguration;
import br.com.alois.aloismobile.application.util.jackson.JacksonDecoder;
import br.com.alois.aloismobile.application.util.jackson.JacksonEncoder;
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
    private GoogleApiClient googleApiClient;

    @Pref
    GeneralPreferences_ generalPreferences;

    public LastLocationService()
    {
        super("LastLocationService");
    }

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
                System.out.println("Chama!");
                TimeUnit.MINUTES.sleep(5);
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

        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(this.googleApiClient);

        if (lastLocation != null)
        {
            PatientClient routeClient = Feign.builder()
                    .encoder(new JacksonEncoder())
                    .decoder(new JacksonDecoder())
                    .target(PatientClient.class, ServerConfiguration.API_ENDPOINT);

            Point lastLocationPoint = new Point(lastLocation.getLatitude(), lastLocation.getLongitude());
            try
            {
                routeClient.updateLastLocation(
                        lastLocationPoint,
                        this.generalPreferences.loggedUserId().get(),
                        this.generalPreferences.loggedUserAuthToken().get()
                );
                this.updateLastLocationHandleSuccess();
            }
            catch (FeignException e)
            {
                e.printStackTrace();
            }
        }

    }

    @UiThread
    public void updateLastLocationHandleSuccess()
    {
        Toast.makeText(this.getApplicationContext(), "Posição Atualizada", Toast.LENGTH_SHORT).show();
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
}
