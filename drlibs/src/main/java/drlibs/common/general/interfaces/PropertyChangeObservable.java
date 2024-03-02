package drlibs.common.general.interfaces;

import java.beans.PropertyChangeListener;

public interface PropertyChangeObservable {

	public void addObserver(PropertyChangeListener observer);
	
	public void removeObserver(PropertyChangeListener observer);
	
}
