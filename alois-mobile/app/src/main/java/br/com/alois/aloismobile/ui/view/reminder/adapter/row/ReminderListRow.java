package br.com.alois.aloismobile.ui.view.reminder.adapter.row;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import org.androidannotations.annotations.EViewGroup;

import br.com.alois.aloismobile.R;
import br.com.alois.domain.entity.reminder.Reminder;

/**
 * Created by victor on 5/3/17.
 */
@EViewGroup(R.layout.reminder_list_row)
public class ReminderListRow extends LinearLayout
{
    //=====================================ATTRIBUTES=======================================
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
        //TODO TERMINAR ESSA PORRA
    }

    //======================================================================================
}
