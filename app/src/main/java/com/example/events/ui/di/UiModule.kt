package com.example.events.ui.di

import com.example.events.ui.viewmodel.EntryViewModel
import com.example.events.ui.viewmodel.EventInfoViewModel
import com.example.events.ui.viewmodel.EventListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { EntryViewModel() }
    viewModel { EventListViewModel(get()) }
    viewModel { EventInfoViewModel(get()) }
}