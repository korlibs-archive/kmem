package com.soywiz.kmem

object Kmem {
	val VERSION = KMEM_VERSION
}

/*
// @TODO: Can't match ByteBuffer (abstract) and Int8Array (non abstract) so one implementation will suffer one level of indirection
expect class MemBuffer

expect class DataBuffer
expect class Int8Buffer
expect class Int16Buffer
expect class Int32Buffer
expect class Float32Buffer
expect class Float64Buffer

expect fun MemBufferAlloc(size: Int): MemBuffer
expect fun MemBufferWrap(array: ByteArray): MemBuffer
expect val MemBuffer.size: Int
expect fun MemBuffer._sliceData(byteOffset: Int, size: Int): DataBuffer
expect fun MemBuffer._sliceInt8(byteOffset: Int, size: Int): Int8Buffer
expect fun MemBuffer._sliceInt16(byteOffset: Int, size: Int): Int16Buffer
expect fun MemBuffer._sliceInt32(byteOffset: Int, size: Int): Int32Buffer
expect fun MemBuffer._sliceFloat32(byteOffset: Int, size: Int): Float32Buffer
expect fun MemBuffer._sliceFloat64(byteOffset: Int, size: Int): Float64Buffer

inline fun MemBuffer.sliceData(byteOffset: Int = 0, size: Int = this.size - byteOffset): DataBuffer = _sliceData(byteOffset, size)
inline fun MemBuffer.sliceInt8(byteOffset: Int = 0, size: Int = this.size - byteOffset): Int8Buffer = _sliceInt8(byteOffset, size)
inline fun MemBuffer.sliceInt16(byteOffset: Int = 0, size: Int = this.size / 2 - byteOffset): Int16Buffer = _sliceInt16(byteOffset, size * 2)
inline fun MemBuffer.sliceInt32(byteOffset: Int = 0, size: Int = this.size / 4 - byteOffset): Int32Buffer = _sliceInt32(byteOffset, size * 4)
inline fun MemBuffer.sliceFloat32(byteOffset: Int = 0, size: Int = this.size / 4 - byteOffset): Float32Buffer = _sliceFloat32(byteOffset, size * 4)
inline fun MemBuffer.sliceFloat64(byteOffset: Int = 0, size: Int = this.size / 8 - byteOffset): Float64Buffer = _sliceFloat64(byteOffset, size * 8)

inline fun MemBuffer.asDataBuffer(): DataBuffer = sliceData()
inline fun MemBuffer.asInt8Buffer(): Int8Buffer = sliceInt8()
inline fun MemBuffer.asInt16Buffer(): Int16Buffer = sliceInt16()
inline fun MemBuffer.asInt32Buffer(): Int32Buffer = sliceInt32()
inline fun MemBuffer.asFloat32Buffer(): Float32Buffer = sliceFloat32()
inline fun MemBuffer.asFloat64Buffer(): Float64Buffer = sliceFloat64()

expect val Int8Buffer.buffer: MemBuffer
expect val Int16Buffer.buffer: MemBuffer
expect val Int32Buffer.buffer: MemBuffer
expect val Float32Buffer.buffer: MemBuffer
expect val Float64Buffer.buffer: MemBuffer

expect val Int8Buffer.byteOffset: Int
expect val Int16Buffer.byteOffset: Int
expect val Int32Buffer.byteOffset: Int
expect val Float32Buffer.byteOffset: Int
expect val Float64Buffer.byteOffset: Int

expect val Int8Buffer.size: Int
expect val Int16Buffer.size: Int
expect val Int32Buffer.size: Int
expect val Float32Buffer.size: Int
expect val Float64Buffer.size: Int

expect operator fun Int8Buffer.get(index: Int): Byte
expect operator fun Int16Buffer.get(index: Int): Short
expect operator fun Int32Buffer.get(index: Int): Int
expect operator fun Float32Buffer.get(index: Int): Float
expect operator fun Float64Buffer.get(index: Int): Double

expect operator fun Int8Buffer.set(index: Int, value: Byte): Unit
expect operator fun Int16Buffer.set(index: Int, value: Short): Unit
expect operator fun Int32Buffer.set(index: Int, value: Int): Unit
expect operator fun Float32Buffer.set(index: Int, value: Float): Unit
expect operator fun Float64Buffer.set(index: Int, value: Double): Unit

expect fun MemBuffer.getI8(index: Int): Byte
expect fun MemBuffer.getI16(index: Int): Short
expect fun MemBuffer.getI32(index: Int): Int
expect fun MemBuffer.getF32(index: Int): Float
expect fun MemBuffer.getF64(index: Int): Double

expect fun MemBuffer.setI8(index: Int, value: Byte): Unit
expect fun MemBuffer.setI16(index: Int, value: Short): Unit
expect fun MemBuffer.setI32(index: Int, value: Int): Unit
expect fun MemBuffer.setF32(index: Int, value: Float): Unit
expect fun MemBuffer.setF64(index: Int, value: Double): Unit

expect fun arraycopy(src: BooleanArray, srcPos: Int, dst: BooleanArray, dstPos: Int, size: Int): Unit
expect fun arraycopy(src: ByteArray, srcPos: Int, dst: ByteArray, dstPos: Int, size: Int): Unit
expect fun arraycopy(src: ShortArray, srcPos: Int, dst: ShortArray, dstPos: Int, size: Int): Unit
expect fun arraycopy(src: CharArray, srcPos: Int, dst: CharArray, dstPos: Int, size: Int): Unit
expect fun arraycopy(src: IntArray, srcPos: Int, dst: IntArray, dstPos: Int, size: Int): Unit
expect fun arraycopy(src: FloatArray, srcPos: Int, dst: FloatArray, dstPos: Int, size: Int): Unit
expect fun arraycopy(src: DoubleArray, srcPos: Int, dst: DoubleArray, dstPos: Int, size: Int): Unit
*/
