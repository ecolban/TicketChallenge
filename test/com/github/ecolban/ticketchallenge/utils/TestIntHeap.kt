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
        assertEquals(testArray.max(), heap.peek())
    }

    @Test
     fun `test empty heap`() {
        val heap = IntHeap(intArrayOf())
        assertTrue(heap.isEmpty())
    }

    @Test
    fun `test pop`() {
        val heap = IntHeap(intArrayOf(8, 3, 9, 6, 2, 1, 9, 4, 3))
        var prev: Int = heap.pop() ?: Int.MIN_VALUE
        while (!heap.isEmpty()) {
            val tmp: Int = heap.pop() ?: Int.MAX_VALUE
            assertTrue(prev >= tmp)
            prev = tmp
        }
    }

    @Test
    fun `random test`() {
        val rng: Random.Default = Random.Default
        val array = (1..10000).map { rng.nextInt(1, 1000) }.toIntArray()
        val heap = IntHeap(array)
        assertEquals(array.sortedDescending(), generateSequence { heap.pop() }.toList())
    }


}