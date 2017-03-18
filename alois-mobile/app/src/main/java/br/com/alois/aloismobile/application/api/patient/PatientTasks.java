package br.com.alois.aloismobile.application.api.patient;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

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
        System.out.println(message);
    }
}
