package br.com.alois.aloismobile.application.api.reminder;

import android.widget.Toast;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.application.preference.GeneralPreferences_;
import br.com.alois.aloismobile.application.preference.ServerConfiguration;
import br.com.alois.aloismobile.ui.view.home.PatientHomeActivity;
import br.com.alois.aloismobile.ui.view.patient.PatientDetailActivity;
import br.com.alois.api.jackson.JacksonDecoder;
import br.com.alois.api.jackson.JacksonEncoder;
import br.com.alois.domain.entity.reminder.Reminder;
import br.com.alois.domain.entity.reminder.ReminderStatus;
import br.com.alois.domain.entity.user.Patient;
import br.com.alois.domain.entity.user.UserType;
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
    public void addPendingReminder(Reminder reminder)
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
            addPendingReminderHandleSuccess( reminderClient.addPendingReminder( reminder, this.generalPreferences.loggedUserAuthToken().get() ) );
        }
        catch(FeignException e)
        {
            e.printStackTrace();
        }
    }

    @UiThread
    public void addPendingReminderHandleSuccess(Reminder reminder)
    {
        this.patientDetailActivity.progressDialog.dismiss();

        Toast.makeText(this.patientDetailActivity.getApplicationContext(), this.patientDetailActivity.getResources().getString(R.string.processingReminder), Toast.LENGTH_LONG).show();

        this.patientDetailActivity.getSupportFragmentManager().popBackStack();
    }

    @Background
    public void listRemindersByPatientId(Long patientId)
    {
        ReminderClient reminderClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(ReminderClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            listRemindersByPatientIdHandleSuccess( reminderClient.listRemindersByPatientId( patientId, this.generalPreferences.loggedUserAuthToken().get() ), null );
        }
        catch(FeignException e)
        {
            e.printStackTrace();
        }
    }

    @Background
    public void listActiveRemindersByPatientId(Long patientId, PatientHomeActivity patientHomeActivity)
    {
        ReminderClient reminderClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(ReminderClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            listRemindersByPatientIdHandleSuccess( reminderClient.listActiveRemindersByPatientId( patientId, this.generalPreferences.loggedUserAuthToken().get() ), patientHomeActivity );
        }
        catch(FeignException e)
        {
            e.printStackTrace();
        }
    }

    @UiThread
    public void listRemindersByPatientIdHandleSuccess(List<Reminder> reminders, PatientHomeActivity patientHomeActivity)
    {
        if(this.generalPreferences.loggedUserType().get().equals(UserType.CAREGIVER.ordinal()))
        {
            if( reminders.size() != 0 )
            {
                this.patientDetailActivity.setReminderList( reminders );
                this.patientDetailActivity.progressDialog.dismiss();
            }
            else
            {
                this.patientDetailActivity.progressDialog.dismiss();
                Toast.makeText(this.patientDetailActivity, this.patientDetailActivity.getResources().getString(R.string.patient_does_not_have_reminders), Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            if( reminders.size() != 0 )
            {
                patientHomeActivity.progressDialog.dismiss();
                patientHomeActivity.setPatientReminderList(reminders);
            }
            else
            {
                patientHomeActivity.progressDialog.dismiss();
                Toast.makeText(patientHomeActivity, patientHomeActivity.getResources().getString(R.string.patient_does_not_have_reminders), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Background
    public void deleteReminderRequest( Reminder reminder )
    {
        ReminderClient reminderClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(ReminderClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            reminderClient.deleteReminderRequest(reminder.getId(), this.generalPreferences.loggedUserAuthToken().get());
            deleteReminderRequestHandleSuccess( reminder );
        }
        catch (FeignException e)
        {
            e.printStackTrace();
        }
    }

    @UiThread
    public void deleteReminderRequestHandleSuccess( Reminder reminder )
    {
        this.patientDetailActivity.progressDialog.dismiss();
        this.patientDetailActivity.onDeleteReminder(reminder);
    }

    @Background
    public void updateReminder(Reminder reminder)
    {
        ReminderClient reminderClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(ReminderClient.class, ServerConfiguration.API_ENDPOINT);

        reminder.setReminderStatus(ReminderStatus.PENDING);

        String notificationToken = reminder.getPatient().getNotificationToken();
        reminder.setPatient(new Patient(reminder.getPatient().getId()));
        reminder.getPatient().setNotificationToken(notificationToken);

        try
        {
            updateReminderHandleSuccess( reminderClient.updateReminder( reminder, this.generalPreferences.loggedUserAuthToken().get() ) );
        }
        catch (FeignException e)
        {
            e.printStackTrace();
        }
    }

    @UiThread
    public void updateReminderHandleSuccess(Reminder reminder)
    {
        this.patientDetailActivity.progressDialog.dismiss();

        Toast.makeText(this.patientDetailActivity.getApplicationContext(), this.patientDetailActivity.getResources().getString(R.string.processingReminder), Toast.LENGTH_LONG).show();

        this.patientDetailActivity.getSupportFragmentManager().popBackStack();
    }


    //======================================================================================

}
