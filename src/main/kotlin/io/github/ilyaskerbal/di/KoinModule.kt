package io.github.ilyaskerbal.di

import io.github.ilyaskerbal.data.repository.UserDataSourceImpl
import io.github.ilyaskerbal.domain.repository.UserDataSource
import io.github.ilyaskerbal.utils.Constants
import org.koin.dsl.module
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val koinModule = module {

    single<CoroutineDatabase>{
        KMongo
            .createClient()
            .coroutine
            .getDatabase(Constants.USER_DATABASE)
    }

    single<UserDataSource> {
        UserDataSourceImpl(get())
    }
}