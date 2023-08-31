package com.apero.sample.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module


val coroutineModule = module {
    single { CoroutineScope(Dispatchers.Default) }
}