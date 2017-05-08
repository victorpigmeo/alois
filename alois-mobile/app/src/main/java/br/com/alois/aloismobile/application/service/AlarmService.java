package br.com.alois.aloismobile.application.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.IOException;
import java.net.URISyntaxException;

import br.com.alois.aloismobile.application.api.reminder.ReminderClient;
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

    @Pref
    GeneralPreferences_ generalPreferences;

    private ObjectMapper objectMapper = new ObjectMapper();

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

        try
        {
            alarmIntent.putExtra("reminder", this.objectMapper.writeValueAsString(reminder));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        reminder.setIntent( alarmIntent.toUri(0) );
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, 0, alarmIntent, 0);

        if(interval != null)
        {
            ReminderClient reminderClient = Feign.builder()
                    .encoder(new JacksonEncoder())
                    .decoder(new JacksonDecoder())
                    .target(ReminderClient.class, ServerConfiguration.API_ENDPOINT);

            try
            {
                reminder.setReminderStatus(ReminderStatus.ACTIVE);
                reminderClient.updateReminder(reminder, ServerConfiguration.LOGGED_USER_AUTH_TOKEN);

                this.alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, reminder.getDateTime().getTimeInMillis(),
                        interval, pendingIntent);
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
        try
        {
            Intent intent = Intent.parseUri(reminder.getIntent(), Intent.URI_ALLOW_UNSAFE);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            this.alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();

            //TODO ACHO QUE AGORA FOI, TESTAR MAIS
            Log.i("ALOIS-REMINDER", "Alois reminder with id: " + reminder.getId() + " canceled");
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
    }
    //======================================================================================

}
