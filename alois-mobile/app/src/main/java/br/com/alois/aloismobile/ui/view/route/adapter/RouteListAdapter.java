package br.com.alois.aloismobile.ui.view.route.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import br.com.alois.aloismobile.ui.view.route.adapter.row.RouteListRow;
import br.com.alois.aloismobile.ui.view.route.adapter.row.RouteListRow_;
import br.com.alois.domain.entity.route.Route;

/**
 * Created by victor on 3/24/17.
 */

@EBean
public class RouteListAdapter extends BaseAdapter
{
    //=====================================ATTRIBUTES=======================================
    List<Route> routeList = new ArrayList<Route>();

    Context context;
    //======================================================================================

    //=====================================INJECTIONS=======================================

    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public RouteListAdapter(Context context)
    {
        this.context = context;
    }
    //======================================================================================

    //==================================GETTERS/SETTERS=====================================
    public void setRouteList(List<Route> routeList)
    {
        this.routeList = routeList;
        this.notifyDataSetChanged();
    }
    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @Override
    public int getCount()
    {
        return this.routeList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.routeList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return this.routeList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        RouteListRow routeListRow;
        if(convertView == null)
        {
            routeListRow = RouteListRow_.build(this.context);
        }
        else
        {
            routeListRow = (RouteListRow) convertView;
        }

        routeListRow.bind(this.routeList.get(position));

        return routeListRow;
    }

    public void onAddRoute(Route route) {
        this.routeList.add(route);
        this.notifyDataSetChanged();
    }
    //======================================================================================

}
