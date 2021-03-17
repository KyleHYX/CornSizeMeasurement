package com.example.cornsizemeasurement.ui.historicalData

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cornsizemeasurement.R
import com.example.cornsizemeasurement.db.CornSize

class CornSizeListAdapter : ListAdapter<CornSize, CornSizeListAdapter.CornSizeViewHolder>(CornSizesComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CornSizeViewHolder {
        return CornSizeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CornSizeViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.cornId)
    }

    class CornSizeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cornSizeItemView: TextView = itemView.findViewById(R.id.RecyclerTextView)

        fun bind(text: Int) {
            cornSizeItemView.text = text.toString()
        }

        companion object {
            fun create(parent: ViewGroup): CornSizeViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return CornSizeViewHolder(view)
            }
        }
    }

    class CornSizesComparator : DiffUtil.ItemCallback<CornSize>() {
        override fun areItemsTheSame(oldItem: CornSize, newItem: CornSize): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CornSize, newItem: CornSize): Boolean {
            return oldItem.cornId == newItem.cornId
        }
    }
}