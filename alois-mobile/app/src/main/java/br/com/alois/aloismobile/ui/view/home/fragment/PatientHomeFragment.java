package br.com.alois.aloismobile.ui.view.home.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.ui.view.home.PatientHomeActivity;
import br.com.alois.aloismobile.ui.view.memory.fragment.MemoryListFragment;
import br.com.alois.aloismobile.ui.view.memory.fragment.MemoryListFragment_;
import br.com.alois.aloismobile.ui.view.reminder.fragment.ReminderListFragment;
import br.com.alois.aloismobile.ui.view.reminder.fragment.ReminderListFragment_;
import br.com.alois.aloismobile.ui.view.route.fragment.RouteListFragment;
import br.com.alois.aloismobile.ui.view.route.fragment.RouteListFragment_;
import br.com.alois.domain.entity.memory.Memory;
import br.com.alois.domain.entity.reminder.Reminder;
import br.com.alois.domain.entity.route.Route;
import br.com.alois.domain.entity.user.Patient;
import br.com.alois.domain.entity.user.Request;
import br.com.alois.domain.entity.user.RequestStatus;
import br.com.alois.domain.entity.user.RequestType;

@EFragment(R.layout.fragment_patient_home)
public class PatientHomeFragment extends Fragment
{
    //=====================================ATTRIBUTES=======================================
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

    @ViewById(R.id.patientHomeLogoffButton)
    Button patientHomeLogoffButton;

    private Patient patient;

    private MemoryListFragment memoryListFragment;

    private RouteListFragment routeListFragment;

    private ReminderListFragment reminderListFragment;
    //======================================================================================

    //=====================================INJECTIONS=======================================
    @FragmentArg("patientId")
    Long patientId;
    private List<Reminder> patientReminderList;

    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public PatientHomeFragment(){/*Required empty constructor*/}

    //======================================================================================

    //==================================GETTERS/SETTERS=====================================
    public void setPatient(Patient patient)
    {
        this.patient = patient;

        if (this.patient != null)
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

            int genderStringId = 0;
            switch (patient.getGender())
            {
                case MALE:
                    genderStringId = R.string.MALE;
                    break;
                case FEMALE:
                    genderStringId = R.string.FEMALE;
                    break;
            }

            this.patientDetailName.setText(this.patient.getName());
            this.patientDetailPhone.setText(this.patient.getPhone());
            this.patientDetailGender.setText(this.getActivity().getResources().getString(genderStringId));
            this.patientDetailDateOfBirth.setText(simpleDateFormat.format(this.patient.getBirthDate().getTime()));
            this.patientDetailAddress.setText(this.patient.getAddress());
            this.patientDetailEmergencyPhone.setText(this.patient.getEmergencyPhone());
//            this.patientDetailNote.setText(this.patient.getNote());
        }
    }

    public Patient getPatient(){
        return this.patient;
    }

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @AfterViews
    public void onAfterViews()
    {
        ((PatientHomeActivity) this.getActivity()).getPatient(this.patientId);

        ((PatientHomeActivity) this.getActivity()).getPatientLogoffApprovedRequest(this.patientId);
    }

    @Click(R.id.patientHomeRequestLogoff)
    public void onPatientHomeRequestLogoffClick()
    {
        Request request = new Request();
        request.setPatient(this.patient);
        request.setRequestStatus(RequestStatus.PENDING);
        request.setTimeRequested(Calendar.getInstance());
        request.setRequestType(RequestType.LOGOFF);

        ((PatientHomeActivity) this.getActivity()).requestLogoff(request);
    }

    public void showLogoffButton()
    {
        this.patientHomeLogoffButton.setVisibility(View.VISIBLE);
    }


    @Click(R.id.patientHomeLogoffButton)
    public void onPatientHomeLogoffButton()
    {
        ((PatientHomeActivity) this.getActivity()).onPatientLogoff(this.patient);
    }

    @Click(R.id.patientHomeRoutesButton)
    public void onPatientHomeRoutesButtonClick()
    {
        this.routeListFragment = RouteListFragment_.builder()
                .patient(this.patient)
                .build();

        this.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.patient_home_frame_layout, routeListFragment)
                .addToBackStack("routeListFragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    public void setPatientRoutes(List<Route> patientRoutes)
    {
        this.routeListFragment.setPatientRouteList(patientRoutes);
    }

    @Click(R.id.patientHomeRemindersButton)
    public void onPatientHomeRemindersButtonClick()
    {
        this.reminderListFragment = ReminderListFragment_.builder()
                .patient(this.patient)
                .build();

        this.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.patient_home_frame_layout, reminderListFragment)
                .addToBackStack("routeListFragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    public void setPatientReminderList(List<Reminder> patientReminderList)
    {
        this.reminderListFragment.setReminderList(patientReminderList);
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

    public void setPatientMemoryList(List<Memory> memoryList)
    {
        this.memoryListFragment.setPatientMemoryList(memoryList);
    }

    public void setPatientMemory(Memory memory)
    {
        this.memoryListFragment.setPatientMemory(memory);
    }

    public void onInsertMemory(Memory memory)
    {
        this.memoryListFragment.onInsertMemory(memory);
    }

    public void onUpdateMemory(Memory memory)
    {
        this.memoryListFragment.onUpdateMemory(memory);
    }
    //======================================================================================

}
