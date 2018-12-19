# ![](https://raw.githubusercontent.com/korlibs/korlibs-logos/master/256/kmem.png)

This library provides extension methods and properties useful for memory handling, and bit manipulation, as well as array and buffer similar to JS typed arrays.

[![Build Status](https://travis-ci.org/korlibs/kmem.svg?branch=master)](https://travis-ci.org/korlibs/kmem)
[![Maven Version](https://img.shields.io/github/tag/korlibs/kmem.svg?style=flat&label=maven)](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22kmem%22)
[![Gitter](https://img.shields.io/gitter/room/korlibs/korlibs.svg)](https://gitter.im/korlibs/Lobby)

### Full Documentation: <https://korlibs.soywiz.com/kmem/>

### Some samples

```kotlin
// Array copying
val array = arrayOf("a", "b", "c", null, null)
arraycopy(array, 0, array, 1, 4)

// Array filling
val array = intArrayOf(1, 1, 1, 1, 1)
array.fill(2)
assertEquals(intArrayOf(2, 2, 2, 2, 2).toList(), array.toList())

// CLZ32
assertEquals(1, (0b11111111111111111111111111111110).toInt().countTrailingZeros())

// Byte Array building
val byteArray = buildByteArray {
    append(1)
    append(2)
    append(byteArrayOf(3, 4, 5))
    s32LE(6)
}

// Byte Array reading
val byteArray = buildByteArray { f32BE(1f).f32LE(2f) }
byteArray.read {
    assertEquals(1f, f32BE())
    assertEquals(2f, f32LE())
}

// Float16
assertEquals(+1.0, Float16.fromBits(0x3c00).toDouble())

// Power of Two
assertEquals(16, 10.nextPowerOfTwo)
assertEquals(16, 17.prevPowerOfTwo)
assertEquals(true, 1024.isPowerOfTwo)

// Aligned (multiple of)
assertEquals(false, 77.isAlignedTo(10))
assertEquals(70, 77.prevAlignedTo(10))
assertEquals(80, 77.nextAlignedTo(10))

// ByteArray indexed typed reading
val v = byteArray.readS32LE(10)

// Buffers
val mem = FBuffer.alloc(10)
for (n in 0 until 8) mem[n] = n
assertEquals(0x03020100, mem.getAlignedInt32(0))
assertEquals(0x07060504, mem.getAlignedInt32(1))
```

### Usage with gradle

```
def kmemVersion = "1.0.0"

repositories {
    maven { url "https://dl.bintray.com/soywiz/soywiz" }
}

dependencies {
    // For multiplatform projects
    implementation "com.soywiz:kmem:$kmemVersion"
    
    // For JVM/Android only
    implementation "com.soywiz:kmem-jvm:$kmemVersion"
    // For JS only
    implementation "com.soywiz:kmem-js:$kmemVersion"
}

// settigs.gradle
enableFeaturePreview('GRADLE_METADATA')
```
