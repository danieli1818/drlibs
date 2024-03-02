package drlibs.utils.reloader.loadparsers;

import org.yaml.snakeyaml.Yaml;

import drlibs.utils.reloader.LoadParser;
import drlibs.utils.reloader.results.ParseResult;
import drlibs.utils.reloader.results.base.BaseParseResult;

public class YamlLoadParser implements LoadParser {

	@Override
	public ParseResult parse(String data) {
		Yaml yaml = new Yaml();
		Object result = yaml.load(data);
		return new BaseParseResult(data, result);
	}
	
	@Override
	public boolean equals(Object otherObject) {
		return otherObject instanceof YamlLoadParser;
	}
	
	@Override
	public int hashCode() {
		return YamlLoadParser.class.hashCode();
	}

}
