package br.com.alois.aloismobile.ui.view.home.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.ui.view.caregiver.fragment.CaregiverFormFragment;
import br.com.alois.aloismobile.ui.view.caregiver.fragment.CaregiverFormFragment_;
import br.com.alois.aloismobile.ui.view.home.adapter.CaregiverListAdapter;
import br.com.alois.domain.entity.user.Caregiver;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_administrator_home)
public class AdministratorHomeFragment extends Fragment
{
    //=====================================ATTRIBUTES=======================================

    @ViewById(R.id.administratorHomeCaregiverList)
    ListView administratorHomeCaregiverList;

    //======================================================================================

    //=====================================INJECTIONS=======================================
    @Bean
    CaregiverListAdapter caregiverListAdapter;
    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public AdministratorHomeFragment()
    {
            // Required empty public constructor
    }

    //======================================================================================

    //==================================GETTERS/SETTERS=====================================
    public void setCaregiverList(List<Caregiver> caregiverList)
    {
        this.caregiverListAdapter.setCaregiverList(caregiverList);
    }
    //======================================================================================

    //=====================================BEHAVIOUR========================================

    @AfterViews
    public void onAfterViews()
    {
        this.administratorHomeCaregiverList.setAdapter(this.caregiverListAdapter);
    }

    @Click(R.id.fabAddCaregiver)
    public void onFabAddCaregiverClick()
    {
        CaregiverFormFragment caregiverFormFragment = CaregiverFormFragment_.builder()
                .build();

        this.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.administratorHomeFrame, caregiverFormFragment)
                .addToBackStack("caregiverFormFragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @ItemClick(R.id.administratorHomeCaregiverList)
    public void onCaregiverListItemClick(Caregiver caregiver)
    {
        AlertDialog caregiverDetailDialog = new AlertDialog.Builder( this.getActivity() ).create();

        View caregiverDetailDialogFragment = this.getActivity().getLayoutInflater().inflate(R.layout.fragment_caregiver_detail, null);

        int genderStringId = 0;

        switch (caregiver.getGender())
        {
            case MALE:
                genderStringId = R.string.MALE;
                break;
            case FEMALE:
                genderStringId = R.string.FEMALE;
                break;
        }

        ( (TextView) caregiverDetailDialogFragment.findViewById( R.id.caregiverDetailName ) ).setText(caregiver.getName());
        ( (TextView) caregiverDetailDialogFragment.findViewById( R.id.caregiverDetailEmail ) ).setText(caregiver.getEmail());
        ( (TextView) caregiverDetailDialogFragment.findViewById( R.id.caregiverDetailGender ) ).setText(this.getActivity().getResources().getString(genderStringId));
        ( (TextView) caregiverDetailDialogFragment.findViewById( R.id.caregiverDetailUsername ) ).setText(caregiver.getUsername());

        caregiverDetailDialog.setView(caregiverDetailDialogFragment);
        caregiverDetailDialog.show();
    }

    public void onUpdateCaregiver(Caregiver caregiver) {
        this.caregiverListAdapter.onUpdateCaregiver(caregiver);
    }

    public void onDeleteCaregiver(Caregiver caregiver)
    {
        this.caregiverListAdapter.onDeleteCaregiver(caregiver);
    }

    public void onAddCaregiver(Caregiver caregiver)
    {
        this.caregiverListAdapter.onAddCaregiver(caregiver);
    }
    //======================================================================================



}
