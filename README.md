<p align="center">
    <img alt="Korio" src="https://raw.githubusercontent.com/korlibs/korlibs-logos/master/128/kmem.png" />
</p>

<h2 align="center">Kmem</h2>

<p align="center">
    This library provides extension methods and properties useful for memory handling, and bit manipulation, as well as array and buffer similar to JS typed arrays.
</p>

<!-- BADGES -->
<p align="center">
	<a href="https://github.com/korlibs/kmem/actions"><img alt="Build Status" src="https://github.com/korlibs/kmem/workflows/CI/badge.svg" /></a>
	<a href="https://bintray.com/korlibs/korlibs/kmem"><img alt="Maven Version" src="https://img.shields.io/bintray/v/korlibs/korlibs/kmem.svg?style=flat&label=maven" /></a>
	<a href="https://discord.korge.org/"><img alt="Discord" src="https://img.shields.io/discord/728582275884908604?logo=discord" /></a>
</p>
<!-- /BADGES -->

<!-- SUPPORT -->
<h2 align="center">Support kmem</h2>
<p align="center">
If you like kmem, or want your company logo here, please consider <a href="https://github.com/sponsors/soywiz">becoming a sponsor ★</a>,<br />
in addition to ensure the continuity of the project, you will get exclusive content.
</p>
<!-- /SUPPORT -->

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
def kmemVersion = "1.9.1"

repositories {
    maven { url "https://dl.bintray.com/korlibs/korlibs" }
}

dependencies {
    // For multiplatform projects
    implementation "com.soywiz.korlibs.kmem:kmem:$kmemVersion"
    
    // For JVM/Android only
    implementation "com.soywiz.korlibs.kmem:kmem-jvm:$kmemVersion"
    // For JS only
    implementation "com.soywiz.korlibs.kmem:kmem-js:$kmemVersion"
}

// settigs.gradle
enableFeaturePreview('GRADLE_METADATA')
```
