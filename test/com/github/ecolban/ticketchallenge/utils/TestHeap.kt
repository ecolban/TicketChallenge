package com.github.ecolban.ticketchallenge.utils

import org.junit.Test
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestHeap {

    @Test
    fun `test constructor`() {
        val strings = "The quick brown fox jumps over the lazy dog".toLowerCase().split(" ")
        val heap = Heap(strings)
        assertEquals(strings.max(), heap.peek())
    }

    @Test
    fun `test empty heap`() {
        val heap = Heap<String>()
        assertTrue(heap.isEmpty)
    }

    @Test
    fun `test pop`() {
        val strings = "The quick brown fox jumps over the lazy dog".toLowerCase().split(" ")
        val heap = Heap(strings)
        var prev: String = heap.pop()
        while (!heap.isEmpty) {
            val tmp: String = heap.pop()
            assertTrue(prev >= tmp)
            prev = tmp
        }
    }

    @Test
    fun `test push`() {
        val strings = "The quick brown fox jumps over the lazy dog".toLowerCase().split(" ")
        val heap = Heap<String>()
        for (element in strings) heap.push(element)
        val expected = strings.sortedDescending()
        assertEquals(expected, generateSequence { if (!heap.isEmpty) heap.pop() else null }.toList())
    }

    @Test
    fun `random test`() {
        val rng: Random.Default = Random.Default
        val values = (1..1000).map { rng.nextInt(1, 100000) }
        val expected = values.sortedDescending()
        val heap = Heap(values)
        assertEquals(expected, generateSequence { if (!heap.isEmpty) heap.pop() else null }.toList())
    }


}