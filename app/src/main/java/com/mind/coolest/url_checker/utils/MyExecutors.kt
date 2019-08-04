package com.mind.coolest.url_checker.utils

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MyExecutors(private val executorIO: Executor) {

    constructor() : this(
        Executors.newFixedThreadPool(3)
    )

    fun executorIO() = executorIO
}