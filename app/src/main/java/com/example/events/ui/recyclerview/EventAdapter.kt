package com.example.events.ui.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.events.R
import com.example.events.data.model.Event
import com.example.events.ui.utils.ImgLoader
import com.google.android.material.card.MaterialCardView

class EventAdapter(
    private val click: AdapterClick<Event>,
    private val imgLoader: ImgLoader
): RecyclerView.Adapter<EventAdapter.ViewHolder>() {
    var events = emptyList<Event>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events.get(position)

        holder.eventName.text = event.title
        holder.eventDate.text = event.date.toString()
        holder.eventPrice.text = event.price.toString()

        imgLoader.loadImage(
            imgUrl = event.image,
            imgView = holder.itemView.findViewById(R.id.item_event_img)
        )

        holder.itemView.findViewById<MaterialCardView>(R.id.event_item_cardview)
            .setOnClickListener { click.simpleClick(event) }
    }

    override fun getItemCount(): Int = events.size

    class ViewHolder(view: View):  RecyclerView.ViewHolder(view)  {
        val eventName = view.findViewById<TextView>(R.id.event_item_name)
        val eventDate = view.findViewById<TextView>(R.id.event_item_date)
        val eventPrice = view.findViewById<TextView>(R.id.event_item_price)
        val eventImg = view.findViewById<ImageView>(R.id.item_event_img)
    }
}