package br.com.alois.aloismobile.ui.view.memory.adapter;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import br.com.alois.aloismobile.ui.view.memory.adapter.item.MemoryListItem;
import br.com.alois.aloismobile.ui.view.memory.adapter.item.MemoryListItem_;
import br.com.alois.domain.entity.memory.Memory;

/**
 * Created by thiago on 12/04/17.
 */
@EBean
public class MemoryListAdapter extends BaseAdapter {
    //=====================================ATTRIBUTES=======================================
    private List<Memory> memories = new ArrayList<Memory>();

    //======================================================================================
    //=====================================INJECTIONS=======================================
    @RootContext
    AppCompatActivity context;

    //======================================================================================

    //==================================GETTERS/SETTERS=====================================
    public void setMemories(List<Memory> memories)
    {
        this.memories = memories;
        this.notifyDataSetChanged();
    }

    //=====================================BEHAVIOUR========================================
    @Override
    public int getCount()
    {
        return this.memories.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.memories.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return this.memories.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        MemoryListItem memoryListItem;
        if (convertView == null)
        {
            memoryListItem = MemoryListItem_.build(this.context);
        }
        else
        {
            memoryListItem = (MemoryListItem) convertView;
        }

        memoryListItem.bind(this.memories.get(position), this.context);

        return memoryListItem;
    }

    public void onInsertMemory(Memory memory)
    {
        this.memories.add(memory);
        this.notifyDataSetChanged();
    }

    public void onUpdateMemory(Memory memory) {
        for(Memory listMemory: this.memories)
        {
            if( listMemory.getId().equals( memory.getId() ) )
            {
                listMemory = memory;
            }
        }

        this.notifyDataSetChanged();
    }

    public void onDeleteMemory(Memory memory)
    {
        this.memories.remove(memory);
        this.notifyDataSetChanged();
    }
    //======================================================================================

}
