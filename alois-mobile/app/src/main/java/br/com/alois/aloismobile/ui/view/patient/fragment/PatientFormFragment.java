package br.com.alois.aloismobile.ui.view.patient.fragment;


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
import com.mobsandgeeks.saripaar.annotation.Password;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.application.preference.GeneralPreferences_;
import br.com.alois.aloismobile.ui.view.home.CaregiverHomeActivity;
import br.com.alois.domain.entity.user.Caregiver;
import br.com.alois.domain.entity.user.Gender;
import br.com.alois.domain.entity.user.Patient;
import br.com.alois.domain.entity.user.User;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_patient_form)
public class PatientFormFragment extends Fragment implements
        DatePickerDialog.OnDateSetListener,
        Validator.ValidationListener
{
    //=====================================ATTRIBUTES=======================================
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @ViewById(R.id.patientFormEditName)
    @NotEmpty(messageResId = R.string.name_is_required_field)
    EditText patientFormEditName;

    @ViewById(R.id.patientFormEditPhone)
    @NotEmpty(messageResId = R.string.phone_is_required_field)
    EditText patientFormEditPhone;

    @ViewById(R.id.patientFormRadioGender)
    @Checked(messageResId = R.string.gender_is_required)
    RadioGroup patientFormRadioGender;

    @ViewById(R.id.patientFormRadioFemale)
    RadioButton patientFormRadioFemale;

    @ViewById(R.id.patientFormRadioMale)
    RadioButton patientFormRadioMale;

    Calendar patientDateOfBirth = Calendar.getInstance();

    @ViewById(R.id.patientFormDateView)
    @NotEmpty(messageResId = R.string.date_of_birth_is_required)
    TextView patientFormDateView;

    @ViewById(R.id.patientFormEditAddress)
    @NotEmpty(messageResId = R.string.address_is_required)
    EditText patientFormEditAddress;

    @ViewById(R.id.patientFormEditEmergencyPhone)
    EditText patientFormEditEmergencyPhone;

    @ViewById(R.id.patientFormEditNotes)
    EditText patientFormEditNotes;

    @ViewById(R.id.patientFormEditUsername)
    @NotEmpty(messageResId = R.string.username_is_required_field)
    EditText patientFormEditUsername;

    @ViewById(R.id.patientFormEditPassword)
    EditText patientFormEditPassword;

    Validator validator = new Validator(this);
    //======================================================================================

    //=====================================INJECTIONS=======================================
    @Pref
    GeneralPreferences_ generalPreferences;

    @FragmentArg("patient")
    Patient patient;
    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public PatientFormFragment()
    {
        // Required empty public constructor
    }

    //======================================================================================

    //==================================GETTERS/SETTERS=====================================

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @AfterViews
    public void onAfterViews()
    {
        this.validator.setValidationListener(this);

        if(this.patient != null && this.patient.getId() != null)
        {
            this.patientFormEditName.setText(this.patient.getName());
            this.patientFormEditPhone.setText(this.patient.getPhone());

            switch (this.patient.getGender())
            {
                case MALE:
                    this.patientFormRadioMale.toggle();
                    break;
                case FEMALE:
                    this.patientFormRadioFemale.toggle();
                    break;
            }

            this.patientFormDateView.setText(this.simpleDateFormat.format(this.patient.getBirthDate().getTime()));
            this.patientDateOfBirth = this.patient.getBirthDate();
            this.patientFormEditAddress.setText(this.patient.getAddress());
            this.patientFormEditEmergencyPhone.setText(this.patient.getEmergencyPhone());
            this.patientFormEditNotes.setText(this.patient.getNote());
            this.patientFormEditUsername.setText(this.patient.getUsername());
        }
    }

    @Click(R.id.patientFormButtonSave)
    public void onClickSaveButton()
    {
        if(this.patient == null)
        {
            if(this.patientFormEditPassword.getText().length() < 6)
            {
                this.patientFormEditPassword.setError(this.getActivity().getResources().getString(R.string.password_is_required_field));
                return;
            }
        }

        this.validator.validate();
    }

    @Click(R.id.patientFormButtonCancel)
    public void onClickCancelButton()
    {
        this.getActivity().getSupportFragmentManager()
                .popBackStack();
    }

    @Click(R.id.patientFormButtonSelectDate)
    public void onClickDatepickerButton()
    {
        this.patientFormDateView.setError(null);

        Calendar now = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show(this.getActivity().getFragmentManager(), "DatePickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth)
    {
        this.patientDateOfBirth.set(Calendar.YEAR, year);
        this.patientDateOfBirth.set(Calendar.MONTH, monthOfYear);
        this.patientDateOfBirth.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        this.patientFormDateView.setText(this.simpleDateFormat.format(this.patientDateOfBirth.getTime()));
    }

    @Override
    public void onValidationSucceeded()
    {
        RadioButton radioGender = (RadioButton) this.getActivity().findViewById(this.patientFormRadioGender.getCheckedRadioButtonId());

        if(this.patient == null && this.patient.getId() == null)
        {
            this.patient = new Patient(
                    this.patientFormEditName.getText().toString(),
                    this.patientFormEditPhone.getText().toString(),
                    Gender.valueOf(radioGender.getTag().toString()),
                    this.patientDateOfBirth,
                    this.patientFormEditAddress.getText().toString(),
                    this.patientFormEditEmergencyPhone.getText().toString(),
                    this.patientFormEditNotes.getText().toString(),
                    this.patientFormEditUsername.getText().toString(),
                    User.encryptPassword( this.patientFormEditPassword.getText().toString() ),
                    new Caregiver(this.generalPreferences.loggedUserId().get())
            );
            ((CaregiverHomeActivity) this.getActivity()).addPatient(this.patient);
        }
        else
        {
            this.patient.setName(this.patientFormEditName.getText().toString());
            this.patient.setPhone(this.patientFormEditPhone.getText().toString());
            this.patient.setGender(Gender.valueOf(radioGender.getTag().toString()));
            this.patient.setBirthDate(this.patientDateOfBirth);
            this.patient.setAddress(this.patientFormEditAddress.getText().toString());
            this.patient.setEmergencyPhone(this.patientFormEditEmergencyPhone.getText().toString());
            this.patient.setNote(this.patientFormEditNotes.getText().toString());
            this.patient.setUsername(this.patientFormEditUsername.getText().toString());
            this.patient.setPassword(User.encryptPassword(this.patientFormEditPassword.getText().toString()));

            ((CaregiverHomeActivity) this.getActivity()).updatePatient(this.patient);
        }

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
                this.patientFormRadioFemale.setError(message);
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
