package mtr.data

import org.msgpack.value.Value
import java.util.function.Consumer

@JvmSuppressWildcards
open class MessagePackHelper(private val map: Map<String?, Value?>?) {
	open fun getBoolean(key: String?): Boolean = getBoolean(key, false)

	open fun getBoolean(key: String?, defaultValue: Boolean): Boolean =
		if (map!!.containsKey(key)) map[key]!!.asBooleanValue().boolean else defaultValue

	open fun getInt(key: String?): Int = getInt(key, 0)

	open fun getInt(key: String?, defaultValue: Int): Int =
		if (map!!.containsKey(key)) map[key]!!.asIntegerValue().asInt() else defaultValue

	open fun getLong(key: String?): Long = getLong(key, 0)

	open fun getLong(key: String?, defaultValue: Long): Long =
		if (map!!.containsKey(key)) map[key]!!.asIntegerValue().asLong() else defaultValue

	open fun getFloat(key: String?): Float = getFloat(key, 0F)

	open fun getFloat(key: String?, defaultValue: Float): Float =
		if (map!!.containsKey(key)) map[key]!!.asFloatValue().toFloat() else defaultValue

	open fun getDouble(key: String?): Double = getDouble(key, 0.0)

	open fun getDouble(key: String?, defaultValue: Double): Double =
		if (map!!.containsKey(key)) map[key]!!.asFloatValue().toDouble() else defaultValue

	open fun getString(key: String?): String? = getString(key, "")

	open fun getString(key: String?, defaultValue: String?): String? =
		if (map!!.containsKey(key)) map[key]!!.asStringValue().asString() else defaultValue

	open fun iterateArrayValue(key: String?, consumer: Consumer<Value>?) {
		if (map!!.containsKey(key)) {
			map[key]!!.asArrayValue().forEach(consumer)
		}
	}

	open fun iterateMapValue(key: String?, consumer: Consumer<Map.Entry<Value, Value>>?) {
		if (map!!.containsKey(key)) {
			map[key]!!.asMapValue().entrySet().forEach(consumer)
		}
	}
}
