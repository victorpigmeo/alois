package br.com.alois.aloismobile.application.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by victor on 4/23/17.
 */

class AlarmReceiverService extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        System.out.println(intent);
    }
}
