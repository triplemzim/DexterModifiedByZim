package com.revesoft.muhim.runtimepermission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Context context;
    ArrayAdapter arrayAdapter;
    ListView listView;
    Activity activity;
    PermissionListener listener;
    MultiplePermissionsListener multiPermissionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initiate_listener();
        context = this;
        activity = this;

        initiate_list_view();
    }

    private void initiate_list_view() {
        arrayAdapter = ArrayAdapter.createFromResource(context, R.array.permissionList, R.layout.list_item);
        listView = findViewById(R.id.listview);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Dexter.withActivity(activity)
                                .withPermissions(Manifest.permission.READ_SMS,
                                        Manifest.permission.RECEIVE_SMS,
                                        Manifest.permission.SEND_SMS)
                                .withListener(multiPermissionListener)
                                .withAlertDialog("Info", "We need this permission because we need to know what are you texting!", activity)
                                .check();
                        break;
                    case 1:
                        Dexter.withActivity(activity)
                                .withPermission(Manifest.permission.CALL_PHONE)
                                .withListener(listener)
                                .withAlertDialog("Info", "We need this permission because we need to know who you are calling all day!", activity)
                                .check();

                        break;
                    case 2:
                        Dexter.withActivity(activity)
                                .withPermission(Manifest.permission.INTERNET)
                                .withListener(listener)
                                .check();
                        break;
                    case 3:
                        Dexter.withActivity(activity)
                                .withPermissions(
                                        Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.ACCESS_FINE_LOCATION)
                                .withListener(multiPermissionListener)
                                .withFullScreenDialog("We need your permission!", "This app needs permission to use this feature. You can grant them in app settings.", activity)
                                .check();
                        break;
                    case 4:
                        Dexter.withActivity(activity)
                                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                                .withListener(listener)
                                .withFullScreenDialog("We need your permission!", "This app needs permission to use this feature. You can grant them in app settings.", activity)
                                .withFullScreenDialogBackground(R.drawable.background)
                                .check();
                        break;
                    case 5:
                        Dexter.withActivity(activity)
                                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .withListener(listener)
                                .withFullScreenDialogAndLayout("We need your permission!", "This app needs permission to use this feature. You can grant them in app settings.", activity, R.layout.custom_background)
                                .check();
                        break;
                    case 6:
                        Dexter.withActivity(activity)
                                .withPermission(Manifest.permission.CAMERA)
                                .withListener(listener)
                                .withAlertDialog("Info", "We need this permission because we really need it!", activity)
                                .check();
                        break;
                    case 7:
                        Dexter.withActivity(activity)
                                .withPermission(Manifest.permission.RECORD_AUDIO)
                                .withListener(listener)
                                .withFullScreenDialog("We need your permission!", "This app needs permission to use this feature. You can grant them in app settings.", activity)
                                .withFullScreenDialogBackground(R.drawable.background, Color.GREEN, Color.LTGRAY, Color.BLACK)
                                .check();
                        break;
                    case 8:
                        Dexter.withActivity(activity)
                                .withPermission(Manifest.permission.VIBRATE)
                                .withListener(listener)
                                .check();
                        break;
                    case 9:
                       Dexter.withActivity(activity)
                                .withPermissions(Manifest.permission.READ_CONTACTS,
                                        Manifest.permission.WRITE_CONTACTS)
                                .withListener(multiPermissionListener)
                                .check();
                        break;
                    case 10:
                        Dexter.withActivity(activity)
                                .withPermissions(Manifest.permission.BLUETOOTH,
                                        Manifest.permission.BLUETOOTH_ADMIN)
                                .withListener(multiPermissionListener)
                                .check();
                        break;
                    case 11:
                        Dexter.withActivity(activity)
                                .withPermissions(Manifest.permission.ACCESS_WIFI_STATE,
                                            Manifest.permission.ACCESS_NETWORK_STATE)
                                .withListener(multiPermissionListener)
                                .check();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void initiate_listener(){
        multiPermissionListener = new MultiplePermissionsListener(){

            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if(report.areAllPermissionsGranted()) {
                    Snackbar.make(findViewById(R.id.main_layout), "Permission Granted", 1000)
                            .setAction("Action", null).show();
                }
                else if(report.isAnyPermissionPermanentlyDenied()){
                    if(report.isAnyPermissionPermanentlyDenied()){
                        showSettingsDialog();
                    }
                    Snackbar.make(findViewById(R.id.main_layout), "Permission permanently Rejected", 1000)
                            .setAction("Action", null).show();
                }
                else{
                    Snackbar.make(findViewById(R.id.main_layout), "Permission Rejected", 1000)
                            .setAction("Action", null).show();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        };

        listener = new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                Snackbar.make(findViewById(R.id.main_layout), "Permission Granted", 1000)
                        .setAction("Action", null).show();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                if(response.isPermanentlyDenied()){
                    showSettingsDialog();
                }
                Snackbar.make(findViewById(R.id.main_layout), "Permission Rejected", 1000)
                        .setAction("Action", null).show();
            }


            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }


        };
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            openSettings();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
