package com.soywiz.kmem

fun Int.nextAlignedTo(align: Int) = when {
    align == 0 -> this
    (this % align) == 0 -> this
    else -> (((this / align) + 1) * align)
}

fun Int.prevAlignedTo(align: Int) = when {
    align == 0 -> this
    (this % align) == 0 -> this
    else -> nextAlignedTo(align) - align
}

fun Long.nextAlignedTo(align: Long) = when {
    align == 0L -> this
    (this % align) == 0L -> this
    else -> (((this / align) + 1) * align)
}

fun Long.prevAlignedTo(align: Long) = when {
    align == 0L -> this
    (this % align) == 0L -> this
    else -> nextAlignedTo(align) - align
}

fun Int.isAlignedTo(alignment: Int) = (this % alignment) == 0
fun Long.isAlignedTo(alignment: Long) = (this % alignment) == 0L
