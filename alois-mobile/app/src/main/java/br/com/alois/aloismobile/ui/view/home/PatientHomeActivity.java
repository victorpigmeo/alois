package br.com.alois.aloismobile.ui.view.home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.InjectMenu;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.application.api.memory.MemoryTasks;
import br.com.alois.aloismobile.application.api.patient.PatientTasks;
import br.com.alois.aloismobile.application.preference.GeneralPreferences_;
import br.com.alois.aloismobile.application.service.LastLocationService;
import br.com.alois.aloismobile.application.service.LastLocationService_;
import br.com.alois.aloismobile.ui.view.home.fragment.CaregiverHomeFragment;
import br.com.alois.aloismobile.ui.view.home.fragment.CaregiverHomeFragment_;
import br.com.alois.aloismobile.ui.view.home.fragment.PatientHomeFragment;
import br.com.alois.aloismobile.ui.view.home.fragment.PatientHomeFragment_;
import br.com.alois.aloismobile.ui.view.login.LoginActivity;
import br.com.alois.aloismobile.ui.view.patient.fragment.PatientDetailFragment;
import br.com.alois.aloismobile.ui.view.patient.fragment.PatientDetailFragment_;
import br.com.alois.domain.entity.user.Patient;

@EActivity(R.layout.activity_patient_home)
public class PatientHomeActivity extends AppCompatActivity
{
    //=====================================ATTRIBUTES=======================================
    public ProgressDialog progressDialog;

    public PatientHomeFragment patientHomeFragment;
    //======================================================================================

    //=====================================INJECTIONS=======================================
    @InjectMenu
    Menu menu;

    @Pref
    GeneralPreferences_ generalPreferences;

    @NonConfigurationInstance
    @Bean
    PatientTasks patientTasks;

    @NonConfigurationInstance
    @Bean
    MemoryTasks memoryTasks;

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @SuppressWarnings("WrongConstant")
    @AfterViews
    public void onAfterViews()
    {
        LastLocationService_.intent(this.getApplicationContext()).start();

        this.patientHomeFragment = PatientHomeFragment_.builder().patientId(this.generalPreferences.loggedUserId().get()).build();

        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.patient_home_frame_layout, patientHomeFragment)
                .addToBackStack("patient_home_fragment")
                .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                .commit();
    }

    public void getPatient(Long patientId)
    {
        this.progressDialog = ProgressDialog.show(this,
                super.getString(R.string.loading_patients),
                super.getString(R.string.please_wait),
                true,//is indeterminate
                false//is cancelable
        );

        this.patientTasks.findById(patientId);
    }

    public void getMemoriesByPatientId(Long patientId)
    {
        this.progressDialog = ProgressDialog.show(this,
                super.getString(R.string.loading_patients),
                super.getString(R.string.please_wait),
                true,//is indeterminate
                false//is cancelable
        );

        this.memoryTasks.getMemoryList(patientId);
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

