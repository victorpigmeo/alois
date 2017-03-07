package br.com.alois.aloismobile.application.api.signup;

import android.widget.Toast;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.application.preference.ServerConfiguration;
import br.com.alois.aloismobile.application.util.jackson.JacksonDecoder;
import br.com.alois.aloismobile.application.util.jackson.JacksonEncoder;
import br.com.alois.aloismobile.ui.view.login.LoginActivity;
import br.com.alois.domain.entity.user.Caregiver;
import feign.Feign;
import feign.FeignException;

/**
 * Created by victor on 3/1/17.
 */

@EBean
public class SignupTasks {

    @RootContext
    LoginActivity loginActivity;

    @Background
    public void signupAsyncTask(Caregiver caregiver){
        SignupClient signupClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(SignupClient.class, ServerConfiguration.API_ENDPOINT);

        try {
            signupHandleResponse(signupClient.signup(caregiver));
        }catch(FeignException e){
            e.printStackTrace();
        }
    }

    @UiThread
    public void signupHandleResponse(Caregiver caregiver){
        this.loginActivity.progressDialog.dismiss();

        if(caregiver != null && caregiver.getId() != null) {
            Toast.makeText(this.loginActivity, this.loginActivity.getResources().getString(R.string.registration_successful), Toast.LENGTH_SHORT).show();
            this.loginActivity.getSupportFragmentManager().popBackStack();
        }else{
            Toast.makeText(this.loginActivity, this.loginActivity.getResources().getString(R.string.registration_error), Toast.LENGTH_SHORT).show();
        }

    }
}
