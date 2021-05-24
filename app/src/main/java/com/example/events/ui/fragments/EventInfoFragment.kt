package com.example.events.ui.fragments

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.events.R
import com.example.events.data.model.Event
import com.example.events.data.model.Person
import com.example.events.domain.exception.PersonAlreadyInEventException
import com.example.events.ui.utils.DateConverter
import com.example.events.ui.utils.ImgLoader
import com.example.events.ui.viewmodel.EventInfoViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class EventInfoFragment : Fragment() {
    val viewModel: EventInfoViewModel by viewModel()

    lateinit var backFab: FloatingActionButton
    lateinit var imgEvent: ImageView
    lateinit var eventNameTxtView: TextView
    lateinit var eventDateTxtView: TextView
    lateinit var eventPriceTxtView: TextView
    lateinit var eventDescriptionTxtView: TextView
    lateinit var eventPeopleTxtView: TextView
    lateinit var readMoreTxtClick: TextView
    lateinit var joinBtn: Button

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
        setJoinBtnClick()
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
        joinBtn = view.findViewById(R.id.join_btn)
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

            val converter = DateConverter()

            eventDateTxtView.setText(converter.convertTimestampToDateString(it.date))
            eventNameTxtView.setText(it.title)
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

    private fun setJoinBtnClick(){
        joinBtn.setOnClickListener {
            getPersonFromSharedPreferences()?.let {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        viewModel.joinEvent(
                            event,
                            it
                        )
                    } catch (e: PersonAlreadyInEventException) {
                        Toast.makeText(context, getString(R.string.person_already_in_event_error),
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
            joinBtn.isEnabled = false
        }
    }

    private fun getPersonFromSharedPreferences(): Person? {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        sharedPref?.let {
            return Person(
                it.getString(getString(R.string.user_name), "") ?: "",
                it.getString(getString(R.string.user_email), "") ?: ""
            )
        }
        return null
    }
}