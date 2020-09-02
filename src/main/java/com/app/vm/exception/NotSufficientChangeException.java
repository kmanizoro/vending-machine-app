/**
 * 
 */
package com.app.vm.exception;

/**
 * @author mani.kasi
 *
 */
public class NotSufficientChangeException extends RuntimeException {

	private static final long serialVersionUID = -6280102347397227303L;

	private final String message;

	public NotSufficientChangeException(String string) {
		this.message = string;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
