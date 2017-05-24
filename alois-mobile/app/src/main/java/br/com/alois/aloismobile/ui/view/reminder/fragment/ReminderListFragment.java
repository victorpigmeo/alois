package br.com.alois.aloismobile.ui.view.reminder.fragment;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.ui.view.patient.PatientDetailActivity;
import br.com.alois.aloismobile.ui.view.reminder.adapter.ReminderListAdapter;
import br.com.alois.domain.entity.reminder.Reminder;
import br.com.alois.domain.entity.user.Patient;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_reminder_list)
public class ReminderListFragment extends Fragment
{
    //=====================================ATTRIBUTES=======================================
    @ViewById(R.id.reminderList)
    ListView reminderList;

    //======================================================================================

    //=====================================INJECTIONS=======================================
    @FragmentArg("patient")
    Patient patient;

    @Bean
    ReminderListAdapter reminderListAdapter;
    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public ReminderListFragment()
    {
        // Required empty public constructor
    }

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @AfterViews
    public void onAfterViews()
    {
        this.reminderList.setAdapter(this.reminderListAdapter);

        ((PatientDetailActivity) this.getActivity()).listRemindersByPatientId(this.patient.getId());
    }

    @Click(R.id.fab_add_reminder)
    public void onFabAddReminderClick()
    {
        ReminderFormFragment reminderFormFragment = ReminderFormFragment_.builder()
                .patient(this.patient)
                .build();

        this.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.patientDetailFrame, reminderFormFragment)
                .addToBackStack("reminderFormFragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @ItemClick(R.id.reminderList)
    public void onReminderListClick(Reminder reminder)
    {
        AlertDialog reminderDetailDialog = new AlertDialog.Builder( this.getActivity() ).create();

        View reminderDetailView = this.getActivity().getLayoutInflater().inflate(R.layout.reminder_detail_view, null);

        ( ( TextView ) reminderDetailView.findViewById( R.id.reminderDetailReminderTitle ) ).setText( reminder.getTitle() );
        ( ( TextView ) reminderDetailView.findViewById( R.id.reminderDetailReminderDescription ) ).setText( reminder.getDescription() );
        ( ( TextView ) reminderDetailView.findViewById( R.id.reminderDetailReminderDateTime ) ).setText( reminder.getDateTime().getTime().toString() );
        ( ( TextView ) reminderDetailView.findViewById( R.id.reminderDetailReminderRecurrency) ).setText( reminder.getFrequency().toString() );

        reminderDetailDialog.setView(reminderDetailView);
        reminderDetailDialog.show();
    }

    public void setReminderList(List<Reminder> reminders)
    {
        this.reminderListAdapter.setReminders( reminders );
    }

    public void onDeleteReminder(Reminder reminder)
    {
        this.reminderListAdapter.onDeleteReminder(reminder);
    }

    //======================================================================================


}