// @WARNING: File AUTOGENERATED by `kmem/jvm/src/test/kotlin/Generator.kt` do not modify manually!
@file:Suppress("NOTHING_TO_INLINE", "EXTENSION_SHADOWED_BY_MEMBER", "RedundantUnitReturnType", "FunctionName")
package com.soywiz.kmem

expect class MemBuffer
expect fun MemBufferAlloc(size: Int): MemBuffer
expect fun MemBufferWrap(array: ByteArray): MemBuffer
expect val MemBuffer.size: Int

expect fun MemBuffer._sliceInt8Buffer(offset: Int, size: Int): Int8Buffer
expect fun MemBuffer._sliceInt16Buffer(offset: Int, size: Int): Int16Buffer
expect fun MemBuffer._sliceInt32Buffer(offset: Int, size: Int): Int32Buffer
expect fun MemBuffer._sliceFloat32Buffer(offset: Int, size: Int): Float32Buffer
expect fun MemBuffer._sliceFloat64Buffer(offset: Int, size: Int): Float64Buffer

inline fun MemBuffer.sliceInt8Buffer(offset: Int = 0, size: Int = (this.size / 1) - offset): Int8Buffer = this._sliceInt8Buffer(offset, size)
inline fun MemBuffer.sliceInt16Buffer(offset: Int = 0, size: Int = (this.size / 2) - offset): Int16Buffer = this._sliceInt16Buffer(offset, size)
inline fun MemBuffer.sliceInt32Buffer(offset: Int = 0, size: Int = (this.size / 4) - offset): Int32Buffer = this._sliceInt32Buffer(offset, size)
inline fun MemBuffer.sliceFloat32Buffer(offset: Int = 0, size: Int = (this.size / 4) - offset): Float32Buffer = this._sliceFloat32Buffer(offset, size)
inline fun MemBuffer.sliceFloat64Buffer(offset: Int = 0, size: Int = (this.size / 8) - offset): Float64Buffer = this._sliceFloat64Buffer(offset, size)

inline fun MemBuffer.asInt8Buffer(): Int8Buffer = this.sliceInt8Buffer()
inline fun MemBuffer.asInt16Buffer(): Int16Buffer = this.sliceInt16Buffer()
inline fun MemBuffer.asInt32Buffer(): Int32Buffer = this.sliceInt32Buffer()
inline fun MemBuffer.asFloat32Buffer(): Float32Buffer = this.sliceFloat32Buffer()
inline fun MemBuffer.asFloat64Buffer(): Float64Buffer = this.sliceFloat64Buffer()

expect class DataBuffer
expect fun MemBuffer.getData(): DataBuffer
expect fun DataBuffer.getByte(index: Int): Byte
expect fun DataBuffer.setByte(index: Int, value: Byte): Unit
expect fun DataBuffer.getShort(index: Int): Short
expect fun DataBuffer.setShort(index: Int, value: Short): Unit
expect fun DataBuffer.getInt(index: Int): Int
expect fun DataBuffer.setInt(index: Int, value: Int): Unit
expect fun DataBuffer.getFloat(index: Int): Float
expect fun DataBuffer.setFloat(index: Int, value: Float): Unit
expect fun DataBuffer.getDouble(index: Int): Double
expect fun DataBuffer.setDouble(index: Int, value: Double): Unit

expect class Int8Buffer
inline fun Int8BufferAlloc(size: Int): Int8Buffer = MemBufferAlloc(size * 1).sliceInt8Buffer() // @TODO: Can't use class name directly (it fails in JS)
expect val Int8Buffer.mem: MemBuffer
expect val Int8Buffer.offset: Int
expect val Int8Buffer.size: Int
expect operator fun Int8Buffer.get(index: Int): Byte
expect operator fun Int8Buffer.set(index: Int, value: Byte): Unit
fun Int8Buffer.subarray(begin: Int, end: Int = this.size): Int8Buffer = this.mem.sliceInt8Buffer(this.offset + begin, end - begin)

expect class Int16Buffer
inline fun Int16BufferAlloc(size: Int): Int16Buffer = MemBufferAlloc(size * 2).sliceInt16Buffer() // @TODO: Can't use class name directly (it fails in JS)
expect val Int16Buffer.mem: MemBuffer
expect val Int16Buffer.offset: Int
expect val Int16Buffer.size: Int
expect operator fun Int16Buffer.get(index: Int): Short
expect operator fun Int16Buffer.set(index: Int, value: Short): Unit
fun Int16Buffer.subarray(begin: Int, end: Int = this.size): Int16Buffer = this.mem.sliceInt16Buffer(this.offset + begin, end - begin)

