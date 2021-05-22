package com.example.events.ui.fragments

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.ListFragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.events.R
import com.example.events.data.model.Event
import com.example.events.ui.recyclerview.AdapterClick
import com.example.events.ui.recyclerview.EventAdapter
import com.example.events.ui.utils.ImgLoader
import com.example.events.ui.viewmodel.EventListViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class EventsListFragment : Fragment() {
    val viewModel: EventListViewModel by viewModel()
    lateinit var adapter: EventAdapter

    lateinit var searchEditTxt: TextInputEditText
    lateinit var eventList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateViews(view)
        initAdapter(view)
        setRecyclerViewAdapter()
        observeViewModelEvents()
        getEvents()
    }

    override fun onResume() {
        super.onResume()

    }

    private fun inflateViews(view : View){
        searchEditTxt = view.findViewById(R.id.search_txt_input)
        eventList = view.findViewById(R.id.events_rv)
    }

    private fun initAdapter(view: View){
        adapter = EventAdapter(object : AdapterClick<Event> {
            override fun simpleClick(event: Event) {
                val bundle = bundleOf(Pair(getString(R.string.event_bundle), event))
                Navigation.findNavController(view)
                    .navigate(R.id.action_eventsListFragment_to_eventInfoFragment, bundle)
            }
        }, ImgLoader(Glide.with(view.context)))
    }

    private fun setRecyclerViewAdapter(){
        eventList.adapter = adapter
        eventList.setHasFixedSize(true)
        eventList.layoutManager = LinearLayoutManager(context)
    }

    private fun observeViewModelEvents(){
        viewModel.events.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.events = it
                adapter.notifyDataSetChanged()
                Log.e("Adapter array", adapter.events.toString())
            }
        })
    }

    private fun getEvents(){
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getEvents()
        }
    }
}