package br.com.alois.aloismobile.application.api.request;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.application.preference.GeneralPreferences_;
import br.com.alois.aloismobile.application.preference.ServerConfiguration;
import br.com.alois.aloismobile.ui.view.home.PatientHomeActivity;
import br.com.alois.aloismobile.ui.view.login.LoginActivity;
import br.com.alois.aloismobile.ui.view.patient.PatientDetailActivity;
import br.com.alois.api.jackson.JacksonDecoder;
import br.com.alois.api.jackson.JacksonEncoder;
import br.com.alois.domain.entity.user.Patient;
import br.com.alois.domain.entity.user.Request;
import feign.Feign;
import feign.FeignException;

/**
 * Created by victor on 6/1/17.
 */
@EBean
public class RequestTasks
{
    //=====================================INJECTIONS=======================================
    @Pref
    GeneralPreferences_ generalPreferences;

    @RootContext
    PatientDetailActivity patientDetailActivity;

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @Background
    public void listLogoffRequestsByPatientId(Long patientId)
    {
        RequestClient requestClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(RequestClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            this.listRequestsByPatientIdHandleSuccess(requestClient.listLogoffRequestsByPatientId(patientId, this.generalPreferences.loggedUserAuthToken().get()));
        }
        catch(FeignException e)
        {
            e.printStackTrace();
        }
    }

    @UiThread
    public void listRequestsByPatientIdHandleSuccess(List<Request> requests)
    {
        this.patientDetailActivity.setPatientLogoffRequests( requests );

        this.patientDetailActivity.progressDialog.dismiss();
    }

    @Background
    public void approveLogoffRequest(Request request)
    {
        RequestClient requestClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(RequestClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            this.approveLogoffRequestHandleSuccess(requestClient.approveLogoffRequest(request, this.generalPreferences.loggedUserAuthToken().get()));
        }
        catch(FeignException e)
        {
            e.printStackTrace();
        }
    }

    @UiThread
    public void approveLogoffRequestHandleSuccess(Request request)
    {
        this.patientDetailActivity.progressDialog.dismiss();
    }

    @Background
    public void discardLogoffRequest(Request request)
    {
        RequestClient requestClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(RequestClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            this.discardLogoffRequestHandleSuccess(requestClient.discardLogoffRequest(request, this.generalPreferences.loggedUserAuthToken().get()));
        }
        catch(FeignException e)
        {
            e.printStackTrace();
        }
    }

    @UiThread
    public void discardLogoffRequestHandleSuccess(Request request)
    {
        this.patientDetailActivity.progressDialog.dismiss();
    }

    @Background
    public void requestLogoff(Request request, PatientHomeActivity rootContext)
    {
        RequestClient requestClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(RequestClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            this.requestLogoffHandleSuccess(requestClient.requestLogoff(request, this.generalPreferences.loggedUserAuthToken().get()), rootContext);
        }
        catch(FeignException e)
        {
            requestLogoffHandleFail(e, rootContext);
        }
    }

    @UiThread
    public void requestLogoffHandleFail(FeignException e, PatientHomeActivity rootContext)
    {
        rootContext.progressDialog.dismiss();

        String contentString = e.getMessage().split("content:\n")[1];
        try
        {
            JSONObject content = new JSONObject(contentString);
            Toast.makeText(rootContext, content.getString("message"), Toast.LENGTH_SHORT).show();
        }
        catch (JSONException e1)
        {
            e1.printStackTrace();
        }

    }

    @UiThread
    public void requestLogoffHandleSuccess(Request request, PatientHomeActivity rootContext)
    {
        rootContext.progressDialog.dismiss();

        if(request != null)
        {
            Toast.makeText(rootContext, rootContext.getResources().getString(R.string.logoff_requested), Toast.LENGTH_SHORT).show();
        }
    }

    @Background
    public void getPatientLogoffApprovedRequest(Long patientId, PatientHomeActivity rootContext)
    {
        RequestClient requestClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(RequestClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            this.getPatientLogoffApprovedRequestHandleSuccess(requestClient.getPatientLogoffApprovedRequest(patientId, this.generalPreferences.loggedUserAuthToken().get()), rootContext);
        }
        catch(FeignException e)
        {
            e.printStackTrace();
        }
    }

    @UiThread
    public void getPatientLogoffApprovedRequestHandleSuccess(Request patientLogoffApprovedRequest, PatientHomeActivity rootContext)
    {
        if(patientLogoffApprovedRequest != null)
        {
            rootContext.showLogoffButton();
        }
    }

    @Background
    public void updateUsedPatientLogoffRequest(Long patientId)
    {
        RequestClient requestClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(RequestClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            requestClient.updateUsedPatientLogoffRequest(patientId, this.generalPreferences.loggedUserAuthToken().get());
        }
        catch(FeignException e)
        {
            e.printStackTrace();
        }

        this.updateUsedPatientLogoffRequestHandleSuccess();
    }

    private void updateUsedPatientLogoffRequestHandleSuccess()
    {
        LoginActivity.clearUserData();
    }
    //======================================================================================
}
