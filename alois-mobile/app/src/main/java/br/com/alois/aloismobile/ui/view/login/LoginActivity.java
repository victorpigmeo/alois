package br.com.alois.aloismobile.ui.view.login;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.sharedpreferences.Pref;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.application.api.login.LoginTasks;
import br.com.alois.aloismobile.application.api.signup.SignupTasks;
import br.com.alois.aloismobile.application.preference.GeneralPreferences_;
import br.com.alois.aloismobile.application.preference.ServerConfiguration;
import br.com.alois.aloismobile.ui.view.home.AdministratorHomeActivity_;
import br.com.alois.aloismobile.ui.view.home.CaregiverHomeActivity_;
import br.com.alois.aloismobile.ui.view.home.PatientHomeActivity_;
import br.com.alois.aloismobile.ui.view.login.fragment.LoginFragment;
import br.com.alois.aloismobile.ui.view.login.fragment.LoginFragment_;
import br.com.alois.domain.entity.user.Caregiver;
import br.com.alois.domain.entity.user.UserType;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity
{
    //=====================================ATTRIBUTES=======================================
    public ProgressDialog progressDialog;
    //======================================================================================

    //=====================================INJECTIONS=======================================
    @NonConfigurationInstance
    @Bean
    LoginTasks loginTask;

    @NonConfigurationInstance
    @Bean
    SignupTasks signupTasks;

    @Pref
    static GeneralPreferences_ generalPreferences;

    @SystemService
    InputMethodManager inputMethodManager;

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @AfterViews
    public void onAfterViews()
    {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        if(!this.generalPreferences.loggedUsername().exists())
        {
            LoginFragment loginFragment = LoginFragment_.builder().build();

            super.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.loginFrameLayout, loginFragment)
                    .addToBackStack("loginFragment")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
        else
        {
            ServerConfiguration.LOGGED_USER_AUTH_TOKEN = this.generalPreferences.loggedUserAuthToken().get();

            Intent homeIntent = null;
            int userType = this.generalPreferences.loggedUserType().get().intValue();

            switch(UserType.values()[userType])
            {
                case ADMINISTRATOR:
                    homeIntent = AdministratorHomeActivity_.intent(this.getApplicationContext()).get();
                    break;
                case CAREGIVER:
                    homeIntent = CaregiverHomeActivity_.intent(this.getApplicationContext()).get();
                    break;
                case PATIENT:
                    homeIntent = PatientHomeActivity_.intent(this.getApplicationContext()).get();
                    break;
            }

            this.startActivityForResult(homeIntent, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getStringExtra("action").equals("logoff"))
        {
            //User logged off

            //clear the backstack
            for(int i = 0; i < this.getSupportFragmentManager().getBackStackEntryCount(); i++)
            {
                this.getSupportFragmentManager().popBackStack();
            }

            //put the login fragment in the backstack
            LoginFragment loginFragment = LoginFragment_.builder().build();
            super.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.loginFrameLayout, loginFragment)
                    .addToBackStack("loginFragment")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
        else
        {
            this.finish();
        }
    }

    public void signup(Caregiver caregiver) {
        this.progressDialog = ProgressDialog.show(this,
                super.getString(R.string.creating_account),
                super.getString(R.string.please_wait),
                true,//is indeterminate
                false//is cancelable
        );

        this.signupTasks.signupAsyncTask(caregiver);
    }

    public void login(String username, String password){
        //force hide keyboard
        this.inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);

        this.progressDialog = ProgressDialog.show(this,
                this.getResources().getString(R.string.siging_in),
                this.getResources().getString(R.string.please_wait),
                true,
                false
        );

        this.loginTask.loginAsyncTask(username, password);
    }

    @Override
    public void onBackPressed()
    {
        int fragments = this.getSupportFragmentManager().getBackStackEntryCount();

        if (fragments > 1)
        {
            getSupportFragmentManager().popBackStack();
        }
        else
        {
            finish();
        }
    }

    public static void clearUserData()
    {
        generalPreferences.edit()
                .loggedUsername()
                .remove()
                .loggedPassword()
                .remove()
                .loggedUserType()
                .remove()
                .loggedUserAuthToken()
                .remove()
                .apply();
    }

    //======================================================================================
}
