package drlibs.utils.files;

import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JacksonUtils {
	
	public static boolean putKeyPathValue(ObjectMapper mapper, InputStream stream, String key, int value) throws IOException {
		JsonNode node = getNode(mapper, stream, key);
		if (node == null) {
			return false;
		}
		((ObjectNode)node).put(key, value);
		return true;
	}
	
	public static boolean putKeyPathValue(ObjectMapper mapper, InputStream stream, String key, String value) throws IOException {
		JsonNode node = getNode(mapper, stream, key);
		if (node == null) {
			return false;
		}
		((ObjectNode)node).put(key, value);
		return true;
	}
	
	public static boolean putKeyPathValue(ObjectMapper mapper, InputStream stream, String key, float value) throws IOException {
		JsonNode node = getNode(mapper, stream, key);
		if (node == null) {
			return false;
		}
		((ObjectNode)node).put(key, value);
		return true;
	}
	
	public static boolean putKeyPathValue(ObjectMapper mapper, InputStream stream, String key, double value) throws IOException {
		JsonNode node = getNode(mapper, stream, key);
		if (node == null) {
			return false;
		}
		((ObjectNode)node).put(key, value);
		return true;
	}
	
	public static boolean putKeyPathValue(ObjectMapper mapper, InputStream stream, String key, boolean value) throws IOException {
		JsonNode node = getNode(mapper, stream, key);
		if (node == null) {
			return false;
		}
		((ObjectNode)node).put(key, value);
		return true;
	}
	
	public static <T> boolean putKeyPathValue(ObjectMapper mapper, InputStream stream, String key, JsonNode value) throws IOException {
		JsonNode node = getNode(mapper, stream, key);
		if (node == null) {
			return false;
		}
		((ObjectNode)node).set(key, value);
		return true;
	}
	
	public static <T> boolean putKeyPathValue(ObjectMapper mapper, InputStream stream, String key, Object value) throws IOException {
		return putKeyPathValue(mapper, stream, key, mapper.convertValue(value, JsonNode.class));
	}
	
	public static boolean createPathWithValue(ObjectMapper mapper, InputStream stream, String path, String key, int value) throws IOException {
		Map.Entry<JsonNode, List<String>> closestNodeAndRestOfPathEntry = getClosestNode(mapper, stream, path);
		JsonNode node = closestNodeAndRestOfPathEntry.getKey();
		List<String> restOfPath = closestNodeAndRestOfPathEntry.getValue();
		if (restOfPath != null) {
			for (String pathKey : restOfPath) {
				((ObjectNode) node).putObject(pathKey);
			}
		}
		((ObjectNode) node).put(key, value);
		return true;
	}
	
	public static boolean createPathWithValue(ObjectMapper mapper, InputStream stream, String path, String key, String value) throws IOException {
		Map.Entry<JsonNode, List<String>> closestNodeAndRestOfPathEntry = getClosestNode(mapper, stream, path);
		JsonNode node = closestNodeAndRestOfPathEntry.getKey();
		List<String> restOfPath = closestNodeAndRestOfPathEntry.getValue();
		if (restOfPath != null) {
			for (String pathKey : restOfPath) {
				((ObjectNode) node).putObject(pathKey);
			}
		}
		((ObjectNode) node).put(key, value);
		return true;
	}
	
	public static boolean createPathWithValue(ObjectMapper mapper, InputStream stream, String path, String key, float value) throws IOException {
		Map.Entry<JsonNode, List<String>> closestNodeAndRestOfPathEntry = getClosestNode(mapper, stream, path);
		JsonNode node = closestNodeAndRestOfPathEntry.getKey();
		List<String> restOfPath = closestNodeAndRestOfPathEntry.getValue();
		if (restOfPath != null) {
			for (String pathKey : restOfPath) {
				((ObjectNode) node).putObject(pathKey);
			}
		}
		((ObjectNode) node).put(key, value);
		return true;
	}
	
	public static boolean createPathWithValue(ObjectMapper mapper, InputStream stream, String path, String key, double value) throws IOException {
		Map.Entry<JsonNode, List<String>> closestNodeAndRestOfPathEntry = getClosestNode(mapper, stream, path);
		JsonNode node = closestNodeAndRestOfPathEntry.getKey();
		List<String> restOfPath = closestNodeAndRestOfPathEntry.getValue();
		if (restOfPath != null) {
			for (String pathKey : restOfPath) {
				((ObjectNode) node).putObject(pathKey);
			}
		}
		((ObjectNode) node).put(key, value);
		return true;
	}
	
	public static boolean createPathWithValue(ObjectMapper mapper, InputStream stream, String path, String key, boolean value) throws IOException {
		Map.Entry<JsonNode, List<String>> closestNodeAndRestOfPathEntry = getClosestNode(mapper, stream, path);
		JsonNode node = closestNodeAndRestOfPathEntry.getKey();
		List<String> restOfPath = closestNodeAndRestOfPathEntry.getValue();
		if (restOfPath != null) {
			for (String pathKey : restOfPath) {
				((ObjectNode) node).putObject(pathKey);
			}
		}
		((ObjectNode) node).put(key, value);
		return true;
	}
	
	public static boolean createPathWithValue(ObjectMapper mapper, InputStream stream, String path, String key, JsonNode value) throws IOException {
		Map.Entry<JsonNode, List<String>> closestNodeAndRestOfPathEntry = getClosestNode(mapper, stream, path);
		JsonNode node = closestNodeAndRestOfPathEntry.getKey();
		List<String> restOfPath = closestNodeAndRestOfPathEntry.getValue();
		if (restOfPath != null) {
			for (String pathKey : restOfPath) {
				((ObjectNode) node).putObject(pathKey);
			}
		}
		((ObjectNode) node).set(key, value);
		return true;
	}
	
	public static <T> boolean createPathWithValue(ObjectMapper mapper, InputStream stream, String path, String key, Object value) throws IOException {
		return createPathWithValue(mapper, stream, path, key, mapper.convertValue(value, JsonNode.class));
	}
	
	public static Map.Entry<JsonNode, List<String>> getClosestNode(ObjectMapper mapper, InputStream stream, String path) throws IOException {
		if (path.startsWith(".") || path.startsWith("$")) {
			path = path.substring(1);
		}
		JsonNode node = mapper.readTree(stream);
		List<String> pathSplitted = Arrays.asList(path.split("."));
		ListIterator<String> pathIterator = pathSplitted.listIterator();
		while (pathIterator.hasNext()) {
			int index = pathIterator.nextIndex();
			String key = pathIterator.next();
			if (node.has(key)) {
				node = node.get(key);
			} else {
				return new AbstractMap.SimpleEntry<>(node, pathSplitted.subList(index, pathSplitted.size()));
			}
		}
		return new AbstractMap.SimpleEntry<>(node, null);
	}
	
	public static JsonNode getNode(ObjectMapper mapper, InputStream stream, String path) throws IOException {
		if (path.startsWith(".") || path.startsWith("$")) {
			path = path.substring(1);
		}
		JsonNode node = mapper.readTree(stream);
		if (path.isEmpty()) {
			return node;
		}
		String[] pathSplitted = path.split("."); 
		for (String key : pathSplitted) {
			node = node.get(key);
			if (node == null) {
				return null;
			}
		}
		return node;
	}
	
	public static boolean hasKey(ObjectMapper mapper, InputStream stream, String key) throws IOException {
		String[] keySplitted = key.split(".");
		if (keySplitted.length <= 1) {
			return false;
		}
		int startIndex = 0;
		if (keySplitted[0] == "" || keySplitted[0] == "$") {
			startIndex = 1;
		}
		JsonNode node = mapper.readTree(stream);
		for (int i = startIndex; i < keySplitted.length; i++) {
			node = node.get(keySplitted[i]);
			if (node == null) {
				return false;
			}
		}
		return true;
	}
	
	public static Map.Entry<String, String> splitPathAndKey(String xPath) {
		int lastDotIndex = xPath.lastIndexOf(".");
		if (lastDotIndex == xPath.length() - 1) {
			if (lastDotIndex == 0) {
				return new AbstractMap.SimpleEntry<String, String>("", "");
			} else {
				return new AbstractMap.SimpleEntry<String, String>(xPath.substring(0, lastDotIndex), "");
			}
		} else if (lastDotIndex == 0) {
			return new AbstractMap.SimpleEntry<String, String>("", xPath.substring(lastDotIndex + 1));
		}
		return new AbstractMap.SimpleEntry<String, String>(xPath.substring(0, lastDotIndex), xPath.substring(lastDotIndex + 1));
	}
	
	public static void clearAllFields(JsonNode node) {
		if (node == null) {
			return;
		}
		Iterator<String> fieldsIterator = node.fieldNames();
		while (fieldsIterator.hasNext()) {
			((ObjectNode) node).remove(fieldsIterator.next()); 
		}
	}

	public static List<String> getAllKeys(ObjectMapper mapper, InputStream stream) throws IOException {
		JsonNode jsonNode = mapper.readTree(stream);
		return getAllKeys(new ArrayList<>(), jsonNode, "");
	}
	
	private static List<String> getAllKeys(List<String> keys, JsonNode jsonNode, String xPath) {
		Iterator<Map.Entry<String, JsonNode>> fieldsIterator = jsonNode.fields();
		while (fieldsIterator.hasNext()) {
			Map.Entry<String, JsonNode> fieldEntry = fieldsIterator.next();
			String fieldXPath = xPath + "." + fieldEntry.getKey();
			keys.add(fieldXPath);
			getAllKeys(keys, fieldEntry.getValue(), fieldXPath);
		}
		return keys;
	}
	
}
