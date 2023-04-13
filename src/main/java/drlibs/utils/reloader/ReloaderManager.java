package drlibs.utils.reloader;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import drlibs.common.plugin.FilesPlugin;

public class ReloaderManager {

	private FilesPlugin plugin;
	private Map<String, Set<Reloadable>> reloadablesMap;
	
	public ReloaderManager(FilesPlugin plugin) {
		this.plugin = plugin;
		this.reloadablesMap = new HashMap<>();
	}
	
	public boolean registerReloadable(Reloadable reloadable) {
		if (reloadable == null) {
			return false;
		}
		addReloadableToSet(null, reloadable);
		return true;
	}
	
	public boolean unRegisterReloadable(Reloadable reloadable) {
		if (reloadable == null) {
			return false;
		}
		removeReloadableToSet(null, reloadable);
		return true;
	}
	
	public boolean registerReloadable(String setID, Reloadable reloadable) {
		if (reloadable == null) {
			return false;
		}
		addReloadableToSet(setID, reloadable);
		return true;
	}
	
	public boolean unRegisterReloadable(String setID, Reloadable reloadable) {
		if (reloadable == null) {
			return false;
		}
		removeReloadableToSet(setID, reloadable);
		return true;
	}
	
	public boolean registerReloadables(Collection<Reloadable> reloadables) {
		if (reloadables == null) {
			return false;
		}
		for (Reloadable reloadable : reloadables) {
			if (reloadable == null) {
				continue;
			}
			addReloadableToSet(null, reloadable);
		}
		return true;
	}
	
	public boolean unRegisterReloadables(Collection<Reloadable> reloadables) {
		if (reloadables == null) {
			return false;
		}
		for (Reloadable reloadable : reloadables) {
			if (reloadable == null) {
				continue;
			}
			removeReloadableToSet(null, reloadable);
		}
		return true;
	}
	
	public boolean registerReloadables(String setID, Collection<Reloadable> reloadables) {
		if (reloadables == null) {
			return false;
		}
		for (Reloadable reloadable : reloadables) {
			if (reloadable == null) {
				continue;
			}
			addReloadableToSet(setID, reloadable);
		}
		return true;
	}
	
	public boolean unRegisterReloadables(String setID, Collection<Reloadable> reloadables) {
		if (reloadables == null) {
			return false;
		}
		for (Reloadable reloadable : reloadables) {
			if (reloadable == null) {
				continue;
			}
			removeReloadableToSet(setID, reloadable);
		}
		return true;
	}
	
	private boolean addReloadableToSet(String setID, Reloadable reloadable) {
		if (!reloadablesMap.containsKey(setID)) {
			reloadablesMap.put(setID, new HashSet<>());
		}
		return reloadablesMap.get(setID).add(reloadable);
	}
	
	private boolean removeReloadableToSet(String setID, Reloadable reloadable) {
		if (!reloadablesMap.containsKey(setID)) {
			return false;
		}
		boolean returnFlag = reloadablesMap.get(setID).remove(reloadable);
		if (reloadablesMap.isEmpty()) {
			reloadablesMap.remove(setID);
		}
		return returnFlag;
	}
	
	public boolean reloadDefaultSet() {
		return reloadSet(null);
	}
	
	public boolean reloadSetWithID(String setID) {
		return reloadSet(setID);
	}
	
	public void reloadAllSet() {
		for (String setID : reloadablesMap.keySet()) {
			reloadSet(setID);
		}
	}
	
	private boolean reloadSet(String setID) {
		Set<Reloadable> reloadables = reloadablesMap.get(setID);
		if (reloadables == null) {
			return false;
		}
		Set<String> filenames = new HashSet<>();
		Set<String> resourceFilenames = new HashSet<>();
		Set<String> resourceReplaceFilenames = new HashSet<>();
		for (Reloadable reloadable : reloadables) {
			Collection<ReloadFileData> reloadFileDataCollection = reloadable.getReloadFilenames();
			if (reloadFileDataCollection == null) {
				continue;
			}
			for (ReloadFileData reloadFileData : reloadFileDataCollection) {
				if (reloadFileData.shouldLoadResource()) {
					if (reloadFileData.shouldReplaceFile()) {
						resourceReplaceFilenames.add(reloadFileData.getFilePath());
					} else {
						resourceFilenames.add(reloadFileData.getFilePath());
					}
				} else {
					filenames.add(reloadFileData.getFilePath());
				}
			}
		}
		reloadFiles(filenames);
		reloadResourceFiles(resourceFilenames);
		reloadResourceReplaceFiles(resourceReplaceFilenames);
		for (Reloadable reloadable : reloadables) {
			reloadable.reload();
		}
		return true;
	}
	
	private void reloadFiles(Set<String> filenames) {
		plugin.getFileConfigurationsUtils().loadFileConfigurations(filenames);
	}
	
	private void reloadResourceFiles(Set<String> filenames) {
		plugin.getFileConfigurationsUtils().loadFileConfigurations(filenames, true);
	}
	
	private void reloadResourceReplaceFiles(Set<String> filenames) {
		plugin.getFileConfigurationsUtils().loadFileConfigurations(filenames, true, true);
	}
	
}
