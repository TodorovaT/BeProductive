package com.example.beproductive;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

//checknet

public class NetworkSchedulerService extends JobService implements ConnectivityReceiver.ConnectivityReceiverListener {
    private static final String TAG=NetworkSchedulerService.class.getSimpleName();
    private ConnectivityReceiver mConnectivityReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"Service created");
        mConnectivityReceiver=new ConnectivityReceiver(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"onStartCommand");
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG,"onStartJob"+mConnectivityReceiver);
        IntentFilter filter=new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mConnectivityReceiver,filter);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG,"onStopJob");
        unregisterReceiver(mConnectivityReceiver);
        return true;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        String message=isConnected ? "Please turn off the Internet!" : "Keep going!";
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
}
