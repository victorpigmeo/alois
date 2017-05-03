package br.com.alois.aloismobile.application.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.sharedpreferences.Pref;

import br.com.alois.aloismobile.application.api.reminder.ReminderClient;
import br.com.alois.aloismobile.application.preference.GeneralPreferences_;
import br.com.alois.aloismobile.application.preference.ServerConfiguration;
import br.com.alois.aloismobile.ui.view.patient.PatientDetailActivity;
import br.com.alois.api.jackson.JacksonDecoder;
import br.com.alois.api.jackson.JacksonEncoder;
import br.com.alois.domain.entity.reminder.Reminder;
import feign.Feign;
import feign.FeignException;

/**
 * Created by victor on 4/23/17.
 */
@EBean
public class AlarmService
{
    //=====================================ATTRIBUTES=======================================
    private final Context context;

    private AlarmManager alarmManager;

    @Pref
    GeneralPreferences_ generalPreferences;

    //====================================CONSTRUCTORS======================================
    public AlarmService(Context context)
    {
        this.context = context;
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }
    //======================================================================================

    //==================================GETTERS/SETTERS=====================================

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @Background
    public void scheduleReminder(Reminder reminder)
    {
        Long interval = null;
        switch (reminder.getFrequency())
        {
            case HOURLY:
                interval = AlarmManager.INTERVAL_HOUR;
                break;
            case DAILY:
                interval = AlarmManager.INTERVAL_DAY;
                break;
            case WEEKLY:
                interval = Long.valueOf(7L * 24L * 60L * 60L * 1000L);
                break;
        }

        Intent myIntent = new Intent(this.context, AlarmReceiverService.class);
        PendingIntent  pendingIntent = PendingIntent.getBroadcast(this.context, 0, myIntent, 0);

        if(interval != null)
        {
            this.alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, reminder.getDateTime().getTimeInMillis(),
                    interval, pendingIntent);
            Log.i("ALOIS-REMINDER", "Alois recurrency reminder succesfully scheduled to: " + reminder.getDateTime().getTime());
        }
        else
        {
            ReminderClient reminderClient = Feign.builder()
                    .encoder(new JacksonEncoder())
                    .decoder(new JacksonDecoder())
                    .target(ReminderClient.class, ServerConfiguration.API_ENDPOINT);

            try
            {
                reminderClient.addReminder(reminder, ServerConfiguration.LOGGED_USER_AUTH_TOKEN);
                this.alarmManager.set(AlarmManager.RTC_WAKEUP, reminder.getDateTime().getTimeInMillis(), pendingIntent);
                Log.i("ALOIS-REMINDER", "Alois one-time reminder succesfully scheduled to: " + reminder.getDateTime().getTime());
            }
            catch (FeignException e )
            {
                e.printStackTrace();
            }
        }

    }
    //======================================================================================

}
