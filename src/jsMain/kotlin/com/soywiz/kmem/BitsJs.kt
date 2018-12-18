package com.soywiz.kmem

actual fun clz32(v: Int): Int = js("Math.clz32(v)")
