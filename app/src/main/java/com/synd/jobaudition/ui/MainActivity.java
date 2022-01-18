package com.synd.jobaudition.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.synd.jobaudition.R;
import com.synd.jobaudition.ui.io.IOFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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