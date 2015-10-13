package com.onyxbeacon.batterysample_take2.view.adapter;

import android.bluetooth.BluetoothDevice;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.onyxbeacon.batterysample_take2.R;
import com.onyxbeacon.batterysample_take2.databinding.DeviceItemBinding;
import com.onyxbeacon.batterysample_take2.viewModel.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hayabusa on 05.10.2015.
 */
public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.BindingHolder> {
    private List<BluetoothDevice> mDevices;

    public DeviceAdapter() {
        mDevices = new ArrayList<>();
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DeviceItemBinding deviceItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.device_item,
                parent,
                false);
        return new BindingHolder(deviceItemBinding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        DeviceItemBinding deviceBinding = holder.binding;
        deviceBinding.setDevice(new DeviceViewModel(mDevices.get(position)));
    }

    @Override
    public int getItemCount() {
        return mDevices.size();
    }

    public void addItem(BluetoothDevice device) {
        mDevices.add(device);
        notifyDataSetChanged();
    }

    public BluetoothDevice getItem(int position) {
        return mDevices.get(position);
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private DeviceItemBinding binding;

        public BindingHolder(DeviceItemBinding binding) {
            super(binding.cardView);
            this.binding = binding;
        }

        public DeviceItemBinding getBinding() {
            return binding;
        }

        public void setBinding(DeviceItemBinding binding) {
            this.binding = binding;
        }

    }
}
