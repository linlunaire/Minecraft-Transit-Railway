package mtr.data

interface EnumHelper {
	companion object {
		@JvmStatic
		fun <T : Enum<T>> valueOf(defaultValue: T?, name: String?): T? = try {
			java.lang.Enum.valueOf(defaultValue!!.declaringJavaClass, name)
		} catch (_: Exception) {
			defaultValue
		}
	}
}
