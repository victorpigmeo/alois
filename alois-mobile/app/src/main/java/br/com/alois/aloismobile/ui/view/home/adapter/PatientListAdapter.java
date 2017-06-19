package br.com.alois.aloismobile.ui.view.home.adapter;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import br.com.alois.aloismobile.ui.view.home.adapter.row.PatientListRow;
import br.com.alois.aloismobile.ui.view.home.adapter.row.PatientListRow_;
import br.com.alois.domain.entity.user.Patient;

/**
 * Created by victor on 3/17/17.
 */
@EBean
public class PatientListAdapter extends BaseAdapter
{
    //=====================================ATTRIBUTES=======================================
    private List<Patient> patients = new ArrayList<Patient>();

    //======================================================================================

    //=====================================INJECTIONS=======================================
    @RootContext
    AppCompatActivity context;

    //======================================================================================

    //==================================GETTERS/SETTERS=====================================
    public void setPatients(List<Patient> patients)
    {
        this.patients = patients;
        this.notifyDataSetChanged();
    }

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @Override
    public int getCount()
    {
        return this.patients.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.patients.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return this.patients.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        PatientListRow patientListRow;
        if (convertView == null)
        {
            patientListRow = PatientListRow_.build(this.context);
        }
        else
        {
            patientListRow = (PatientListRow) convertView;
        }

        patientListRow.bind(this.patients.get(position), this.context);

        return patientListRow;
    }

    public void onInsertPatient(Patient patient)
    {
        this.patients.add(patient);
        this.notifyDataSetChanged();
    }

    public void onUpdatePatient(Patient patient) {
        for(Patient listPatient: this.patients)
        {
            if( listPatient.getId().equals( patient.getId() ) )
            {
                listPatient = patient;
            }
        }

        this.notifyDataSetChanged();
    }

    public void onDeletePatient(Patient patient)
    {
        this.patients.remove(patient);
        this.notifyDataSetChanged();
    }
    //======================================================================================

}
