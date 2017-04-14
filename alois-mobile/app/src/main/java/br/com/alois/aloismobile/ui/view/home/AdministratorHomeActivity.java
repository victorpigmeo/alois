package br.com.alois.aloismobile.ui.view.home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.FrameLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.application.api.caregiver.CaregiverTasks;
import br.com.alois.aloismobile.application.service.NotificationService;
import br.com.alois.aloismobile.ui.view.home.fragment.AdministratorHomeFragment;
import br.com.alois.aloismobile.ui.view.home.fragment.AdministratorHomeFragment_;
import br.com.alois.aloismobile.ui.view.login.LoginActivity;
import br.com.alois.domain.entity.user.Caregiver;

@EActivity(R.layout.activity_administrator_home)
@OptionsMenu(R.menu.home_caregiver_menu)
public class AdministratorHomeActivity extends AppCompatActivity
{
//=====================================ATTRIBUTES=======================================
    @ViewById(R.id.administratorHomeFrame)
    FrameLayout administratorHomeFrame;

    public ProgressDialog progressDialog;

    AdministratorHomeFragment administratorHomeFragment;
//======================================================================================

//=====================================INJECTIONS=======================================
    @NonConfigurationInstance
    @Bean
    CaregiverTasks caregiverTasks;
    private List<Caregiver> caregiverList;
//======================================================================================

//====================================CONSTRUCTORS======================================

//======================================================================================

//==================================GETTERS/SETTERS=====================================

//======================================================================================

//=====================================BEHAVIOUR========================================

    // executa toda vez que a activity é criada
    @AfterViews
    public void onAfterViews()
    {
        //cria uma instancia do fragment
        this.administratorHomeFragment = AdministratorHomeFragment_.builder()
                .build();

        //coloca a instancia no F Manager, cria uma transacao, coloca o fragment no FrameLayout, coloca o fragment na pilha..
        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.administratorHomeFrame, administratorHomeFragment)
                .addToBackStack("administratorHomeFragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();

        this.getCaregiversList();
    }

    public void getCaregiversList()
    {
        this.progressDialog = ProgressDialog.show(this,
                this.getResources().getString(R.string.loading_caregivers),
                this.getResources().getString(R.string.please_wait),
                true,
                false);

        this.caregiverTasks.getCaregiverList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.getItem(0).getIcon().setColorFilter(this.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        return true;
    }

    public void setCaregiverList(List<Caregiver> caregiverList)
    {
        this.administratorHomeFragment.setCaregiverList(caregiverList);
    }

    @OptionsItem(R.id.menu_logoff)
    public void logoff()
    {
        //TODO FAZER UM CONFIRM DIALOG AQUI PRA VER SE O CARA REALMENTE QUER DESLOGAR
        LoginActivity.clearUserData();

        final Intent returnIntent = new Intent();
        returnIntent.putExtra("action", "logoff");
        this.setResult(Activity.RESULT_OK, returnIntent);
        this.finish();
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
            final Intent returnIntent = new Intent();
            returnIntent.putExtra("action", "quit");
            this.setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }

    //======================================================================================



}
