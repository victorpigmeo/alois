package br.com.alois.aloismobile.ui.view.login.adapter;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;

import org.androidannotations.annotations.EBean;

import br.com.alois.domain.entity.user.Gender;

/**
 * Created by victor on 3/9/17.
 */

@EBean
public class GenderSpinnerAdapter implements SpinnerAdapter{
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return Gender.values().length;
    }

    @Override
    public Object getItem(int position) {
        return Gender.values()[position];
    }

    @Override
    public long getItemId(int position) {
        return Gender.values()[position].ordinal();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
