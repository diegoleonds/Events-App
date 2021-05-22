package com.example.events.ui.fragments

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.events.R
import com.example.events.data.model.Event
import com.example.events.ui.utils.ImgLoader
import com.google.android.material.floatingactionbutton.FloatingActionButton


class EventInfoFragment : Fragment() {
    lateinit var backFab: FloatingActionButton
    lateinit var imgEvent: ImageView
    lateinit var eventNameTxtView: TextView
    lateinit var eventDateTxtView: TextView
    lateinit var eventPriceTxtView: TextView
    lateinit var eventDescriptionTxtView: TextView
    lateinit var eventPeopleTxtView: TextView
    lateinit var readMoreTxtClick: TextView

    lateinit var event: Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateViews(view)
        setBackFabClick(view)
        getDataFromBundle()
        setReadMoreTxtClick()
    }

    private fun inflateViews(view: View){
        backFab = view.findViewById(R.id.event_info_back_fab)
        eventNameTxtView = view.findViewById(R.id.event_name)
        eventDateTxtView = view.findViewById(R.id.event_info_date)
        eventPriceTxtView = view.findViewById(R.id.event_info_price)
        eventDescriptionTxtView = view.findViewById(R.id.event_info_description)
        eventPeopleTxtView = view.findViewById(R.id.event_info_people)
        imgEvent = view.findViewById(R.id.event_img)
        readMoreTxtClick = view.findViewById(R.id.read_more_txt_click)
    }

    private fun setBackFabClick(view: View){
        backFab.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_eventInfoFragment_to_eventsListFragment)
        }
    }

    private fun getDataFromBundle(){
        val bundleEvent = arguments?.getParcelable<Event>(getString(R.string.event_bundle))
        bundleEvent?.let {
            event = it

            val imgLoader = ImgLoader(Glide.with(this))
            imgLoader.loadImage(
                imgUrl = it.image,
                imgView = imgEvent)

            eventNameTxtView.setText(it.title)
            eventDateTxtView.setText(it.date.toString())
            eventPriceTxtView.setText(it.price.toString())
            eventDescriptionTxtView.setText(it.description)
            eventPeopleTxtView.setText(it.people.size.toString())
        }
    }

    private fun setReadMoreTxtClick(){
        readMoreTxtClick.setOnClickListener {
            eventDescriptionTxtView.maxLines = 1000
            readMoreTxtClick.visibility = View.GONE
        }
    }
}