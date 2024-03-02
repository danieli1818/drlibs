package drlibs.utils.reloader;

import java.util.Collection;

public interface ReloadablesSet {
	
	public Collection<Reloadable> getReloadables();
	
	public void onPreReload();
	
	public void onPostReload();

}
