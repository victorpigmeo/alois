package br.com.alois.aloismobile.ui.view.home.fragment;


import android.support.v4.app.Fragment;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;


import java.util.List;

import br.com.alois.aloismobile.R;
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

    @ItemClick(R.id.administratorHomeCaregiverList)
    public void onCaregiverListItemClick(Caregiver caregiver)
    {
        //TODO Chamar o fragment de detalhe do cuidador
    }

    public void onUpdateCaregiver(Caregiver caregiver) {
        this.caregiverListAdapter.onUpdateCaregiver(caregiver);
    }

    public void onDeleteCaregiver(Caregiver caregiver)
    {
        this.caregiverListAdapter.onDeleteCaregiver(caregiver);
    }
    //======================================================================================



}
