package br.com.alois.aloismobile.application.api.caregiver;

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
import br.com.alois.aloismobile.ui.view.home.AdministratorHomeActivity;
import br.com.alois.domain.entity.user.Caregiver;
import feign.Feign;
import feign.FeignException;

/**
 * Created by sarah on 3/29/17.
 */
@EBean
public class CaregiverTasks
{
    @Pref
    GeneralPreferences_ generalPreferences;

    @RootContext
    AdministratorHomeActivity administratorHomeActivity;

    @Background
    public void getCaregiverList()
    {
        CaregiverClient caregiverClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(CaregiverClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            getCaregiverListHandleSucess( caregiverClient.getCaregiverList( this.generalPreferences.loggedUserAuthToken().get() ) );
        }
        catch(FeignException e)
        {
            e.printStackTrace();
        }
    }

    @UiThread
    public void getCaregiverListHandleSucess(List<Caregiver> caregiverList)
    {
        this.administratorHomeActivity.progressDialog.dismiss();
        this.administratorHomeActivity.setCaregiverList(caregiverList);
    }
}
