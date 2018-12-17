@file:Suppress("NOTHING_TO_INLINE")

package com.soywiz.kmem

import kotlin.math.*

inline fun Float.reinterpretAsInt() = this.toBits()
inline fun Int.reinterpretAsFloat() = Float.fromBits(this)

inline fun Double.reinterpretAsLong() = this.toBits()
inline fun Long.reinterpretAsDouble() = Double.fromBits(this)

fun Int.rotateLeft(bits: Int): Int = (this shl bits) or (this ushr (32 - bits))

fun Int.rotateRight(bits: Int): Int = (this shl (32 - bits)) or (this ushr bits)

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

fun Int.countLeadingZeros(): Int {
	var v = this
	if (v == 0) return 32
	var result = 0
	if ((v and 0xFFFF0000.toInt()) == 0) run { v = v shl 16; result += 16; }
	if ((v and 0xFF000000.toInt()) == 0) run { v = v shl 8; result += 8; }
	if ((v and 0xF0000000.toInt()) == 0) run { v = v shl 4; result += 4; }
	if ((v and 0xC0000000.toInt()) == 0) run { v = v shl 2; result += 2; }
	if ((v and 0x80000000.toInt()) == 0) run { v = v shl 1; result += 1; }
	return result
}

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

fun Int.signExtend(bits: Int) = (this shl (32 - bits)) shr (32 - bits)
fun Int.signExtend8(): Int = this shl 24 shr 24
fun Int.signExtend16(): Int = this shl 16 shr 16

fun Long.signExtend(bits: Int): Long = (this shl (64 - bits)) shr (64 - bits)
fun Long.signExtend8(): Long = this shl 24 shr 24
fun Long.signExtend16(): Long = this shl 16 shr 16

inline val Byte.unsigned get() = this.toInt() and 0xFF
inline val Int.unsigned get() = this.toLong() and 0xFFFFFFFFL

inline fun Int.mask(): Int = (1 shl this) - 1
inline fun Long.mask(): Long = (1L shl this.toInt()) - 1L

fun Int.getBit(offset: Int): Boolean = ((this ushr offset) and 1) != 0
fun Int.getBits(offset: Int, count: Int): Int = (this ushr offset) and count.mask()

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
fun Int.insertScaled(value: Int, offset: Int, count: Int, scale: Int): Int = insert((value * count.mask()) / scale, offset, count)
fun Int.insertScaledFF(value: Int, offset: Int, count: Int): Int = if (count == 0) this else this.insertScaled(value, offset, count, 0xFF)

fun Int.extractBool(offset: Int) = this.extract(offset, 1) != 0

fun rint(v: Double): Double = if (v >= floor(v) + 0.5) ceil(v) else round(v)  // @TODO: Is this right?
fun signum(v: Double): Double = sign(v)

fun Float.isAlmostZero(): Boolean = abs(this) <= 1e-19
fun Float.isNanOrInfinite() = this.isNaN() || this.isInfinite()

fun Double.isAlmostZero(): Boolean = abs(this) <= 1e-19
fun Double.isNanOrInfinite() = this.isNaN() || this.isInfinite()

infix fun Int.hasFlag(bits: Int) = (this and bits) == bits

infix fun Int.divCeil(that: Int): Int = if (this % that != 0) (this / that) + 1 else (this / that)

infix fun Int.umod(other: Int): Int {
	val remainder = this % other
	return when {
		remainder < 0 -> remainder + other
		else -> remainder
	}
}

infix fun Double.umod(other: Double): Double {
	val remainder = this % other
	return when {
		remainder < 0 -> remainder + other
		else -> remainder
	}
}

// @TODO: Use bit counting instead
fun ilog2(v: Int): Int = log2(v.toDouble()).toInt()


fun Double.toIntCeil() = ceil(this).toInt()
fun Double.toIntFloor() = floor(this).toInt()
fun Double.toIntRound() = round(this).toLong().toInt()

fun Double.convertRange(srcMin: Double, srcMax: Double, dstMin: Double, dstMax: Double): Double = (dstMin + (dstMax - dstMin) * ((this - srcMin) / (srcMax - srcMin)))
fun Double.convertRangeClamped(srcMin: Double, srcMax: Double, dstMin: Double, dstMax: Double): Double = convertRange(srcMin, srcMax, dstMin, dstMax).clamp(dstMin, dstMax)
fun Long.convertRange(srcMin: Long, srcMax: Long, dstMin: Long, dstMax: Long): Long = (dstMin + (dstMax - dstMin) * ((this - srcMin).toDouble() / (srcMax - srcMin).toDouble())).toLong()

fun Long.toIntSafe(): Int {
	if (this.toInt().toLong() != this) throw IllegalArgumentException("Long doesn't fit Integer")
	return this.toInt()
}

infix fun Byte.and(mask: Long): Long = this.toLong() and mask

infix fun Byte.and(mask: Int): Int = this.toInt() and mask
infix fun Short.and(mask: Int): Int = this.toInt() and mask

infix fun Byte.or(mask: Int): Int = this.toInt() or mask
infix fun Short.or(mask: Int): Int = this.toInt() or mask
infix fun Short.or(mask: Short): Int = this.toInt() or mask.toInt()

infix fun Byte.shl(that: Int): Int = this.toInt() shl that
infix fun Short.shl(that: Int): Int = this.toInt() shl that

infix fun Byte.shr(that: Int): Int = this.toInt() shr that
infix fun Short.shr(that: Int): Int = this.toInt() shr that

infix fun Byte.ushr(that: Int): Int = this.toInt() ushr that
infix fun Short.ushr(that: Int): Int = this.toInt() ushr that

inline fun Boolean.toInt() = if (this) 1 else 0
