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

    fun append(v: Byte) = this.apply { prepare(1) { data[_size] = v } }

    fun appendByte(v: Int) = this.apply { prepare(1) { data[_size] = v.toByte() } }
    fun appendBytes(vararg v: Byte) = append(v)
    fun appendBytes(vararg v: Int) {
        prepare(v.size) {
            for (n in 0 until v.size) this.data[this._size + n] = v[n].toByte()
        }
    }

    fun appendShort(v: Int, little: Boolean) = this.apply { prepare(2) { data.write16(_size, v, little) } }
    fun appendShortBE(v: Int) = this.apply { prepare(2) { data.write16BE(_size, v) } }
    fun appendShortLE(v: Int) = this.apply { prepare(2) { data.write16LE(_size, v) } }

    fun append24Bits(v: Int, little: Boolean) = this.apply { prepare(3) { data.write24(_size, v, little) } }
    fun append24BitsBE(v: Int) = this.apply { prepare(3) { data.write24BE(_size, v) } }
    fun append24BitsLE(v: Int) = this.apply { prepare(3) { data.write24LE(_size, v) } }

    fun appendInt(v: Int, little: Boolean) = this.apply { prepare(4) { data.write32(_size, v, little) } }
    fun appendIntLE(v: Int) = this.apply { prepare(4) { data.write32LE(_size, v) } }
    fun appendIntBE(v: Int) = this.apply { prepare(4) { data.write32BE(_size, v) } }

    fun appendHalfFloat(v: Float16, little: Boolean) = this.apply { prepare(2) { data.writeF16(_size, v, little) } }
    fun appendHalfFloatLE(v: Float16) = this.apply { prepare(2) { data.writeF16LE(_size, v) } }
    fun appendHalfFloatBE(v: Float16) = this.apply { prepare(2) { data.writeF16BE(_size, v) } }

    fun appendFloat(v: Float, little: Boolean) = this.apply { prepare(4) { data.writeF32(_size, v, little) } }
    fun appendFloatLE(v: Float) = this.apply { prepare(4) { data.writeF32LE(_size, v) } }
    fun appendFloatBE(v: Float) = this.apply { prepare(4) { data.writeF32BE(_size, v) } }

    fun appendDouble(v: Double, little: Boolean) = this.apply { prepare(8) { data.writeF64(_size, v, little) } }
    fun appendDoubleLE(v: Double) = this.apply { prepare(8) { data.writeF64LE(_size, v) } }
    fun appendDoubleBE(v: Double) = this.apply { prepare(8) { data.writeF64BE(_size, v) } }

    fun clear() {
        _size = 0
    }

    fun toByteArray(): ByteArray = data.copyOf(_size)
}

inline class ByteArrayBuilderLE(val bab: ByteArrayBuilder)

val ByteArrayBuilderLE.size get() = bab.size
fun ByteArrayBuilderLE.append(array: ByteArray, offset: Int = 0, len: Int = array.size - offset) = bab.append(array, offset, len)
fun ByteArrayBuilderLE.append(v: Byte) = bab.append(v)
fun ByteArrayBuilderLE.appendByte(v: Int) = bab.appendByte(v)
fun ByteArrayBuilderLE.appendBytes(vararg v: Byte) = bab.appendBytes(*v)
fun ByteArrayBuilderLE.appendBytes(vararg v: Int) = bab.appendBytes(*v)
fun ByteArrayBuilderLE.appendShort(v: Int) = bab.appendShortLE(v)
fun ByteArrayBuilderLE.append24Bits(v: Int) = bab.append24BitsLE(v)
fun ByteArrayBuilderLE.appendInt(v: Int) = bab.appendIntLE(v)
fun ByteArrayBuilderLE.appendHalfFloat(v: Float16) = bab.appendHalfFloatLE(v)
fun ByteArrayBuilderLE.appendFloat(v: Float) = bab.appendFloatLE(v)
fun ByteArrayBuilderLE.appendDouble(v: Double) = bab.appendDoubleLE(v)
fun ByteArrayBuilderLE.clear() = bab.clear()
fun ByteArrayBuilderLE.toByteArray(): ByteArray = bab.toByteArray()

inline class ByteArrayBuilderBE(val bab: ByteArrayBuilder)

val ByteArrayBuilderBE.size get() = bab.size
fun ByteArrayBuilderBE.append(array: ByteArray, offset: Int = 0, len: Int = array.size - offset) = bab.append(array, offset, len)
fun ByteArrayBuilderBE.append(v: Byte) = bab.append(v)
fun ByteArrayBuilderBE.appendByte(v: Int) = bab.appendByte(v)
fun ByteArrayBuilderBE.appendBytes(vararg v: Byte) = bab.appendBytes(*v)
fun ByteArrayBuilderBE.appendBytes(vararg v: Int) = bab.appendBytes(*v)
fun ByteArrayBuilderBE.appendShort(v: Int) = bab.appendShortBE(v)
fun ByteArrayBuilderBE.append24Bits(v: Int) = bab.append24BitsBE(v)
fun ByteArrayBuilderBE.appendInt(v: Int) = bab.appendIntBE(v)
fun ByteArrayBuilderBE.appendHalfFloat(v: Float16) = bab.appendHalfFloatBE(v)
fun ByteArrayBuilderBE.appendFloat(v: Float) = bab.appendFloatBE(v)
fun ByteArrayBuilderBE.appendDouble(v: Double) = bab.appendDoubleBE(v)
fun ByteArrayBuilderBE.clear() = bab.clear()
fun ByteArrayBuilderBE.toByteArray(): ByteArray = bab.toByteArray()

inline fun buildByteArray(capacity: Int = 4096, callback: ByteArrayBuilder.() -> Unit): ByteArray =
    ByteArrayBuilder(capacity).apply(callback).toByteArray()

inline fun buildByteArrayLE(capacity: Int = 4096, callback: ByteArrayBuilderLE.() -> Unit): ByteArray =
    ByteArrayBuilderLE(ByteArrayBuilder(capacity)).apply(callback).toByteArray()

inline fun buildByteArrayBE(capacity: Int = 4096, callback: ByteArrayBuilderBE.() -> Unit): ByteArray =
    ByteArrayBuilderBE(ByteArrayBuilder(capacity)).apply(callback).toByteArray()
