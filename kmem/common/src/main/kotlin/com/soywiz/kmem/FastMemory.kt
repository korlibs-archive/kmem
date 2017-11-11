package com.soywiz.kmem

/*
class FastMemory(buffer: MemBuffer, val size: Int) {
	val buffer: MemBuffer = buffer
	val i8 = buffer.asInt8Buffer()
	val i16 = buffer.asInt16Buffer()
	val i32 = buffer.asInt32Buffer()
	val f32 = buffer.asFloat32Buffer()

	companion actual object {
		actual fun alloc(size: Int): FastMemory = FastMemory(MemBufferAlloc((size + 0xF) and 0xF.inv()), size)

		actual fun copy(src: FastMemory, srcPos: Int, dst: FastMemory, dstPos: Int, length: Int): Unit {
			val srcBuffer = src.buffer.duplicate()
			srcBuffer.position(srcPos)
			srcBuffer.limit(srcPos + length)
			dst.buffer.position(dstPos)
			dst.buffer.put(srcBuffer)
		}

		actual fun copy(src: FastMemory, srcPos: Int, dst: ByteArray, dstPos: Int, length: Int): Unit {
			src.buffer.position(srcPos)
			src.buffer.get(dst, dstPos, length)
		}

		actual fun copy(src: ByteArray, srcPos: Int, dst: FastMemory, dstPos: Int, length: Int): Unit {
			dst.buffer.position(dstPos)
			dst.buffer.put(src, srcPos, length)
		}

		actual fun copyAligned(src: FastMemory, srcPosAligned: Int, dst: ShortArray, dstPosAligned: Int, length: Int): Unit {
			src.i16.position(srcPosAligned)
			src.i16.get(dst, dstPosAligned, length)
		}

		actual fun copyAligned(src: ShortArray, srcPosAligned: Int, dst: FastMemory, dstPosAligned: Int, length: Int): Unit {
			dst.i16.position(dstPosAligned)
			dst.i16.put(src, srcPosAligned, length)
		}

		actual fun copyAligned(src: FastMemory, srcPosAligned: Int, dst: IntArray, dstPosAligned: Int, length: Int): Unit {
			src.i32.position(srcPosAligned)
			src.i32.get(dst, dstPosAligned, length)
		}

		actual fun copyAligned(src: IntArray, srcPosAligned: Int, dst: FastMemory, dstPosAligned: Int, length: Int): Unit {
			dst.i32.position(dstPosAligned)
			dst.i32.put(src, srcPosAligned, length)
		}

		actual fun copyAligned(src: FastMemory, srcPosAligned: Int, dst: FloatArray, dstPosAligned: Int, length: Int): Unit {
			src.f32.position(srcPosAligned)
			src.f32.get(dst, dstPosAligned, length)
		}

		actual fun copyAligned(src: FloatArray, srcPosAligned: Int, dst: FastMemory, dstPosAligned: Int, length: Int): Unit {
			dst.f32.position(dstPosAligned)
			dst.f32.put(src, srcPosAligned, length)
		}
	}

	actual operator fun get(index: Int): Int = i8[index].toInt() and 0xFF
	actual operator fun set(index: Int, value: Int): Unit = run { i8[index] = value.toByte() }

	actual fun setAlignedInt16(index: Int, value: Short): Unit = run { i16[index] = value }
	actual fun getAlignedInt16(index: Int): Short = i16[index]
	actual fun setAlignedInt32(index: Int, value: Int): Unit = run { i32[index] = value }
	actual fun getAlignedInt32(index: Int): Int = i32[index]
	actual fun setAlignedFloat32(index: Int, value: Float): Unit = run { f32[index] = value }
	actual fun getAlignedFloat32(index: Int): Float = f32[index]

	actual fun getInt16(index: Int): Short = _buffer.getShort(index)
	actual fun setInt16(index: Int, value: Short): Unit = run { _buffer.putShort(index, value) }
	actual fun setInt32(index: Int, value: Int): Unit = run { _buffer.putInt(index, value) }
	actual fun getInt32(index: Int): Int = _buffer.getInt(index)
	actual fun setFloat32(index: Int, value: Float): Unit = run { _buffer.putFloat(index, value) }
	actual fun getFloat32(index: Int): Float = _buffer.getFloat(index)

	actual fun setArrayInt8(dstPos: Int, src: ByteArray, srcPos: Int, len: Int) = copy(src, srcPos, this, dstPos, len)
	actual fun setAlignedArrayInt8(dstPos: Int, src: ByteArray, srcPos: Int, len: Int) = copy(src, srcPos, this, dstPos, len)
	actual fun setAlignedArrayInt16(dstPos: Int, src: ShortArray, srcPos: Int, len: Int) = copyAligned(src, srcPos, this, dstPos, len)
	actual fun setAlignedArrayInt32(dstPos: Int, src: IntArray, srcPos: Int, len: Int) = copyAligned(src, srcPos, this, dstPos, len)
	actual fun setAlignedArrayFloat32(dstPos: Int, src: FloatArray, srcPos: Int, len: Int) = copyAligned(src, srcPos, this, dstPos, len)

	actual fun getArrayInt8(srcPos: Int, dst: ByteArray, dstPos: Int, len: Int) = copy(this, srcPos, dst, dstPos, len)
	actual fun getAlignedArrayInt8(srcPos: Int, dst: ByteArray, dstPos: Int, len: Int) = copy(this, srcPos, dst, dstPos, len)
	actual fun getAlignedArrayInt16(srcPos: Int, dst: ShortArray, dstPos: Int, len: Int) = copyAligned(this, srcPos, dst, dstPos, len)
	actual fun getAlignedArrayInt32(srcPos: Int, dst: IntArray, dstPos: Int, len: Int) = copyAligned(this, srcPos, dst, dstPos, len)
	actual fun getAlignedArrayFloat32(srcPos: Int, dst: FloatArray, dstPos: Int, len: Int) = copyAligned(this, srcPos, dst, dstPos, len)
}
*/
