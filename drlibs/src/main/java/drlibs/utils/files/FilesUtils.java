package drlibs.utils.files;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import com.google.common.collect.Sets;

public class FilesUtils {

	public static Set<String> getFilesRecursively(File rootFolder, String filesRegex) throws IOException {
		if (!rootFolder.isDirectory()) {
			return null;
		}
		Path rootFolderPath = Paths.get(rootFolder.getAbsolutePath());
		final Pattern pattern = Pattern.compile(filesRegex);
		return Files
				.find(rootFolderPath, Integer.MAX_VALUE,
						(Path path, BasicFileAttributes attributes) -> pattern.matcher(path.toString()).matches())
				.map((Path path) -> path.toString()).collect(Collectors.toSet());
	}
	
	public static Set<String> getAllFilesRecursively(File rootFolder) throws IOException {
		if (!rootFolder.isDirectory()) {
			return null;
		}
		Path rootFolderPath = Paths.get(rootFolder.getAbsolutePath());
		return Files
				.find(rootFolderPath, Integer.MAX_VALUE,
						(Path path, BasicFileAttributes attributes) -> attributes.isRegularFile())
				.map((Path path) -> path.toString()).collect(Collectors.toSet());
	}
	
	public static Set<File> getAllFilesInFolder(File rootFolder) throws IOException {
		if (!rootFolder.isDirectory()) {
			return null;
		}
		return Sets.newHashSet(rootFolder.listFiles((File file) -> file.isFile()));
	}
	
	public static Set<File> getAllFilesInFolder(File rootFolder, String filesRegex) throws IOException {
		if (!rootFolder.isDirectory()) {
			return null;
		}
		return Sets.newHashSet(rootFolder.listFiles((FileFilter) new RegexFileFilter(filesRegex)));
	}
	
	public static Set<File> getAllFilesInFolder(File rootFolder, String... extentions) throws IOException {
		if (!rootFolder.isDirectory()) {
			return null;
		}
		return Sets.newHashSet(rootFolder.listFiles((FileFilter) new SuffixFileFilter(extentions)));
	}

}
