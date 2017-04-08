package br.com.alois.aloismobile.application.api.login;

import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.application.api.caregiver.CaregiverClient;
import br.com.alois.aloismobile.application.preference.GeneralPreferences_;
import br.com.alois.aloismobile.application.preference.ServerConfiguration;
import br.com.alois.aloismobile.application.service.NotificationService;
import br.com.alois.aloismobile.application.util.jackson.JacksonDecoder;
import br.com.alois.aloismobile.application.util.jackson.JacksonEncoder;
import br.com.alois.aloismobile.ui.view.home.AdministratorHomeActivity;
import br.com.alois.aloismobile.ui.view.home.AdministratorHomeActivity_;
import br.com.alois.aloismobile.ui.view.home.CaregiverHomeActivity_;
import br.com.alois.aloismobile.ui.view.home.PatientHomeActivity;
import br.com.alois.aloismobile.ui.view.home.PatientHomeActivity_;
import br.com.alois.aloismobile.ui.view.login.LoginActivity;
import br.com.alois.domain.entity.user.User;
import feign.Feign;
import feign.FeignException;
import feign.Request;

/**
 * Created by victor on 2/27/17.
 * Class responsible by the LoginActivity async tasks
 */

@EBean
public class LoginTasks
{
    @Pref
    GeneralPreferences_ generalPreferences;

    @RootContext
    LoginActivity loginActivity;

    @Bean
    NotificationService notificationService;

    @Background
    public void loginAsyncTask(String username, String password)
    {
        LoginClient loginClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(LoginClient.class, ServerConfiguration.API_ENDPOINT);

        String basicAuthToken = Base64.encodeToString(new String(username + ":" + password).getBytes(), 0);

        JsonNodeFactory factory = JsonNodeFactory.instance;
        ObjectNode params = new ObjectNode(factory);
        params.put("username", username);
        params.put("password", password);

        try
        {
            loginHandleResponseSuccess(loginClient.doLogin(params, basicAuthToken), basicAuthToken);
        }
        catch(FeignException e)
        {
            e.printStackTrace();
            loginHandleResponseFail(e.getMessage());
        }
    }

    @UiThread
    public void loginHandleResponseSuccess(User user, String authToken)
    {
        this.loginActivity.progressDialog.dismiss();

        if(user != null)
        {
            this.generalPreferences
                    .edit()
                    .loggedUserId()
                    .put(user.getId())
                    .loggedUsername()
                    .put(user.getUsername())
                    .loggedPassword()
                    .put(user.getPassword())
                    .loggedUserType()
                    .put(user.getUserType().ordinal())
                    .loggedUserAuthToken()
                    .put(authToken)
                    .apply();


            Intent homeIntent = null;
            switch(user.getUserType())
            {
                case ADMINISTRATOR:
                    homeIntent = AdministratorHomeActivity_.intent(this.loginActivity.getApplicationContext()).get();
                    break;
                case CAREGIVER:
                    homeIntent = CaregiverHomeActivity_.intent(this.loginActivity.getApplicationContext()).get();
                    this.updateNotificationToken(user.getId());
                    break;
                case PATIENT:
                    homeIntent = PatientHomeActivity_.intent(this.loginActivity.getApplicationContext()).get();
                    break;
            }

            this.loginActivity.startActivityForResult(homeIntent, 1);
        }
        else
        {
            loginHandleResponseFail(this.loginActivity.getResources().getString(R.string.wrong_username_or_password));
        }

    }

    @Background
    public void updateNotificationToken(Long caregiverId)
    {
        CaregiverClient caregiverClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(CaregiverClient.class, ServerConfiguration.API_ENDPOINT);

        this.notificationService.onTokenRefresh();

        System.out.println(this.notificationService.getToken());
        try
        {
            caregiverClient.updateNotificationToken(this.notificationService.getToken(), caregiverId, this.generalPreferences.loggedUserAuthToken().get());
            Log.i("INFO", this.loginActivity.getResources().getString(R.string.success_update_notification_token));
        }
        catch(FeignException e)
        {
            //What a Terrible Failure
            Log.wtf("FAIL", this.loginActivity.getResources().getString(R.string.fail_update_notification_token));
            e.printStackTrace();
        }
    }

    @UiThread
    public void loginHandleResponseFail(String message)
    {
        this.loginActivity.progressDialog.dismiss();
        Toast.makeText(
                this.loginActivity,
                this.loginActivity.getResources().getString(R.string.cannot_connect),
                Toast.LENGTH_SHORT)
                .show();
    }

}