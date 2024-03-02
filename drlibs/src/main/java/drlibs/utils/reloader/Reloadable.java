package drlibs.utils.reloader;

import java.util.Collection;

import drlibs.utils.reloader.reloadparams.ReloadParams;

public interface Reloadable {
	
	/**
	 * Returns a collection of ReloadParams of the files to load,
	 * parse with the fitting parser and run the post processing consumer
	 * in order to reload the reloadable.
	 * @return A collection of ReloadParams in order to reload the reloadable.
	 */
	public Collection<ReloadParams> getReloadParams();
	
}
