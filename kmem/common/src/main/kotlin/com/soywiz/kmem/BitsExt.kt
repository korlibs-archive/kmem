package com.soywiz.kmem

fun Int.rotateLeft(bits: Int): Int = (this shl bits) or (this ushr (32 - bits))

fun Int.rotateRight(bits: Int): Int = (this shl (32 - bits)) or (this ushr bits)

fun Int.reverseBytes(): Int {
	val v0 = ((this ushr 0) and 0xFF)
	val v1 = ((this ushr 8) and 0xFF)
	val v2 = ((this ushr 16) and 0xFF)
	val v3 = ((this ushr 24) and 0xFF)
	return (v0 shl 24) or (v1 shl 16) or (v2 shl 8) or (v3 shl 0)
}

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
	// Binary search.
	if ((v and 0xFFFF0000.toInt()) == 0) run { v = v shl 16; result += 16; }
	if ((v and 0xFF000000.toInt()) == 0) run { v = v shl 8; result += 8; }
	if ((v and 0xF0000000.toInt()) == 0) run { v = v shl 4; result += 4; }
	if ((v and 0xC0000000.toInt()) == 0) run { v = v shl 2; result += 2; }
	if ((v and 0x80000000.toInt()) == 0) run { v = v shl 1; result += 1; }
	return result
}

fun Int.countLeadingOnes(): Int = this.inv().countLeadingZeros()

fun Int.signExtend(bits: Int) = (this shl (32 - bits)) shr (32 - bits)
fun Int.signExtend8(): Int = this shl 24 shr 24
fun Int.signExtend16(): Int = this shl 16 shr 16

fun Long.signExtend(bits: Int): Long = (this shl (64 - bits)) shr (64 - bits)
fun Long.signExtend8(): Long = this shl 24 shr 24
fun Long.signExtend16(): Long = this shl 16 shr 16

fun Byte.toUnsigned() = this.toInt() and 0xFF
fun Int.toUnsigned() = this.toLong() and 0xFFFFFFFFL

fun Int.extract8(offset: Int): Int = (this ushr offset) and 0xFF
