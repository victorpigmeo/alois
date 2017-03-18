package br.com.alois.aloismobile.ui.view.login.fragment;

import android.net.ConnectivityManager;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.ui.view.login.LoginActivity;
import br.com.alois.domain.entity.user.User;

@EFragment(R.layout.fragment_login)
public class LoginFragment extends Fragment implements Validator.ValidationListener {
    //=====================================ATTRIBUTES=======================================
    @ViewById(R.id.editUsername)
    @NotEmpty(messageResId = R.string.username_is_required_field)
    EditText editUsername;

    @ViewById(R.id.editPassword)
    @Password(messageResId = R.string.password_is_required_field)
    EditText editPassword;

    Validator validator = new Validator(this);
    //======================================================================================

    //=====================================INJECTIONS=======================================
    @SystemService
    ConnectivityManager connectivityManager;

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @AfterViews
    public void onAfterViews()
    {
        this.validator.setValidationListener(this);
    }
    
    @Click(R.id.buttonSignIn)
    public void onClickSignIn(){
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

    //Tem um bug aqui no onResume

    @Click(R.id.buttonRegister)
    public void onClickSignup(){
        final SignupFragment signupFragment = SignupFragment_.builder().build();

        this.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.loginFrameLayout, signupFragment)
                .addToBackStack("loginFragment")
                .commit();
    }

    @Override
    public void onValidationSucceeded() {
        String username = this.editUsername.getText().toString();
        String password = User.encryptPassword(this.editPassword.getText().toString());

        ((LoginActivity) this.getActivity()).login(username, password);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors)
        {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this.getContext());

            // Display error messages ;)
            if (view instanceof EditText)
            {
                ((EditText) view).setError(message);
            }
            else
            {
                Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
    //======================================================================================

}
