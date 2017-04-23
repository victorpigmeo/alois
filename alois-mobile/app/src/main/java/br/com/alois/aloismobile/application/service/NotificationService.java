package br.com.alois.aloismobile.application.service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.androidannotations.annotations.EBean;

/**
 * Created by victor on 4/6/17.
 */
@EBean
public class NotificationService extends FirebaseInstanceIdService
{
    //=====================================ATTRIBUTES=======================================
    private String token;

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @Override
    public void onTokenRefresh()
    {
        this.token = FirebaseInstanceId.getInstance().getToken();
    }

    public String getToken()
    {
        return this.token;
    }

    //======================================================================================

}
