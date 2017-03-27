package br.com.alois.aloismobile.ui.view.home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InjectMenu;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.application.api.patient.PatientTasks;
import br.com.alois.aloismobile.application.preference.GeneralPreferences_;
import br.com.alois.aloismobile.ui.view.home.fragment.CaregiverHomeFragment;
import br.com.alois.aloismobile.ui.view.home.fragment.CaregiverHomeFragment_;
import br.com.alois.aloismobile.ui.view.login.LoginActivity;
import br.com.alois.domain.entity.user.Patient;

@EActivity(R.layout.activity_caregiver_home)
@OptionsMenu(R.menu.home_caregiver_menu)
public class CaregiverHomeActivity extends AppCompatActivity
{
    //=====================================ATTRIBUTES=======================================
    public ProgressDialog progressDialog;

    public CaregiverHomeFragment caregiverHomeFragment;

    //======================================================================================

    //=====================================INJECTIONS=======================================
    @InjectMenu
    Menu menu;

    @Pref
    GeneralPreferences_ generalPreferences;

    @NonConfigurationInstance
    @Bean
    PatientTasks patientTasks;
    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @AfterViews
    public void onAfterViews()
    {
        this.caregiverHomeFragment = CaregiverHomeFragment_.builder().build();

        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.caregiver_home_frame_layout, caregiverHomeFragment)
                .addToBackStack("caregiverHomeFragment")
                .commit();

        this.getPatientListByCaregiverId(this.generalPreferences.loggedUserId().get());
    }

    private void getPatientListByCaregiverId(Long caregiverId)
    {
        this.progressDialog = ProgressDialog.show(this,
                super.getString(R.string.loading_patients),
                super.getString(R.string.please_wait),
                true,//is indeterminate
                false//is cancelable
        );

        this.patientTasks.listPatientsByCaregiverIdAsyncTask(caregiverId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.getItem(0).getIcon().setColorFilter(this.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);

        return true;
    }

    public void setPatientList(List<Patient> patientList)
    {
        this.caregiverHomeFragment.setPatientList(patientList);
    }

    @OptionsItem(R.id.menu_logoff)
    public void logoff()
    {
        LoginActivity.clearUserData();

        final Intent returnIntent = new Intent();
        returnIntent.putExtra("action", "logoff");
        this.setResult(Activity.RESULT_OK, returnIntent);
        this.finish();
    }

    public void addPatient(Patient patient)
    {
        this.progressDialog = ProgressDialog.show(this,
                super.getString(R.string.loading_patients),
                super.getString(R.string.please_wait),
                true,//is indeterminate
                false//is cancelable
        );

        this.patientTasks.addPatient(patient);
    }

    public void onInsertPatient(Patient patient)
    {
        this.caregiverHomeFragment.onInsertPatient(patient);
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
            //TODO FAZER UM CONFIRM DIALOG AQUI PRA VER SE O CARA REALMENTE QUER DESLOGAR
            final Intent returnIntent = new Intent();
            returnIntent.putExtra("action", "quit");
            this.setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }
    //======================================================================================

}
