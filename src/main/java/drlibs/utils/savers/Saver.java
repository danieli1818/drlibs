package drlibs.utils.savers;

public interface Saver<T, P> {
	
	public SaveResult save(T object, P params);

}
