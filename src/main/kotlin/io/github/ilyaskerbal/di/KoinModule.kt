package io.github.ilyaskerbal.di

import io.github.ilyaskerbal.utils.Constants
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val koinModule = module {
    single {
        KMongo
            .createClient()
            .coroutine
            .getDatabase(Constants.USER_DATABASE)
    }
}