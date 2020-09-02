/**
 * 
 */
package com.app.vm.service;

/**
 * @author mani.kasi
 *
 */
public class CartBucket<V1, V2> {
	private V1 product;
	private V2 coin;

	public CartBucket(V1 product, V2 coin) {
		this.product = product;
		this.coin = coin;
	}

	public V1 getProduct() {
		return product;
	}

	public V2 getCoin() {
		return coin;
	}

}
