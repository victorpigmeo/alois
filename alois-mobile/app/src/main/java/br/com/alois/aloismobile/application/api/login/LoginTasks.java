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
            loginHandleResponseSuccess(loginClient.doLogin(params, basicAuthToken));
        }
        catch(FeignException e)
        {
            e.printStackTrace();
            loginHandleResponseFail(e.getMessage());
        }
    }

    @UiThread
    public void loginHandleResponseSuccess(User user)
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


            //ALTERAR A HOME QUE ESTIVER DESENVOLVENDO AQUI
            this.loginActivity.startActivityForResult(HomeActivity_.intent(this.loginActivity.getApplicationContext()).get(), 1);
        }
        else
        {
            loginHandleResponseFail(this.loginActivity.getResources().getString(R.string.wrong_username_or_password));
        }

    }

    @UiThread
    public void loginHandleResponseFail(String message)
    {
        Toast.makeText(
                this.loginActivity,
                message,
                Toast.LENGTH_SHORT)
                .show();
    }

}