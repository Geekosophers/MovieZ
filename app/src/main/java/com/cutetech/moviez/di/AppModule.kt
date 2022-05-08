package com.cutetech.moviez.di

import com.cutetech.moviez.BuildConfig
import com.cutetech.moviez.data.network.ApiService
import com.cutetech.moviez.data.repository.MoviesRepository
import com.cutetech.moviez.ui.details.MovieDetailsViewModel
import com.cutetech.moviez.ui.home.HomeViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppModule {

    val netModule = module {
        single {
            val interceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            OkHttpClient.Builder().addInterceptor(interceptor).build()
        }
        factory {
            Retrofit.Builder()
                .client(get())
                .baseUrl(BuildConfig.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        single { get<Retrofit>().create(ApiService::class.java) }
    }

    val appModule = module {
        single { MoviesRepository(get()) }
        viewModel { HomeViewModel(get()) }
        viewModel { MovieDetailsViewModel(get()) }
    }
}