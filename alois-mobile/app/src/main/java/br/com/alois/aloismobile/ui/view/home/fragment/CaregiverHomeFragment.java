package br.com.alois.aloismobile.ui.view.home.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.ListView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.ui.view.home.adapter.PatientListAdapter;
import br.com.alois.aloismobile.ui.view.patient.PatientDetailActivity;
import br.com.alois.aloismobile.ui.view.patient.PatientDetailActivity_;
import br.com.alois.aloismobile.ui.view.patient.fragment.PatientDetailFragment;
import br.com.alois.aloismobile.ui.view.patient.fragment.PatientDetailFragment_;
import br.com.alois.aloismobile.ui.view.patient.fragment.PatientFormFragment;
import br.com.alois.aloismobile.ui.view.patient.fragment.PatientFormFragment_;
import br.com.alois.domain.entity.user.Patient;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_caregiver_home)
public class CaregiverHomeFragment extends Fragment
{
    //=====================================ATTRIBUTES=======================================
    @ViewById(R.id.patientList)
    ListView patientList;

    //======================================================================================

    //=====================================INJECTIONS=======================================
    @Bean
    PatientListAdapter patientListAdapter;
    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public CaregiverHomeFragment(){/*Required empty constructor*/}

    //======================================================================================

    //==================================GETTERS/SETTERS=====================================
    public void setPatientList(List<Patient> patients)
    {
        this.patientListAdapter.setPatients(patients);
    }

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @AfterViews
    public void onAfterViews()
    {
        this.patientList.setAdapter(this.patientListAdapter);
    }

    @ItemClick(R.id.patientList)
    public void onPatientListItemClick(Patient patient)
    {
        Intent patientDetailActivity = PatientDetailActivity_
                .intent(this.getActivity().getApplicationContext())
                .patient(patient)
                .get();

        this.getActivity().startActivity(patientDetailActivity);
    }

    @Click(R.id.fab_add_patient)
    public void onFabAddPatientClick()
    {
        PatientFormFragment patientFormFragment = PatientFormFragment_.builder().build();

        this.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.caregiver_home_frame_layout, patientFormFragment)
                .addToBackStack("patient_form_fragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    public void onInsertPatient(Patient patient)
    {
        this.patientListAdapter.onInsertPatient(patient);
    }

    public void onUpdatePatient(Patient patient) {
        this.patientListAdapter.onUpdatePatient(patient);
    }

    public void onDeletePatient(Patient patient)
    {
        this.patientListAdapter.onDeletePatient(patient);
    }
    //======================================================================================

}
