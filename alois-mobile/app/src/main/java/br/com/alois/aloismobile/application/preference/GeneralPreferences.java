package br.com.alois.aloismobile.application.preference;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by victor on 3/4/17.
 */

@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface GeneralPreferences
{
    //User Info

    //Authentication info, this is used to construct the basic authentication token in every request
    /**
     * Stores the username of the logged user
     * @return
     */
    String loggedUsername();

    /**
     * Stores the password (encrypted) of the logged user
     * @return
     */
    String loggedPassword();
    //End authentication info

    int loggedUserType();

    //End User Info
}
