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
import br.com.alois.aloismobile.ui.view.home.PatientHomeActivity;
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

    @RootContext
    PatientHomeActivity patientHomeActivity;

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

    @Background
    public void findById(Long patientId)
    {
        PatientClient patientClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(PatientClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            Patient pt = patientClient.findById(patientId, this.generalPreferences.loggedUserAuthToken().get());
            this.findHandleSuccess(pt);
        }
        catch(FeignException e)
        {
            e.printStackTrace();
            this.findHandleFail(e.getMessage());
        }
    }

    @UiThread
    public void findHandleSuccess(Patient patient)
    {
        this.patientHomeActivity.patientHomeFragment.setPatient(patient);
        this.patientHomeActivity.progressDialog.dismiss();
    }

    @UiThread
    public void findHandleFail(String message)
    {
        this.patientHomeActivity.progressDialog.dismiss();
        System.out.println(message);
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

        ObjectMapper objectMapper = new ObjectMapper();

        try
        {
            String jsonPatient = objectMapper.writeValueAsString(patient);
            System.out.println(jsonPatient);
            this.addPatientHandleSuccess( patientClient.addPatient( patient, this.generalPreferences.loggedUserAuthToken().get() ) );
        }
        catch(Exception e)
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

    @Background
    public void updatePatient(Patient patient) {
        PatientClient patientClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(PatientClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            updatePatientHandleSuccess( patientClient.updatePatient( patient, this.generalPreferences.loggedUserAuthToken().get() ) );
        }
        catch(FeignException e)
        {
            e.printStackTrace();
            updatePatientHandleFail(e.getMessage());
        }
    }

    @UiThread
    public void updatePatientHandleSuccess(Patient patient) {
        if(patient != null)
        {
            this.caregiverHomeActivity.progressDialog.dismiss();

            this.caregiverHomeActivity.onUpdatePatient(patient);

            this.caregiverHomeActivity.getSupportFragmentManager()
                    .popBackStack();
        }
    }

    @UiThread
    public void updatePatientHandleFail(String message)
    {
        this.caregiverHomeActivity.progressDialog.dismiss();
        Toast.makeText(this.caregiverHomeActivity, this.caregiverHomeActivity.getResources().getString(R.string.patient_update_error), Toast.LENGTH_SHORT).show();
        System.out.println(message);
    }

    @Background
    public void deletePatient(Patient patient) {
        PatientClient patientClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(PatientClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            patientClient.deletePatient(patient, this.generalPreferences.loggedUserAuthToken().get());
            deletePatientHandleSuccess(patient);
        }
        catch(FeignException e)
        {
            e.printStackTrace();
            deletePatientHandleFail(e.getMessage());
        }
    }

    @UiThread
    public void deletePatientHandleSuccess(Patient patient)
    {
        this.caregiverHomeActivity.progressDialog.dismiss();
        this.caregiverHomeActivity.onDeletePatient(patient);
    }

    @UiThread
    public void deletePatientHandleFail(String message)
    {
        this.caregiverHomeActivity.progressDialog.dismiss();
        Toast.makeText(this.caregiverHomeActivity, this.caregiverHomeActivity.getResources().getString(R.string.error_default), Toast.LENGTH_SHORT).show();
        System.out.println(message);
    }
}
