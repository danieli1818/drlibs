package drlibs.utils.reloader.reloadables.sets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import drlibs.utils.reloader.Reloadable;
import drlibs.utils.reloader.ReloadableUtils;

public class ResourcesReloadablesSet extends BaseReloadablesSet {

	private Path pluginDirPath;
	private Function<Path, Path> getResourcePath;
	
	public ResourcesReloadablesSet(Path pluginDirPath, Function<Path, Path> getResourcePath) {
		super();
		this.pluginDirPath = pluginDirPath;
		this.getResourcePath = getResourcePath;
	}
	
	@Override
	public void onPreReload() {
		for (String filePath : getReloadablesFilePaths()) {
			Path path = Paths.get(filePath);
			Path relativePath = null;
			if (path.isAbsolute()) {
				if (!path.startsWith(pluginDirPath)) {
					continue; // Not a sub file of plugin dir path
				}
				relativePath = pluginDirPath.relativize(path);
			} else {
				relativePath = path;
				path = pluginDirPath.resolve(path);
			}
			if (Files.exists(path)) {
				continue; // Skip existing files
			}
			Path resourcePath = getResourcePath.apply(relativePath);
			if (!Files.exists(resourcePath)) {
				continue;
			}
			try {
				Files.copy(resourcePath, path, StandardCopyOption.REPLACE_EXISTING);
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
