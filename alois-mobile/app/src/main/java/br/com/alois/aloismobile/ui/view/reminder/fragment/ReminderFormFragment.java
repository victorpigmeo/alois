package br.com.alois.aloismobile.ui.view.reminder.fragment;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;
import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.application.api.reminder.ReminderTasks;
import br.com.alois.domain.entity.reminder.Frequency;
import br.com.alois.domain.entity.reminder.Reminder;
import br.com.alois.domain.entity.user.Patient;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_reminder_form)
public class ReminderFormFragment extends Fragment implements
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener,
        Validator.ValidationListener
{
    //=====================================ATTRIBUTES=======================================
    @NotEmpty(messageResId = R.string.reminder_title_is_required)
    @ViewById(R.id.reminderFormTitleEdit)
    EditText reminderFormTitleEdit;

    @ViewById(R.id.reminderFormDescriptionEdit)
    EditText reminderFormDescriptionEdit;

    @NotEmpty(messageResId = R.string.date_of_birth_is_required)
    @ViewById(R.id.reminderFormDateView)
    TextView reminderFormDateView;

    @Checked(messageResId = R.string.recurrency_is_required)
    @ViewById(R.id.reminderFormRadioGroup)
    RadioGroup reminderFormRadioGroup;

    @ViewById(R.id.reminderFormRadioWeekly)
    RadioButton reminderFormRadioWeekly;

    Calendar reminderDate;

    Calendar reminderTime;

    Validator validator = new Validator(this);
    //======================================================================================

    //=====================================INJECTIONS=======================================
    @FragmentArg("patient")
    Patient patient;

    @Bean
    ReminderTasks reminderTasks;
    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public ReminderFormFragment()
    {
        // Required empty public constructor
    }

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @AfterViews
    public void onAfterViews()
    {
        this.validator.setValidationListener(this);
    }

    @Click(R.id.reminderFormButtonSave)
    public void onReminderFormButtonSaveClick()
    {
        if( this.reminderDate == null || this.reminderTime == null ||
                this.reminderFormDateView.getText() == null || this.reminderFormDateView.getText().equals("") )
        {
            this.reminderFormDateView.setError( this.getActivity().getResources().getString( R.string.you_must_select_a_date_and_time ) );
            return;
        }

        this.validator.validate();
    }

    @Click(R.id.reminderFormButtonSelectDate)
    public void onClickDatepickerButton()
    {
        this.reminderFormDateView.setError(null);

        Calendar now = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show(this.getActivity().getFragmentManager(), "DatePickerdialog");
    }

    @Click(R.id.reminderFormButtonSelectTime)
    public void onClickTimepickerButton()
    {
        this.reminderFormDateView.setError(null);

        Calendar now = Calendar.getInstance();
        TimePickerDialog datePickerDialog = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND),
                true);
        datePickerDialog.show(this.getActivity().getFragmentManager(), "DatePickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth)
    {
        this.reminderDate = Calendar.getInstance();
        this.reminderDate.set(Calendar.YEAR, year);
        this.reminderDate.set(Calendar.MONTH, monthOfYear);
        this.reminderDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        if(this.reminderFormDateView.getText() != null && !this.reminderFormDateView.getText().equals(""))
        {
            this.reminderFormDateView.setText(dayOfMonth+"/"+monthOfYear+"/"+year+" "+this.reminderFormDateView.getText().toString());
        }
        else
        {
            this.reminderFormDateView.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
        }
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second)
    {
        this.reminderTime = Calendar.getInstance();
        this.reminderTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        this.reminderTime.set(Calendar.MINUTE, minute);

        if(this.reminderFormDateView.getText() != null && !this.reminderFormDateView.getText().equals(""))
        {
            this.reminderFormDateView.setText(this.reminderFormDateView.getText().toString()+" "+hourOfDay+":"+minute);
        }
        else
        {
            this.reminderFormDateView.setText(hourOfDay+":"+minute);
        }
    }

    @Override
    public void onValidationSucceeded()
    {
        Reminder reminder = new Reminder();
        reminder.setTitle(this.reminderFormTitleEdit.getText().toString());
        reminder.setDescription(this.reminderFormDescriptionEdit.getText().toString());
        reminder.setPatient(this.patient);

        RadioButton radioFrequency = (RadioButton) this.getActivity().findViewById(this.reminderFormRadioGroup.getCheckedRadioButtonId());
        reminder.setFrequency(Frequency.valueOf(radioFrequency.getTag().toString()));

        Calendar reminderDateTime = Calendar.getInstance();
        reminderDateTime.set(Calendar.YEAR, this.reminderDate.get(Calendar.YEAR));
        reminderDateTime.set(Calendar.MONTH, this.reminderDate.get(Calendar.MONTH));
        reminderDateTime.set(Calendar.DAY_OF_MONTH, this.reminderDate.get(Calendar.DAY_OF_MONTH));

        reminderDateTime.set(Calendar.HOUR_OF_DAY, this.reminderTime.get(Calendar.HOUR_OF_DAY));
        reminderDateTime.set(Calendar.MINUTE, this.reminderDate.get(Calendar.MINUTE));

        reminder.setDateTime(reminderDateTime);

        this.reminderTasks.addReminder( reminder );
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors)
    {
        for (ValidationError error : errors)
        {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this.getContext());

            // Display error messages ;)
            if (view instanceof EditText)
            {
                ((EditText) view).setError(message);
            }
            else if (view instanceof RadioGroup)
            {
                this.reminderFormRadioWeekly.setError(message);
            }
            else if( view instanceof TextView)
            {
                ((TextView) view).setError(message);
            }
            else
            {
                Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }

    //======================================================================================
}
