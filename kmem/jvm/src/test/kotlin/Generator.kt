import java.io.File

data class Type(
	val commonName: String,
	val jsName: String,
	val jvmName: String
)

val INT8 = Type("Int8Buffer", "Int8Array", "ByteBuffer")
val INT16 = Type("Int16Buffer", "Int16Array", "ShortBuffer")
val INT32 = Type("Int32Buffer", "Int32Array", "IntBuffer")
val FLOAT32 = Type("Float32Buffer", "Float32Array", "FloatBuffer")
val FLOAT64 = Type("Float64Buffer", "Float64Array", "DoubleBuffer")

fun main(args: Array<String>) {
	println(File(".").absolutePath)
}