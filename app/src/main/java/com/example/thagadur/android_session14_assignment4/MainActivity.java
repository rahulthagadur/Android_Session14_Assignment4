package com.example.thagadur.android_session14_assignment4;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int PERMISSION_REQUEST_CODE = 200;
    private View view;
    Button checkPermission,reqPermission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        checkPermission = (Button) findViewById(R.id.check_permission);
        reqPermission = (Button) findViewById(R.id.req_permission);
        checkPermission.setOnClickListener(this);
        reqPermission.setOnClickListener(this);

    }

    //Onclick of the button asking the user to enter give the perimission and check the permission if alloted
    @Override
    public void onClick(View v) {

        view = v;

        int id = v.getId();
        switch (id) {
            case R.id.check_permission:
                if (checkPermission()) {

                    Snackbar.make(view, "Permission given already ", Snackbar.LENGTH_LONG).show();

                } else {

                    Snackbar.make(view, "Request for the permission Press the button", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.req_permission:
                if (!checkPermission()) {

                    requestPermission();

                } else {

                    Snackbar.make(view, "Permission has already given", Snackbar.LENGTH_LONG).show();

                }
                break;
        }

    }
    //Check whether the permission is given or not
    private boolean checkPermission() {
        int res= ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);

        return res== PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, PERMISSION_REQUEST_CODE);

    }

    //Request for the permission to be Given
    // here i am requesting for camera permission
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean camera= grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (camera)
                        Snackbar.make(view, "Permission has been Granted.", Snackbar.LENGTH_LONG).show();
                    else {

                        Snackbar.make(view, "Permission has not been granted.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("give access to permission",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

}
