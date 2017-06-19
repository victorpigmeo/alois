package br.com.alois.aloismobile.ui.view.home.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.application.api.patient.PatientTasks;
import br.com.alois.aloismobile.ui.view.home.PatientHomeActivity;
import br.com.alois.aloismobile.ui.view.home.adapter.PatientListAdapter;
import br.com.alois.aloismobile.ui.view.memory.fragment.MemoryListFragment;
import br.com.alois.aloismobile.ui.view.memory.fragment.MemoryListFragment_;
import br.com.alois.aloismobile.ui.view.patient.PatientDetailActivity_;
import br.com.alois.aloismobile.ui.view.patient.fragment.PatientFormFragment;
import br.com.alois.aloismobile.ui.view.patient.fragment.PatientFormFragment_;
import br.com.alois.domain.entity.user.Patient;

@EFragment(R.layout.fragment_patient_home)
public class PatientHomeFragment extends Fragment
{
    //=====================================ATTRIBUTES=======================================
    @ViewById(R.id.buttonMyRoutes)
    Button myRoutes;

    @ViewById(R.id.buttonMyMemories)
    Button myMemories;

    @ViewById(R.id.buttonMyReminders)
    Button myReminders;

    @ViewById(R.id.patientDetailName)
    TextView patientDetailName;

    @ViewById(R.id.patientDetailPhone)
    TextView patientDetailPhone;

    @ViewById(R.id.patientDetailGender)
    TextView patientDetailGender;

    @ViewById(R.id.patientDetailDateOfBirth)
    TextView patientDetailDateOfBirth;

    @ViewById(R.id.patientDetailAddress)
    TextView patientDetailAddress;

    @ViewById(R.id.patientDetailEmergenyPhone)
    TextView patientDetailEmergencyPhone;

    @ViewById(R.id.patientDetailNote)
    TextView patientDetailNote;

    private Patient patient;

    public MemoryListFragment memoryListFragment;

    //======================================================================================

    //=====================================INJECTIONS=======================================
    @FragmentArg("patientId")
    Long patientId;

    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public PatientHomeFragment(){/*Required empty constructor*/}

    //======================================================================================

    //==================================GETTERS/SETTERS=====================================
    public void setPatient(Patient _patient)
    {
        this.patient = _patient;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if (this.patient != null)
        {
            this.patientDetailName.setText(this.patient.getName());
            this.patientDetailPhone.setText(this.patient.getPhone());
            this.patientDetailGender.setText(this.patient.getGender().toString());
            this.patientDetailDateOfBirth.setText(simpleDateFormat.format(this.patient.getBirthDate().getTime()));
            this.patientDetailAddress.setText(this.patient.getAddress());
            this.patientDetailEmergencyPhone.setText(this.patient.getEmergencyPhone());
            this.patientDetailNote.setText(this.patient.getNote());
        }
    }

    public Patient getPatient(){
        return this.patient;
    }


    @Click(R.id.buttonMyMemories)
    public void onClickButtonMyMemories()
    {
        this.memoryListFragment = MemoryListFragment_.builder().patient(patient).build();

        this.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.patient_home_frame_layout, memoryListFragment)
                .addToBackStack("memory_list_fragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }


    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @AfterViews
    public void onAfterViews()
    {
        ((PatientHomeActivity) this.getActivity()).getPatient(this.patientId);
    }

    //======================================================================================

}
