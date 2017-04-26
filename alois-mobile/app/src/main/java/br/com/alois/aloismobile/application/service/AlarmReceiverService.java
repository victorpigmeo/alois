package br.com.alois.aloismobile.application.service;

import android.content.Context;
import android.content.Intent;
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
        System.out.println(intent);
        //FECHO, TO GALO!
        Log.i("ALOIS-REMINDER", "Alois reminder ringing");
    }
}
