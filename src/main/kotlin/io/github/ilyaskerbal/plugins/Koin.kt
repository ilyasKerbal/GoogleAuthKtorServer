package io.github.ilyaskerbal.plugins

import io.github.ilyaskerbal.di.koinModule
import io.github.ilyaskerbal.module
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    install(Koin) {
        modules(koinModule)
    }
}