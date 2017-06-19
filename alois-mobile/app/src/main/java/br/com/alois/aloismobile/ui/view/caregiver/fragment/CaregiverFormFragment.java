package br.com.alois.aloismobile.ui.view.caregiver.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.ui.view.home.AdministratorHomeActivity;
import br.com.alois.domain.entity.user.Caregiver;
import br.com.alois.domain.entity.user.Gender;
import br.com.alois.domain.entity.user.User;

/**
 * Created by victor on 15/06/17.
 */
@EFragment(R.layout.fragment_caregiver_form)
public class CaregiverFormFragment extends Fragment implements Validator.ValidationListener
{
    //=====================================ATTRIBUTES=======================================
    @ViewById(R.id.caregiverFormTitle)
    TextView caregiverFormTitle;

    @ViewById(R.id.caregiverFormName)
    @NotEmpty(messageResId = R.string.name_is_required_field)
    EditText caregiverFormName;

    @ViewById(R.id.caregiverFormEmail)
    @NotEmpty(messageResId = R.string.email_is_required_field)
    EditText caregiverFormEmail;

    @ViewById(R.id.caregiverFormUsername)
    @NotEmpty(messageResId = R.string.username_is_required_field)
    EditText caregiverFormUsername;

    @ViewById(R.id.caregiverFormPassword)
    EditText caregiverFormPassword;

    @ViewById(R.id.caregiverFormGenderRadio)
    RadioGroup caregiverFormGenderRadio;

    @ViewById(R.id.caregiverFormRadioFemale)
    RadioButton caregiverFormRadioFemale;

    @ViewById(R.id.caregiverFormRadioMale)
    RadioButton caregiverFormRadioMale;

    Validator validator = new Validator(this);

    //======================================================================================

    //=====================================INJECTIONS=======================================
    @FragmentArg("caregiver")
    Caregiver caregiver;

    //======================================================================================

    //====================================CONSTRUCTORS======================================

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @AfterViews
    public void onAfterViews()
    {
        this.validator.setValidationListener(this);

        this.caregiverFormTitle.setText(this.getActivity().getResources().getString(R.string.add_caregiver));

        if(this.caregiver != null)
        {
            this.caregiverFormTitle.setText(this.getActivity().getResources().getString(R.string.edit_caregiver));
            this.caregiverFormName.setText(this.caregiver.getName());
            this.caregiverFormEmail.setText(this.caregiver.getEmail());

            switch (this.caregiver.getGender())
            {
                case MALE:
                    this.caregiverFormRadioMale.toggle();
                    break;
                case FEMALE:
                    this.caregiverFormRadioFemale.toggle();
                    break;
            }

            this.caregiverFormUsername.setText(this.caregiver.getUsername());
        }
    }

    @Click(R.id.caregiverFormButtonSave)
    public void onCaregiverFormButtonSaveClick()
    {
        if(this.caregiver == null)
        {
            if(this.caregiverFormPassword.getText() == null || this.caregiverFormPassword.getText().equals("") || this.caregiverFormPassword.getText().length() < 6)
            {
                this.caregiverFormPassword.setError(this.getActivity().getResources().getString(R.string.password_is_required_field));
                return;
            }

        }

        this.validator.validate();
    }

    @Click(R.id.caregiverFormButtonCancel)
    public void onCaregiverFormButtonCancel()
    {
        this.getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onValidationSucceeded()
    {
        RadioButton radioGender = (RadioButton) this.getActivity().findViewById(this.caregiverFormGenderRadio.getCheckedRadioButtonId());

        if(this.caregiver == null)
        {
            Caregiver caregiver = new Caregiver();
            caregiver.setName(this.caregiverFormName.getText().toString());
            caregiver.setEmail(this.caregiverFormEmail.getText().toString());
            caregiver.setGender(Gender.valueOf(radioGender.getTag().toString()));
            caregiver.setUsername(this.caregiverFormUsername.getText().toString());
            caregiver.setPassword(User.encryptPassword(this.caregiverFormPassword.getText().toString()));

            ((AdministratorHomeActivity) this.getActivity()).addCaregiver(caregiver);
        }
        else
        {
            this.caregiver.setName(this.caregiverFormName.getText().toString());
            this.caregiver.setEmail(this.caregiverFormEmail.getText().toString());
            this.caregiver.setGender(Gender.valueOf(radioGender.getTag().toString()));
            this.caregiver.setUsername(this.caregiverFormUsername.getText().toString());

            if(this.caregiverFormPassword.getText().toString() == null || !this.caregiverFormPassword.getText().toString().equals(""))
            {
                this.caregiver.setPassword(User.encryptPassword(this.caregiverFormPassword.getText().toString()));
            }

            ((AdministratorHomeActivity) this.getActivity()).editCaregiver(this.caregiver);
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
                this.caregiverFormRadioFemale.setError(message);
            }
            else
            {
                Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
    //======================================================================================
}
