package br.com.alois.aloismobile.ui.view.login.fragment;

import android.net.ConnectivityManager;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Select;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.application.util.AloisUtils;
import br.com.alois.aloismobile.ui.view.login.LoginActivity;
import br.com.alois.aloismobile.ui.view.login.adapter.GenderSpinnerAdapter;
import br.com.alois.domain.entity.user.Caregiver;
import br.com.alois.domain.entity.user.Gender;

@EFragment(R.layout.fragment_signup)
public class SignupFragment extends Fragment implements Validator.ValidationListener
{
    //=====================================ATTRIBUTES=======================================
    @ViewById(R.id.editName)
    @NotEmpty(messageResId = R.string.name_is_required_field)
    EditText editName;

    @ViewById(R.id.editEmail)
    @NotEmpty(messageResId = R.string.email_is_required_field)
    EditText editEmail;

    @ViewById(R.id.editUsername)
    @NotEmpty(messageResId = R.string.username_is_required_field)
    EditText editUserName;

    @ViewById(R.id.editPassword)
    @Password(messageResId = R.string.password_is_required_field)
    EditText editPassword;

    @ViewById(R.id.radioGender)
    @Checked(messageResId = R.string.gender_is_required)
    RadioGroup radioGender;

    @ViewById(R.id.radioFemale)
    RadioButton radioFemale;

    @Bean
    GenderSpinnerAdapter genderSpinnerAdapter;

    Validator validator = new Validator(this);

    //=====================================INJECTIONS=======================================
    @SystemService
    ConnectivityManager connectivityManager;

    //======================================================================================

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @AfterViews
    public void onAfterViews()
    {
        this.validator.setValidationListener(this);
    }

    @Click(R.id.buttonSignUp)
    public void onClickSingUp()
    {
        if(this.connectivityManager.getActiveNetworkInfo() != null &&
                this.connectivityManager.getActiveNetworkInfo().isConnected())
        {

            this.validator.validate();
        }
        else
        {
            Toast.makeText(this.getContext(), this.getResources().getString(R.string.need_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onValidationSucceeded()
    {
        RadioButton radioButton = (RadioButton) this.getActivity().findViewById((this.radioGender.getCheckedRadioButtonId()));

        Caregiver caregiver = new Caregiver(
                this.editName.getText().toString(),
                this.editEmail.getText().toString(),
                Gender.valueOf(radioButton.getTag().toString()),
                this.editUserName.getText().toString(),
                AloisUtils.encryptPassword(this.editPassword.getText().toString())
        );

        ((LoginActivity) this.getActivity()).signup(caregiver);
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
                this.radioFemale.setError(message);
            }
            else
            {
                Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
    //======================================================================================
}
