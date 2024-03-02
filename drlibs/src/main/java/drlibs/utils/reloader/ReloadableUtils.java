package drlibs.utils.reloader;

import java.util.Set;
import java.util.stream.Collectors;

import drlibs.utils.reloader.reloadparams.ReloadParams;

public class ReloadableUtils {

	public static Set<String> getReloadableSources(Reloadable reloadable) {
		return reloadable.getReloadParams().stream().map((ReloadParams reloadParams) -> reloadParams.getSource())
				.collect(Collectors.toSet());
	}

}
