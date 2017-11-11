package com.soywiz.kmem

import org.junit.Test
import kotlin.test.assertEquals

class KmemTest {
	@Test
	fun testBasicUsage() {
		val data = MemBufferAlloc(16)

		val i8 = data.asInt8Buffer()
		i8[0] = 0
		i8[1] = 1
		i8[2] = 2
		i8[3] = 3

		i8[4] = 4
		i8[5] = 5
		i8[6] = 6
		i8[7] = 7

		val i32 = data.asInt32Buffer()
		assertEquals(0x03020100, i32[0])
		assertEquals(0x07060504, i32[1])

		val i32_off1 = i32.subarray(1)
		assertEquals(0x07060504, i32_off1[0])
		i32_off1[1] = 0x0B0A0908

		assertEquals(0x0B0A0908, i32[2])
	}

	@Test
	fun testArrayCopyOverlapping() {
		val i32 = Int32BufferAlloc(10)
		i32[0] = 0x01020304
		i32[1] = 0x05060708
		arraycopy(i32, 0, i32, 1, 4)
		assertEquals(0x01020304, i32[0])
		assertEquals(0x01020304, i32[1])
		assertEquals(0x05060708, i32[2])
		assertEquals(0x00000000, i32[3])
		assertEquals(0x00000000, i32[4])
	}

	@Test
	fun testFastMemory() {
		val mem = FastMemory.alloc(10)
		for (n in 0 until 8) mem[n] = n
		assertEquals(0x03020100, mem.getAlignedInt32(0))
		assertEquals(0x07060504, mem.getAlignedInt32(1))

		assertEquals(0x03020100, mem.getInt32(0))
		assertEquals(0x04030201, mem.getInt32(1))
		assertEquals(0x05040302, mem.getInt32(2))
	}

	@Test
	fun testCopy() {
		val array = arrayOf<String?>("a", "b", "c", null, null)
		arraycopy(array, 0, array, 1, 4)
		assertEquals(listOf("a", "a", "b", "c", null), array.toList())
		arraycopy(array, 2, array, 1, 3)
		assertEquals(listOf("a", "b", "c", null, null), array.toList())
	}
}