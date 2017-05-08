package br.com.alois.aloismobile.ui.view.reminder.fragment;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.ListView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.List;

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