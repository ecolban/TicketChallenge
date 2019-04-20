package com.github.ecolban.ticketchallenge.utils

import org.junit.Test
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertFalse
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
        assertTrue(heap.isEmpty())
        val heap2 = Heap(listOf<String>())
        assertTrue(heap2.isEmpty())
    }

    @Test
    fun `test pop`() {
        val strings = "The quick brown fox jumps over the lazy dog".toLowerCase().split(" ")
        val heap = Heap(strings)
        var prev: String = heap.pop()
        while (!heap.isEmpty()) {
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
        assertEquals(expected, heap.toSequence().toList())
    }

    @Test
    fun `test contains and containsAll`() {
        val strings = "The quick brown fox jumps over the lazy dog".toLowerCase().split(" ")
        val heap = Heap(strings)
        for (s in strings) assertTrue(heap.contains(s))
        assertTrue(heap.containsAll(strings))
        assertFalse(heap.contains("alien"))
        assertFalse(heap.containsAll("the quick brown fox jumps over the lazy alien dog".split(" ")))
    }

    @Test
    fun `random test`() {
        val rng: Random.Default = Random.Default
        val values = (1..1000).map { rng.nextInt(1, 100000) }
        val heap = Heap(values)
        for (i in 1..100) heap.pop()
        val expected = values.sortedDescending().subList(100, 1000)
        assertEquals(900, heap.size)
        assertEquals(expected, heap.toSequence().toList())
    }


}