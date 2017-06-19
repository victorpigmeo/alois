package br.com.alois.aloismobile.ui.view.requests.fragment;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.ui.view.requests.adapter.RequestsPagerAdapter;
import br.com.alois.domain.entity.user.Patient;
import br.com.alois.domain.entity.user.Request;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_request_list)
public class RequestListFragment extends Fragment
{
    //=====================================ATTRIBUTES=======================================
    @FragmentArg("patient")
    Patient patient;

    @ViewById(R.id.patientRequestTabs)
    TabLayout patientRequestTabs;

    @ViewById(R.id.viewpager)
    ViewPager viewPager;

    LogoffRequestListFragment logoffRequestListFragment;

    MemoryRequestListFragment memoryRequestListFragment;

    //======================================================================================

    //=====================================INJECTIONS=======================================

    //======================================================================================

    //====================================CONSTRUCTORS======================================

    //======================================================================================

    //==================================GETTERS/SETTERS=====================================

    public void setPatientLogoffRequests(List<Request> patientLogoffRequests)
    {
        this.logoffRequestListFragment.setPatientLogoffRequests( patientLogoffRequests );
    }

    public void setPatientMemoryRequests(List<Request> patientMemoryRequests)
    {
        this.memoryRequestListFragment.setPatientMemoryRequests( patientMemoryRequests );
    }
    //======================================================================================

    //=====================================BEHAVIOUR========================================

    @AfterViews
    public void onAfterViews()
    {
        this.logoffRequestListFragment = LogoffRequestListFragment_.builder()
                .patient(this.patient)
                .build();

        this.memoryRequestListFragment = MemoryRequestListFragment_.builder()
                .patient(this.patient)
                .build();

        RequestsPagerAdapter requestsPagerAdapter = new RequestsPagerAdapter(getChildFragmentManager());

        requestsPagerAdapter.addFragment(this.logoffRequestListFragment, "Logoff");
        requestsPagerAdapter.addFragment(this.memoryRequestListFragment, "Memories");

        this.viewPager.setAdapter(requestsPagerAdapter);

        this.patientRequestTabs.setupWithViewPager(this.viewPager);

    }

    public void onApproveMemoryRequest(Request request){
        this.memoryRequestListFragment.memoryRequestListAdapter.onDeleteRequest(request);
    }
    //======================================================================================

}
