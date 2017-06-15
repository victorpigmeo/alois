package br.com.alois.aloismobile.application.api.route;

import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.application.preference.GeneralPreferences_;
import br.com.alois.aloismobile.application.preference.ServerConfiguration;
import br.com.alois.aloismobile.ui.view.home.PatientHomeActivity;
import br.com.alois.aloismobile.ui.view.patient.PatientDetailActivity;
import br.com.alois.api.jackson.JacksonDecoder;
import br.com.alois.api.jackson.JacksonEncoder;
import br.com.alois.domain.entity.route.Point;
import br.com.alois.domain.entity.route.Route;
import br.com.alois.domain.entity.route.Step;
import br.com.alois.domain.entity.user.UserType;
import feign.Feign;
import feign.FeignException;

/**
 * Created by victor on 3/24/17.
 */
@EBean
public class RouteTasks
{
    //=====================================INJECTIONS=======================================
    @Pref
    GeneralPreferences_ generalPreferences;

    @RootContext
    PatientDetailActivity patientDetailActivity;
    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @Background
    public void listRoutesByPatientId(Long patientId, PatientHomeActivity patientHomeActivity)
    {
        RouteClient routeClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(RouteClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            listRoutesByPatientIdHandleSuccess(routeClient.listRoutesByPatientId(patientId, this.generalPreferences.loggedUserAuthToken().get()), patientHomeActivity);
        }
        catch (FeignException e)
        {
            listRoutesByPatientIdHandleFail(e.getMessage());
        }
    }

    @UiThread
    public void listRoutesByPatientIdHandleSuccess(List<Route> routes, PatientHomeActivity patientHomeActivity)
    {
        if (routes.size() != 0)
        {
            if(this.generalPreferences.loggedUserType().get().equals(UserType.CAREGIVER.ordinal()))
            {
                this.patientDetailActivity.setPatientRouteList(routes);
                this.patientDetailActivity.progressDialog.dismiss();
            }
            else
            {
                patientHomeActivity.setPatientRouteList(routes);
                patientHomeActivity.progressDialog.dismiss();
            }
        }else{
            Toast.makeText(this.patientDetailActivity, this.patientDetailActivity.getResources().getString(R.string.patient_does_not_have_routes), Toast.LENGTH_SHORT).show();
        }

    }

    @UiThread
    public void listRoutesByPatientIdHandleFail(String message)
    {
        System.out.println(message);
    }

    @Background
    public void generateGoogleRoute(List<LatLng> _points)
    {
        final List<LatLng> points = new ArrayList<>(_points);

        final String googleRouteBaseUrl = "https://maps.googleapis.com/maps/api/directions/";
        final Map<String, String> routeParams = new HashMap<String, String>();

        //general config of route
        routeParams.put("key", this.patientDetailActivity.getResources().getString(R.string.google_maps_key));
        routeParams.put("mode", "walking");

        //put origin in routeParams
        routeParams.put("origin", points.get(0).latitude+","+points.get(0).longitude);
        points.remove(0);

        //put destination in routeParams
        routeParams.put("destination", points.get(points.size() - 1).latitude+","+points.get(points.size() - 1).longitude);
        points.remove(points.size() - 1);

        if(points.size() != 0)
        {
            String waypoints = "";
            for (LatLng latLng : points) {
                waypoints += "via:"+latLng.latitude+ "," + latLng.longitude;
                if(points.indexOf(latLng) != (points.size() - 1)){
                    waypoints+= "|";
                }
            }
            routeParams.put("waypoints", waypoints);
        }

        try
        {
            RouteClient routeClient = Feign.builder()
                    .target(RouteClient.class, googleRouteBaseUrl);

            generateGoogleRouteHandleSuccess(routeClient.generateGoogleRoute(routeParams));
        }
        catch(FeignException e)
        {
            e.printStackTrace();
            generateGoogleRouteHandleFail(e.getMessage());
        }
    }

