package br.com.alois.aloismobile.ui.view.reminder.adapter;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import br.com.alois.aloismobile.ui.view.reminder.adapter.row.ReminderListRow;
import br.com.alois.aloismobile.ui.view.reminder.adapter.row.ReminderListRow_;
import br.com.alois.domain.entity.reminder.Reminder;

/**
 * Created by victor on 5/3/17.
 */
@EBean
public class ReminderListAdapter extends BaseAdapter
{
    private List<Reminder> reminders = new ArrayList<Reminder>();

    @RootContext
    AppCompatActivity context;

    @Override
    public int getCount()
    {
        return this.reminders.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.reminders.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return this.reminders.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ReminderListRow reminderListRow;
        if (convertView == null)
        {
            reminderListRow = ReminderListRow_.build(this.context);
        }
        else
        {
            reminderListRow = (ReminderListRow) convertView;
        }

        reminderListRow.bind(this.reminders.get(position), this.context);

        return reminderListRow;
    }
}
