package br.com.alois.aloismobile.application.service;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import br.com.alois.aloismobile.R;
import br.com.alois.domain.entity.reminder.Frequency;
import br.com.alois.domain.entity.reminder.Reminder;

/**
 * Created by victor on 4/23/17.
 */

public class AlarmReceiverService extends WakefulBroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        //TODO preciso testar os reminder recorrentes!
        Log.i("ALOIS-REMINDER", "Alois reminder ringing");

        Reminder reminder = (Reminder) intent.getExtras().get("reminder");

        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle(context.getResources().getString(R.string.alois))
                .setContentText(reminder.getTitle());

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify("ALOIS-REMINDER", 0, notification.build());

        if( reminder.getFrequency() != null && !reminder.getFrequency().equals( Frequency.ONCE ) )
        {
            Long interval = null;

            switch ( reminder.getFrequency() )
            {
                case HOURLY:
                    interval = AlarmManager.INTERVAL_HOUR;
                    break;
                case DAILY:
                    interval = AlarmManager.INTERVAL_DAY;
                    break;
                case WEEKLY:
                    interval = AlarmManager.INTERVAL_DAY * 7L;
            }

            AlarmService alarmService = new AlarmService(context);

            Calendar reminderDateTime = reminder.getDateTime();

            reminderDateTime.setTime( new Date( reminderDateTime.getTimeInMillis() + interval ) );

            reminder.setDateTime( reminderDateTime );

            alarmService.rescheduleReminder(reminder);
        }

    }
}
