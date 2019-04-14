package com.github.ecolban.ticketchallenge.utils

import java.lang.IllegalArgumentException


class IntHeap(private val array: IntArray, private var last: Int = array.size - 1) {

    constructor(capacity: Int) : this(IntArray(capacity), last = -1)

    init {
        if (last < -1 || array.size <= last) {
            throw IllegalArgumentException("array cannot be under-empty or over-full.")
        }
        for (i in (last - 1) / 2 downTo 0) sink(array[i], i)
    }

    val isEmpty: Boolean
        get() = last < 0

    fun push(element: Int) = if (last >= array.size - 1) {
        throw IllegalStateException("Heap is full.")
    } else {
        float(element, ++last)
    }


    fun pop(): Int = if (isEmpty) {
        throw IllegalStateException("Heap is empty.")
    } else {
        val result = array[0]
        sink(array[last--], 0)
        result
    }

    fun peek(): Int = if (isEmpty) throw IllegalStateException("Heap is empty.") else array[0]

    private tailrec fun float(element: Int, index: Int) {
        if (index == 0) {
            array[0] = element
            return
        }
        val nextIndex = (index - 1) / 2
        if (array[nextIndex] < element) {
            array[index] = array[nextIndex]
            float(element, nextIndex)
        } else {
            array[index] = element
        }
    }

    private tailrec fun sink(element: Int, index: Int): Unit {
        val left = 2 * index + 1
        if (left > last) {
            array[index] = element
            return
        }
        val right = left + 1
        val nextIndex = if (right > last || array[left] > array[right]) left else right
        if (array[nextIndex] > element) {
            array[index] = array[nextIndex]
            sink(element, nextIndex)
        } else {
            array[index] = element
        }
    }
}
