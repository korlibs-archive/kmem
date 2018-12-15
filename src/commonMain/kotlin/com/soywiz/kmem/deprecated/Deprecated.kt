package com.soywiz.kmem

// Unsigned comparisons

private const val LONG_MIN_VALUE: Long = 0x7fffffffffffffffL.inv()
private const val LONG_MAX_VALUE: Long = 0x7fffffffffffffffL

@Deprecated("Deprecated. Use Kotlin unsigned types.")
fun Long.Companion.compare(x: Long, y: Long): Int = if (x < y) -1 else if (x == y) 0 else 1

@Deprecated("Deprecated. Use Kotlin unsigned types.")
fun Long.Companion.compareUnsigned(x: Long, y: Long): Int = compare(x xor LONG_MIN_VALUE, y xor LONG_MIN_VALUE)

@Deprecated("Deprecated. Use Kotlin unsigned types.")
fun Long.Companion.divideUnsigned(dividend: Long, divisor: Long): Long {
    if (divisor < 0) return (if (compareUnsigned(dividend, divisor) < 0) 0 else 1).toLong()
    if (dividend >= 0) return dividend / divisor
    val quotient = dividend.ushr(1) / divisor shl 1
    val rem = dividend - quotient * divisor
    return quotient + if (compareUnsigned(rem, divisor) >= 0) 1 else 0
}

@Deprecated("Deprecated. Use Kotlin unsigned types.")
fun Long.Companion.remainderUnsigned(dividend: Long, divisor: Long): Long {
    if (divisor < 0) return if (compareUnsigned(dividend, divisor) < 0) dividend else dividend - divisor
    if (dividend >= 0) return dividend % divisor
    val quotient = dividend.ushr(1) / divisor shl 1
    val rem = dividend - quotient * divisor
    return rem - if (compareUnsigned(rem, divisor) >= 0) divisor else 0
}

private const val INT_MIN_VALUE = -0x80000000
private const val INT_MAX_VALUE = 0x7fffffff

@Deprecated("Deprecated. Use Kotlin unsigned types.")
fun Int.Companion.compare(l: Int, r: Int): Int = if (l < r) -1 else if (l > r) 1 else 0

@Deprecated("Deprecated. Use Kotlin unsigned types.")
fun Int.Companion.compareUnsigned(l: Int, r: Int): Int = compare(l xor INT_MIN_VALUE, r xor INT_MIN_VALUE)

@Deprecated("Deprecated. Use Kotlin unsigned types.")
fun Int.Companion.divideUnsigned(dividend: Int, divisor: Int): Int {
    if (divisor < 0) return if (compareUnsigned(dividend, divisor) < 0) 0 else 1
    if (dividend >= 0) return dividend / divisor
    val quotient = dividend.ushr(1) / divisor shl 1
    val rem = dividend - quotient * divisor
    return quotient + if (compareUnsigned(rem, divisor) >= 0) 1 else 0
}

@Deprecated("Deprecated. Use Kotlin unsigned types.")
fun Int.Companion.remainderUnsigned(dividend: Int, divisor: Int): Int {
    if (divisor < 0) return if (compareUnsigned(dividend, divisor) < 0) dividend else dividend - divisor
    if (dividend >= 0) return dividend % divisor
    val quotient = dividend.ushr(1) / divisor shl 1
    val rem = dividend - quotient * divisor
    return rem - if (compareUnsigned(rem, divisor) >= 0) divisor else 0
}

@Deprecated("Deprecated. Use Kotlin unsigned types.")
infix fun Int.udiv(that: Int) = Int.divideUnsigned(this, that)

@Deprecated("Deprecated. Use Kotlin unsigned types.")
infix fun Int.urem(that: Int) = Int.remainderUnsigned(this, that)

@Deprecated("Deprecated. Use Kotlin unsigned types.")
infix fun Long.udiv(that: Long) = Long.divideUnsigned(this, that)

@Deprecated("Deprecated. Use Kotlin unsigned types.")
infix fun Long.urem(that: Long) = Long.remainderUnsigned(this, that)

@Deprecated("Deprecated. Use Kotlin unsigned types.")
inline infix fun Int.ult(that: Int) = (this xor (-0x80000000)) < (that xor (-0x80000000))

@Deprecated("Deprecated. Use Kotlin unsigned types.")
infix fun Int.ule(that: Int) = Int.compareUnsigned(this, that) <= 0

@Deprecated("Deprecated. Use Kotlin unsigned types.")
infix fun Int.ugt(that: Int) = Int.compareUnsigned(this, that) > 0

@Deprecated("Deprecated. Use Kotlin unsigned types.")
infix fun Int.uge(that: Int) = Int.compareUnsigned(this, that) >= 0

@Deprecated("Deprecated. Use Kotlin unsigned types.")
fun Int.compareToUnsigned(that: Int) = Int.compareUnsigned(this, that)
