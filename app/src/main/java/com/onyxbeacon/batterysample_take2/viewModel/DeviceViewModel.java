package com.onyxbeacon.batterysample_take2.viewModel;

import android.bluetooth.BluetoothDevice;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.onyxbeacon.batterysample_take2.BR;

/**
 * Created by hayabusa on 05.10.2015.
 */
public class DeviceViewModel extends BaseObservable {
    private BluetoothDevice bluetoothDevice;
    private boolean readBattery = false;

    public DeviceViewModel(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    @Bindable
    public String getDeviceName() {
        return bluetoothDevice.getName() + " | " + bluetoothDevice.getAddress();
    }

    @Bindable
    public String getMacAddress() {
        return bluetoothDevice.getAddress();
    }

    @Bindable
    public boolean getReadBattery() { return readBattery; }

    public void setReadBattery(boolean readBattery) {
        this.readBattery = readBattery;
        notifyPropertyChanged(BR.readBattery);
    }

    public View.OnClickListener onDeviceClick() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setReadBattery(true);
            }
        };
    }

}
