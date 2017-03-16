package br.com.alois.aloismobile.ui.view.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InjectMenu;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.sharedpreferences.Pref;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.application.preference.GeneralPreferences_;

@EActivity(R.layout.activity_home)
@OptionsMenu(R.menu.home_caregiver_menu)
public class HomeActivity extends AppCompatActivity
{
    //=====================================ATTRIBUTES=======================================

    //======================================================================================

    //=====================================INJECTIONS=======================================
    @InjectMenu
    Menu menu;

    @Pref
    GeneralPreferences_ generalPreferences;
    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.getItem(0).getIcon().setColorFilter(this.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);

        return true;
    }

    @OptionsItem(R.id.menu_logoff)
    public void logoff(){
        this.generalPreferences.edit()
                .loggedUsername()
                .remove()
                .loggedPassword()
                .remove()
                .loggedUserType()
                .remove()
                .apply();

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
            //FAZER UM CONFIRM DIALOG AQUI PRA VER SE O CARA REALMENTE QUER DESLOGAR
            final Intent returnIntent = new Intent();
            returnIntent.putExtra("action", "quit");
            this.setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }
    //======================================================================================

}
