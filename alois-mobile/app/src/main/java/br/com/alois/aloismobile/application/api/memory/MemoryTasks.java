package br.com.alois.aloismobile.application.api.memory;

import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.application.preference.GeneralPreferences_;
import br.com.alois.aloismobile.application.preference.ServerConfiguration;
import br.com.alois.aloismobile.ui.view.home.PatientHomeActivity;
import br.com.alois.aloismobile.ui.view.memory.fragment.MemoryListFragment;
import br.com.alois.aloismobile.ui.view.memory.fragment.MemoryListFragment_;
import br.com.alois.api.jackson.JacksonDecoder;
import br.com.alois.api.jackson.JacksonEncoder;
import br.com.alois.domain.entity.memory.Memory;
import feign.Feign;
import feign.FeignException;

/**
 * Created by thiago on 08/04/17.
 */
@EBean
public class MemoryTasks {

    @Pref
    GeneralPreferences_ generalPreferences;

    @RootContext
    PatientHomeActivity patientHomeActivity;

    @Background
    public void getMemoryList(Long patientId)
    {
        MemoryClient memoryClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(MemoryClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            getMemoryListHandleSuccess( memoryClient.listMemoryByPatient(patientId  ,this.generalPreferences.loggedUserAuthToken().get()));
        }
        catch(FeignException e)
        {
            e.printStackTrace();
        }
    }

    @UiThread
    public void getMemoryListHandleSuccess(List<Memory> memoryList)
    {
        this.patientHomeActivity.progressDialog.dismiss();
        this.patientHomeActivity.setPatientMemoryList(memoryList);
    }

    @Background
    public void addMemory(Memory memory)
    {
        MemoryClient memoryClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(MemoryClient.class, ServerConfiguration.API_ENDPOINT);

        ObjectMapper objectMapper = new ObjectMapper();

        try
        {
            String jsonMemory = objectMapper.writeValueAsString(memory);
            this.addMemoryHandleSuccess( memoryClient.addMemory( memory, this.generalPreferences.loggedUserAuthToken().get() ) );
        }
        catch(Exception e)
        {
            e.printStackTrace();
            this.addMemoryHandleFail(e.getMessage());
        }
    }

    @UiThread
    public void addMemoryHandleSuccess(Memory memory)
    {
        this.patientHomeActivity.progressDialog.dismiss();
        if(memory != null)
        {
            int aux = this.patientHomeActivity.getSupportFragmentManager().getBackStackEntryCount();
            for(int i=0;i<aux;i++){
                if(this.patientHomeActivity.getSupportFragmentManager().getBackStackEntryAt(i).equals("memory_list_fragment"))
                    this.patientHomeActivity.onInsertMemory(memory);
            }
            this.patientHomeActivity.getSupportFragmentManager()
                    .popBackStack();
        }
        else
        {
            Toast.makeText(this.patientHomeActivity, "Erro", Toast.LENGTH_SHORT).show();
        }
    }

    @UiThread
    public void addMemoryHandleFail(String message)
    {
        this.patientHomeActivity.progressDialog.dismiss();
        Toast.makeText(this.patientHomeActivity, "Erro", Toast.LENGTH_SHORT).show();
    }

    @Background
    public void findMemoryById(Long memoryId)
    {
        MemoryClient memoryClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(MemoryClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            this.findHandleSuccess(memoryClient.findMemoryById(memoryId, this.generalPreferences.loggedUserAuthToken().get()));
        }
        catch(FeignException e)
        {
            e.printStackTrace();
            this.findHandleFail(e.getMessage());
        }
    }

    @UiThread
    public void findHandleSuccess(Memory memory)
    {
        this.patientHomeActivity.setPatientMemory(memory);
        this.patientHomeActivity.progressDialog.dismiss();
    }

    @UiThread
    public void findHandleFail(String message)
    {
        this.patientHomeActivity.progressDialog.dismiss();
    }

    @Background
    public void updateMemory(Memory memory) {
        MemoryClient memoryClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(MemoryClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            updateMemoryHandleSuccess( memoryClient.updateMemory( memory, this.generalPreferences.loggedUserAuthToken().get() ) );
        }
        catch(FeignException e)
        {
            e.printStackTrace();
            updateMemoryHandleFail(e.getMessage());
        }
    }

    @UiThread
    public void updateMemoryHandleSuccess(Memory memory) {
        if(memory != null)
        {
            this.patientHomeActivity.progressDialog.dismiss();

            this.patientHomeActivity.onUpdateMemory(memory);

            this.patientHomeActivity.getSupportFragmentManager()
                    .popBackStack();
        }
    }


    @UiThread
    public void updateMemoryHandleFail(String message)
    {
        this.patientHomeActivity.progressDialog.dismiss();
        Toast.makeText(this.patientHomeActivity, this.patientHomeActivity.getResources().getString(R.string.patient_update_error), Toast.LENGTH_SHORT).show();
    }
}
