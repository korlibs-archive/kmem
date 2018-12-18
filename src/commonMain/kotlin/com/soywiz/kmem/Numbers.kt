package com.soywiz.kmem

import kotlin.math.*

inline val Byte.unsigned get() = this.toInt() and 0xFF
inline val Int.unsigned get() = this.toLong() and 0xFFFFFFFFL

inline fun Boolean.toInt() = if (this) 1 else 0

fun rint(v: Double): Double = if (v >= floor(v) + 0.5) ceil(v) else round(v)  // @TODO: Is this right?
fun signum(v: Double): Double = sign(v)

fun Float.isAlmostZero(): Boolean = abs(this) <= 1e-19
fun Float.isNanOrInfinite() = this.isNaN() || this.isInfinite()

fun Double.isAlmostZero(): Boolean = abs(this) <= 1e-19
fun Double.isNanOrInfinite() = this.isNaN() || this.isInfinite()

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

fun ilog2(v: Int): Int = if (v == 0) (-1) else (31 - v.countLeadingZeros())

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

////////////////////
////////////////////

fun Int.nextAlignedTo(align: Int) = if (this.isAlignedTo(align)) this else (((this / align) + 1) * align)
fun Long.nextAlignedTo(align: Long) = if (this.isAlignedTo(align)) this else (((this / align) + 1) * align)

fun Int.prevAlignedTo(align: Int) = if (this.isAlignedTo(align)) this else nextAlignedTo(align) - align
fun Long.prevAlignedTo(align: Long) = if (this.isAlignedTo(align)) this else nextAlignedTo(align) - align

fun Int.isAlignedTo(alignment: Int) = alignment == 0 || (this % alignment) == 0
fun Long.isAlignedTo(alignment: Long) = alignment == 0L || (this % alignment) == 0L

////////////////////
////////////////////

fun Int.clamp(min: Int, max: Int): Int = if (this < min) min else if (this > max) max else this
fun Long.clamp(min: Long, max: Long): Long = if (this < min) min else if (this > max) max else this
fun Double.clamp(min: Double, max: Double): Double = if (this < min) min else if (this > max) max else this
fun Float.clamp(min: Float, max: Float): Float = if ((this < min)) min else if ((this > max)) max else this

fun Double.clamp01() = clamp(0.0, 1.0)

fun Long.toIntClamp(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Int {
    if (this < min) return min
    if (this > max) return max
    return this.toInt()
}

fun Long.toUintClamp(min: Int = 0, max: Int = Int.MAX_VALUE) = this.toIntClamp(0, Int.MAX_VALUE)

////////////////////
////////////////////

val Int.isOdd get() = (this % 2) == 1
val Int.isEven get() = (this % 2) == 0

////////////////////
////////////////////

val Int.nextPowerOfTwo: Int get() {
    var v = this
    v--
    v = v or (v shr 1)
    v = v or (v shr 2)
    v = v or (v shr 4)
    v = v or (v shr 8)
    v = v or (v shr 16)
    v++
    return v
}

val Int.prevPowerOfTwo: Int get() = if (isPowerOfTwo) this else (nextPowerOfTwo ushr 1)

val Int.isPowerOfTwo: Boolean get() = this.nextPowerOfTwo == this
