package dev.forkhandles.hub4k

@Target(AnnotationTarget.CLASS)

/**
 * Marker attached to all actions to drive the adapter code generation.
 *
 * docs: Optional information for this action. Can be link or other notes.
 */
annotation class Hub4kAction(val docs: String = "")
