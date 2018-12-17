package com.soywiz.kmem

fun Int.nextAlignedTo(align: Int) = if (this.isAlignedTo(align)) this else (((this / align) + 1) * align)
fun Long.nextAlignedTo(align: Long) = if (this.isAlignedTo(align)) this else (((this / align) + 1) * align)

fun Int.prevAlignedTo(align: Int) = if (this.isAlignedTo(align)) this else nextAlignedTo(align) - align
fun Long.prevAlignedTo(align: Long) = if (this.isAlignedTo(align)) this else nextAlignedTo(align) - align

fun Int.isAlignedTo(alignment: Int) = alignment == 0 || (this % alignment) == 0
fun Long.isAlignedTo(alignment: Long) = alignment == 0L || (this % alignment) == 0L
