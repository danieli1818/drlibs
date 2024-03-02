package drlibs.utils.reloader.loadparsers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import drlibs.utils.reloader.LoadParser;
import drlibs.utils.reloader.results.ParseResult;
import drlibs.utils.reloader.results.ParseResult.ResultType;
import drlibs.utils.reloader.results.base.BaseParseResult;

public class FileLoadParser implements LoadParser {

	private Map<String, String> errorMessagesTransformer;
	
	public static final String FILE_DOESNT_EXIST_TRANFORM_KEY = "file_doesnt_exist";
	public static final String FILE_ISNT_A_FILE_TRANSFORM_KEY = "file_isnt_a_file";
	public static final String IO_EXCEPTION_TRANSFORM_KEY = "io_exception";
	
	public FileLoadParser() {
		errorMessagesTransformer = new HashMap<>();
		errorMessagesTransformer.put(FILE_DOESNT_EXIST_TRANFORM_KEY, "File doesn't exist!");
		errorMessagesTransformer.put(FILE_ISNT_A_FILE_TRANSFORM_KEY, "This is not a valid file!");
		errorMessagesTransformer.put(IO_EXCEPTION_TRANSFORM_KEY, "An IO Exception has occurred, check logs for more info!");
	}
	
	public FileLoadParser(Map<String, String> errorMessagesTransformer) {
		this();
		for (Map.Entry<String, String> errorMessageTransformEntry : errorMessagesTransformer.entrySet()) {
			String value = errorMessageTransformEntry.getValue();
			if (value == null) {
				value = "";
			}
			this.errorMessagesTransformer.put(errorMessageTransformEntry.getKey(), value);
		}
	}
	
	@Override
	public ParseResult parse(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			return new BaseParseResult(filePath, ResultType.LOAD_ERROR, errorMessagesTransformer.get(FILE_DOESNT_EXIST_TRANFORM_KEY));
		}
		if (!file.isFile()) {
			return new BaseParseResult(filePath, ResultType.LOAD_ERROR, errorMessagesTransformer.get(FILE_ISNT_A_FILE_TRANSFORM_KEY));
		}
		try {
			String fileData = Files.readString(Paths.get(filePath));
			return new BaseParseResult(filePath, fileData);
		} catch (IOException e) {
			e.printStackTrace();
			return new BaseParseResult(filePath, ResultType.LOAD_ERROR, errorMessagesTransformer.get(IO_EXCEPTION_TRANSFORM_KEY));
		}
	}
	
	@Override
	public boolean equals(Object otherObject) {
		return otherObject instanceof FileLoadParser;
	}
	
	@Override
	public int hashCode() {
		return FileLoadParser.class.hashCode();
	}

}
