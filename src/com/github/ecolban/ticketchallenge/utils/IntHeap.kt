package com.github.ecolban.ticketchallenge.utils


class IntHeap(private val array: IntArray) {

    private var last: Int = array.size - 1

    init {
        for (i in (last - 1) / 2 downTo 0) sink(array[i], i)
    }

    fun isEmpty(): Boolean = last < 0

    fun pop(): Int? = if (last >= 0) {
        val result = array[0]
        sink(array[last--], 0)
        result
    } else null

    fun peek(): Int? = if (last >= 0) array[0] else null

    private tailrec fun sink(value: Int, index: Int): Unit {
        val left = 2 * index + 1
        if (left > last) {
            array[index] = value
            return
        }
        val right = 2 * index + 2
        val nextIndex = if (right > last || array[left] > array[right]) left else right
        if (array[nextIndex] > value) {
            array[index] = array[nextIndex]
            sink(value, nextIndex)
        } else {
            array[index] = value
        }
    }
}
