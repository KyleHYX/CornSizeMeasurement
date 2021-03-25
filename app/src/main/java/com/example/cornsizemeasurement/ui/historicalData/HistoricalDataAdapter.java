package com.example.cornsizemeasurement.ui.historicalData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.cornsizemeasurement.R;
import com.example.cornsizemeasurement.db.CornSize;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.List;

public class HistoricalDataAdapter extends RecyclerView.Adapter<HistoricalDataAdapter.ViewHolder> {
    private ClickListener listener;
    private List<CornSize> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textView;
        private final Button selectBtn;
        private WeakReference<ClickListener> listenerRef;

        public ViewHolder(View view, ClickListener listener) {
            super(view);

            textView = (TextView) view.findViewById(R.id.RecyclerTextView);
            selectBtn = (Button) view.findViewById(R.id.selectBtn);
            listenerRef = new WeakReference<>(listener);

            selectBtn.setOnClickListener(this);
        }

        public TextView getTextView() {
            return textView;
        }
        public Button getSelectBtn() { return selectBtn; }

        @Override
        public void onClick(View v) {
            listenerRef.get().onPositionClicked(getAdapterPosition());
        }
    }

    public HistoricalDataAdapter(List<CornSize> dataSet, ClickListener listener) {
        localDataSet = dataSet;
        this.listener = listener;
    }

    public void setData(List<CornSize> newData) {
        this.localDataSet = newData;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_item, viewGroup, false);

        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        String displayText = "Corn ID: " + Integer.toString(localDataSet.get(position).getCornId()) +
                "\nSizeData: " + localDataSet.get(position).getSizeData();

        viewHolder.getTextView().setText(displayText);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
