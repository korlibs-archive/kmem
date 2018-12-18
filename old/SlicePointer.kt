
class ByteArraySlice(val data: ByteArray, val position: Int, val length: Int) {
    fun getPointer(): Pointer = Pointer(data, position)
    override fun toString() = "ByteArraySlice(data=$data, position=$position, length=$length)"

    operator fun get(n: Int): Byte = data[position + n]
    operator fun set(n: Int, value: Byte): Unit = run { data[position + n] = value }

    companion object {
        fun create(start: Pointer, end: Pointer): ByteArraySlice {
            if (start.ba !== end.ba) throw RuntimeException("Pointer must reference the same array")
            return ByteArraySlice(start.ba, start.offset, end.offset - start.offset)
        }
    }
}

fun ByteArray.toByteArraySlice() = ByteArraySlice(this, 0, size)

class UByteArraySlice(val data: ByteArray, val position: Int, val length: Int) {
    fun getPointer(): Pointer = Pointer(data, position)
    override fun toString() = "UByteArraySlice(data=$data, position=$position, length=$length)"

    operator fun get(n: Int): Int = data[position + n].toInt() and 0xFF
    operator fun set(n: Int, value: Int): Unit = run { data[position + n] = value.toByte() }

    companion object {
        fun create(start: Pointer, end: Pointer): ByteArraySlice {
            if (start.ba !== end.ba) throw RuntimeException("Pointer must reference the samea array")
            return ByteArraySlice(start.ba, start.offset, end.offset - start.offset)
        }
    }
}

fun ByteArray.toUByteArraySlice() = UByteArraySlice(this, 0, size)

fun ByteArraySlice.toUnsigned() = UByteArraySlice(this.data, position, length)

class Pointer(val ba: ByteArray, var offset: Int = 0) : Comparable<Pointer> {
    fun inc() = run { offset++ }
    fun dec() = run { offset-- }

    fun getU8() = ba[offset].toInt() and 0xFF
    fun setU8(v: Int) = run { ba[offset] = v.toByte() }

    fun readU8() = ba[offset++].toInt() and 0xFF
    fun writeU8(v: Int) = run { ba[offset++] = v.toByte() }

    operator fun plus(offset: Int) = Pointer(ba, this.offset + offset)
    operator fun minus(that: Pointer) = this.offset - that.offset
    fun setAdd(that: Pointer, add: Int) {
        this.offset = that.offset + add
    }

    override fun compareTo(other: Pointer): Int = this.offset.compareTo(other.offset)
    fun take(count: Int) = ByteArraySlice(ba, offset, count)

    override fun toString(): String = "Pointer($ba, $offset)"
}

fun ByteArrayBuilder.toByteArraySlice(position: Long = 0) = ByteArraySlice(data, position.toInt(), size)
