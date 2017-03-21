package br.com.alois.aloismobile.ui.view.patient.fragment;


import android.support.v4.app.Fragment;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.alois.aloismobile.R;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_patient_form)
public class PatientFormFragment extends Fragment implements DatePickerDialog.OnDateSetListener
{
    //=====================================ATTRIBUTES=======================================
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    Calendar selectedDate = Calendar.getInstance();

    @ViewById(R.id.patientFormDateView)
    TextView patientFormDateView;

    //======================================================================================

    //=====================================INJECTIONS=======================================

    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public PatientFormFragment()
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

    }

    @Click(R.id.patientFormButtonSelectDate)
    public void onClickDatepickerButton()
    {
        Calendar now = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show(this.getActivity().getFragmentManager(), "DatePickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth)
    {
        this.selectedDate.set(Calendar.YEAR, year);
        this.selectedDate.set(Calendar.MONTH, monthOfYear);
        this.selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        this.patientFormDateView.setText(this.simpleDateFormat.format(this.selectedDate.getTime()));
    }

    //======================================================================================

}
