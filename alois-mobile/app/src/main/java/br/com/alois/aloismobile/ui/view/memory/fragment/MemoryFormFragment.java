package br.com.alois.aloismobile.ui.view.memory.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.application.preference.GeneralPreferences_;
import br.com.alois.aloismobile.ui.view.home.CaregiverHomeActivity;
import br.com.alois.aloismobile.ui.view.home.PatientHomeActivity;
import br.com.alois.domain.entity.memory.Memory;
import br.com.alois.domain.entity.user.Caregiver;
import br.com.alois.domain.entity.user.Gender;
import br.com.alois.domain.entity.user.Patient;
import br.com.alois.domain.entity.user.User;

@EFragment(R.layout.fragment_memory_form)
public class MemoryFormFragment extends Fragment implements  Validator.ValidationListener{
    //=====================================ATTRIBUTES=======================================
    @ViewById(R.id.memoryFormTitleEdit)
    @NotEmpty(messageResId = R.string.memory_title_is_required)
    EditText memoryFormTitleEdit;

    @ViewById(R.id.memoryFormDescriptionEdit)
    @NotEmpty(messageResId = R.string.memory_description_is_required)
    EditText memoryFormDescriptionEdit;

    @ViewById(R.id.memoryFormImageView)
    ImageView memoryFormImageView;

    private Patient patient;

    Validator validator = new Validator(this);
    //======================================================================================

    //=====================================INJECTIONS=======================================
    @FragmentArg("memory")
    Memory memory;

    @FragmentArg("image")
    Bitmap image;

    @Pref
    GeneralPreferences_ generalPreferences;

    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public MemoryFormFragment(){/*Required empty constructor*/}

    //======================================================================================

    //==================================GETTERS/SETTERS=====================================

    @AfterViews
    public void onAfterViews()
    {
        this.validator.setValidationListener(this);
        if(this.memory != null && this.memory.getId() != null)
        {
            this.memoryFormTitleEdit.setText(this.memory.getTitle());
            this.memoryFormDescriptionEdit.setText(this.memory.getDescription());
            Bitmap bmp = BitmapFactory.decodeByteArray(this.memory.getFile(), 0, this.memory.getFile().length);
            this.memoryFormImageView.setImageBitmap(bmp);

        }else{
            this.memoryFormImageView.setImageBitmap(image);
        }
    }

    @Click(R.id.memoryFormSaveButton)
    public void onClickSaveButton()
    {
        this.validator.validate();
    }

    @Override
    public void onValidationSucceeded()
    {
        if(this.memory == null)
        {

            this.memory = new Memory(
                    this.memoryFormTitleEdit.getText().toString(),
                    this.memoryFormDescriptionEdit.getText().toString(),
                    this.convertToByte(this.image),
                    Calendar.getInstance(),
                    new Patient(this.generalPreferences.loggedUserId().get())
            );
            ((PatientHomeActivity) this.getActivity()).addMemory(this.memory);
        }
        else
        {
            this.memory.setTitle(this.memoryFormTitleEdit.getText().toString());
            this.memory.setDescription(this.memoryFormDescriptionEdit.getText().toString());
            this.memory.setPatient(new Patient(this.generalPreferences.loggedUserId().get()));

            ((PatientHomeActivity) this.getActivity()).updateMemory(this.memory);
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
            else
            {
                Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public byte[] convertToByte(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }

}
