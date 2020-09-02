/**
 * 
 */
package com.app.vm.model;

/**
 * @author mani.kasi
 *
 */
public enum Product {

	CHOCOLATE("Chocolate", 10), CANDY("Candy", 5), COLD_DRINK("Cold Drink", 15), SWEET_BREAD("Sweet Bread", 20);

	private String name;

	private int price;

	private Product(String name, int price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public long getPrice() {
		return price;
	}
}