expect class Int32Buffer
inline fun Int32BufferAlloc(size: Int): Int32Buffer = MemBufferAlloc(size * 4).sliceInt32Buffer() // @TODO: Can't use class name directly (it fails in JS)
expect val Int32Buffer.mem: MemBuffer
expect val Int32Buffer.offset: Int
expect val Int32Buffer.size: Int
expect operator fun Int32Buffer.get(index: Int): Int
expect operator fun Int32Buffer.set(index: Int, value: Int): Unit
fun Int32Buffer.subarray(begin: Int, end: Int = this.size): Int32Buffer = this.mem.sliceInt32Buffer(this.offset + begin, end - begin)

expect class Float32Buffer
inline fun Float32BufferAlloc(size: Int): Float32Buffer = MemBufferAlloc(size * 4).sliceFloat32Buffer() // @TODO: Can't use class name directly (it fails in JS)
expect val Float32Buffer.mem: MemBuffer
expect val Float32Buffer.offset: Int
expect val Float32Buffer.size: Int
expect operator fun Float32Buffer.get(index: Int): Float
expect operator fun Float32Buffer.set(index: Int, value: Float): Unit
fun Float32Buffer.subarray(begin: Int, end: Int = this.size): Float32Buffer = this.mem.sliceFloat32Buffer(this.offset + begin, end - begin)

expect class Float64Buffer
inline fun Float64BufferAlloc(size: Int): Float64Buffer = MemBufferAlloc(size * 8).sliceFloat64Buffer() // @TODO: Can't use class name directly (it fails in JS)
expect val Float64Buffer.mem: MemBuffer
expect val Float64Buffer.offset: Int
expect val Float64Buffer.size: Int
expect operator fun Float64Buffer.get(index: Int): Double
expect operator fun Float64Buffer.set(index: Int, value: Double): Unit
fun Float64Buffer.subarray(begin: Int, end: Int = this.size): Float64Buffer = this.mem.sliceFloat64Buffer(this.offset + begin, end - begin)

fun arraycopy(src: Int8Buffer, srcPos: Int, dst: Int8Buffer, dstPos: Int, size: Int): Unit = arraycopy(src.mem, srcPos * 1, dst.mem, dstPos * 1, size * 1)
fun arraycopy(src: ByteArray, srcPos: Int, dst: Int8Buffer, dstPos: Int, size: Int): Unit = arraycopy(src, srcPos, dst.mem, dstPos, size)
fun arraycopy(src: Int8Buffer, srcPos: Int, dst: ByteArray, dstPos: Int, size: Int): Unit = arraycopy(src.mem, srcPos, dst, dstPos, size)
fun arraycopy(src: Int16Buffer, srcPos: Int, dst: Int16Buffer, dstPos: Int, size: Int): Unit = arraycopy(src.mem, srcPos * 2, dst.mem, dstPos * 2, size * 2)
fun arraycopy(src: ShortArray, srcPos: Int, dst: Int16Buffer, dstPos: Int, size: Int): Unit = arraycopy(src, srcPos, dst.mem, dstPos, size)
fun arraycopy(src: Int16Buffer, srcPos: Int, dst: ShortArray, dstPos: Int, size: Int): Unit = arraycopy(src.mem, srcPos, dst, dstPos, size)
fun arraycopy(src: Int32Buffer, srcPos: Int, dst: Int32Buffer, dstPos: Int, size: Int): Unit = arraycopy(src.mem, srcPos * 4, dst.mem, dstPos * 4, size * 4)
fun arraycopy(src: IntArray, srcPos: Int, dst: Int32Buffer, dstPos: Int, size: Int): Unit = arraycopy(src, srcPos, dst.mem, dstPos, size)
fun arraycopy(src: Int32Buffer, srcPos: Int, dst: IntArray, dstPos: Int, size: Int): Unit = arraycopy(src.mem, srcPos, dst, dstPos, size)
fun arraycopy(src: Float32Buffer, srcPos: Int, dst: Float32Buffer, dstPos: Int, size: Int): Unit = arraycopy(src.mem, srcPos * 4, dst.mem, dstPos * 4, size * 4)
fun arraycopy(src: FloatArray, srcPos: Int, dst: Float32Buffer, dstPos: Int, size: Int): Unit = arraycopy(src, srcPos, dst.mem, dstPos, size)
fun arraycopy(src: Float32Buffer, srcPos: Int, dst: FloatArray, dstPos: Int, size: Int): Unit = arraycopy(src.mem, srcPos, dst, dstPos, size)
fun arraycopy(src: Float64Buffer, srcPos: Int, dst: Float64Buffer, dstPos: Int, size: Int): Unit = arraycopy(src.mem, srcPos * 8, dst.mem, dstPos * 8, size * 8)
fun arraycopy(src: DoubleArray, srcPos: Int, dst: Float64Buffer, dstPos: Int, size: Int): Unit = arraycopy(src, srcPos, dst.mem, dstPos, size)
fun arraycopy(src: Float64Buffer, srcPos: Int, dst: DoubleArray, dstPos: Int, size: Int): Unit = arraycopy(src.mem, srcPos, dst, dstPos, size)

