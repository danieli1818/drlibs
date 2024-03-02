package drlibs.utils.parsers;

import drlibs.exceptions.parse.ParseException;

public interface Parser<T, S> {

	public T parse(S object) throws ParseException;
	
}
