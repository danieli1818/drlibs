package drlibs.utils.savers.results;

import drlibs.utils.savers.SaveResult;

public class BaseSaveResult implements SaveResult {

	private Status status;
	private String error;
	
	public BaseSaveResult(Status status, String error) {
		this.status = status;
		this.error = error;
	}
	
	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public String getError() {
		return error;
	}

}
