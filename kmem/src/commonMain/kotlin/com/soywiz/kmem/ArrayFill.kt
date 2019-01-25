package com.soywiz.kmem

expect fun <T> arrayfill(array: Array<T>, value: T, start: Int, end: Int): Unit
expect fun arrayfill(array: BooleanArray, value: Boolean, start: Int, end: Int): Unit
expect fun arrayfill(array: LongArray, value: Long, start: Int, end: Int): Unit
expect fun arrayfill(array: ByteArray, value: Byte, start: Int, end: Int): Unit
expect fun arrayfill(array: ShortArray, value: Short, start: Int, end: Int): Unit
expect fun arrayfill(array: IntArray, value: Int, start: Int, end: Int): Unit
expect fun arrayfill(array: FloatArray, value: Float, start: Int, end: Int): Unit
expect fun arrayfill(array: DoubleArray, value: Double, start: Int, end: Int): Unit

fun <T> Array<T>.fill(value: T, start: Int = 0, end: Int = this.size): Unit = arrayfill(this, value, start, end)
fun BooleanArray.fill(value: Boolean, start: Int = 0, end: Int = this.size): Unit = arrayfill(this, value, start, end)
fun LongArray.fill(value: Long, start: Int = 0, end: Int = this.size): Unit = arrayfill(this, value, start, end)
inline fun ByteArray.fill(value: Byte, start: Int = 0, end: Int = this.size): Unit = arrayfill(this, value, start, end)
inline fun ShortArray.fill(value: Short, start: Int = 0, end: Int = this.size): Unit = arrayfill(this, value, start, end)
inline fun IntArray.fill(value: Int, start: Int = 0, end: Int = this.size): Unit = arrayfill(this, value, start, end)
inline fun FloatArray.fill(value: Float, start: Int = 0, end: Int = this.size): Unit = arrayfill(this, value, start, end)
inline fun DoubleArray.fill(value: Double, start: Int = 0, end: Int = this.size): Unit = arrayfill(this, value, start, end)
