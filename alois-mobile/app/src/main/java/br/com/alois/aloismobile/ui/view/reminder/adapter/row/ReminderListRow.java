package br.com.alois.aloismobile.ui.view.reminder.adapter.row;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.ui.view.patient.PatientDetailActivity;
import br.com.alois.domain.entity.reminder.Reminder;

/**
 * Created by victor on 5/3/17.
 */
@EViewGroup(R.layout.reminder_list_row)
public class ReminderListRow extends RelativeLayout
{
    //=====================================ATTRIBUTES=======================================
    @ViewById(R.id.reminderListRowName)
    TextView reminderListRowName;

    @ViewById(R.id.reminderListRowPopUpMenu)
    ImageButton reminderListRowPopUpMenu;

    @ViewById(R.id.reminderListRowSign)
    ImageButton reminderListRowSign;

    private AppCompatActivity activity;

    private Reminder reminder;

    //======================================================================================

    //=====================================INJECTIONS=======================================

    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public ReminderListRow(Context context)
    {
        super(context);
    }

    //======================================================================================

    //==================================GETTERS/SETTERS=====================================

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    public void bind(Reminder reminder, AppCompatActivity context)
    {
        this.reminder = reminder;
        this.activity = context;
        this.reminderListRowName.setText(reminder.getTitle());

        switch (reminder.getReminderStatus())
        {
            case ACTIVE:
                this.reminderListRowSign.setVisibility(INVISIBLE);
                break;
            case PENDING:
                this.reminderListRowSign.setColorFilter(this.activity.getResources().getColor(R.color.colorWarn), PorterDuff.Mode.SRC_IN);

                this.reminderListRowSign.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Toast.makeText(activity, activity.getResources().getString(R.string.reminder_pending), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case INACTIVE:
                this.reminderListRowSign.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Toast.makeText(activity, activity.getResources().getString(R.string.reminder_inactive), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    @Click(R.id.reminderListRowPopUpMenu)
    void onClickPopup()
    {
        PopupMenu popup = new PopupMenu(getContext(), reminderListRowPopUpMenu);
        popup.getMenuInflater().inflate(R.menu.reminder_list_pop_up_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem)
            {
                int id = menuItem.getItemId();
                switch (id)
                {
                    case R.id.reminderListMenuEditButton:

                        break;
                    case R.id.reminderListMenuDeleteButton:
                        ((PatientDetailActivity) activity).deleteReminder(reminder);
                        break;
                }
                return true;
            }
        });
        popup.show();
    }
    //======================================================================================
}
