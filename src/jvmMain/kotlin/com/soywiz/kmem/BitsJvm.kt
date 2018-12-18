package com.soywiz.kmem

actual fun clz32(v: Int): Int = java.lang.Integer.numberOfLeadingZeros(v)
