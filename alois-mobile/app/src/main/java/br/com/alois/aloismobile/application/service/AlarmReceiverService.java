package br.com.alois.aloismobile.application.service;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by victor on 4/23/17.
 */

public class AlarmReceiverService extends WakefulBroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.i("ALOIS-REMINDER", "Alois reminder ringing");

        System.out.println(intent.getExtras().get("reminder"));

        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();
    }
}
