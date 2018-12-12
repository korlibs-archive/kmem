import java.io.File

fun main(args: Array<String>) = Generator.main(args)

data class AType(
	val prim: String,
	val bytes: Int,
	val int: Boolean,
	val primSize: String = "${if (int) "Int" else "Float"}${bytes * 8}",
	val commonName: String = "${primSize}Buffer",
	val jsName: String = "${primSize}Array",
	val jvmName: String = "${prim}Buffer",
	val karray: String = "${prim}Array"
)

data class UnType(val t: String, val array: String, val element: String)

object Generator {
	val AUTOGEN_NOTICE = "@WARNING: File AUTOGENERATED by `src/test/kotlin/Generator.kt` @ korlibs/kmem do not modify manually!"

	val BYTE = AType("Byte", bytes = 1, int = true)
	val SHORT = AType("Short", bytes = 2, int = true)
	//val CHAR = AType("Char", bytes = 2, int = true)
	val INT = AType("Int", bytes = 4, int = true)
	val FLOAT = AType("Float", bytes = 4, int = false)
	val DOUBLE = AType("Double", bytes = 8, int = false)

	val TYPES = listOf(BYTE, SHORT, INT, FLOAT, DOUBLE)

	val UNOPTIMIZED_ARRAYS = listOf(
		UnType("<T>", "Array<T>", "T"),
		UnType("", "BooleanArray", "Boolean"),
		UnType("", "LongArray", "Long")
	)

	@JvmStatic
	fun main(args: Array<String>) {
		println("Generated")
		println("Root: ${File(".").absolutePath}")
		//File("temp/KmemGen.kt").writeText(generateCommon().joinToString("\n"))
		//File("temp/KmemGenJs.kt").writeText(generateJs().joinToString("\n"))
		//File("temp/KmemGenJvm.kt").writeText(generateJvm().joinToString("\n"))

		File("kmem/common/src/main/kotlin/com/soywiz/kmem/KmemGen.kt").writeText(generateCommon().joinToString("\n"))
		File("kmem/js/src/main/kotlin/com/soywiz/kmem/KmemGenJs.kt").writeText(generateJs().joinToString("\n"))
		File("kmem/jvm/src/main/kotlin/com/soywiz/kmem/KmemGenJvm.kt").writeText(generateJvm().joinToString("\n"))
	}

