package com.onyxbeacon.batterysample_take2.listener;

import android.bluetooth.BluetoothDevice;

import java.util.List;

/**
 * Created by hayabusa on 05.10.2015.
 */
public interface BleListener {

    public void onBleDeviceDetected(BluetoothDevice device);

}
