package br.com.alois.aloismobile.application.api.login;

import android.util.Base64;
import android.widget.Toast;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.application.preference.GeneralPreferences_;
import br.com.alois.aloismobile.application.preference.ServerConfiguration;
import br.com.alois.aloismobile.application.util.jackson.JacksonDecoder;
import br.com.alois.aloismobile.application.util.jackson.JacksonEncoder;
import br.com.alois.aloismobile.ui.view.home.HomeActivity_;
import br.com.alois.aloismobile.ui.view.login.LoginActivity;
import br.com.alois.domain.entity.user.User;
import feign.Feign;
import feign.FeignException;

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
            loginSuccess(loginClient.doLogin(params, basicAuthToken));
        }
        catch(FeignException e)
        {
            e.printStackTrace();
            loginFail(e.getMessage());
        }
    }

    @UiThread
    void loginSuccess(User user)
    {
        this.loginActivity.progressDialog.dismiss();
        if(user != null)
        {
            this.generalPreferences
                    .edit()
                    .loggedUsername()
                    .put(user.getUsername())
                    .loggedPassword()
                    .put(user.getPassword())
                    .loggedUserType()
                    .put(user.getUserType().ordinal())
                    .apply();

            this.loginActivity.startActivityForResult(HomeActivity_.intent(this.loginActivity.getApplicationContext()).get(), 1);
        }
        else
        {
            Toast.makeText(
                    this.loginActivity,
                    this.loginActivity.getResources().getString(R.string.wrong_username_or_password),
                    Toast.LENGTH_SHORT)
                    .show();
        }


    }

    @UiThread
    void loginFail(String errorMessage)
    {
        this.loginActivity.progressDialog.dismiss();
        System.out.println(errorMessage);
    }

}