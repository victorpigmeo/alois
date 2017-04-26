package br.com.alois.aloismobile.ui.view.memory.adapter.item;

import android.content.Context;

import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import br.com.alois.aloismobile.R;
import br.com.alois.domain.entity.memory.Memory;

/**
 * Created by thiago on 12/04/17.
 */
@EViewGroup(R.layout.memory_list_item)
public class MemoryListItem extends LinearLayout {

    //=====================================ATTRIBUTES=======================================

    @ViewById(R.id.memoryName)
    TextView memoryName;

    Memory memory;

    //======================================================================================

    //=====================================INJECTIONS=======================================
    AppCompatActivity activity;
    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public MemoryListItem(Context context)
    {
        super(context);
    }

    //======================================================================================

    //==================================GETTERS/SETTERS=====================================

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    public void bind(Memory memory, AppCompatActivity activity) {
        this.memory = memory;
        this.memoryName.setText(memory.getDescription());
        this.activity = activity;
    }
    //======================================================================================
}
