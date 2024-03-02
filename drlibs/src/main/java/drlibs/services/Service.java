package drlibs.services;

public interface Service {

	/**
	 * Registers the service
	 * @return True if the service registered successfully or is already registered else False
	 */
	public boolean registerService();
	
	/**
	 * Returns whether the service has been registered or not
	 * @return True if the service has been registered else False
	 */
	public boolean isRegistered();
	
}
