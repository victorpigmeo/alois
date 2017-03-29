package br.com.alois.aloismobile.ui.view.home.adapter.row;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.ui.view.home.CaregiverHomeActivity;
import br.com.alois.aloismobile.ui.view.patient.fragment.PatientFormFragment;
import br.com.alois.aloismobile.ui.view.patient.fragment.PatientFormFragment_;
import br.com.alois.domain.entity.user.Patient;

/**
 * Created by victor on 3/17/17.
 */

@EViewGroup(R.layout.patient_list_row)
public class PatientListRow extends LinearLayout
{
    //=====================================ATTRIBUTES=======================================
    @ViewById(R.id.textViewTest)
    TextView textViewTest;

    @ViewById(R.id.popUpMenu)
    ImageButton popUpButton;

    Patient patient;

    //======================================================================================

    //====================================INJECTIONS========================================
    AppCompatActivity activity;

    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public PatientListRow(Context context)
    {
        super(context);
    }

    //======================================================================================

    //==================================GETTERS/SETTERS=====================================

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    public void bind(Patient patient, AppCompatActivity activity) {
        this.patient = patient;
        this.textViewTest.setText(patient.getName().toString());
        this.activity = activity;
    }

    @Click(R.id.popUpMenu)
    void onClickPopup()
    {
        PopupMenu popup = new PopupMenu(getContext(), popUpButton);
        popup.getMenuInflater().inflate(R.menu.home_caregiver_patient_pop_up_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem)
            {
                int id = menuItem.getItemId();
                switch (id)
                {
                    case R.id.caregiver_home_edit_patient_button:
                        PatientFormFragment patientFormFragment = PatientFormFragment_.builder()
                                .patient(patient)
                                .build();

                        activity.getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.caregiver_home_frame_layout, patientFormFragment)
                                .addToBackStack("patientFormFragment")
                                .commit();
                        break;
                    case R.id.caregiver_home_delete_patient_button:
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity)
                                .setTitle(R.string.are_you_sure)
                                .setMessage(R.string.you_really_want_delete_patient)
                                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        ((CaregiverHomeActivity) activity).deletePatient(patient);
                                    }
                                })
                                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog alert = alertDialog.create();
                        alert.show();
                        break;
                }
                return true;
            }
        });
        popup.show();
    }
    //======================================================================================





}
