package faq.rest;

/**
 * @author jcvidal
 *
 * Enum of HTTP statuses
 */
public enum HttpStatus {
	RET_OK(200),
	RET_DONE(201),
	RET_AUTH(401),
	RET_FATAL(500);
	
	private int status;
	
	HttpStatus(int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}
}
