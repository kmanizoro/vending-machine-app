/**
 * 
 */
package com.app.vm;

import com.app.vm.model.Coin;
import com.app.vm.model.Product;
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
		vMachine.collectProducts();
	}
}
