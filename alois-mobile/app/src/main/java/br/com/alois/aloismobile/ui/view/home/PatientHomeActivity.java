package br.com.alois.aloismobile.ui.view.home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.InjectMenu;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.application.api.memory.MemoryTasks;
import br.com.alois.aloismobile.application.api.patient.PatientTasks;
import br.com.alois.aloismobile.application.api.reminder.ReminderTasks;
import br.com.alois.aloismobile.application.api.request.RequestTasks;
import br.com.alois.aloismobile.application.api.route.RouteTasks;
import br.com.alois.aloismobile.application.preference.GeneralPreferences_;
import br.com.alois.aloismobile.application.service.LastLocationService;
import br.com.alois.aloismobile.application.service.LastLocationService_;
import br.com.alois.aloismobile.ui.view.home.fragment.CaregiverHomeFragment;
import br.com.alois.aloismobile.ui.view.home.fragment.CaregiverHomeFragment_;
import br.com.alois.aloismobile.ui.view.home.fragment.PatientHomeFragment;
import br.com.alois.aloismobile.ui.view.home.fragment.PatientHomeFragment_;
import br.com.alois.aloismobile.ui.view.login.LoginActivity;
import br.com.alois.aloismobile.ui.view.memory.fragment.MemoryFormFragment;
import br.com.alois.aloismobile.ui.view.memory.fragment.MemoryFormFragment_;
import br.com.alois.aloismobile.ui.view.memory.fragment.MemoryListFragment_;
import br.com.alois.aloismobile.ui.view.patient.PatientDetailActivity_;
import br.com.alois.aloismobile.ui.view.patient.fragment.PatientDetailFragment;
import br.com.alois.aloismobile.ui.view.patient.fragment.PatientDetailFragment_;
import br.com.alois.aloismobile.ui.view.patient.fragment.PatientFormFragment;
import br.com.alois.aloismobile.ui.view.patient.fragment.PatientFormFragment_;
import br.com.alois.domain.entity.memory.Memory;
import br.com.alois.domain.entity.reminder.Reminder;
import br.com.alois.domain.entity.route.Route;
import br.com.alois.domain.entity.user.Patient;
import br.com.alois.domain.entity.user.Request;

@EActivity(R.layout.activity_patient_home)
@OptionsMenu(R.menu.home_patient_menu)
public class PatientHomeActivity extends AppCompatActivity
{
    //=====================================ATTRIBUTES=======================================
    public ProgressDialog progressDialog;

    public PatientHomeFragment patientHomeFragment;

    public MemoryFormFragment memoryFormFragment;
    //======================================================================================

    //=====================================INJECTIONS=======================================
    @InjectMenu
    Menu menu;

    @Pref
    GeneralPreferences_ generalPreferences;

    @NonConfigurationInstance
    @Bean
    PatientTasks patientTasks;

    @NonConfigurationInstance
    @Bean
    MemoryTasks memoryTasks;

    @NonConfigurationInstance
    @Bean
    RequestTasks requestTasks;

    @NonConfigurationInstance
    @Bean
    RouteTasks routeTasks;

    @NonConfigurationInstance
    @Bean
    ReminderTasks reminderTasks;

    @SystemService
    LocationManager locationManager;


    //======================================================================================
    //TODO SE FOR ATRIBUTOS COLOCAR NO LUGAR DE ATRIBUTOS
    Uri photoUri;

    byte[] photo;
    String photoPath;
    private List<Reminder> patientReminderList;
    //=====================================BEHAVIOUR========================================

