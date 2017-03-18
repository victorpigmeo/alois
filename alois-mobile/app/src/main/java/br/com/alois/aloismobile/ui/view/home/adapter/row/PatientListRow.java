package br.com.alois.aloismobile.ui.view.home.adapter.row;

import android.content.Context;
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

    //====================================CONSTRUCTORS======================================
    public PatientListRow(Context context)
    {
        super(context);
    }

    //======================================================================================

    //==================================GETTERS/SETTERS=====================================

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    public void bind(Patient patient) {
        this.patient = patient;
        this.textViewTest.setText(patient.getName().toString());
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
                        Toast.makeText(getContext(), "Edit patient: "+patient.getName(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.caregiver_home_delete_patient_button:
                        Toast.makeText(getContext(), "Delete patient: "+patient.getName(), Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        popup.show();
    }
    //======================================================================================





}
