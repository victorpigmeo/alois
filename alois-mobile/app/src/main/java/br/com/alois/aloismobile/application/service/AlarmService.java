package br.com.alois.aloismobile.application.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import br.com.alois.aloismobile.application.api.reminder.ReminderClient;
import br.com.alois.aloismobile.application.preference.GeneralPreferences;
import br.com.alois.aloismobile.application.preference.GeneralPreferences_;
import br.com.alois.aloismobile.application.preference.ServerConfiguration;
import br.com.alois.api.jackson.JacksonDecoder;
import br.com.alois.api.jackson.JacksonEncoder;
import br.com.alois.domain.entity.reminder.Reminder;
import br.com.alois.domain.entity.reminder.ReminderStatus;
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

    //====================================CONSTRUCTORS======================================
    public AlarmService(Context context)
    {
        this.context = context;
        this.alarmManager = AlarmManagerSingleton.getInstance( context );
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

        Intent alarmIntent = new Intent(this.context, AlarmReceiverService.class);
        alarmIntent.putExtra("reminder", reminder);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, reminder.getId().intValue(), alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        if(interval != null)
        {
            ReminderClient reminderClient = Feign.builder()
                    .encoder(new JacksonEncoder())
                    .decoder(new JacksonDecoder())
                    .target(ReminderClient.class, ServerConfiguration.API_ENDPOINT);

            try
            {
                this.alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, reminder.getDateTime().getTimeInMillis(),
                        interval, pendingIntent);

                reminder.setReminderStatus(ReminderStatus.ACTIVE);
                reminderClient.updateReminder(reminder, ServerConfiguration.LOGGED_USER_AUTH_TOKEN);

                Log.i("ALOIS-REMINDER", "Alois recurrency reminder succesfully scheduled to: " + reminder.getDateTime().getTime());
            }
            catch (FeignException e )
            {
                e.printStackTrace();
            }
        }
        else
        {
            ReminderClient reminderClient = Feign.builder()
                    .encoder(new JacksonEncoder())
                    .decoder(new JacksonDecoder())
                    .target(ReminderClient.class, ServerConfiguration.API_ENDPOINT);

            try
            {
                reminder.setReminderStatus(ReminderStatus.ACTIVE);
                reminderClient.updateReminder(reminder, ServerConfiguration.LOGGED_USER_AUTH_TOKEN);

                this.alarmManager.set(AlarmManager.RTC_WAKEUP, reminder.getDateTime().getTimeInMillis(), pendingIntent);
                Log.i("ALOIS-REMINDER", "Alois one-time reminder succesfully scheduled to: " + reminder.getDateTime().getTime());
            }
            catch (FeignException e )
            {
                e.printStackTrace();
            }
        }

    }

    public void deleteReminder(Reminder reminder)
    {
            Intent alarmIntent = new Intent(this.context, AlarmReceiverService.class);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, reminder.getId().intValue(), alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            this.alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();

            ReminderClient reminderClient = Feign.builder()
                    .encoder(new JacksonEncoder())
                    .decoder(new JacksonDecoder())
                    .target(ReminderClient.class, ServerConfiguration.API_ENDPOINT);

            try
            {
                reminderClient.deleteReminder(reminder, ServerConfiguration.LOGGED_USER_AUTH_TOKEN);
            }
            catch (FeignException e)
            {
                e.printStackTrace();
            }

            Log.i("ALOIS-REMINDER", "Alois reminder with id: " + reminder.getId() + " canceled");
    }

    //======================================================================================

}
