package br.com.alois.aloismobile.application.api.reminder;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.sharedpreferences.Pref;

import br.com.alois.aloismobile.application.preference.GeneralPreferences_;
import br.com.alois.aloismobile.application.preference.ServerConfiguration;
import br.com.alois.aloismobile.ui.view.patient.PatientDetailActivity;
import br.com.alois.api.jackson.JacksonDecoder;
import br.com.alois.api.jackson.JacksonEncoder;
import br.com.alois.domain.entity.reminder.Reminder;
import br.com.alois.domain.entity.user.Patient;
import feign.Feign;
import feign.FeignException;

/**
 * Created by victor on 4/23/17.
 */
@EBean
public class ReminderTasks
{
    //=====================================INJECTIONS=======================================
    @Pref
    GeneralPreferences_ generalPreferences;

    @RootContext
    PatientDetailActivity patientDetailActivity;
    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @Background
    public void addReminder(Reminder reminder)
    {
        ReminderClient reminderClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(ReminderClient.class, ServerConfiguration.API_ENDPOINT);

        String notificationToken = reminder.getPatient().getNotificationToken();
        reminder.setPatient(new Patient(reminder.getPatient().getId()));
        reminder.getPatient().setNotificationToken(notificationToken);

        try
        {
            addReminderHandleSuccess( reminderClient.sendRequest( reminder, this.generalPreferences.loggedUserAuthToken().get() ) );
        }
        catch(FeignException e)
        {
            e.printStackTrace();
        }
    }

    private void addReminderHandleSuccess(Reminder reminder)
    {
        System.out.println(reminder);
    }
    //======================================================================================

}