	fun generateCommon(): List<String> {
		val out = arrayListOf<String>()

		fun line(str: String = "") = run { out += str }

		line("// $AUTOGEN_NOTICE")
		line("@file:Suppress(\"NOTHING_TO_INLINE\", \"EXTENSION_SHADOWED_BY_MEMBER\", \"RedundantUnitReturnType\", \"FunctionName\")")
		line("package com.soywiz.kmem")
		line()
		line("expect class MemBuffer")
		line("expect fun MemBufferAlloc(size: Int): MemBuffer")
		line("expect fun MemBufferWrap(array: ByteArray): MemBuffer")
		line("expect val MemBuffer.size: Int")
		line()
		for (type in TYPES) type.apply {
			line("expect fun MemBuffer._slice$commonName(offset: Int, size: Int): $commonName")
		}
		line()
		for (type in TYPES) type.apply {
			line("inline fun MemBuffer.slice$commonName(offset: Int = 0, size: Int = (this.size / $bytes) - offset): $commonName = this._slice$commonName(offset, size)")
		}
		line()
		for (type in TYPES) type.apply {
			line("inline fun MemBuffer.as$commonName(): $commonName = this.slice$commonName()")
		}
		line()
		line("expect class DataBuffer")
		line("expect fun MemBuffer.getData(): DataBuffer")
		for (type in TYPES) type.apply {
			line("expect fun DataBuffer.get$prim(index: Int): $prim")
			line("expect fun DataBuffer.set$prim(index: Int, value: $prim): Unit")
		}
		line()

		for (type in TYPES) type.apply {
			line("expect class $commonName")
			line("inline fun ${commonName}Alloc(size: Int): $commonName = MemBufferAlloc(size * $bytes).slice$commonName() // @TODO: Can't use class name directly (it fails in JS)")
			line("expect val $commonName.mem: MemBuffer")
			line("expect val $commonName.offset: Int")
			line("expect val $commonName.size: Int")
			line("expect operator fun $commonName.get(index: Int): $prim")
			line("expect operator fun $commonName.set(index: Int, value: $prim): Unit")
			line("fun $commonName.subarray(begin: Int, end: Int = this.size): $commonName = this.mem.slice$commonName(this.offset + begin, end - begin)")
			line()
		}

		for (type in TYPES) type.apply {
			line("fun arraycopy(src: $commonName, srcPos: Int, dst: $commonName, dstPos: Int, size: Int): Unit = arraycopy(src.mem, srcPos * $bytes, dst.mem, dstPos * $bytes, size * $bytes)")
			line("fun arraycopy(src: $karray, srcPos: Int, dst: $commonName, dstPos: Int, size: Int): Unit = arraycopy(src, srcPos, dst.mem, dstPos, size)")
			line("fun arraycopy(src: $commonName, srcPos: Int, dst: $karray, dstPos: Int, size: Int): Unit = arraycopy(src.mem, srcPos, dst, dstPos, size)")
		}
		line()

		for ((t, at, _) in UNOPTIMIZED_ARRAYS) {
			line("expect fun $t arraycopy(src: $at, srcPos: Int, dst: $at, dstPos: Int, size: Int): Unit")
		}

		for (type in TYPES) type.apply {
			line("expect fun arraycopy(src: $karray, srcPos: Int, dst: $karray, dstPos: Int, size: Int): Unit")
		}
		line()

		line("expect fun arraycopy(src: MemBuffer, srcPos: Int, dst: MemBuffer, dstPos: Int, size: Int): Unit")
		for (type in TYPES) type.apply {
			line("expect fun arraycopy(src: $karray, srcPos: Int, dst: MemBuffer, dstPos: Int, size: Int): Unit")
			line("expect fun arraycopy(src: MemBuffer, srcPos: Int, dst: $karray, dstPos: Int, size: Int): Unit")
		}
		line()

		for ((t, at, v) in UNOPTIMIZED_ARRAYS) {
			line("@PublishedApi expect internal fun $t _fill(array: $at, value: $v, start: Int, end: Int): Unit")
		}
		for (type in TYPES) type.apply {
			line("@PublishedApi expect internal fun _fill(array: $karray, value: $prim, start: Int, end: Int): Unit")
		}
		line()

		for ((t, at, v) in UNOPTIMIZED_ARRAYS) {
			line("fun $t $at.fill(value: $v, start: Int = 0, end: Int = this.size): Unit = _fill(this, value, start, end)")
		}
		for (type in TYPES) type.apply {
			line("inline fun $karray.fill(value: $prim, start: Int = 0, end: Int = this.size): Unit = _fill(this, value, start, end)")
		}
		line()

		return out
	}

