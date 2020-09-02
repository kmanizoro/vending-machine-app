package com.app.vm.service;

import java.util.List;

import com.app.vm.model.Coin;
import com.app.vm.model.Product;

/**
 * @author mani.kasi
 *
 */
public interface IVendingMachine {

	public long chooseProduct(Product product);

	public void insertCoin(Coin coin);

	public List<Coin> refund();

	public CartBucket<Product, List<Coin>> collectProducts();

	public void reset();
	
	public void refillCoins(List<Coin> coins);
	
	public void refillProducts(List<Product> products);
}
