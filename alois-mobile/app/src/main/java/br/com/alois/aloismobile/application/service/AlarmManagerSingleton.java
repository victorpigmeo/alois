package br.com.alois.aloismobile.application.service;

import android.app.AlarmManager;
import android.content.Context;

/**
 * Created by victor on 5/5/17.
 */

public class AlarmManagerSingleton
{
    private static AlarmManager instance;

    private AlarmManagerSingleton(){}

    public static AlarmManager getInstance( Context context )
    {
        if(instance == null)
        {
            instance = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            return instance;
        }
        else
        {
            return instance;
        }
    }
}
