package br.com.alois.aloismobile.application.api.memory;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

import br.com.alois.aloismobile.application.api.caregiver.CaregiverClient;
import br.com.alois.aloismobile.application.preference.GeneralPreferences_;
import br.com.alois.aloismobile.application.preference.ServerConfiguration;
import br.com.alois.api.jackson.JacksonDecoder;
import br.com.alois.api.jackson.JacksonEncoder;
import br.com.alois.aloismobile.ui.view.home.AdministratorHomeActivity;
import br.com.alois.aloismobile.ui.view.home.PatientHomeActivity;
import br.com.alois.domain.entity.memory.Memory;
import br.com.alois.domain.entity.user.Caregiver;
import feign.Feign;
import feign.FeignException;

/**
 * Created by thiago on 08/04/17.
 */
@EBean
public class MemoryTasks {

    @Pref
    GeneralPreferences_ generalPreferences;

    @RootContext
    PatientHomeActivity patientHomeActivity;

    @Background
    public void getMemoryList(Long patientId)
    {
        MemoryClient memoryClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(MemoryClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            getMemoryListHandleSuccess( memoryClient.listMemoryByPatient(patientId  ,this.generalPreferences.loggedUserAuthToken().get()));
        }
        catch(FeignException e)
        {
            e.printStackTrace();
        }
    }

    @UiThread
    public void getMemoryListHandleSuccess(List<Memory> memoryList)
    {
        this.patientHomeActivity.progressDialog.dismiss();
        this.patientHomeActivity.patientHomeFragment.memoryListFragment.setPatientMemoryList(memoryList);
    }
}
