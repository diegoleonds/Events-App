package com.example.events.ui.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.events.R
import com.example.events.data.model.Event
import com.example.events.ui.utils.DateConverter
import com.example.events.ui.utils.ImgLoader
import com.google.android.material.card.MaterialCardView

class EventAdapter(
    private val click: AdapterClick<Event>,
    private val imgLoader: ImgLoader
): RecyclerView.Adapter<EventAdapter.ViewHolder>() {
    var events: ArrayList<Event> = ArrayList<Event>()
    var eventsSearchList = ArrayList<Event>()
    val converter = DateConverter()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events.get(position)

        holder.eventName.text = event.title
        holder.eventDate.text = converter.convertTimestampToDateString(event.date)
        holder.eventPrice.text = event.price.toString()

        imgLoader.loadImage(
            imgUrl = event.image,
            imgView = holder.itemView.findViewById(R.id.item_event_img)
        )

        holder.itemView.findViewById<MaterialCardView>(R.id.event_item_cardview)
            .setOnClickListener { click.simpleClick(event) }
    }

    override fun getItemCount(): Int = events.size

    fun getFilter(): Filter {
        return eventNameFilter
    }

    private val eventNameFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: ArrayList<Event> = ArrayList()
            if (constraint == null || constraint.isEmpty()) {
                eventsSearchList.let { filteredList.addAll(it) }
            } else {
                val query = constraint.toString().trim().toLowerCase()
                eventsSearchList.forEach {
                    if (it.title.toLowerCase().contains(query)) {
                        filteredList.add(it)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results?.values is ArrayList<*>) {
                events.clear()
                events.addAll(results.values as ArrayList<Event>)
                notifyDataSetChanged()
            }
        }
    }

    fun updateData(list: List<Event>) {
        events = ArrayList<Event>(list)
        eventsSearchList = ArrayList<Event>(list)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View):  RecyclerView.ViewHolder(view)  {
        val eventName = view.findViewById<TextView>(R.id.event_item_name)
        val eventDate = view.findViewById<TextView>(R.id.event_item_date)
        val eventPrice = view.findViewById<TextView>(R.id.event_item_price)
        val eventImg = view.findViewById<ImageView>(R.id.item_event_img)
    }
}