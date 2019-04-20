package com.github.ecolban.ticketchallenge.utils

import org.junit.Test
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestIntHeap {

    @Test
    fun `test constructor`() {

        val testArray = intArrayOf(8, 3, 9, 6, 2, 1, 9, 4, 3)
        val heap = IntHeap(testArray)
        assertTrue(heap.isFull)
        assertEquals(testArray.max(), heap.peek())
    }

    @Test
    fun `test constructor 2`() {
        val heap = IntHeap(0)
        assertTrue(heap.isEmpty)
        assertTrue(heap.isFull)
    }

    @Test
    fun `test constructor 3`() {
        val heap = IntHeap(10)
        assertTrue(heap.isEmpty)
    }

    @Test
    fun `test empty heap`() {
        val heap = IntHeap(intArrayOf())
        assertTrue(heap.isEmpty)
    }

    @Test
    fun `test pop`() {
        val heap = IntHeap(intArrayOf(8, 3, 9, 6, 2, 1, 9, 4, 3))
        var prev: Int = heap.pop()
        while (!heap.isEmpty) {
            val tmp: Int = heap.pop()
            assertTrue(prev >= tmp)
            prev = tmp
        }
    }

    @Test
    fun `test push`() {
        val array = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        val heap = IntHeap(array.size)
        for (element in array) heap.push(element)
        assertEquals(array.sortedDescending(), generateSequence { if (!heap.isEmpty) heap.pop() else null }.toList())
    }

    @Test
    fun `random test`() {
        val rng: Random.Default = Random.Default
        val array = (1..1000).map { rng.nextInt(1, 100000) }.toIntArray()
        val heap1 = IntHeap(array.size)
        for (element in array) heap1.push(element)
        val expected = array.sortedDescending()
        assertEquals(expected, generateSequence { if (!heap1.isEmpty) heap1.pop() else null }.toList())
        val heap2 = IntHeap(array)
        assertEquals(expected, generateSequence { if (!heap2.isEmpty) heap2.pop() else null }.toList())
    }


}