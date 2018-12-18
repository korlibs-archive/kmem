package com.soywiz.kmem

// @TODO: Rename methods!

private fun ByteArray.u8(o: Int): Int = this[o].toInt() and 0xFF

private inline fun ByteArray._read16_le(o: Int): Int = (u8(o + 0) shl 0) or (u8(o + 1) shl 8)
private inline fun ByteArray._read24_le(o: Int): Int = (u8(o + 0) shl 0) or (u8(o + 1) shl 8) or (u8(o + 2) shl 16)
private inline fun ByteArray._read32_le(o: Int): Int = (u8(o + 0) shl 0) or (u8(o + 1) shl 8) or (u8(o + 2) shl 16) or (u8(o + 3) shl 24)
private inline fun ByteArray._read64_le(o: Int): Long = (_read32_le(o + 0).unsigned shl 0) or (_read32_le(o + 4).unsigned shl 32)
private inline fun ByteArray._read16_be(o: Int): Int = (u8(o + 1) shl 0) or (u8(o + 0) shl 8)
private inline fun ByteArray._read24_be(o: Int): Int = (u8(o + 2) shl 0) or (u8(o + 1) shl 8) or (u8(o + 0) shl 16)
private inline fun ByteArray._read32_be(o: Int): Int = (u8(o + 3) shl 0) or (u8(o + 2) shl 8) or (u8(o + 1) shl 16) or (u8(o + 0) shl 24)
private inline fun ByteArray._read64_be(o: Int): Long = (_read32_be(o + 4).unsigned shl 0) or (_read32_be(o + 0).unsigned shl 32)

// Unsigned
fun ByteArray.readU8(o: Int): Int = u8(o)
fun ByteArray.readU16_le(o: Int): Int = _read16_le(o)
fun ByteArray.readU24_le(o: Int): Int = _read24_le(o)
fun ByteArray.readU32_le(o: Int): Long = _read32_le(o).unsigned
fun ByteArray.readU16_be(o: Int): Int = _read16_be(o)
fun ByteArray.readU24_be(o: Int): Int = _read24_be(o)
fun ByteArray.readU32_be(o: Int): Long = _read32_be(o).unsigned

fun ByteArray.readS8(o: Int): Int = this[o].toInt()
fun ByteArray.readS16_le(o: Int): Int = _read16_le(o).signExtend(16)
fun ByteArray.readS24_le(o: Int): Int = _read24_le(o).signExtend(24)
fun ByteArray.readS32_le(o: Int): Int = _read32_le(o)
fun ByteArray.readS64_le(o: Int): Long = _read64_le(o)
fun ByteArray.readF32_le(o: Int): Float = Float.fromBits(_read32_le(o))
fun ByteArray.readF64_le(o: Int): Double = Double.fromBits(_read64_le(o))
fun ByteArray.readS16_be(o: Int): Int = _read16_be(o).signExtend(16)
fun ByteArray.readS24_be(o: Int): Int = _read24_be(o).signExtend(24)
fun ByteArray.readS32_be(o: Int): Int = _read32_be(o)
fun ByteArray.readS64_be(o: Int): Long = _read64_be(o)
fun ByteArray.readF32_be(o: Int): Float = Float.fromBits(_read32_be(o))
fun ByteArray.readF64_be(o: Int): Double = Double.fromBits(_read64_be(o))

fun ByteArray.readS16(o: Int, little: Boolean): Int = if (little) readS16_le(o) else readS16_be(o)
fun ByteArray.readS32(o: Int, little: Boolean): Int = if (little) readS32_le(o) else readS32_be(o)
fun ByteArray.readS64(o: Int, little: Boolean): Long = if (little) readS64_le(o) else readS64_be(o)
fun ByteArray.readF32(o: Int, little: Boolean): Float = if (little) readF32_le(o) else readF32_be(o)
fun ByteArray.readF64(o: Int, little: Boolean): Double = if (little) readF64_le(o) else readF64_be(o)

private inline fun <T> ByteArray.readTypedArray(o: Int, count: Int, elementSize: Int, array: T, crossinline read: ByteArray.(array: T, n: Int, pos: Int) -> Unit): T = array.also {
    for (n in 0 until count) read(this, array, n, o + n * elementSize)
}

