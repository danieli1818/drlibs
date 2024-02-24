package drlibs.utils.reloader.reloadables.sets;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import drlibs.utils.reloader.Reloadable;
import drlibs.utils.reloader.ReloadablesSet;

public class BaseReloadablesSet implements ReloadablesSet {

	private Set<Reloadable> reloadables;

	public BaseReloadablesSet() {
		this.reloadables = new HashSet<>();
	}

	public BaseReloadablesSet(Collection<Reloadable> reloadables) {
		this.reloadables.addAll(reloadables);
	}

	@Override
	public Collection<Reloadable> getReloadables() {
		return reloadables;
	}

	@Override
	public void onPreReload() {
	}

	@Override
	public void onPostReload() {
	}

}
