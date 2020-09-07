/**
 * 
 */
package com.app.vm.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.app.vm.exception.AmountNotEnoughException;
import com.app.vm.exception.NotSufficientChangeException;
import com.app.vm.exception.ProductSoldOutException;
import com.app.vm.model.Coin;
import com.app.vm.model.Product;

/**
 * @author mani.kasi
 *
 */
public class VendingMachineImpl implements IVendingMachine {

	private Stock<Coin> cashInventory = new Stock<>();
	private Stock<Product> itemInventory = new Stock<>();

	private long totalSales;
	private Product cartProduct;
	private long cartBalance;

	public VendingMachineImpl(List<Product> products, List<Coin> coins) {
		initialize(products, coins);
	}

	public VendingMachineImpl(List<Product> products) {
		this(products, Arrays.asList(Coin.values()));
	}

	public VendingMachineImpl() {
		this(Arrays.asList(Product.values()), Arrays.asList(Coin.values()));
	}

	private void initialize(List<Product> products, List<Coin> coins) {
		for (Coin c : coins) {
			cashInventory.put(c, 5);
		}

		for (Product i : products) {
			itemInventory.put(i, 5);
		}

	}

	@Override
	public long chooseProduct(Product item) {
		if (itemInventory.hasItem(item)) {
			cartProduct = item;
			return cartProduct.getPrice();
		}
		throw new ProductSoldOutException("Sold Out, Please buy another item");
	}

	@Override
	public void insertCoin(Coin coin) {
		cartBalance = cartBalance + coin.getValue();
		cashInventory.add(coin);
	}

	@Override
	public CartBucket<Product, List<Coin>> collectProducts() {

		Product item = collectItem();
		totalSales += cartProduct.getPrice();
		List<Coin> change = collectChange();
		resetCart();
		return new CartBucket<>(item, change);
	}

	private Product collectItem() {
		if (isFullPaid()) {
			if (hasSufficientChange()) {
				itemInventory.deduct(cartProduct);
				return cartProduct;
			}
			resetCart();
			throw new NotSufficientChangeException("Not Sufficient change in Stock");
		}
		long remainingBalance = cartProduct.getPrice() - cartBalance;
		throw new AmountNotEnoughException("Price not full paid, remaining : ", remainingBalance);
	}

	private List<Coin> collectChange() {
		long changeAmount = cartBalance - cartProduct.getPrice();
		List<Coin> change = getChange(changeAmount);
		updateCashInventory(change);
		cartBalance = 0;
		cartProduct = null;
		return change;
	}

	@Override
	public List<Coin> refund() {
		List<Coin> refund = getChange(cartBalance);
		updateCashInventory(refund);
		cartBalance = 0;
		cartProduct = null;
		return refund;
	}

	private boolean isFullPaid() {
		return (cartBalance >= cartProduct.getPrice());
	}

	private List<Coin> getChange(long amount) {
		List<Coin> changes = new ArrayList<>();
		if (amount > 0) {
			long balance = amount;
			while (balance > 0) {
				if (balance >= Coin.QUARTER.getValue() && cashInventory.hasItem(Coin.QUARTER)) {
					changes.add(Coin.QUARTER);
					balance -= Coin.QUARTER.getValue();
				} else if (balance >= Coin.DIME.getValue() && cashInventory.hasItem(Coin.DIME)) {
					changes.add(Coin.DIME);
					balance -= Coin.DIME.getValue();
				} else if (balance >= Coin.NICKLE.getValue() && cashInventory.hasItem(Coin.NICKLE)) {
					changes.add(Coin.NICKLE);
					balance -= Coin.NICKLE.getValue();
				} else if (balance >= Coin.CENT.getValue() && cashInventory.hasItem(Coin.CENT)) {
					changes.add(Coin.CENT);
					balance -= Coin.CENT.getValue();
				} else {
					return new ArrayList<>();
				}
			}
		}
		return changes;
	}

	@Override
	public void reset() {
		cashInventory.clear();
		itemInventory.clear();
		totalSales = 0;
		resetCart();
	}

	@Override
	public void refillCoins(List<Coin> coins) {
		for (Coin c : coins) {
			this.cashInventory.add(c);
		}
	}

	@Override
	public void refillProducts(List<Product> products) {
		for (Product p : products) {
			this.itemInventory.add(p);
		}
	}

	private boolean hasSufficientChange() {
		return hasSufficientChangeForAmount(cartBalance - cartProduct.getPrice());
	}

	private boolean hasSufficientChangeForAmount(long amount) {
		return amount == 0 || !getChange(amount).isEmpty();
	}

	private void updateCashInventory(List<Coin> change) {
		for (Coin c : change) {
			cashInventory.deduct(c);
		}
	}

	public long getTotalSales() {
		return totalSales;
	}

	private void resetCart() {
		cartProduct = null;
		cartBalance = 0;
	}

}
