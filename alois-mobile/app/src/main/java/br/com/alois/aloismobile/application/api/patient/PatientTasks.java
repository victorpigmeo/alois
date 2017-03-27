package br.com.alois.aloismobile.application.api.patient;

import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.application.preference.GeneralPreferences_;
import br.com.alois.aloismobile.application.preference.ServerConfiguration;
import br.com.alois.aloismobile.application.util.jackson.JacksonDecoder;
import br.com.alois.aloismobile.application.util.jackson.JacksonEncoder;
import br.com.alois.aloismobile.ui.view.home.CaregiverHomeActivity;
import br.com.alois.domain.entity.user.Patient;
import feign.Feign;
import feign.FeignException;

/**
 * Created by victor on 3/17/17.
 */
@EBean
public class PatientTasks
{
    @Pref
    GeneralPreferences_ generalPreferences;

    @RootContext
    CaregiverHomeActivity caregiverHomeActivity;

    @Background
    public void listPatientsByCaregiverIdAsyncTask(Long caregiverId)
    {
        PatientClient patientClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(PatientClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            this.listPatientsByCaregiverIdHandleSuccess(patientClient.listPatientsByCaregiverId(caregiverId, this.generalPreferences.loggedUserAuthToken().get()));
        }
        catch(FeignException e)
        {
            e.printStackTrace();
            this.listPatientsByCaregiverIdHandleFail(e.getMessage());
        }
    }

    @UiThread
    public void listPatientsByCaregiverIdHandleSuccess(List<Patient> patients)
    {
        this.caregiverHomeActivity.setPatientList(patients);
        this.caregiverHomeActivity.progressDialog.dismiss();
    }

    @UiThread
    public void listPatientsByCaregiverIdHandleFail(String message)
    {
        this.caregiverHomeActivity.progressDialog.dismiss();
        System.out.println(message);
    }

    @Background
    public void addPatient(Patient patient)
    {
        PatientClient patientClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(PatientClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            this.addPatientHandleSuccess( patientClient.addPatient( patient, this.generalPreferences.loggedUserAuthToken().get() ) );
        }
        catch(FeignException e)
        {
            e.printStackTrace();
            this.addPatientHandleFail(e.getMessage());
        }
    }

    @UiThread
    public void addPatientHandleSuccess(Patient patient)
    {
        this.caregiverHomeActivity.progressDialog.dismiss();
        if(patient != null)
        {
            this.caregiverHomeActivity.onInsertPatient(patient);
            this.caregiverHomeActivity.getSupportFragmentManager()
                    .popBackStack();
        }
        else
        {
            Toast.makeText(this.caregiverHomeActivity, this.caregiverHomeActivity.getResources().getString(R.string.username_already_exists), Toast.LENGTH_SHORT).show();
        }
    }

    @UiThread
    public void addPatientHandleFail(String message)
    {
        this.caregiverHomeActivity.progressDialog.dismiss();
        Toast.makeText(this.caregiverHomeActivity, this.caregiverHomeActivity.getResources().getString(R.string.patient_insert_error), Toast.LENGTH_SHORT).show();
        System.out.println(message);
    }
}
