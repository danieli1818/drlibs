package drlibs.utils.messages;

import java.util.Map;
import java.util.logging.Level;

import javax.annotation.Nonnull;

import org.apache.commons.lang.NullArgumentException;
import org.bukkit.command.CommandSender;

import drlibs.common.plugin.PluginProperties;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class MessagesSender {

	private PluginProperties pluginParameters;

	private String prefix;

	private String errorPrefix;

	private MessagesStorage messagesStorage;

	public MessagesSender(PluginProperties pluginParameters, String prefix, String errorPrefix, MessagesStorage messagesStorage) {
		if (pluginParameters == null) {
			throw new NullArgumentException("pluginParameters");
		}
		this.pluginParameters = pluginParameters;
		if (prefix == null) {
			prefix = "";
		}
		if (errorPrefix == null) {
			errorPrefix = "&4";
		}
		this.prefix = ChatColor.translateAlternateColorCodes('&', prefix);
		this.errorPrefix = ChatColor.translateAlternateColorCodes('&', errorPrefix);
		this.messagesStorage = messagesStorage;
	}

	public MessagesSender(PluginProperties pluginParameters, String prefix, String errorPrefix) {
		this(pluginParameters, prefix, errorPrefix, null);
	}

	public MessagesSender(PluginProperties pluginParameters, String prefix, MessagesStorage messagesStorage) {
		this(pluginParameters, prefix, null, messagesStorage);
	}

	public MessagesSender(PluginProperties pluginParameters, String prefix) {
		this(pluginParameters, prefix, (String) null);
	}

	public MessagesSender(PluginProperties pluginParameters, MessagesStorage messagesStorage) {
		this(pluginParameters, null, null, messagesStorage);
	}

	public MessagesSender(PluginProperties pluginParameters) {
		this(pluginParameters, (String) null);
	}

	public void sendMessage(String message, CommandSender sender) {
		if (!isMessageNullOrBlank(message)) {
			sendMessageWithoutPrefixes(this.prefix + message, sender, null, null, null);
		}
	}

	public void sendMessage(String message, CommandSender sender, Map<String, String> variablesMap,
			String variablesPrefix, String variablesSuffix) {
		if (!isMessageNullOrBlank(message)) {
			sendMessageWithoutPrefixes(this.prefix + message, sender, variablesMap, variablesPrefix, variablesSuffix);
		}
	}
	
	public void sendMessage(String message, CommandSender sender, Map<String, String> variablesMap) {
		if (!isMessageNullOrBlank(message)) {
			sendMessageWithoutPrefixes(this.prefix + message, sender, variablesMap, null, null);
		}
	}

	public boolean sendTranslatedMessage(String messageID, CommandSender sender) {
		if (messagesStorage == null) {
			pluginParameters.getPluginLogger().log(Level.SEVERE,
					"There has been a call to translate a message without translation file existing!",
					new IllegalStateException());
			return false;
		}
		String message = messagesStorage.getMessage(messageID);
		if (message == null) {
			pluginParameters.getPluginLogger().log(Level.SEVERE, "Couldn't find a message with the message ID: " + messageID,
					new IllegalArgumentException());
			return false;
		}
		sendMessageWithoutPrefixes(message, sender, null, null, null);
		return true;
	}

	public boolean sendTranslatedMessage(String messageID, CommandSender sender, Map<String, String> variablesMap,
			String variablesPrefix, String variablesSuffix) {
		if (messagesStorage == null) {
			pluginParameters.getPluginLogger().log(Level.SEVERE,
					"There has been a call to translate a message without translation file existing!",
					new IllegalStateException());
			return false;
		}
		String message = messagesStorage.getMessage(messageID);
		if (message == null) {
			pluginParameters.getPluginLogger().log(Level.SEVERE, "Couldn't find a message with the message ID: " + messageID,
					new IllegalArgumentException());
			return false;
		}
		sendMessageWithoutPrefixes(message, sender, variablesMap, variablesPrefix, variablesSuffix);
		return true;
	}
	
	public boolean sendTranslatedMessage(String messageID, CommandSender sender, Map<String, String> variablesMap) {
		return sendTranslatedMessage(messageID, sender, variablesMap, null, null);
	}

	private void sendMessageWithoutPrefixes(String message, CommandSender sender, Map<String, String> variablesMap,
			String variablesPrefix, String variablesSuffix) {
		if (!isMessageNullOrBlank(message)) {
			sender.spigot().sendMessage(
					new TextComponent(getFinalMessage(message, variablesMap, variablesPrefix, variablesSuffix)));
		}
	}

	private String getFinalMessage(@Nonnull String message, Map<String, String> variablesMap, String variablesPrefix,
			String variablesSuffix) {
		String finalMessage = message;
		if (variablesMap != null) {
			if (variablesPrefix == null || variablesSuffix == null) {
				variablesPrefix = "<";
				variablesSuffix = ">";
			}
			for (Map.Entry<String, String> entry : variablesMap.entrySet()) {
				finalMessage = finalMessage.replaceAll(variablesPrefix + entry.getKey() + variablesSuffix,
						entry.getValue());
			}
		}
		return ChatColor.translateAlternateColorCodes('&', finalMessage);
	}

	private boolean isMessageNullOrBlank(String message) {
		return message == null || message.equals("");
	}

	public void sendMessage(String[] messages, CommandSender sender) {
		if (!areMessagesNullOrBlank(messages)) {
			sender.sendMessage(this.prefix);
			TextComponent[] messagesTextComponents = new TextComponent[messages.length];
			for (int i = 0; i < messages.length; i++) {
				messagesTextComponents[i] = new TextComponent(
						TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', messages[i])));
			}
			sender.spigot().sendMessage(messagesTextComponents);
		}
	}

	private boolean areMessagesNullOrBlank(String[] messages) {
		for (String message : messages) {
			if (!isMessageNullOrBlank(message)) {
				return false;
			}
		}
		return true;
	}

	public void sendMessage(BaseComponent message, CommandSender sender) {
		TextComponent newMessage = new TextComponent(TextComponent
				.fromLegacyText(ChatColor.translateAlternateColorCodes('&', this.prefix + message.toPlainText())));
		newMessage.setClickEvent(message.getClickEvent());
		newMessage.setHoverEvent(message.getHoverEvent());
		sender.spigot().sendMessage(newMessage);
	}

	public void sendErrorMessage(String message, CommandSender sender) {
		if (!isMessageNullOrBlank(message)) {
			sendMessageWithoutPrefixes(this.errorPrefix + message, sender, null, null, null);
		}
	}

	public void sendErrorMessage(String message, CommandSender sender, Map<String, String> variablesMap,
			String variablesPrefix, String variablesSuffix) {
		if (!isMessageNullOrBlank(message)) {
			sendMessageWithoutPrefixes(this.errorPrefix + message, sender, variablesMap, variablesPrefix, variablesSuffix);
		}
	}

}
