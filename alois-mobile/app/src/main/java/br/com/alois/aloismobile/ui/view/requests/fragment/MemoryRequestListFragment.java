package br.com.alois.aloismobile.ui.view.requests.fragment;


import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.ui.view.patient.PatientDetailActivity;
import br.com.alois.aloismobile.ui.view.requests.adapter.MemoryRequestListAdapter;
import br.com.alois.domain.entity.user.Patient;
import br.com.alois.domain.entity.user.Request;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_memory_request_list)
public class MemoryRequestListFragment extends Fragment {
    //=====================================ATTRIBUTES=======================================
    @ViewById(R.id.memoryRequestListView)
    ListView memoryRequestListView;

    //======================================================================================

    //=====================================INJECTIONS=======================================
    @FragmentArg("patient")
    Patient patient;

    @Bean
    MemoryRequestListAdapter memoryRequestListAdapter;

    private ProgressDialog progressDialog;

    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public MemoryRequestListFragment() {
        // Required empty public constructor
    }

    //======================================================================================

    //==================================GETTERS/SETTERS=====================================

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @AfterViews
    public void onAfterViews()
    {
        this.memoryRequestListView.setAdapter(this.memoryRequestListAdapter);

        this.progressDialog = ProgressDialog.show(this.getActivity(),
                this.getResources().getString(R.string.loading_requests),
                this.getResources().getString(R.string.please_wait),
                true,
                true
        );

        ((PatientDetailActivity) this.getActivity()).listPatientMemoryRequests(this.patient);
    }

    public void setPatientMemoryRequests(List<Request> patientMemoryRequests)
    {
        this.progressDialog.dismiss();

        if(this.memoryRequestListView == null)
        {
            this.memoryRequestListAdapter = new MemoryRequestListAdapter();
        }

        this.memoryRequestListAdapter.setMemoryDeleteRequests(patientMemoryRequests);
    }
//======================================================================================}
}