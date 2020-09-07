/**
 * 
 */
package com.app.vm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.app.vm.exception.AmountNotEnoughException;
import com.app.vm.exception.NotSufficientChangeException;
import com.app.vm.exception.ProductSoldOutException;
import com.app.vm.model.Coin;
import com.app.vm.model.Product;
import com.app.vm.service.CartBucket;
import com.app.vm.service.IVendingMachine;

/**
 * @author mani.kasi
 *
 */
public class VendingMachineTest {

	private static IVendingMachine vendingMachine;

	@BeforeClass
	public static void setUp() {
		vendingMachine = VendingMachineFactory.getVendingMachine();
	}

	@AfterClass
	public static void tearDown() {
		vendingMachine = null;
	}

	@Test
	public void buyWithNoBalance_Test() {
		long price = vendingMachine.chooseProduct(Product.CHOCOLATE);
		assertEquals(Product.CHOCOLATE.getPrice(), price);
		vendingMachine.insertCoin(Coin.DIME);

		CartBucket<Product, List<Coin>> bucket = vendingMachine.collectProducts();
		Product product = bucket.getProduct();
		List<Coin> change = bucket.getCoin();

		assertEquals(Product.CHOCOLATE, product);
		assertTrue(change.isEmpty());
	}

	@Test
	public void buyWithBalance_Test() {
		refillCoins();
		long price = vendingMachine.chooseProduct(Product.COLD_DRINK);
		assertEquals(Product.COLD_DRINK.getPrice(), price);

		vendingMachine.insertCoin(Coin.QUARTER);

		CartBucket<Product, List<Coin>> bucket = vendingMachine.collectProducts();
		Product item = bucket.getProduct();
		List<Coin> change = bucket.getCoin();

		assertEquals(Product.COLD_DRINK, item);
		assertTrue(!change.isEmpty());
		assertEquals(25 - Product.COLD_DRINK.getPrice(), getTotal(change));
	}

	@Test
	public void testRefund() {
		long price = vendingMachine.chooseProduct(Product.COLD_DRINK);
		assertEquals(Product.COLD_DRINK.getPrice(), price);
		vendingMachine.insertCoin(Coin.DIME);
		vendingMachine.insertCoin(Coin.NICKLE);
		vendingMachine.insertCoin(Coin.CENT);
		vendingMachine.insertCoin(Coin.QUARTER);

		assertEquals(41, getTotal(vendingMachine.refund()));
	}

	@Test(expected = ProductSoldOutException.class)
	public void testSoldOut() {
		for (int i = 0; i < 5; i++) {
			vendingMachine.chooseProduct(Product.CHOCOLATE);
			vendingMachine.insertCoin(Coin.QUARTER);
			vendingMachine.collectProducts();
		}

	}

	@Test(expected = NotSufficientChangeException.class)
	public void testNotSufficientChangeException() {
		for (int i = 0; i < 5; i++) {
			vendingMachine.chooseProduct(Product.CHOCOLATE);
			vendingMachine.insertCoin(Coin.QUARTER);
			vendingMachine.insertCoin(Coin.QUARTER);
			vendingMachine.collectProducts();

			vendingMachine.chooseProduct(Product.COLD_DRINK);
			vendingMachine.insertCoin(Coin.QUARTER);
			vendingMachine.insertCoin(Coin.QUARTER);
			vendingMachine.collectProducts();
		}
	}

	@Test(expected = ProductSoldOutException.class)
	public void testReset() {
		IVendingMachine vmachine = VendingMachineFactory.getVendingMachine();
		vmachine.reset();
		vmachine.chooseProduct(Product.CHOCOLATE);
	}
	
	@Test(expected = AmountNotEnoughException.class)
	public void testAmountNotEnough() {
		long price = vendingMachine.chooseProduct(Product.SWEET_BREAD);
		assertEquals(Product.SWEET_BREAD.getPrice(), price);
		vendingMachine.insertCoin(Coin.DIME);
		vendingMachine.collectProducts();
	}

	private long getTotal(List<Coin> change) {
		long total = 0;
		for (Coin c : change) {
			total = total + c.getValue();
		}
		return total;
	}

	private void refillCoins() {
		List<Coin> coins = Arrays.asList(Coin.DIME, Coin.CENT, Coin.NICKLE, Coin.DIME, Coin.CENT, Coin.NICKLE);
		vendingMachine.refillCoins(coins);
	}
}
