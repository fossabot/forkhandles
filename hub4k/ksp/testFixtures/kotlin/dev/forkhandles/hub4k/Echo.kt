package dev.forkhandles.hub4k

import dev.forkhandles.result4k.Success

data class Echo(val input: String) : TestHubAction<String> {
    context(TestHubContext)
    override fun invoke() = Success(input)
}
