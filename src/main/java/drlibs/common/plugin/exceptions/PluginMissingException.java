package drlibs.common.plugin.exceptions;

import org.apache.commons.lang.NullArgumentException;

public class PluginMissingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7258767019064494975L;
	private String pluginName;
	
	public PluginMissingException(String pluginName) {
		if (pluginName == null) {
			throw new NullArgumentException(pluginName);
		}
		this.pluginName = pluginName;
	}
	
	public String getPluginName() {
		return pluginName;
	}
	
	@Override
	public String getMessage() {
		return "Plugin: " + pluginName + " is missing!";
	}
	
}
