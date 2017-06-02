package br.com.alois.aloismobile.application.api.request;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

import br.com.alois.aloismobile.application.preference.GeneralPreferences_;
import br.com.alois.aloismobile.application.preference.ServerConfiguration;
import br.com.alois.aloismobile.ui.view.patient.PatientDetailActivity;
import br.com.alois.api.jackson.JacksonDecoder;
import br.com.alois.api.jackson.JacksonEncoder;
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
    //======================================================================================
}
