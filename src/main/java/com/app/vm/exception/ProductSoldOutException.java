/**
 * 
 */
package com.app.vm.exception;

/**
 * @author mani.kasi
 *
 */
public class ProductSoldOutException extends RuntimeException {
	private static final long serialVersionUID = 3044276881347399083L;
	private final String message;

	public ProductSoldOutException(String string) {
		this.message = string;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