	fun generateJs(): List<String> {
		val out = arrayListOf<String>()

		fun line(str: String = "") = run { out += str }

		line("// $AUTOGEN_NOTICE")
		line("@file:Suppress(\"NOTHING_TO_INLINE\", \"EXTENSION_SHADOWED_BY_MEMBER\", \"RedundantUnitReturnType\", \"FunctionName\", \"UnsafeCastFromDynamic\")")
		line("package com.soywiz.kmem")
		line()
		line("import org.khronos.webgl.*")
		line()
		line("actual typealias MemBuffer = ArrayBuffer")
		line("actual inline fun MemBufferAlloc(size: Int): MemBuffer = ArrayBuffer((size + 0xF) and 0xF.inv())")
		line("actual inline fun MemBufferWrap(array: ByteArray): MemBuffer = array.unsafeCast<Int8Array>().buffer")
		line("actual inline val MemBuffer.size: Int get() = this.byteLength")
		line()
		for (type in TYPES) type.apply {
			line("actual inline fun MemBuffer._slice$commonName(offset: Int, size: Int): $commonName = $jsName(this, offset * $bytes, size)")
		}
		line()
		line("actual typealias DataBuffer = DataView")
		line("actual fun MemBuffer.getData(): DataBuffer = DataView(this)")
		for (type in TYPES) type.apply {
			val optLE = if (type == BYTE) "" else ", true"
			line("actual fun DataBuffer.get$prim(index: Int): $prim = this.get$primSize(index$optLE)")
			line("actual fun DataBuffer.set$prim(index: Int, value: $prim): Unit = run { this.set$primSize(index, value$optLE) }")
		}
		line()

		for (type in TYPES) type.apply {
			line("actual typealias $commonName = $jsName")
			line("actual inline val $commonName.mem: MemBuffer get() = this.buffer")
			line("actual inline val $commonName.offset: Int get() = this.byteOffset / $bytes")
			line("actual inline val $commonName.size: Int get() = this.asDynamic().length")
			line("actual inline operator fun $commonName.get(index: Int): $prim = this.asDynamic()[index]")
			line("actual inline operator fun $commonName.set(index: Int, value: $prim): Unit = run { this.asDynamic()[index] = value }")
			line()
		}

		for (type in TYPES) type.apply {
			line("inline fun $karray.as$jsName(): $jsName = this.unsafeCast<$jsName>()")
			line("inline fun $karray.asTyped(): $jsName = this.unsafeCast<$jsName>()")
		}

		line()

		for ((t, at, _) in UNOPTIMIZED_ARRAYS) {
			line("actual fun $t arraycopy(src: $at, srcPos: Int, dst: $at, dstPos: Int, size: Int): Unit {")
			line("\tif ((src === dst) && (srcPos >= dstPos)) {")
			line("\t\tfor (n in 0 until size) dst[dstPos + n] = src[srcPos + n]")
			line("\t} else {")
			line("\t\tfor (n in size - 1 downTo 0) dst[dstPos + n] = src[srcPos + n]")
			line("\t}")
			line("}")
		}

		for (type in TYPES) type.apply {
			line("actual fun arraycopy(src: $karray, srcPos: Int, dst: $karray, dstPos: Int, size: Int): Unit = dst.asTyped().set(src.asTyped().subarray(srcPos, srcPos + size), dstPos)")
		}
		line()

		line("actual fun arraycopy(src: MemBuffer, srcPos: Int, dst: MemBuffer, dstPos: Int, size: Int): Unit = Int8Array(dst, dstPos).set(Int8Array(src, srcPos, size), 0)")
		for (type in TYPES) type.apply {
			line("actual fun arraycopy(src: $karray, srcPos: Int, dst: MemBuffer, dstPos: Int, size: Int): Unit = $jsName(dst).set(src.asTyped().subarray(srcPos, srcPos + size), dstPos)")
			line("actual fun arraycopy(src: MemBuffer, srcPos: Int, dst: $karray, dstPos: Int, size: Int): Unit = dst.asTyped().set(src._slice$commonName(0, src.size / $bytes).subarray(srcPos, srcPos + size), dstPos)")
		}

		line()

		for ((t, at, v) in UNOPTIMIZED_ARRAYS) {
			line("@PublishedApi actual internal fun $t _fill(array: $at, value: $v, start: Int, end: Int): Unit = run { for (n in start until end) array[n] = value }")
		}
		for (type in TYPES) type.apply {
			line("@PublishedApi actual inline internal fun _fill(array: $karray, value: $prim, start: Int, end: Int): Unit = run { array.asDynamic().fill(value, start, end) }")
		}
		line()

		return out
	}

