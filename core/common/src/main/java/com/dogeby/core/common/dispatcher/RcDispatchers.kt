package com.dogeby.core.common.dispatcher

import javax.inject.Qualifier

@Qualifier
@Retention
annotation class Dispatcher(val rcDispatcher: RcDispatchers)

enum class RcDispatchers {
    IO,
}
