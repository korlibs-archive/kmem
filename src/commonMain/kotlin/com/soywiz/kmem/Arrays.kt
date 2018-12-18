package com.soywiz.kmem

inline class UByteArrayInt(val bytes: ByteArray) {
    val size: Int get() = bytes.size
    operator fun get(index: Int) = bytes[index].toInt() and 0xFF
    operator fun set(index: Int, value: Int) = run { bytes[index] = value.toByte() }
}

fun UByteArrayInt(size: Int) = UByteArrayInt(ByteArray(size))

fun ByteArray.asUByteArrayInt() = UByteArrayInt(this)
fun UByteArrayInt.asByteArray() = this.bytes

inline class FloatArrayFromIntArray(val base: IntArray) {
    operator fun get(i: Int) = base[i].reinterpretAsFloat()
    operator fun set(i: Int, v: Float) = run { base[i] = v.reinterpretAsInt() }
}

fun IntArray.asFloatArray(): FloatArrayFromIntArray = FloatArrayFromIntArray(this)
fun FloatArrayFromIntArray.asIntArray(): IntArray = base

/////////////////////////
/////////////////////////

fun ByteArray.contains(other: ByteArray): Boolean = indexOf(other) >= 0

fun ByteArray.indexOf(other: ByteArray): Int {
    val full = this
    for (n in 0 until full.size - other.size) if (other.indices.all { full[n + it] == other[it] }) return n
    return -1
}
