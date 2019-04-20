package com.github.ecolban.ticketchallenge.utils

class Heap<T : Comparable<T>>(elements: Collection<T>) {

    constructor(): this(listOf<T>())

    private var array = elements.toMutableList()
    private var last = array.size - 1

    init {
        for (i in (last - 1) / 2 downTo 0) sinkIt(array[i], i)
    }

    val isEmpty: Boolean
        get() = last < 0

    fun push(element: T)  {
        array.add(element)
        floatIt(element, ++last)
    }

    fun pop(): T = if (isEmpty) {
        throw IllegalStateException("Heap is empty.")
    } else {
        val result = array[0]
        sinkIt(array[last--], 0)
        result
    }

    fun peek(): T = if (isEmpty) throw IllegalStateException("Heap is empty.") else array[0]

    private tailrec fun floatIt(element: T, index: Int): Unit {
        if (index == 0) {
            array[0] = element
            return
        }
        val nextIndex = (index - 1) / 2
        if (array[nextIndex] < element) {
            array[index] = array[nextIndex]
            floatIt(element, nextIndex)
        } else {
            array[index] = element
        }
    }

    private tailrec fun sinkIt(element: T, index: Int): Unit {
        val left = 2 * index + 1
        if (left > last) {
            array[index] = element
            return
        }
        val right = left + 1
        val nextIndex = if (right > last || array[left] > array[right]) left else right
        if (array[nextIndex] > element) {
            array[index] = array[nextIndex]
            sinkIt(element, nextIndex)
        } else {
            array[index] = element
        }
    }

}