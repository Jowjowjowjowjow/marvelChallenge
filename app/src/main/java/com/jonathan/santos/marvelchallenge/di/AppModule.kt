package com.jonathan.santos.marvelchallenge.di

import com.jonathan.santos.marvelchallenge.CharactersViewModel
import com.jonathan.santos.marvelchallenge.repositories.CharactersRepository
import com.squareup.picasso.Picasso
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        CharactersViewModel(
            charactersRepository = get()
        )
    }

    single {
        CharactersRepository(
            get()
        )
    }

    single<Picasso> {
        val builder = Picasso.Builder(androidContext())
        builder.build()
    }
}