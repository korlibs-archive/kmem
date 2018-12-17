package com.soywiz.kmem.internal

internal inline infix fun Int.ult(that: Int) = (this xor (-0x80000000)) < (that xor (-0x80000000))

@UseExperimental(ExperimentalUnsignedTypes::class)
internal infix fun Int.urem(that: Int): Int = (this.toUInt() % that.toUInt()).toInt()

@UseExperimental(ExperimentalUnsignedTypes::class)
internal infix fun Int.udiv(that: Int): Int = (this.toUInt() / that.toUInt()).toInt()

@UseExperimental(ExperimentalUnsignedTypes::class)
internal infix fun Long.urem(that: Long): Long = (this.toULong() % that.toULong()).toLong()

@UseExperimental(ExperimentalUnsignedTypes::class)
internal infix fun Long.udiv(that: Long): Long = (this.toULong() / that.toULong()).toLong()

