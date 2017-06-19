package br.com.alois.aloismobile.ui.view.home.adapter.row;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.ui.view.caregiver.fragment.CaregiverFormFragment;
import br.com.alois.aloismobile.ui.view.caregiver.fragment.CaregiverFormFragment_;
import br.com.alois.aloismobile.ui.view.home.AdministratorHomeActivity;
import br.com.alois.domain.entity.user.Caregiver;

/**
 * Created by victor on 15/06/17.
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
    AppCompatActivity activity;

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
    public void bind(Caregiver caregiver, AppCompatActivity activity)
    {
        this.caregiverRowName.setText(caregiver.getName());
        this.caregiver = caregiver;
        this.activity = activity;
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
                        CaregiverFormFragment caregiverFormFragment = CaregiverFormFragment_.builder()
                                .caregiver(caregiver)
                                .build();

                        activity.getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.administratorHomeFrame, caregiverFormFragment)
                                .addToBackStack("caregiverFormFragment")
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit();

                        break;
                    case R.id.caregiverListMenuDeleteButton:
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext())
                                .setTitle(R.string.confirm_delete_caregiver)
                                .setMessage(R.string.delete_caregiver_consequences)
                                .setPositiveButton(R.string.mdtp_ok, new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        ((AdministratorHomeActivity) activity).removeCaregiver(caregiver);
                                    }
                                })
                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        return;
                                    }
                                });

                        alertDialog.show();
                        break;
                }
                return true;
            }
        });
        popup.show();
    }
    //======================================================================================

}
