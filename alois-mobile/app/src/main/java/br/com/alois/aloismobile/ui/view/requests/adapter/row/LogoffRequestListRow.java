package br.com.alois.aloismobile.ui.view.requests.adapter.row;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.ui.view.patient.PatientDetailActivity;
import br.com.alois.domain.entity.user.Request;

/**
 * Created by victor on 6/1/17.
 */
@EViewGroup(R.layout.logoff_request_list_row)
public class LogoffRequestListRow extends RelativeLayout
{
    //=====================================ATTRIBUTES=======================================
    @ViewById(R.id.logoffRowTimeRequested)
    TextView logoffRowTimeRequested;

    @ViewById(R.id.requestListRowPopUpMenu)
    ImageButton requestListRowPopUpMenu;

    @ViewById(R.id.requestListRowSign)
    ImageView requestListRowSign;

    private Request request;

    private AppCompatActivity context;

    //======================================================================================

    //=====================================INJECTIONS=======================================

    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public LogoffRequestListRow(Context context)
    {
        super(context);
    }

    //======================================================================================

    //==================================GETTERS/SETTERS=====================================

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    public void bind(Request request, AppCompatActivity context)
    {
        this.request = request;
        this.context = context;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        this.logoffRowTimeRequested.setText(simpleDateFormat.format(this.request.getTimeRequested().getTime()));

        switch (this.request.getRequestStatus())
        {
            case APPROVED:
                this.requestListRowSign.setColorFilter(this.context.getResources().getColor(R.color.colorSuccess), PorterDuff.Mode.SRC_IN);
                this.requestListRowPopUpMenu.setVisibility(INVISIBLE);

                break;
            case DISCARTED:
                this.requestListRowSign.setColorFilter(this.context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
                this.requestListRowPopUpMenu.setVisibility(INVISIBLE);

                break;
            case PENDING:
                this.requestListRowSign.setVisibility(INVISIBLE);
                this.requestListRowPopUpMenu.setVisibility(VISIBLE);

                break;

        }
    }

    @Click(R.id.requestListRowPopUpMenu)
    void onClickPopup()
    {
        PopupMenu popup = new PopupMenu(getContext(), requestListRowPopUpMenu);
        popup.getMenuInflater().inflate(R.menu.request_list_pop_up_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                int id = item.getItemId();
                switch (id)
                {
                    case R.id.requestMenuApproveButton:
                        ((PatientDetailActivity) context).approveLogoffRequest(request);

                        requestListRowSign.setVisibility(VISIBLE);
                        requestListRowSign.setColorFilter(context.getResources().getColor(R.color.colorSuccess), PorterDuff.Mode.SRC_IN);
                        requestListRowPopUpMenu.setVisibility(INVISIBLE);

                        break;
                    case R.id.requestMenuDiscardButton:
                        ((PatientDetailActivity) context).discardLogoffRequest(request);

                        requestListRowSign.setVisibility(VISIBLE);
                        requestListRowSign.setColorFilter(context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
                        requestListRowPopUpMenu.setVisibility(INVISIBLE);

                        break;
                }

                return true;
            }
        });

        popup.show();
    }
    //======================================================================================

}