expect fun arraycopy(src: ByteArray, srcPos: Int, dst: ByteArray, dstPos: Int, size: Int): Unit
expect fun arraycopy(src: ShortArray, srcPos: Int, dst: ShortArray, dstPos: Int, size: Int): Unit
expect fun arraycopy(src: IntArray, srcPos: Int, dst: IntArray, dstPos: Int, size: Int): Unit
expect fun arraycopy(src: FloatArray, srcPos: Int, dst: FloatArray, dstPos: Int, size: Int): Unit
expect fun arraycopy(src: DoubleArray, srcPos: Int, dst: DoubleArray, dstPos: Int, size: Int): Unit

expect fun arraycopy(src: MemBuffer, srcPos: Int, dst: MemBuffer, dstPos: Int, size: Int): Unit
expect fun arraycopy(src: ByteArray, srcPos: Int, dst: MemBuffer, dstPos: Int, size: Int): Unit
expect fun arraycopy(src: MemBuffer, srcPos: Int, dst: ByteArray, dstPos: Int, size: Int): Unit
expect fun arraycopy(src: ShortArray, srcPos: Int, dst: MemBuffer, dstPos: Int, size: Int): Unit
expect fun arraycopy(src: MemBuffer, srcPos: Int, dst: ShortArray, dstPos: Int, size: Int): Unit
expect fun arraycopy(src: IntArray, srcPos: Int, dst: MemBuffer, dstPos: Int, size: Int): Unit
expect fun arraycopy(src: MemBuffer, srcPos: Int, dst: IntArray, dstPos: Int, size: Int): Unit
expect fun arraycopy(src: FloatArray, srcPos: Int, dst: MemBuffer, dstPos: Int, size: Int): Unit
expect fun arraycopy(src: MemBuffer, srcPos: Int, dst: FloatArray, dstPos: Int, size: Int): Unit
expect fun arraycopy(src: DoubleArray, srcPos: Int, dst: MemBuffer, dstPos: Int, size: Int): Unit
expect fun arraycopy(src: MemBuffer, srcPos: Int, dst: DoubleArray, dstPos: Int, size: Int): Unit

@PublishedApi expect internal fun _fill(array: ByteArray, value: Byte, pos: Int, size: Int): Unit
@PublishedApi expect internal fun _fill(array: ShortArray, value: Short, pos: Int, size: Int): Unit
@PublishedApi expect internal fun _fill(array: IntArray, value: Int, pos: Int, size: Int): Unit
@PublishedApi expect internal fun _fill(array: FloatArray, value: Float, pos: Int, size: Int): Unit
@PublishedApi expect internal fun _fill(array: DoubleArray, value: Double, pos: Int, size: Int): Unit

inline fun ByteArray.fill(value: Byte, pos: Int = 0, size: Int = this.size): Unit = _fill(this, value, pos, size)
inline fun ShortArray.fill(value: Short, pos: Int = 0, size: Int = this.size): Unit = _fill(this, value, pos, size)
inline fun IntArray.fill(value: Int, pos: Int = 0, size: Int = this.size): Unit = _fill(this, value, pos, size)
inline fun FloatArray.fill(value: Float, pos: Int = 0, size: Int = this.size): Unit = _fill(this, value, pos, size)
inline fun DoubleArray.fill(value: Double, pos: Int = 0, size: Int = this.size): Unit = _fill(this, value, pos, size)
