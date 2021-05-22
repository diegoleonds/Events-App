package com.example.events.data.di

import com.example.events.data.repository.EventRepositoryImpl
import org.koin.dsl.module

val dataModule = module {
    single { EventRepositoryImpl() }
}