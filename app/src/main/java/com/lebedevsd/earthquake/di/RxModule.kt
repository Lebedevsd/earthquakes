package com.lebedevsd.earthquake.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object RxModule {

    @MainScheduler
    @Provides
    fun provideMainScheduler() = AndroidSchedulers.mainThread()

    @IOScheduler
    @Provides
    fun provideIOScheduler() = Schedulers.io()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainScheduler

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IOScheduler
