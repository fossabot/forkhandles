package dev.forkhandles.hub4k

/**
 * The internal state of the hub - contains the dependencies which might be required by an action
 */
@Hub4kAdapter
class TestHub(private val context: TestHubContext) {
    operator fun <R> invoke(action: TestHubAction<R>) = with(context) { action() }
}
