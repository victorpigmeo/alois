package br.com.alois.aloismobile.ui.view.requests.adapter;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import br.com.alois.aloismobile.ui.view.requests.adapter.row.MemoryRequestListRow;
import br.com.alois.aloismobile.ui.view.requests.adapter.row.MemoryRequestListRow_;
import br.com.alois.domain.entity.user.Request;

/**
 * Created by thiago on 15/06/17.
 */
@EBean
public class MemoryRequestListAdapter extends BaseAdapter {

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
        MemoryRequestListRow memoryDeleteRequestListRow;

        if( convertView == null )
        {
           memoryDeleteRequestListRow = MemoryRequestListRow_.build(this.context);
        }
        else
        {
            memoryDeleteRequestListRow = (MemoryRequestListRow) convertView;
        }

        memoryDeleteRequestListRow.bind(this.requests.get(position), this.context);

        return memoryDeleteRequestListRow;
    }

    public void setMemoryDeleteRequests(List<Request> memoryDeleteRequests)
    {
        this.requests = memoryDeleteRequests;
        this.notifyDataSetChanged();
    }

    public void onDeleteRequest(Request request)
    {
        this.requests.remove(request);
        this.notifyDataSetChanged();
    }
}
