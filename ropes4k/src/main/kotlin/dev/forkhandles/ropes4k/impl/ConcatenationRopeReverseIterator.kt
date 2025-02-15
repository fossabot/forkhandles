/*
 * Copyright (C) 2024 James Richardson
 *  - Originally Copyright (C) 2007 Amin Ahmad.
 * Licenced under GPL
 */
package dev.forkhandles.ropes4k.impl

import dev.forkhandles.ropes4k.Rope
import java.util.ArrayDeque

/**
 * A fast reverse iterator for concatenated ropes. Iterating over
 * a complex rope structure is guaranteed to be O(n) so long as it
 * is reasonably well-balanced. Compare this to O(n log n) for
 * iteration using `charAt`.
 */
internal class ConcatenationRopeReverseIterator(private val rope: Rope, start: Int) : MutableIterator<Char> {
    private val toTraverse = ArrayDeque<Rope>()
    private var currentRope: Rope?
    private var currentRopePos = 0
    private var skip = 0
    var pos: Int = 0
        private set

    init {
        toTraverse.push(rope)
        currentRope = null
        initialize()

        require(!(start < 0 || start > rope.length)) { "Rope index out of range: $start" }
        moveForward(start)
    }

    fun canMoveBackwards(amount: Int): Boolean {
        return (currentRopePos + amount <= currentRope!!.length)
    }

    override fun hasNext(): Boolean {
        return currentRopePos > 0 || !toTraverse.isEmpty()
    }

    private fun initialize() {
        while (!toTraverse.isEmpty()) {
            currentRope = toTraverse.pop()
            if (currentRope is ConcatenationRope) {
                toTraverse.push((currentRope as ConcatenationRope).left)
                toTraverse.push((currentRope as ConcatenationRope).right)
            } else {
                break
            }
        }
        requireNotNull(currentRope) { "No terminal ropes present." }
        currentRopePos = currentRope!!.length
        pos = rope.length
    }

    fun moveBackwards(amount: Int) {
        require(canMoveBackwards(amount)) { "Unable to move backwards $amount." }
        currentRopePos += amount
        pos += amount
    }

    fun moveForward(amount: Int) {
        pos -= amount
        var remainingAmt = amount
        while (remainingAmt != 0) {
            if (currentRopePos - remainingAmt > -1) {
                currentRopePos -= remainingAmt
                return
            }
            remainingAmt = remainingAmt - currentRopePos
            require(!(remainingAmt > 0 && toTraverse.isEmpty())) { "Unable to move forward $amount. Reached end of rope." }

            while (!toTraverse.isEmpty()) {
                currentRope = toTraverse.pop()
                if (currentRope is ConcatenationRope) {
                    toTraverse.push((currentRope as ConcatenationRope).left)
                    toTraverse.push((currentRope as ConcatenationRope).right)
                } else {
                    currentRopePos = currentRope!!.length
                    break
                }
            }
        }
    }

    override fun next(): Char {
        moveForward(1 + skip)
        skip = 0
        return currentRope!![currentRopePos]
    }

    override fun remove() {
        throw UnsupportedOperationException("Rope iterator is read-only.")
    }

    fun skip(skip: Int) {
        this.skip = skip
    }
}
