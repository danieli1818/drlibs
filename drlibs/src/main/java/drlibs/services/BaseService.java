package drlibs.services;

public abstract class BaseService implements Service {

	private boolean isRegistered;
	
	public BaseService() {
		isRegistered = false;
	}
	
	@Override
	public boolean isRegistered() {
		return isRegistered;
	}
	
	@Override
	public boolean registerService() {
		if (isRegistered) {
			return true;
		}
		return false;
	}
	
	protected void setIsRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}
	
}
