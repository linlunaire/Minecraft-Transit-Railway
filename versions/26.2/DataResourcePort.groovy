import groovy.json.JsonOutput
import groovy.json.JsonSlurper

/** Build-time conversion only; original 1.21.1 resources are never rewritten. */
class DataResourcePort {
	static String targetPath(String path) {
		path.replaceFirst(/^(data\/[^\/]+\/)recipes\//, '$1recipe/')
			.replaceFirst(/^(data\/[^\/]+\/)loot_tables\//, '$1loot_table/')
			.replaceFirst(/^(data\/[^\/]+\/tags\/)blocks\//, '$1block/')
			.replaceFirst(/^(data\/[^\/]+\/tags\/)items\//, '$1item/')
	}

	static Map convert(String path, Map original) {
		Map result = new JsonSlurper().parseText(JsonOutput.toJson(original)) as Map
		if (path ==~ /data\/[^\/]+\/recipes\/.+\.json/) {
			if (result.type == 'minecraft:crafting_shaped') {
				result.key = result.key.collectEntries { key, value -> [(key): ingredient(value)] }
			} else if (result.type == 'minecraft:crafting_shapeless') {
				result.ingredients = result.ingredients.collect { ingredient(it) }
			} else {
				throw new IllegalArgumentException("Review unsupported recipe ${path}: ${result.type}")
			}
			if (!(result.result instanceof Map) || !(result.result.item instanceof String) || result.result.containsKey('id') || !(['item', 'count'] as Set).containsAll(result.result.keySet())) {
				throw new IllegalArgumentException("Review unsupported recipe result in ${path}")
			}
			result.result.id = itemId(result.result.remove('item'))
		} else if (path ==~ /data\/[^\/]+\/loot_tables\/.+\.json/) {
			renameLootConditions(result)
		} else if (!(path ==~ /data\/[^\/]+\/tags\/(items|blocks)\/.+\.json/)) {
			throw new IllegalArgumentException("Review unsupported data resource ${path}")
		}
		return result
	}

	private static Object ingredient(Object value) {
		if (value instanceof Map && value.size() == 1) {
			if (value.item instanceof String) return itemId(value.item)
			if (value.tag instanceof String) return '#' + value.tag
		} else if (value instanceof List && !value.isEmpty()) {
			def converted = value.collect { ingredient(it) }
			if (converted.every { it instanceof String && !it.startsWith('#') }) return converted
		}
		throw new IllegalArgumentException("Review unsupported legacy ingredient ${value}")
	}

	private static String itemId(String id) {
		// The existing chain was renamed when copper chains were added.
		return id == 'minecraft:chain' ? 'minecraft:iron_chain' : id
	}

	private static void renameLootConditions(Object value) {
		if (value instanceof Map) {
			if (value.condition == 'minecraft:alternative') value.condition = 'minecraft:any_of'
			value.values().each { renameLootConditions(it) }
		} else if (value instanceof List) {
			value.each { renameLootConditions(it) }
		}
	}

	static Map merge(String path, Map previous, Map next) {
		if (previous == null || previous == next) return next
		if (path ==~ /data\/[^\/]+\/tags\/(item|block)\/.+\.json/) {
			if (next.replace == true) return next
			if (!(['replace', 'values'] as Set).containsAll(previous.keySet()) || !(['replace', 'values'] as Set).containsAll(next.keySet())) {
				throw new IllegalArgumentException("Review unsupported tag merge ${path}")
			}
			return [replace: previous.replace ?: false, values: (previous.values + next.values).unique()]
		}
		throw new IllegalArgumentException("Conflicting resource copies for ${path}; reconcile the shared/generated source before building.")
	}
}
