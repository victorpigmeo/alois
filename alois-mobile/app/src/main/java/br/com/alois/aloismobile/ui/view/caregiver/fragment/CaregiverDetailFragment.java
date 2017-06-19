package br.com.alois.aloismobile.ui.view.caregiver.fragment;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import br.com.alois.aloismobile.R;
import br.com.alois.domain.entity.user.Caregiver;

/**
 * Created by victor on 15/06/17.
 */
@EFragment(R.layout.fragment_caregiver_detail)
public class CaregiverDetailFragment extends Fragment
{
    //=====================================ATTRIBUTES=======================================
    @ViewById(R.id.caregiverDetailName)
    TextView caregiverDetailName;

    @ViewById(R.id.caregiverDetailEmail)
    TextView caregiverDetailEmail;

    @ViewById(R.id.caregiverDetailGender)
    TextView caregiverDetailGender;

    @ViewById(R.id.caregiverDetailUsername)
    TextView caregiverDetailUsername;

    //======================================================================================

    //=====================================INJECTIONS=======================================
    @FragmentArg("caregiver")
    Caregiver caregiver;

    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public CaregiverDetailFragment()
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
        this.caregiverDetailName.setText(this.caregiver.getName());
        this.caregiverDetailEmail.setText(this.caregiver.getEmail());
        this.caregiverDetailGender.setText(this.caregiver.getGender().toString());
        this.caregiverDetailUsername.setText(this.caregiver.getUsername());
    }

    //======================================================================================
}
