package com.example.events.domain.di

import com.example.events.domain.transform.EventPersonTransform
import com.example.events.domain.usecase.JoinEventUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { EventPersonTransform() }
    factory { JoinEventUseCase(get(), get()) }
}