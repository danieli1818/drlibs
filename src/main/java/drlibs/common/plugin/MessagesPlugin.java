package drlibs.common.plugin;

import drlibs.utils.messages.MessagesSender;

public interface MessagesPlugin extends FilesPlugin {

	public MessagesSender getMessagesSender();
	
}
