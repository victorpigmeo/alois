package br.com.alois.aloismobile.application.service;

import android.app.NotificationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;

import br.com.alois.aloismobile.R;
import br.com.alois.domain.entity.reminder.Reminder;

/**
 * Created by victor on 4/15/17.
 */
public class NotificationReceiverService extends FirebaseMessagingService
{
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone ringtone = RingtoneManager.getRingtone(this.getApplicationContext(), alarmUri);
        ringtone.play();

        if(remoteMessage.getData().get("type") != null)
        {
            String type = remoteMessage.getData().get("type");

            switch (type)
            {
                case "PATIENT_OUT_OF_ROUTE":
                    NotificationCompat.Builder notification = new NotificationCompat.Builder(this.getApplicationContext())
                            .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                            .setContentTitle(this.getResources().getString(R.string.alois))
                            .setContentText(this.getResources().getString(R.string.warning_patient_out_of_route, remoteMessage.getData().get("patient_name")));

                    NotificationManager notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify("PatientOutOfRoute", 0, notification.build());
                    break;
                case "ADD_REMINDER_REQUEST":
                    AlarmService alarmService = new AlarmService( this.getApplicationContext() );

                    ObjectMapper objectMapper = new ObjectMapper();
                    try
                    {
                        Reminder reminder = objectMapper.readValue(remoteMessage.getData().get("reminder"), Reminder.class);
                        alarmService.scheduleReminder(reminder);
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                    break;
            }
        }

    }
}