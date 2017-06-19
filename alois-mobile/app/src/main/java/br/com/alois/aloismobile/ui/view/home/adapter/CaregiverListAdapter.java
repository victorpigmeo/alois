package br.com.alois.aloismobile.ui.view.home.adapter;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import br.com.alois.aloismobile.ui.view.home.adapter.row.CaregiverListRow;
import br.com.alois.aloismobile.ui.view.home.adapter.row.CaregiverListRow_;
import br.com.alois.domain.entity.user.Caregiver;

/**
 * Created by victor on 15/06/17.
 */
@EBean
public class CaregiverListAdapter extends BaseAdapter
{

    //=====================================ATTRIBUTES=======================================
    List<Caregiver> caregiverList = new ArrayList<Caregiver>();

    @RootContext
    AppCompatActivity activity;
    //======================================================================================

    //=====================================INJECTIONS=======================================

    //======================================================================================

    //====================================CONSTRUCTORS======================================

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
            caregiverListRow = CaregiverListRow_.build(this.activity);
        }
        else
        {
            caregiverListRow = (CaregiverListRow) convertView;
        }

        caregiverListRow.bind(this.caregiverList.get(position), this.activity);

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
        notifyDataSetChanged();
    }

    public void onAddCaregiver(Caregiver caregiver)
    {
        this.caregiverList.add(caregiver);
        notifyDataSetChanged();
    }
    //======================================================================================

}

