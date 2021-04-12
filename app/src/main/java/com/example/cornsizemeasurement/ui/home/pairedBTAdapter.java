package com.example.cornsizemeasurement.ui.home;

import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cornsizemeasurement.R;

import java.lang.ref.WeakReference;
import java.util.List;

public class pairedBTAdapter extends RecyclerView.Adapter<pairedBTAdapter.ViewHolder> {
    private ClickListener listener;
    private List<BluetoothDevice> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textView;
        private final Button connectBtn;
        private final Button disconnectBtn;
        private WeakReference<ClickListener> listenerRef;

        public ViewHolder(View view, ClickListener listener) {
            super(view);

            textView = (TextView) view.findViewById(R.id.paired_bt_tv);
            connectBtn = (Button) view.findViewById(R.id.paired_connect);
            disconnectBtn = (Button) view.findViewById(R.id.paired_disconnect);
            listenerRef = new WeakReference<>(listener);

            connectBtn.setOnClickListener(this);
            disconnectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenerRef.get().onDisconnectClicked(getAdapterPosition());
                }
            });
        }

        public TextView getTextView() { return textView; }

        @Override
        public void onClick(View v) { listenerRef.get().onConnectClicked(getAdapterPosition());}
    }

    public pairedBTAdapter(List<BluetoothDevice> deviceList, ClickListener listener) {
        localDataSet = deviceList;
        this.listener = listener;
    }

    public void setData(List<BluetoothDevice> newData) {
        this.localDataSet = newData;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.paired_bt, viewGroup, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int posotion) {
        String displayText = "Device name: " + localDataSet.get(posotion).getName();

        viewHolder.getTextView().setText(displayText);
    }

    @Override
    public int getItemCount() { return localDataSet.size(); }
}