	fun generateJvm(): List<String> {
		val out = arrayListOf<String>()
		fun line(str: String = "") = run { out += str }

		line("// $AUTOGEN_NOTICE")
		line("// @TODO: USELESS_CAST is required since it requires a cast to work, but IDE says that that cast is not necessary")
		line("@file:Suppress(\"NOTHING_TO_INLINE\", \"EXTENSION_SHADOWED_BY_MEMBER\", \"RedundantUnitReturnType\", \"FunctionName\", \"USELESS_CAST\")")
		line("package com.soywiz.kmem")
		line()
		line("import java.nio.*")
		line("import java.util.*")
		line()

		for (type in TYPES) type.apply {
			line("fun $jvmName.slice(offset: Int, size: Int): $jvmName = run { val out = this.duplicate(); (out as Buffer).position(this.position() + offset); (out as Buffer).limit(out.position() + size); return out }")
		}

		line("actual class MemBuffer(val buffer: ByteBuffer, val size: Int)")
		line("actual fun MemBufferAlloc(size: Int): MemBuffer = MemBuffer(ByteBuffer.allocate((size + 0xF) and 0xF.inv()).order(ByteOrder.nativeOrder()), size)")
		line("actual fun MemBufferWrap(array: ByteArray): MemBuffer = MemBuffer(ByteBuffer.wrap(array).order(ByteOrder.nativeOrder()), array.size)")
		line("actual inline val MemBuffer.size: Int get() = this.size")
		line()
		for (type in TYPES) type.apply {
			val asBuffer = if (type == BYTE) "" else ".as$jvmName()"
			line("actual fun MemBuffer._slice$commonName(offset: Int, size: Int): $commonName = $commonName(this, this.buffer$asBuffer.slice(offset, size))")
		}

		line()
		line("actual typealias DataBuffer = MemBuffer")
		line("actual fun MemBuffer.getData(): DataBuffer = this")
		for (type in TYPES) type.apply {
			val comp = if (type == BYTE) "" else prim
			line("actual fun DataBuffer.get$prim(index: Int): $prim = buffer.get$comp(index)")
			line("actual fun DataBuffer.set$prim(index: Int, value: $prim): Unit = run { buffer.put$comp(index, value) }")
		}
		line()

		for (type in TYPES) type.apply {
			line("actual class $commonName(val mbuffer: MemBuffer, val jbuffer: $jvmName)")
			line("actual val $commonName.mem: MemBuffer get() = mbuffer")
			line("actual val $commonName.offset: Int get() = (jbuffer as Buffer).position()")
			line("actual val $commonName.size: Int get() = (jbuffer as Buffer).limit()")
			line("actual operator fun $commonName.get(index: Int): $prim = jbuffer.get(offset + index)")
			line("actual operator fun $commonName.set(index: Int, value: $prim): Unit = run { jbuffer.put(offset + index, value) }")
			line()
		}

		for ((t, at, _) in UNOPTIMIZED_ARRAYS) {
			line("actual fun $t arraycopy(src: $at, srcPos: Int, dst: $at, dstPos: Int, size: Int): Unit = System.arraycopy(src, srcPos, dst, dstPos, size)")
		}

		for (type in TYPES) type.apply {
			line("actual fun arraycopy(src: $karray, srcPos: Int, dst: $karray, dstPos: Int, size: Int): Unit = System.arraycopy(src, srcPos, dst, dstPos, size)")
		}
		line()

		line("actual fun arraycopy(src: MemBuffer, srcPos: Int, dst: MemBuffer, dstPos: Int, size: Int): Unit = run { dst.buffer.slice(dstPos, size).put(src.buffer.slice(srcPos, size)) }")
		for (type in TYPES) type.apply {
			line("actual fun arraycopy(src: $karray, srcPos: Int, dst: MemBuffer, dstPos: Int, size: Int): Unit = run { (dst.slice$commonName(dstPos, size) as $commonName).jbuffer.put(src, srcPos, size) }")
			line("actual fun arraycopy(src: MemBuffer, srcPos: Int, dst: $karray, dstPos: Int, size: Int): Unit = run { (src.slice$commonName(srcPos, size) as $commonName).jbuffer.get(dst, dstPos, size) }")
		}
		line()

		for ((t, at, v) in UNOPTIMIZED_ARRAYS) {
			line("@PublishedApi actual internal fun $t _fill(array: $at, value: $v, start: Int, end: Int): Unit = Arrays.fill(array, start, end, value)")
		}
		for (type in TYPES) type.apply {
			line("@PublishedApi actual internal fun _fill(array: $karray, value: $prim, start: Int, end: Int): Unit = Arrays.fill(array, start, end, value)")
		}
		line()

		return out
	}
}