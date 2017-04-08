package br.com.alois.aloismobile.application.service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EService;

/**
 * Created by victor on 4/6/17.
 */
@EBean
public class NotificationService extends FirebaseInstanceIdService
{
    private String token;

    @Override
    public void onTokenRefresh()
    {
        this.token = FirebaseInstanceId.getInstance().getToken();
    }

    public String getToken()
    {
        return this.token;
    }
}
