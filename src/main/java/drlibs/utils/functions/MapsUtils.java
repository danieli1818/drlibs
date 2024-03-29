package drlibs.utils.functions;

import java.util.HashMap;
import java.util.Map;

public class MapsUtils {

	public static <K, V> Map<K, V> mapOf(K key, V value) {
		Map<K, V> map = new HashMap<>();
		map.put(key, value);
		return map;
	}

	public static <K, V> Map<K, V> mapOf(K key1, V value1, K key2, V value2) {
		Map<K, V> map = new HashMap<>();
		map.put(key1, value1);
		map.put(key2, value2);
		return map;
	}

	public static <K, V> Map<K, V> mapOf(K key1, V value1, K key2, V value2, K key3, V value3) {
		Map<K, V> map = new HashMap<>();
		map.put(key1, value1);
		map.put(key2, value2);
		map.put(key3, value3);
		return map;
	}

	public static <K, V> Map<K, V> mapOf(K key1, V value1, K key2, V value2, K key3, V value3, K key4, V value4) {
		Map<K, V> map = new HashMap<>();
		map.put(key1, value1);
		map.put(key2, value2);
		map.put(key3, value3);
		map.put(key4, value4);
		return map;
	}

	public static <K, V> Map<K, V> mapOf(K key1, V value1, K key2, V value2, K key3, V value3, K key4, V value4, K key5,
			V value5) {
		Map<K, V> map = new HashMap<>();
		map.put(key1, value1);
		map.put(key2, value2);
		map.put(key3, value3);
		map.put(key4, value4);
		map.put(key5, value5);
		return map;
	}

	public static <K, V> Map<K, V> mapOf(K key1, V value1, K key2, V value2, K key3, V value3, K key4, V value4, K key5,
			V value5, K key6, V value6) {
		Map<K, V> map = new HashMap<>();
		map.put(key1, value1);
		map.put(key2, value2);
		map.put(key3, value3);
		map.put(key4, value4);
		map.put(key5, value5);
		map.put(key6, value6);
		return map;
	}

}
