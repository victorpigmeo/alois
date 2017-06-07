package br.com.alois.aloismobile.ui.view.memory.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.ui.view.home.PatientHomeActivity;
import br.com.alois.aloismobile.ui.view.memory.adapter.MemoryListAdapter;
import br.com.alois.aloismobile.ui.view.patient.PatientDetailActivity;
import br.com.alois.aloismobile.ui.view.route.adapter.RouteListAdapter;
import br.com.alois.aloismobile.ui.view.route.fragment.RouteDetailFragment;
import br.com.alois.aloismobile.ui.view.route.fragment.RouteDetailFragment_;
import br.com.alois.aloismobile.ui.view.route.fragment.RouteFormFragment;
import br.com.alois.aloismobile.ui.view.route.fragment.RouteFormFragment_;
import br.com.alois.domain.entity.memory.Memory;
import br.com.alois.domain.entity.route.Route;
import br.com.alois.domain.entity.route.Step;
import br.com.alois.domain.entity.user.Patient;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_memory_list)
public class MemoryListFragment extends Fragment {
    //=====================================ATTRIBUTES=======================================
    @ViewById(R.id.memoryList)
    GridView memoryList;

    @FragmentArg("patient")
    Patient patient;

    //======================================================================================

    //=====================================INJECTIONS=======================================
    @Bean
    MemoryListAdapter memoryListAdapter;

    public MemoryDetailFragment memoryDetailFragment;
    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public MemoryListFragment()
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
        this.memoryList.setAdapter(this.memoryListAdapter);

        ((PatientHomeActivity) this.getActivity()).getMemoriesByPatientId(this.patient.getId());
    }

    public void setPatientMemoryList(List<Memory> patientMemoryList) {
        this.memoryListAdapter.setMemories(patientMemoryList);
    }

    @ItemClick(R.id.memoryList)
    public void onClickButtonMyMemories(Memory memory)
    {
        this.memoryDetailFragment = MemoryDetailFragment_.builder().memory(memory).build();

        this.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.patient_home_frame_layout, memoryDetailFragment)
                .addToBackStack("memory_detail_fragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    public void onAddMemory(Memory memory) {
        this.memoryListAdapter.onInsertMemory(memory);
    }

    public void onUpdateMemory(Memory memory)
    {
        this.memoryListAdapter.onUpdateMemory(memory);
    }

    public void onDeleteMemory(Memory memory)
    {
        this.memoryListAdapter.onDeleteMemory(memory);
    }
    //======================================================================================

}
