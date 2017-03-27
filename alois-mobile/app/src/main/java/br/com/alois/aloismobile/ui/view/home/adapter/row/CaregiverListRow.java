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
import br.com.alois.domain.entity.user.Caregiver;

/**
 * Created by sarah on 3/23/17.
 */
@EViewGroup(R.layout.caregiver_list_row)
public class CaregiverListRow extends LinearLayout
{
    //=====================================ATTRIBUTES=======================================
    Caregiver caregiver;

    @ViewById(R.id.caregiverRowName)
    TextView caregiverRowName;

    @ViewById(R.id.caregiverRowMenuButton)
    ImageButton caregiverRowMenuButton;

    //======================================================================================

    //=====================================INJECTIONS=======================================

    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public CaregiverListRow(Context context)
    {
        super(context);
    }

    //======================================================================================

    //==================================GETTERS/SETTERS=====================================

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    public void bind(Caregiver caregiver)
    {
        this.caregiverRowName.setText(caregiver.getName());
        this.caregiver = caregiver;
    }

    @Click(R.id.caregiverRowMenuButton)
    public void onCaregiverRowMenuButtonClick()
    {
        PopupMenu popup = new PopupMenu(getContext(), caregiverRowMenuButton);
        popup.getMenuInflater().inflate(R.menu.caregiver_list_pop_up_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem)
            {
                int id = menuItem.getItemId();
                switch (id)
                {
                    case R.id.caregiverListMenuEditButton:
                        Toast.makeText(getContext(), "Edit caregiver: "+caregiver.getName(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.caregiverListMenuDeleteButton:
                        Toast.makeText(getContext(), "Delete caregiver: "+caregiver.getName(), Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        popup.show();
    }
    //======================================================================================

}
