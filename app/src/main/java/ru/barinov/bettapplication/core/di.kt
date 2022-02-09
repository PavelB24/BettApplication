package ru.barinov.bettapplication.core

import android.content.*
import ru.barinov.bettapplication.data.localDataBase.DataBase
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.barinov.bettapplication.data.*
import ru.barinov.bettapplication.ui.MainActivity

import ru.barinov.bettapplication.ui.homeFragment.HomeFragmentViewModel
import ru.barinov.bettapplication.ui.strategyPage.BettingStrategyPageViewModel

val appModule = module {

    single<DataBase> {
        Room.databaseBuilder(get(), DataBase::class.java, "local_database").allowMainThreadQueries().build()
    }

    single  <SharedPreferences> {
        androidApplication().getSharedPreferences(MainActivity.sharedPreferencesName, Context.MODE_PRIVATE)
    }

    single<BettingStrategyDAO>{
        get<DataBase>().getProfileDao()
    }

    single<BettingStrategyRepository>{
        BettingStrategyRepository(get())
    }

    viewModel<HomeFragmentViewModel>{
        HomeFragmentViewModel(get())
    }

    viewModel<BettingStrategyPageViewModel>{id->
        BettingStrategyPageViewModel(get(), id.get())
    }
}