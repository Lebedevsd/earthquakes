package com.lebedevsd.earthquake.di

import com.lebedevsd.earthquake.api.AuthInterceptor
import com.lebedevsd.earthquake.api.EarthQuakeRestApi
import com.lebedevsd.earthquake.util.DateAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideAuthInterceptorOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    fun provideMoshiJsonAdapter() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(DateAdapter())
        .build()

    @Provides
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi) = Retrofit.Builder()
        .baseUrl(EarthQuakeRestApi.endpoint)
        .client(client)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    fun provideEQService(retrofit: Retrofit): EarthQuakeRestApi =
        retrofit.create(EarthQuakeRestApi::class.java)

}
