package br.com.alois.aloismobile.ui.view.route.adapter.row;

import android.content.Context;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import br.com.alois.aloismobile.R;
import br.com.alois.domain.entity.route.Route;

/**
 * Created by victor on 3/24/17.
 */
@EViewGroup(R.layout.route_list_row)
public class RouteListRow extends LinearLayout
{
    //=====================================ATTRIBUTES=======================================
    @ViewById(R.id.routeRowName)
    TextView routeRowName;

    @ViewById(R.id.routeRowMenuButton)
    ImageButton routeRowMenuButton;

    Route route;

    //======================================================================================

    //=====================================INJECTIONS=======================================

    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public RouteListRow(Context context)
    {
        super(context);
    }
    //======================================================================================

    //==================================GETTERS/SETTERS=====================================

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    public void bind(Route route)
    {
        this.routeRowName.setText(route.getName());
        this.route = route;
    }

    @Click(R.id.routeRowMenuButton)
    public void onRouteRowMenuButtonClick()
    {
        PopupMenu routeRowPopupMenu = new PopupMenu(this.getContext(), this.routeRowMenuButton);
        routeRowPopupMenu.getMenuInflater().inflate(R.menu.route_list_pop_up_menu, routeRowPopupMenu.getMenu());

        routeRowPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                int id = item.getItemId();

                switch (id)
                {
                    case R.id.routeListMenuEditButton:
                        Toast.makeText(getContext(), "Edit route", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.routeListMenuDeleteButton:
                        Toast.makeText(getContext(), "Delete route", Toast.LENGTH_SHORT).show();
                        break;
                }

                return true;
            }
        });

        routeRowPopupMenu.show();
    }
    //======================================================================================

}
