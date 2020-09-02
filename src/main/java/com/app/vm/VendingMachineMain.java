/**
 * 
 */
package com.app.vm;

import java.util.List;

import com.app.vm.model.Coin;
import com.app.vm.model.Product;
import com.app.vm.service.CartBucket;
import com.app.vm.service.IVendingMachine;

/**
 * @author mani.kasi
 *
 */
public class VendingMachineMain {

	public static void main(String[] args) {
		
		IVendingMachine vMachine = VendingMachineFactory.getVendingMachine();
		vMachine.chooseProduct(Product.SWEET_BREAD);
		vMachine.insertCoin(Coin.QUARTER);
		vMachine.insertCoin(Coin.QUARTER);
		CartBucket<Product, List<Coin>> bucket = vMachine.collectProducts();
		System.out.println(bucket.getProduct().name());
		System.out.println(bucket.getCoin().stream().mapToInt(obj->obj.getValue()).sum());
	}
}
