/**
 * 
 */
package com.app.vm.exception;

/**
 * @author mani.kasi
 *
 */
public class AmountNotEnoughException extends RuntimeException {
	
	private static final long serialVersionUID = 3846418905783926738L;
	private final String message;
	private final long remaining;

	public AmountNotEnoughException(String message, long remaining) {
		this.message = message;
		this.remaining = remaining;
	}

	public long getRemaining() {
		return remaining;
	}

	@Override
	public String getMessage() {
		return message + remaining;
	}

}
