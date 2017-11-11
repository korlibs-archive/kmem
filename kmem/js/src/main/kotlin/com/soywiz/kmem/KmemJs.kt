package com.soywiz.kmem

/*
import org.khronos.webgl.*

actual typealias MemBuffer = ArrayBuffer
actual typealias Int8Buffer = Int8Array
actual typealias Int16Buffer = Int16Array
actual typealias Int32Buffer = Int32Array
actual typealias Float32Buffer = Float32Array
actual typealias Float64Buffer = Float64Array

actual inline fun MemBufferAlloc(size: Int): MemBuffer = ArrayBuffer(size)
actual inline fun MemBufferWrap(array: ByteArray): MemBuffer = (array.unsafeCast<Int8Array>()).buffer
actual inline val MemBuffer.size: Int get() = this.byteLength
actual inline fun MemBuffer._sliceInt8(byteOffset: Int, size: Int): Int8Buffer = Int8Array(this, byteOffset, size)
actual inline fun MemBuffer._sliceInt16(byteOffset: Int, size: Int): Int16Buffer = Int16Array(this, byteOffset, size)
actual inline fun MemBuffer._sliceInt32(byteOffset: Int, size: Int): Int32Buffer = Int32Array(this, byteOffset, size)
actual inline fun MemBuffer._sliceFloat32(byteOffset: Int, size: Int): Float32Buffer = Float32Array(this, byteOffset, size)
actual inline fun MemBuffer._sliceFloat64(byteOffset: Int, size: Int): Float64Buffer = Float64Array(this, byteOffset, size)

actual inline val Int8Buffer.size: Int get() = this.asDynamic().length
actual inline val Int16Buffer.size: Int get() = this.asDynamic().length
actual inline val Int32Buffer.size: Int get() = this.asDynamic().length
actual inline val Float32Buffer.size: Int get() = this.asDynamic().length
actual inline val Float64Buffer.size: Int get() = this.asDynamic().length

actual inline val Int8Buffer.buffer: MemBuffer get() = this.buffer
actual inline val Int16Buffer.buffer: MemBuffer get() = this.buffer
actual inline val Int32Buffer.buffer: MemBuffer get() = this.buffer
actual inline val Float32Buffer.buffer: MemBuffer get() = this.buffer
actual inline val Float64Buffer.buffer: MemBuffer get() = this.buffer

actual inline val Int8Buffer.byteOffset: Int get() = this.byteOffset
actual inline val Int16Buffer.byteOffset: Int get() = this.byteOffset
actual inline val Int32Buffer.byteOffset: Int get() = this.byteOffset
actual inline val Float32Buffer.byteOffset: Int get() = this.byteOffset
actual inline val Float64Buffer.byteOffset: Int get() = this.byteOffset

actual inline operator fun Int8Buffer.get(index: Int): Byte = this.asDynamic()[index]
actual inline operator fun Int16Buffer.get(index: Int): Short = this.asDynamic()[index]
actual inline operator fun Int32Buffer.get(index: Int): Int = this.asDynamic()[index]
actual inline operator fun Float32Buffer.get(index: Int): Float = this.asDynamic()[index]
actual inline operator fun Float64Buffer.get(index: Int): Double = this.asDynamic()[index]

actual inline operator fun Int8Buffer.set(index: Int, value: Byte): Unit = run { this.asDynamic()[index] = value }
actual inline operator fun Int16Buffer.set(index: Int, value: Short): Unit = run { this.asDynamic()[index] = value }
actual inline operator fun Int32Buffer.set(index: Int, value: Int): Unit = run { this.asDynamic()[index] = value }
actual inline operator fun Float32Buffer.set(index: Int, value: Float): Unit = run { this.asDynamic()[index] = value }
actual inline operator fun Float64Buffer.set(index: Int, value: Double): Unit = run { this.asDynamic()[index] = value }
*/
