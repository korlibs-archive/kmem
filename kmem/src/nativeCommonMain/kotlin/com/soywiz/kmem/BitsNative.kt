package com.soywiz.kmem

// @TODO: Change to intrinsics when available.
actual fun clz32(v: Int): Int {
    var vv = v
    if (vv == 0) return 32
    var result = 0
    if ((vv and 0xFFFF0000.toInt()) == 0) { vv = vv shl 16; result += 16; }
    if ((vv and 0xFF000000.toInt()) == 0) { vv = vv shl 8; result += 8; }
    if ((vv and 0xF0000000.toInt()) == 0) { vv = vv shl 4; result += 4; }
    if ((vv and 0xC0000000.toInt()) == 0) { vv = vv shl 2; result += 2; }
    if ((vv and 0x80000000.toInt()) == 0) { vv = vv shl 1; result += 1; }
    return result
}
