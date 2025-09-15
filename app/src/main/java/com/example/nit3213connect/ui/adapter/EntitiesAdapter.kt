package com.example.nit3213connect.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nit3213connect.R
import com.example.nit3213connect.domain.model.Entity

class EntitiesAdapter(
    private val onClick: (Entity) -> Unit
) : ListAdapter<Entity, EntitiesAdapter.VH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Entity>() {
            override fun areItemsTheSame(oldItem: Entity, newItem: Entity) =
                oldItem.title == newItem.title && oldItem.subtitle == newItem.subtitle

            override fun areContentsTheSame(oldItem: Entity, newItem: Entity) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_entity, parent, false)
        return VH(v, onClick)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    class VH(itemView: View, private val onClick: (Entity) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvSubtitle = itemView.findViewById<TextView>(R.id.tvSubtitle)
        private val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)

        private var current: Entity? = null

        init {
            itemView.setOnClickListener { current?.let(onClick) }
        }

        fun bind(item: Entity) {
            current = item
            tvTitle.text = item.title
            tvSubtitle.text = item.subtitle
            tvDescription.text = item.description
        }
    }
}
