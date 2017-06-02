package br.com.alois.aloismobile.ui.view.requests.adapter;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import br.com.alois.aloismobile.ui.view.requests.adapter.row.LogoffRequestListRow;
import br.com.alois.aloismobile.ui.view.requests.adapter.row.LogoffRequestListRow_;
import br.com.alois.domain.entity.user.Request;

/**
 * Created by victor on 6/1/17.
 */
@EBean
public class LogoffRequestListAdapter extends BaseAdapter
{
    private List<Request> requests = new ArrayList<Request>();

    @RootContext
    AppCompatActivity context;

    @Override
    public int getCount()
    {
        return this.requests.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.requests.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return this.requests.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LogoffRequestListRow logoffRequestListRow;

        if( convertView == null )
        {
            logoffRequestListRow = LogoffRequestListRow_.build(this.context);
        }
        else
        {
            logoffRequestListRow = (LogoffRequestListRow) convertView;
        }

        logoffRequestListRow.bind(this.requests.get(position), this.context);

        return logoffRequestListRow;
    }

    public void setLogoffRequests(List<Request> logoffRequests)
    {
        this.requests = logoffRequests;
        this.notifyDataSetChanged();
    }
}
