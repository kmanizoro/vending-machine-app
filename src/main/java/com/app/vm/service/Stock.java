/**
 * 
 */
package com.app.vm.service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mani.kasi
 *
 */
public class Stock<T> {

	private Map<T, Integer> stocks = new HashMap<>();

	public int getQuantity(T item) {
		Integer value = stocks.get(item);
		return value == null ? 0 : value;
	}

	public void add(T item) {
		stocks.put(item, stocks.getOrDefault(item, 0) + 1);
	}

	public void deduct(T item) {
		if (hasItem(item)) {
			int count = stocks.get(item);
			stocks.put(item, count - 1);
		}
	}

	public boolean hasItem(T item) {
		return getQuantity(item) > 0;
	}

	public void clear() {
		stocks.clear();
	}

	public void put(T item, int quantity) {
		stocks.put(item, quantity);
	}

}
