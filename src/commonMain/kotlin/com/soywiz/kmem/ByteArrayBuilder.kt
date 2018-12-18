package com.soywiz.kmem

import kotlin.math.max

class ByteArrayBuilder private constructor(var data: ByteArray, size: Int = data.size, val allowGrow: Boolean = true) {
    constructor(initialCapacity: Int = 4096) : this(ByteArray(initialCapacity), 0)

    private var _size: Int = size
    val size: Int get() = _size

    private fun ensure(expected: Int) {
        if (data.size < expected) {
            if (!allowGrow) throw RuntimeException("ByteArrayBuffer configured to not grow!")
            data = data.copyOf(max(expected, (data.size + 7) * 5))
        }
    }

    private inline fun <T> prepare(count: Int, callback: () -> T): T {
        ensure(_size + count)
        return callback().also { _size += count }
    }

    fun append(array: ByteArray, offset: Int = 0, len: Int = array.size - offset) {
        prepare(len) {
            arraycopy(array, offset, this.data, _size, len)
        }
    }

    fun append(v: Int): Unit = run { prepare(1) { data[_size] = v.toByte() } }
    fun appendShortLE(v: Int): Unit = prepare(2) { data.write16_le(_size, v) }
    fun appendShortBE(v: Int): Unit = prepare(2) { data.write16_be(_size, v) }
    fun appendIntLE(v: Int): Unit = prepare(4) { data.write32_le(_size, v) }
    fun appendIntBE(v: Int): Unit = prepare(4) { data.write32_be(_size, v) }
    fun appendFloatLE(v: Float): Unit = prepare(4) { data.writeF32_le(_size, v) }
    fun appendFloatBE(v: Float): Unit = prepare(4) { data.writeF32_be(_size, v) }

    fun clear() {
        _size = 0
    }

    fun toByteArray(): ByteArray = data.copyOf(_size)
}

inline fun buildByteArray(capacity: Int = 4096, callback: ByteArrayBuilder.() -> Unit): ByteArray =
    ByteArrayBuilder(capacity).apply(callback).toByteArray()
