package com.onyxbeacon.batterysample_take2.view.activity;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.onyxbeacon.batterysample_take2.databinding.DeviceItemBinding;
import com.onyxbeacon.batterysample_take2.listener.BleListener;
import com.onyxbeacon.batterysample_take2.BleScanner;
import com.onyxbeacon.batterysample_take2.R;
import com.onyxbeacon.batterysample_take2.view.adapter.DeviceAdapter;
import com.onyxbeacon.batterysample_take2.viewModel.DeviceViewModel;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BleListener {

    private DeviceAdapter mDeviceAdapter;
    private BleScanner mBleScanner;

    @Bind(R.id.recycler_devices)
    public RecyclerView mDevicesList;

    private ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            Log.d("UI", "Move card");
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            BluetoothDevice device = mDeviceAdapter.getItem(viewHolder.getAdapterPosition());

            DeviceAdapter.BindingHolder bindingHolder = (DeviceAdapter.BindingHolder) viewHolder;
            DeviceItemBinding deviceItemBinding = bindingHolder.getBinding();
            DeviceViewModel deviceViewModel = deviceItemBinding.getDevice();
            deviceViewModel.setReadBattery(true);
            //deviceItemBinding.setDevice(deviceViewModel);
            /*bindingHolder.setBinding(deviceItemBinding);*/

            //mDeviceAdapter.notifyDataSetChanged();

            //mDeviceAdapter.notifyItemChanged(viewHolder.getAdapterPosition());

            Log.d("BLE", "Connect to device with mac " + deviceViewModel.getMacAddress() +
                    " with read battery " + deviceViewModel.getReadBattery());
            mBleScanner.connectToDevice(device);

        }
    };

    private ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mBleScanner = new BleScanner(this);
        mBleScanner.setBleListener(this);
        mDeviceAdapter = new DeviceAdapter();

        setupRecyclerView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                mBleScanner.scanLeDevice(true);
            }
        });

        mBleScanner.scanLeDevice(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mBleScanner.initializeBluetoothScanner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setupRecyclerView() {
        mDevicesList.setLayoutManager(new LinearLayoutManager(this));
        itemTouchHelper.attachToRecyclerView(mDevicesList);
        mDevicesList.setAdapter(mDeviceAdapter);
    }

    @Override
    public void onBleDeviceDetected(BluetoothDevice device) {
        Log.d("BLE", "Bluetooth device found");
        mDeviceAdapter.addItem(device);
    }
}
