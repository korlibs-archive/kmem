package com.soywiz.kmem

val Int.isOdd get() = (this % 2) == 1
val Int.isEven get() = (this % 2) == 0
