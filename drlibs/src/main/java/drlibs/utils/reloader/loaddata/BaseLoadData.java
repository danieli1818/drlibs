package drlibs.utils.reloader.loaddata;

import java.util.Objects;

import drlibs.utils.reloader.LoadData;
import drlibs.utils.reloader.LoadParser;

public class BaseLoadData implements LoadData {
	
	private String source;
	private LoadParser parser;

	public BaseLoadData(String source, LoadParser parser) {
		this.source = source;
		this.parser = parser;
	}
	
	@Override
	public String getSource() {
		return source;
	}

	@Override
	public LoadParser getParser() {
		return parser;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof LoadData)) {
			return false;
		}
		LoadData otherLoadData = (LoadData)obj;
		return getSource().equals(otherLoadData.getSource()) && getParser().equals(otherLoadData.getParser());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getSource(), getParser());
	}

}