fun ByteArray.readByteArray(o: Int, count: Int): ByteArray = this.copyOfRange(o, o + count)
fun ByteArray.readShortArray_le(o: Int, count: Int): ShortArray = this.readTypedArray(o, count, 2, ShortArray(count)) { array, n, pos -> array[n] = readS16_le(pos).toShort() }
fun ByteArray.readCharArray_le(o: Int, count: Int): CharArray = this.readTypedArray(o, count, 2, kotlin.CharArray(count)) { array, n, pos -> array[n] = readS16_le(pos).toChar() }
fun ByteArray.readIntArray_le(o: Int, count: Int): IntArray = this.readTypedArray(o, count, 4, IntArray(count)) { array, n, pos -> array[n] = readS32_le(pos) }
fun ByteArray.readLongArray_le(o: Int, count: Int): LongArray = this.readTypedArray(o, count, 8, LongArray(count)) { array, n, pos -> array[n] = readS64_le(pos) }
fun ByteArray.readFloatArray_le(o: Int, count: Int): FloatArray = this.readTypedArray(o, count, 4, FloatArray(count)) { array, n, pos -> array[n] = readF32_le(pos) }
fun ByteArray.readDoubleArray_le(o: Int, count: Int): DoubleArray = this.readTypedArray(o, count, 8, DoubleArray(count)) { array, n, pos -> array[n] = readF64_le(pos) }
fun ByteArray.readShortArray_be(o: Int, count: Int): ShortArray = this.readTypedArray(o, count, 2, ShortArray(count)) { array, n, pos -> array[n] = readS16_be(pos).toShort() }
fun ByteArray.readCharArray_be(o: Int, count: Int): CharArray = this.readTypedArray(o, count, 2, kotlin.CharArray(count)) { array, n, pos -> array[n] = readS16_be(pos).toChar() }
fun ByteArray.readIntArray_be(o: Int, count: Int): IntArray = this.readTypedArray(o, count, 4, IntArray(count)) { array, n, pos -> array[n] = readS32_be(pos) }
fun ByteArray.readLongArray_be(o: Int, count: Int): LongArray = this.readTypedArray(o, count, 8, LongArray(count)) { array, n, pos -> array[n] = readS64_be(pos) }
fun ByteArray.readFloatArray_be(o: Int, count: Int): FloatArray = this.readTypedArray(o, count, 4, FloatArray(count)) { array, n, pos -> array[n] = readF32_be(pos) }
fun ByteArray.readDoubleArray_be(o: Int, count: Int): DoubleArray = this.readTypedArray(o, count, 8, DoubleArray(count)) { array, n, pos -> array[n] = readF64_be(pos) }

/////////////////////////////////////////
/////////////////////////////////////////
/////////////////////////////////////////


fun ByteArray.write8(o: Int, v: Int) = run { this[o] = v.toByte() }
fun ByteArray.write8(o: Int, v: Long) = run { this[o] = v.toByte() }

fun ByteArray.write16(o: Int, v: Int, little: Boolean) = if (little) write16_le(o, v) else write16_be(o, v)
fun ByteArray.write32(o: Int, v: Int, little: Boolean) = if (little) write32_le(o, v) else write32_be(o, v)
fun ByteArray.write64(o: Int, v: Long, little: Boolean) = if (little) write64_le(o, v) else write64_be(o, v)

fun ByteArray.writeF32(o: Int, v: Float, little: Boolean) = if (little) writeF32_le(o, v) else writeF32_be(o, v)
fun ByteArray.writeF64(o: Int, v: Double, little: Boolean) = if (little) writeF64_le(o, v) else writeF64_be(o, v)

