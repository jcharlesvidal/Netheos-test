package faq.auth;

/**
 * @author jcvidal
 *
 * Used to return error to JSON
 *
 */
public class AuthErr {
	private int errorCode;
	private String errorText;
	private boolean error;
	
	public AuthErr(int errorCode, String errorText) {
		this.errorCode = errorCode;
		this.errorText = errorText;
		error = true;
	}
	public AuthErr(AuthErrorStatus status) {
		this.errorCode = status.getStatus();
		this.errorText = status.getText();
		error = true;
	}
	public AuthErr() {
		error = false;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorText() {
		return errorText;
	}
	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	
}
