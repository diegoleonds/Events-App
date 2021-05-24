package com.example.events.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
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
    lateinit var tryAgainBtn: Button
    lateinit var errorMessage: TextView
    lateinit var eventList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateViews(view)
        setTryAgainBtnClick()
        initAdapter(view)
        setRecyclerViewAdapter()
        observeViewModelEvents()
        getEvents()
        observeSearchField()
    }

    override fun onStart() {
        super.onStart()
        searchEditTxt.setText("")
    }

    private fun inflateViews(view: View) {
        searchEditTxt = view.findViewById(R.id.search_txt_input)
        eventList = view.findViewById(R.id.events_rv)
        tryAgainBtn = view.findViewById(R.id.try_again_btn)
        errorMessage = view.findViewById(R.id.erro_message_txt)
    }

    private fun setTryAgainBtnClick(){
        tryAgainBtn.setOnClickListener {
            getEvents()
        }
    }

    private fun initAdapter(view: View) {
        adapter = EventAdapter(object : AdapterClick<Event> {
            override fun simpleClick(event: Event) {
                val bundle = bundleOf(Pair(getString(R.string.event_bundle), event))
                Navigation.findNavController(view)
                    .navigate(R.id.action_eventsListFragment_to_eventInfoFragment, bundle)
            }
        }, ImgLoader(Glide.with(view.context)))
    }

    private fun setRecyclerViewAdapter() {
        eventList.adapter = adapter
        eventList.setHasFixedSize(true)
        eventList.layoutManager = LinearLayoutManager(context)
    }

    private fun observeViewModelEvents() {
        viewModel.events.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.isEmpty()) {
                    Toast.makeText(context, getString(R.string.error_network_toast),
                        Toast.LENGTH_SHORT).show()

                    setViewAsGone(eventList)
                    setViewAsVisible(errorMessage)
                    setViewAsVisible(tryAgainBtn)
                } else {
                    adapter.updateData(it)
                    setViewAsVisible(eventList)
                    setViewAsGone(errorMessage)
                    setViewAsGone(tryAgainBtn)
                }
            }
        })
    }

    private fun setViewAsGone(view: View) {
        if (view.visibility.equals(View.VISIBLE))
            view.visibility = View.GONE
    }

    private fun setViewAsVisible(view: View) {
        if (view.visibility.equals((View.GONE)))
            view.visibility = View.VISIBLE
    }

    private fun getEvents() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getEvents()
        }
    }

    private fun observeSearchField(){
        searchEditTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(c: CharSequence?, p1: Int, p2: Int, p3: Int) {
                adapter.getFilter().filter(c)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
        )
    }
}