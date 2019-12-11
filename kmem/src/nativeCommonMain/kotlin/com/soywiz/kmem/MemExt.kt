package com.soywiz.kmem

import kotlinx.cinterop.*

//private val emptyPinnedBoolean = BooleanArray(1).pin()
private val emptyPinnedByte = ByteArray(1).pin()
private val emptyPinnedShort = ShortArray(1).pin()
private val emptyPinnedInt = IntArray(1).pin()
private val emptyPinnedLong = LongArray(1).pin()

private val emptyPinnedUByte = UByteArray(1).pin()
private val emptyPinnedUShort = UShortArray(1).pin()
private val emptyPinnedUInt = UIntArray(1).pin()
private val emptyPinnedULong = ULongArray(1).pin()

// Missing CharArray? (equivalent to UShort) and BooleanArray (equivalent to Byte)
//val Pinned<BooleanArray>.startAddressOf: CPointer<BooleanVar> get() = if (this.get().isNotEmpty()) this.addressOf(0) else emptyPinnedBoolean.addressOf(0)
//val Pinned<CharArray>.startAddressOf: CPointer<UShortVar> get() = if (this.get().isNotEmpty()) this.addressOf(0) else emptyPinnedBoolean.addressOf(0)

// @TODO: Performance penalty due to requiring calling `this.get()` to get the size of the array. Optimal solution should be provided my Kotlin/Native.

/** Returns a valid not-null address, either from the beginning of this array or from another address if the array is empty */
val Pinned<ByteArray>.startAddressOf: CPointer<ByteVar> get() = if (this.get().isNotEmpty()) this.addressOf(0) else emptyPinnedByte.addressOf(0)
/** Returns a valid not-null address, either from the beginning of this array or from another address if the array is empty */
val Pinned<ShortArray>.startAddressOf: CPointer<ShortVar> get() = if (this.get().isNotEmpty()) this.addressOf(0) else emptyPinnedShort.addressOf(0)
/** Returns a valid not-null address, either from the beginning of this array or from another address if the array is empty */
val Pinned<IntArray>.startAddressOf: CPointer<IntVar> get() = if (this.get().isNotEmpty()) this.addressOf(0) else emptyPinnedInt.addressOf(0)
/** Returns a valid not-null address, either from the beginning of this array or from another address if the array is empty */
val Pinned<LongArray>.startAddressOf: CPointer<LongVar> get() = if (this.get().isNotEmpty()) this.addressOf(0) else emptyPinnedLong.addressOf(0)
/** Returns a valid not-null address, either from the beginning of this array or from another address if the array is empty */
val Pinned<UByteArray>.startAddressOf: CPointer<UByteVar> get() = if (this.get().isNotEmpty()) this.addressOf(0) else emptyPinnedUByte.addressOf(0)
/** Returns a valid not-null address, either from the beginning of this array or from another address if the array is empty */
val Pinned<UShortArray>.startAddressOf: CPointer<UShortVar> get() = if (this.get().isNotEmpty()) this.addressOf(0) else emptyPinnedUShort.addressOf(0)
/** Returns a valid not-null address, either from the beginning of this array or from another address if the array is empty */
val Pinned<UIntArray>.startAddressOf: CPointer<UIntVar> get() = if (this.get().isNotEmpty()) this.addressOf(0) else emptyPinnedUInt.addressOf(0)
/** Returns a valid not-null address, either from the beginning of this array or from another address if the array is empty */
val Pinned<ULongArray>.startAddressOf: CPointer<ULongVar> get() = if (this.get().isNotEmpty()) this.addressOf(0) else emptyPinnedULong.addressOf(0)
