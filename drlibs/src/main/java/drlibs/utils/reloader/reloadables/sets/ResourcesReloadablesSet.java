package drlibs.utils.reloader.reloadables.sets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import drlibs.utils.reloader.Reloadable;
import drlibs.utils.reloader.ReloadableUtils;

public class ResourcesReloadablesSet extends BaseReloadablesSet {

	private File pluginDirPath;
	private Function<String, File> getResourceFile;
	
	public ResourcesReloadablesSet(File pluginDirPath, Function<String, File> getResourceFile) {
		super();
		this.pluginDirPath = pluginDirPath;
		this.getResourceFile = getResourceFile;
	}
	
	@Override
	public void onPreReload() {
		for (String filePath : getReloadablesFilePaths()) {
			File file = new File(filePath);
			if (file.exists()) {
				continue;
			}
			String relativeFilePath = pluginDirPath.toURI().relativize(file.toURI()).getPath();
			if (filePath != pluginDirPath.getAbsolutePath().concat(relativeFilePath)) {
				continue;
			}
			File resourceFile = getResourceFile.apply(relativeFilePath);
			if (!resourceFile.exists()) {
				continue;
			}
			try {
				Files.copy(resourceFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected Set<String> getReloadablesFilePaths() {
		return getReloadables().stream()
				.flatMap((Reloadable reloadable) -> ReloadableUtils.getReloadableSources(reloadable).stream())
				.collect(Collectors.toSet());
	}
	
}
