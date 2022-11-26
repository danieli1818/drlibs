package drlibs.utils.messages;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.SerializationException;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class MessagesGroup implements ConfigurationSerializable {

	private String prefix;
	
	private Map<String, String> messages;
	
	public MessagesGroup(String prefix) {
		this.prefix = prefix;
	}
	
	public String getMessage(String messageID) {
		String message = messages.get(messageID);
		if (message != null) {
			message = prefix + message;
		}
		return message;
	}
	
	public String setMessage(String messageID, String message) {
		return messages.put(messageID, message);
	}
	
	public void setMessages(Map<String, String> messages) {
		this.messages = messages;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> serializationMap = new HashMap<>();
		serializationMap.put("prefix", prefix);
		serializationMap.put("messages", messages);
		return serializationMap;
	}
	
	@SuppressWarnings("unchecked")
	public static MessagesGroup deserialize(Map<String, Object> serializationMap) {
		Object prefixObj = serializationMap.get("prefix");
		if (prefixObj == null || !(prefixObj instanceof String)) {
			throw new SerializationException("Missing or invalid prefix of Messages Group!");
		}
		Object messagesObj = serializationMap.get("messages");
		if (messagesObj == null || !(messagesObj instanceof Map<?, ?>)) {
			throw new SerializationException("Missing or invalid messages of Messages Group!");
		}
		Map<?, ?> messagesMap = (Map<?, ?>)messagesObj;
		MessagesGroup messagesGroup = new MessagesGroup((String)prefixObj);
		try {
			messagesGroup.setMessages((Map<String, String>)messagesMap);
		} catch (ClassCastException exception) {
			throw new SerializationException("Missing or invalid messages of Messages Group!");
		}
		return messagesGroup;
	}
	
}
