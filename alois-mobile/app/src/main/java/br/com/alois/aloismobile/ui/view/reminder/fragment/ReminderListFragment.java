package br.com.alois.aloismobile.ui.view.reminder.fragment;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import br.com.alois.aloismobile.R;
import br.com.alois.domain.entity.user.Patient;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_reminder_list)
public class ReminderListFragment extends Fragment
{
    //=====================================INJECTIONS=======================================
    @FragmentArg("patient")
    Patient patient;

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
        Toast.makeText(this.getContext(), "Implementar serviço de busca dos reminders", Toast.LENGTH_SHORT).show();
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

    //======================================================================================


}