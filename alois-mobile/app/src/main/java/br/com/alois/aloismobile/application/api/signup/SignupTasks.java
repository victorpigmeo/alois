package br.com.alois.aloismobile.application.api.signup;

import android.widget.Toast;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.application.preference.ServerConfiguration;
import br.com.alois.aloismobile.ui.view.login.LoginActivity;
import br.com.alois.api.jackson.JacksonDecoder;
import br.com.alois.api.jackson.JacksonEncoder;
import br.com.alois.domain.entity.user.Caregiver;
import feign.Feign;
import feign.FeignException;

/**
 * Created by victor on 3/1/17.
 */

@EBean
public class SignupTasks
{

    @RootContext
    LoginActivity loginActivity;

    @Background
    public void signupAsyncTask(Caregiver caregiver)
    {
        SignupClient signupClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(SignupClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            signupHandleResponseSuccess(signupClient.signup(caregiver));
        }
        catch(FeignException e)
        {
            if(e.getMessage().contains("ConstraintViolationException"))
            {
                this.signupHandleResponseFail( this.loginActivity.getResources().getString(R.string.username_already_exists));
            }
            else
            {
                e.printStackTrace();
                this.signupHandleResponseFail(e.getMessage());
            }
        }
    }

    @UiThread
    public void signupHandleResponseSuccess(Caregiver caregiver)
    {
        this.loginActivity.progressDialog.dismiss();

        Toast.makeText(this.loginActivity, this.loginActivity.getResources().getString(R.string.registration_successful), Toast.LENGTH_SHORT).show();
        this.loginActivity.getSupportFragmentManager().popBackStack();
    }

    @UiThread
    public void signupHandleResponseFail(String message)
    {
        this.loginActivity.progressDialog.dismiss();
        Toast.makeText(this.loginActivity, message, Toast.LENGTH_SHORT).show();
    }

}
