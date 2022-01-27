package com.synd.jobaudition.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.synd.jobaudition.R;
import com.synd.jobaudition.databinding.ActivityMainBinding;
import com.synd.jobaudition.ui.io.IOFragment;
import com.synd.jobaudition.utils.GeneralUtils;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ConnectivityManager connectivityManager;

    private BroadcastReceiver mainReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case ConnectivityManager.CONNECTIVITY_ACTION:
                    checkNetwork();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private void checkNetwork() {
        boolean isConnected = GeneralUtils.isConnected(connectivityManager);
        binding.textNoConnection.setVisibility(isConnected ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNetwork();
        // From Android 7, we must register action ConnectivityManager.CONNECTIVITY_ACTION
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mainReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            unregisterReceiver(mainReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_format_input:
                item.setChecked(!item.isChecked());
                IOFragment ioFragment = (IOFragment) getSupportFragmentManager().findFragmentById(R.id.io_fragment);
                ioFragment.reloadList(item.isChecked());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}