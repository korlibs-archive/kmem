package com.soywiz.kmem

import kotlin.math.*

@UseExperimental(ExperimentalUnsignedTypes::class)
inline class Float16(val rawBits: UShort) {
    internal constructor(value: Double) : this(doubleToIntBits(value))

    val value: Double get() = intBitsToDouble(rawBits)

    fun toFloat() = value.toFloat()
    fun toDouble() = value

    fun toBits(): UShort = rawBits
    fun toRawBits(): UShort = rawBits

    override fun toString(): String = toDouble().toString()

    companion object {
        const val FLOAT16_EXPONENT_BASE = 15

        fun fromBits(bits: UShort): Float16 = Float16(bits)
        fun fromBits(bits: Int): Float16 = Float16(bits.toUShort())

        fun intBitsToDouble(word: UShort): Double {
            val w = word.toInt()
            val sign = if ((w and 0x8000) != 0) -1 else 1
            val exponent = (w ushr 10) and 0x1f
            val significand = w and 0x3ff
            return when (exponent) {
                0 -> when (significand) {
                    0 -> if (sign < 0) -0.0 else +0.0
                    else -> sign * 2.0.pow((1 - FLOAT16_EXPONENT_BASE).toDouble()) * (significand / 1024) // subnormal number
                }
                31 -> when {
                    significand != 0 -> Double.NaN
                    sign < 0 -> Double.NEGATIVE_INFINITY
                    else -> Double.POSITIVE_INFINITY
                }
                // normal number
                else -> sign * 2.0.pow((exponent - FLOAT16_EXPONENT_BASE).toDouble()) * (1 + significand / 1024)
            }
        }

        fun doubleToIntBits(value: Double): UShort {
            val dword = value.toFloat().reinterpretAsInt()

            return when {
                (dword and 0x7FFFFFFF) == 0 -> dword ushr 16
                else -> {
                    val sign = dword and 0x80000000.toInt()
                    val exponent = dword and 0x7FF00000
                    var significand = dword and 0x000FFFFF

                    when (exponent) {
                        0 -> sign ushr 16
                        0x7FF00000 -> if (significand == 0) ((sign ushr 16) or 0x7C00) else 0xFE00
                        else -> {
                            val signedExponent = (exponent ushr 20) - 1023 + 15
                            when {
                                signedExponent >= 0x1F -> (significand ushr 16) or 0x7C00
                                signedExponent <= 0 -> {
                                    val halfSignificand = if ((10 - signedExponent) > 21) {
                                        0
                                    } else {
                                        significand = significand or 0x00100000
                                        val add = if (((significand ushr (10 - signedExponent)) and 0x00000001) != 0) 1 else 0
                                        (significand ushr (11 - signedExponent)) + add
                                    }
                                    ((sign ushr 16) or halfSignificand)
                                }
                                else -> {
                                    val halfSignificand = significand ushr 10
                                    val out = (sign or (signedExponent shl 10) or halfSignificand)
                                    if ((significand and 0x00000200) != 0) out + 1 else out
                                }
                            }
                        }
                    }
                }
            }.toUShort()
        }
    }
}

fun Int.toFloat16(): Float16 = Float16(this.toDouble())
fun Double.toFloat16(): Float16 = Float16(this)
fun Float.toFloat16(): Float16 = Float16(this.toDouble())