    @UiThread
    public void generateGoogleRouteHandleSuccess(String route)
    {
        try
        {
            final JSONObject responseObject = new JSONObject(route);
            final JSONArray routesArray = responseObject.getJSONArray("routes");
            final JSONObject routeObject = routesArray.getJSONObject(0);
            final JSONArray legs = routeObject.getJSONArray("legs");
            final JSONObject legsObject = legs.getJSONObject(0);
            final JSONArray steps = legsObject.getJSONArray("steps");

            final List<LatLng> line = new ArrayList<LatLng>();
            final List<Step> routeSteps = new ArrayList<Step>();
            int i = 0;

            for(i = 0; i < steps.length(); i++){
                JSONObject startLocation = steps.getJSONObject(i).getJSONObject("start_location");
                JSONObject endLocation = steps.getJSONObject(i).getJSONObject("end_location");

                LatLng stepLineStart = new LatLng(
                        Double.parseDouble(startLocation.getString("lat")),
                        Double.parseDouble(startLocation.getString("lng"))
                );

                line.add(stepLineStart);

                LatLng stepLineEnd = new LatLng(
                        Double.parseDouble(endLocation.getString("lat")),
                        Double.parseDouble(endLocation.getString("lng"))
                );

                line.add(stepLineEnd);

                Step step = new Step(new Point(stepLineStart.latitude, stepLineStart.longitude), new Point(stepLineEnd.latitude, stepLineEnd.longitude), i);
                routeSteps.add(step);
            }

            this.patientDetailActivity.setRouteSteps(routeSteps);
            this.patientDetailActivity.drawRouteFormPolyline(line);

            this.patientDetailActivity.progressDialog.dismiss();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    @UiThread
    public void generateGoogleRouteHandleFail(String message)
    {
        this.patientDetailActivity.progressDialog.dismiss();
        System.out.println(message);
        Toast.makeText(this.patientDetailActivity, this.patientDetailActivity.getResources().getString(R.string.error_default), Toast.LENGTH_SHORT).show();
    }

    @Background
    public void addRoute(Route route)
    {
        RouteClient routeClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(RouteClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            addRouteHandleSuccess( routeClient.addRoute( route, this.generalPreferences.loggedUserAuthToken().get() ) );
        }
        catch (FeignException e)
        {
            e.printStackTrace();
            addRouteHandleFail(e.getMessage());
        }
    }

    @UiThread
    public void addRouteHandleSuccess(Route route)
    {
        if(route != null)
        {
            this.patientDetailActivity.progressDialog.dismiss();

            this.patientDetailActivity.onAddRoute(route);
            this.patientDetailActivity.getSupportFragmentManager()
                    .popBackStack();
        }
    }

    @UiThread
    public void addRouteHandleFail(String message)
    {
        this.patientDetailActivity.progressDialog.dismiss();
        System.out.println(message);
        Toast.makeText(this.patientDetailActivity, this.patientDetailActivity.getResources().getString(R.string.error_default), Toast.LENGTH_SHORT).show();
    }

    @Background
    public void updateRoute(Route route)
    {
        RouteClient routeClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(RouteClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            updateRouteHandleSuccess(routeClient.updateRoute(route, this.generalPreferences.loggedUserAuthToken().get()));
        }
        catch(Exception e)
        {
            e.printStackTrace();
            updateRouteHandleFail(e.getMessage());
        }
    }

    @UiThread
    public void updateRouteHandleSuccess(Route route)
    {
        this.patientDetailActivity.progressDialog.dismiss();
        this.patientDetailActivity.onUpdateRoute(route);
        this.patientDetailActivity.getSupportFragmentManager()
                .popBackStack();
    }

    @UiThread
    public void updateRouteHandleFail(String message)
    {
        this.patientDetailActivity.progressDialog.dismiss();
        System.out.println(message);
    }

    @Background
    public void deleteRoute(Route route)
    {
        RouteClient routeClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(RouteClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            routeClient.deleteRoute(route, this.generalPreferences.loggedUserAuthToken().get());
            deleteRouteHandleSuccess(route);
        }
        catch (FeignException e)
        {
            e.printStackTrace();
        }
    }

    @UiThread
    public void deleteRouteHandleSuccess(Route route)
    {
        this.patientDetailActivity.progressDialog.dismiss();
        this.patientDetailActivity.onDeleteRoute(route);
    }
    //======================================================================================

}
