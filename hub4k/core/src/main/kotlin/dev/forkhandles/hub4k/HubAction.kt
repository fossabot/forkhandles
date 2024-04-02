package dev.forkhandles.hub4k

import dev.forkhandles.result4k.Result4k

fun interface HubAction<CTX : HubContext, R, F> {
    context(CTX)
    operator fun invoke(): Result4k<R, F>
}