fun ByteArray.write16_le(o: Int, v: Int) = run { this[o + 0] = v.extractByte(0); this[o + 1] = v.extractByte(8) }
fun ByteArray.write24_le(o: Int, v: Int) = run { this[o + 0] = v.extractByte(0); this[o + 1] = v.extractByte(8); this[o + 2] = v.extractByte(16) }
fun ByteArray.write32_le(o: Int, v: Int) = run { this[o + 0] = v.extractByte(0); this[o + 1] = v.extractByte(8); this[o + 2] = v.extractByte(16); this[o + 3] = v.extractByte(24) }
fun ByteArray.write32_le(o: Int, v: Long) = write32_le(o, v.toInt())
fun ByteArray.write64_le(o: Int, v: Long) = run { write32_le(o + 0, (v ushr 0).toInt()); write32_le(o + 4, (v ushr 32).toInt()) }
fun ByteArray.writeF32_le(o: Int, v: Float) = run { write32_le(o + 0, v.toRawBits()) }
fun ByteArray.writeF64_le(o: Int, v: Double) = run { write64_le(o + 0, v.toRawBits()) }

fun ByteArray.write16_be(o: Int, v: Int) = run { this[o + 1] = v.extractByte(0); this[o + 0] = v.extractByte(8) }
fun ByteArray.write24_be(o: Int, v: Int) = run { this[o + 2] = v.extractByte(0); this[o + 1] = v.extractByte(8); this[o + 0] = v.extractByte(16) }
fun ByteArray.write32_be(o: Int, v: Int) = run { this[o + 3] = v.extractByte(0); this[o + 2] = v.extractByte(8); this[o + 1] = v.extractByte(16); this[o + 0] = v.extractByte(24) }
fun ByteArray.write32_be(o: Int, v: Long) = write32_be(o, v.toInt())
fun ByteArray.write64_be(o: Int, v: Long) = run { write32_le(o + 0, (v ushr 32).toInt()); write32_le(o + 4, (v ushr 0).toInt()) }
fun ByteArray.writeF32_be(o: Int, v: Float) = run { write32_be(o + 0, v.toRawBits()) }
fun ByteArray.writeF64_be(o: Int, v: Double) = run { write64_be(o + 0, v.toRawBits()) }

fun ByteArray.writeBytes(o: Int, bytes: ByteArray) = arraycopy(bytes, 0, this, o, bytes.size)

private inline fun _wa(o: Int, elementSize: Int, size: Int, write: (p: Int, n: Int) -> Unit) = run { for (n in 0 until size) write(o + n * elementSize, n) }
fun ByteArray.writeArray_le(o: Int, array: CharArray) = _wa(o, 2, array.size) { p, n -> write16_le(p, array[n].toInt()) }
fun ByteArray.writeArray_le(o: Int, array: ShortArray) = _wa(o, 2, array.size) { p, n -> write16_le(p, array[n].toInt()) }
fun ByteArray.writeArray_le(o: Int, array: IntArray) = _wa(o, 4, array.size) { p, n -> write32_le(p, array[n]) }
fun ByteArray.writeArray_le(o: Int, array: LongArray) = _wa(o, 8, array.size) { p, n -> write64_le(p, array[n]) }
fun ByteArray.writeArray_le(o: Int, array: FloatArray) = _wa(o, 4, array.size) { p, n -> writeF32_le(p, array[n]) }
fun ByteArray.writeArray_le(o: Int, array: DoubleArray) = _wa(o, 8, array.size) { p, n -> writeF64_le(p, array[n]) }
fun ByteArray.writeArray_be(o: Int, array: CharArray) = _wa(o, 2, array.size) { p, n -> write16_be(p, array[n].toInt()) }
fun ByteArray.writeArray_be(o: Int, array: ShortArray) = _wa(o, 2, array.size) { p, n -> write16_be(p, array[n].toInt()) }
fun ByteArray.writeArray_be(o: Int, array: IntArray) = _wa(o, 4, array.size) { p, n -> write32_be(p, array[n]) }
fun ByteArray.writeArray_be(o: Int, array: LongArray) = _wa(o, 8, array.size) { p, n -> write64_be(p, array[n]) }
fun ByteArray.writeArray_be(o: Int, array: FloatArray) = _wa(o, 4, array.size) { p, n -> writeF32_be(p, array[n]) }
fun ByteArray.writeArray_be(o: Int, array: DoubleArray) = _wa(o, 8, array.size) { p, n -> writeF64_be(p, array[n]) }
