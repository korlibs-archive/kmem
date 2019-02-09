@file:Suppress("NOTHING_TO_INLINE")

package com.soywiz.kmem

expect fun clz32(v: Int): Int

inline fun Float.reinterpretAsInt() = this.toBits()
inline fun Int.reinterpretAsFloat() = Float.fromBits(this)

inline fun Double.reinterpretAsLong() = this.toBits()
inline fun Long.reinterpretAsDouble() = Double.fromBits(this)

@UseExperimental(ExperimentalUnsignedTypes::class)
fun UInt.rotateLeft(bits: Int): UInt = (this shl bits) or (this shr (32 - bits))

@UseExperimental(ExperimentalUnsignedTypes::class)
fun UInt.rotateRight(bits: Int): UInt = (this shl (32 - bits)) or (this shr bits)

fun Int.rotateLeft(bits: Int): Int = (this shl bits) or (this ushr (32 - bits))
fun Int.rotateRight(bits: Int): Int = (this shl (32 - bits)) or (this ushr bits)

fun Long.rotateLeft(bits: Int): Long = (this shl bits) or (this ushr (64 - bits))
fun Long.rotateRight(bits: Int): Long = (this shl (64 - bits)) or (this ushr bits)

fun Short.reverseBytes(): Short {
    val low = ((this.toInt() ushr 0) and 0xFF)
    val high = ((this.toInt() ushr 8) and 0xFF)
    return ((high and 0xFF) or (low shl 8)).toShort()
}

fun Int.reverseBytes(): Int {
    val v0 = ((this ushr 0) and 0xFF)
    val v1 = ((this ushr 8) and 0xFF)
    val v2 = ((this ushr 16) and 0xFF)
    val v3 = ((this ushr 24) and 0xFF)
    return (v0 shl 24) or (v1 shl 16) or (v2 shl 8) or (v3 shl 0)
}

fun Long.reverseBytes(): Long {
    val v0 = (this ushr 0).toInt().reverseBytes().toLong() and 0xFFFFFFFFL
    val v1 = (this ushr 32).toInt().reverseBytes().toLong() and 0xFFFFFFFFL
    return (v0 shl 32) or (v1 shl 0)
}

fun Char.reverseBytes(): Char = this.toShort().reverseBytes().toChar()

fun Int.reverseBits(): Int {
    var v = this
    v = ((v ushr 1) and 0x55555555) or ((v and 0x55555555) shl 1) // swap odd and even bits
    v = ((v ushr 2) and 0x33333333) or ((v and 0x33333333) shl 2) // swap consecutive pairs
    v = ((v ushr 4) and 0x0F0F0F0F) or ((v and 0x0F0F0F0F) shl 4) // swap nibbles ...
    v = ((v ushr 8) and 0x00FF00FF) or ((v and 0x00FF00FF) shl 8) // swap bytes
    v = ((v ushr 16) and 0x0000FFFF) or ((v and 0x0000FFFF) shl 16) // swap 2-byte long pairs
    return v
}

inline fun Int.countLeadingZeros(): Int = clz32(this)

fun Int.countTrailingZeros(): Int {
    if (this == 0) return 32
    var n = this
    var c = 32
    n = n and (-n)
    if (n != 0) c--
    if ((n and 0x0000FFFF) != 0) c -= 16
    if ((n and 0x00FF00FF) != 0) c -= 8
    if ((n and 0x0F0F0F0F) != 0) c -= 4
    if ((n and 0x33333333) != 0) c -= 2
    if ((n and 0x55555555) != 0) c -= 1
    return c
}

fun Int.countLeadingOnes(): Int = this.inv().countLeadingZeros()
fun Int.countTrailingOnes(): Int = this.inv().countTrailingZeros()

fun Int.signExtend(bits: Int): Int = (this shl (32 - bits)) shr (32 - bits) // Int.SIZE_BITS
fun Long.signExtend(bits: Int): Long = (this shl (64 - bits)) shr (64 - bits) // Long.SIZE_BITS

fun Int.mask(): Int = (1 shl this) - 1
fun Long.mask(): Long = (1L shl this.toInt()) - 1L

//fun Int.getBit(offset: Int): Boolean = ((this ushr offset) and 1) != 0
//fun Int.getBits(offset: Int, count: Int): Int = (this ushr offset) and count.mask()

fun Int.extract(offset: Int, count: Int): Int = (this ushr offset) and count.mask()
fun Int.extract(offset: Int): Boolean = ((this ushr offset) and 1) != 0
fun Int.extract8(offset: Int): Int = (this ushr offset) and 0xFF
fun Int.extract16(offset: Int): Int = (this ushr offset) and 0xFFFF

fun Int.extractSigned(offset: Int, count: Int): Int = ((this ushr offset) and count.mask()).signExtend(count)
fun Int.extract8Signed(offset: Int): Int = (this ushr offset).toByte().toInt()
fun Int.extract16Signed(offset: Int): Int = (this ushr offset).toShort().toInt()

fun Int.extractByte(offset: Int): Byte = (this ushr offset).toByte()
fun Int.extractShort(offset: Int): Short = (this ushr offset).toShort()

fun Int.extractScaled(offset: Int, count: Int, scale: Int): Int = (extract(offset, count) * scale) / count.mask()
fun Int.extractScaledf01(offset: Int, count: Int): Double = extract(offset, count).toDouble() / count.mask().toDouble()

fun Int.extractScaledFF(offset: Int, count: Int): Int = extractScaled(offset, count, 0xFF)
fun Int.extractScaledFFDefault(offset: Int, count: Int, default: Int): Int =
    if (count == 0) default else extractScaled(offset, count, 0xFF)

fun Int.insert(value: Int, offset: Int, count: Int): Int {
    val mask = count.mask()
    val clearValue = this and (mask shl offset).inv()
    return clearValue or ((value and mask) shl offset)
}

fun Int.insert8(value: Int, offset: Int): Int = insert(value, offset, 8)
fun Int.insert(value: Boolean, offset: Int): Int = this.insert(if (value) 1 else 0, offset, 1)
fun Int.insertScaled(value: Int, offset: Int, count: Int, scale: Int): Int =
    insert((value * count.mask()) / scale, offset, count)

fun Int.insertScaledFF(value: Int, offset: Int, count: Int): Int =
    if (count == 0) this else this.insertScaled(value, offset, count, 0xFF)

fun Int.extractBool(offset: Int) = this.extract(offset)

//infix fun Int.hasFlag(bits: Int) = (this and bits) == bits
infix fun Int.hasFlags(bits: Int) = (this and bits) == bits

/** Creates an integer with only bit [bit] set */
fun bit(bit: Int) = 1 shl bit

/** Returns the integer [this] without the [bits] set */
fun Int.unsetBits(bits: Int) = this and bits.inv()

/** Returns the integer [this] with the [bits] set */
fun Int.setBits(bits: Int) = this or bits
