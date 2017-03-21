package br.com.alois.aloismobile.ui.view.home.fragment;

import android.support.v4.app.Fragment;
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
    @ViewById(R.id.patient_list)
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

    @ItemClick(R.id.patient_list)
    public void onPatientListItemClick(Patient patient)
    {
        Toast.makeText(this.getContext(), patient.getName().toString(), Toast.LENGTH_SHORT).show();
    }

    @Click(R.id.fab_add_patient)
    public void onFabAddPatientClick()
    {
        PatientFormFragment patientFormFragment = PatientFormFragment_.builder().build();

        this.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.caregiver_home_frame_layout, patientFormFragment)
                .addToBackStack("patient_form_fragment")
                .commit();
    }
    //======================================================================================

}
