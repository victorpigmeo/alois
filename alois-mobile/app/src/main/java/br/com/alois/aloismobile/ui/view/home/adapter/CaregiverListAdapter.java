package br.com.alois.aloismobile.ui.view.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import br.com.alois.aloismobile.ui.view.home.adapter.row.CaregiverListRow;
import br.com.alois.aloismobile.ui.view.home.adapter.row.CaregiverListRow_;
import br.com.alois.domain.entity.user.Caregiver;
import br.com.alois.domain.entity.user.Patient;

/**
 * Created by sarah on 3/23/17.
 */
@EBean
public class CaregiverListAdapter extends BaseAdapter
{

    //=====================================ATTRIBUTES=======================================
    List<Caregiver> caregiverList = new ArrayList<Caregiver>();

    Context context;
    //======================================================================================

    //=====================================INJECTIONS=======================================

    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public CaregiverListAdapter(Context context)
    {
        this.context = context;
    }
    //======================================================================================

    //==================================GETTERS/SETTERS=====================================

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @Override
    public int getCount()
    {
        return this.caregiverList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.caregiverList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return this.caregiverList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        CaregiverListRow caregiverListRow;
        if (convertView == null)
        {
            caregiverListRow = CaregiverListRow_.build(this.context);
        }
        else
        {
            caregiverListRow = (CaregiverListRow) convertView;
        }

        caregiverListRow.bind(this.caregiverList.get(position));

        return caregiverListRow;
    }

    public void setCaregiverList(List<Caregiver> caregiverList)
    {
        this.caregiverList = caregiverList;
        this.notifyDataSetChanged();
    }

    public void onUpdateCaregiver(Caregiver caregiver) {
        for(Caregiver listCaregiver: this.caregiverList)
        {
            if( listCaregiver.getId().equals( caregiver.getId() ) )
            {
                listCaregiver = caregiver;
            }
        }

        this.notifyDataSetChanged();
    }

    public void onDeleteCaregiver(Caregiver caregiver)
    {
        this.caregiverList.remove(caregiver);
        this.notifyDataSetChanged();
    }
    //======================================================================================

}

