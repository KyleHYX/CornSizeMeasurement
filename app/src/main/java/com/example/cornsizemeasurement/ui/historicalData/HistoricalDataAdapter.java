package com.example.cornsizemeasurement.ui.historicalData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.cornsizemeasurement.R;
import com.example.cornsizemeasurement.db.CornSize;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoricalDataAdapter extends RecyclerView.Adapter<HistoricalDataAdapter.ViewHolder> {
    private List<CornSize> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);

            textView = (TextView) view.findViewById(R.id.RecyclerTextView);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public HistoricalDataAdapter(List<CornSize> dataSet) {
        localDataSet = dataSet;
    }

    public void setData(List<CornSize> newData) {
        this.localDataSet = newData;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        String displayText = "Corn ID: ";
        displayText += Integer.toString(localDataSet.get(position).getCornId());
        displayText += "\nSizeData: ";
        displayText += localDataSet.get(position).getSizeData();
        viewHolder.getTextView().setText(displayText);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