    //TODO PORQUE TEM ESSE SUPRESS WARNINGS?
    @SuppressWarnings("WrongConstant")
    @AfterViews
    public void onAfterViews()
    {
        if( !this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) )
        {
            AlertDialog.Builder alertDialogGps = new AlertDialog.Builder(this);

            alertDialogGps.setTitle(this.getResources().getString(R.string.gps_is_off))
                    .setMessage(this.getResources().getString(R.string.alert_gps_off))
                    .setNeutralButton(R.string.mdtp_ok, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    }).create().show();
        }

        LastLocationService_.intent(this.getApplicationContext()).start();

        this.patientHomeFragment = PatientHomeFragment_.builder().patientId(this.generalPreferences.loggedUserId().get()).build();

        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.patient_home_frame_layout, patientHomeFragment)
                .addToBackStack("patient_home_fragment")
                .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                .commit();
    }

    public void getPatient(Long patientId)
    {
        this.progressDialog = ProgressDialog.show(this,
                super.getString(R.string.loading_patient),
                super.getString(R.string.please_wait),
                true,//is indeterminate
                false//is cancelable
        );

        this.patientTasks.findPatientById(patientId);
    }

    public void getMemory(Long memoryId)
    {
        System.out.println("Chegou aqui!!");
        this.progressDialog = ProgressDialog.show(this,
                super.getString(R.string.loading_memory),
                super.getString(R.string.please_wait),
                true,//is indeterminate
                false//is cancelable
        );

        this.memoryTasks.findMemoryById(memoryId);
    }

    public void getMemoriesByPatientId(Long patientId)
    {
        this.progressDialog = ProgressDialog.show(this,
                super.getString(R.string.loading_memories),
                super.getString(R.string.please_wait),
                true,//is indeterminate
                false//is cancelable
        );

        this.memoryTasks.getMemoryList(patientId);
    }

    public void addMemory(Memory memory)
    {
        this.progressDialog = ProgressDialog.show(this,
                super.getString(R.string.saving_memory),
                super.getString(R.string.please_wait),
                true,//is indeterminate
                false//is cancelable
        );

        this.memoryTasks.addMemory(memory);
    }

    public void updateMemory(Memory memory) {
        this.progressDialog = ProgressDialog.show(this,
                super.getString(R.string.saving_memory),
                super.getString(R.string.please_wait),
                true,//is indeterminate
                false//is cancelable
        );

        this.memoryTasks.updateMemory(memory);
    }

    //TODO SE NAO FOR MAIS USAR O METODO APAGA
    public void onInsertMemory(Memory memory)
    {
        //this.patientHomeFragment.onInsertPatient(patient);
    }

    //TODO IDEM AO DE CIMA
    public void onUpdateMemory(Memory memory)
    {
        //this.patientHomeFragment.onInsertPatient(patient);
    }


    @Override
    public void onBackPressed()
    {
        int fragments = this.getSupportFragmentManager().getBackStackEntryCount();

        if (fragments > 1)
        {
            getSupportFragmentManager().popBackStack();
        }
        else
        {
            final Intent returnIntent = new Intent();
            returnIntent.putExtra("action", "quit");
            this.setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }

    @OptionsItem(R.id.menu_camera)
    public void openCameraClick()
    {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       /* takePictureIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);*/

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null)
        {
            // Create the File where the photo should go
            File photoFile = null;
            try
            {
                photoFile = createImageFile();
            }
            catch (IOException ex)
            {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null)
            {
                photoUri = FileProvider.getUriForFile(this,"br.com.alois.aloismobile.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, 1);
            }

        }

    }

    public File createImageFile() throws IOException
    {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        photoPath = "file://" + image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 1 && resultCode == this.RESULT_OK )
        {
            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getBaseContext()));
            ImageLoader imageLoader = ImageLoader.getInstance();

            ImageSize targetSize = new ImageSize(400, 200);

            this.progressDialog = ProgressDialog.show(this,
                    "Aguarde!",
                    "Carregando imagem..."
            );

            imageLoader.loadImage(photoPath, targetSize, null, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    progressDialog.dismiss();

                    memoryFormFragment = MemoryFormFragment_.builder().image(loadedImage).build();

                        getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.patient_home_frame_layout, memoryFormFragment)
                            .addToBackStack("memory_list_fragment")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                }
          });
        }
    }

    public void requestLogoff(Request request)
    {
        this.progressDialog = ProgressDialog.show(this,
                super.getString(R.string.saving_memory),
                super.getString(R.string.please_wait),
                true,//is indeterminate
                true//is cancelable
        );

        this.requestTasks.requestLogoff(request, this);
    }

    public void getPatientLogoffApprovedRequest(Long patientId)
    {
        this.requestTasks.getPatientLogoffApprovedRequest(patientId, this);
    }

    public void showLogoffButton()
    {
        this.patientHomeFragment.showLogoffButton();
    }

    public void onPatientLogoff(Patient patient)
    {
        LastLocationService_.intent(this.getApplicationContext()).stop();

        this.requestTasks.updateUsedPatientLogoffRequest(patient.getId());

        final Intent returnIntent = new Intent();
        returnIntent.putExtra("action", "logoff");
        this.setResult(Activity.RESULT_OK, returnIntent);
        this.finish();
    }

    public void listPatientRoutes(Long patientId)
    {
        this.progressDialog = ProgressDialog.show(this,
                super.getString(R.string.loading_routes),
                super.getString(R.string.please_wait),
                true,//is indeterminate
                true//is cancelable
        );
        
        this.routeTasks.listRoutesByPatientId(patientId, this);
    }

    public void setPatientRouteList(List<Route> patientRouteList)
    {
        this.patientHomeFragment.setPatientRoutes(patientRouteList);
    }

    public void listRemindersByPatientId(Long patientId)
    {
        this.progressDialog = ProgressDialog.show(this,
                super.getString(R.string.loading_routes),
                super.getString(R.string.please_wait),
                true,//is indeterminate
                true//is cancelable
        );

        this.reminderTasks.listActiveRemindersByPatientId(patientId, this);
    }

    public void setPatientReminderList(List<Reminder> patientReminderList)
    {
        this.patientHomeFragment.setPatientReminderList(patientReminderList);
    }
    //======================================================================================
}

