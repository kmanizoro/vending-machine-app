/**
 * 
 */
package com.app.vm;

import com.app.vm.service.IVendingMachine;
import com.app.vm.service.VendingMachineImpl;

/**
 * @author mani.kasi
 *
 */
public class VendingMachineFactory {

	private VendingMachineFactory() {
	}

	public static IVendingMachine getVendingMachine() {
		return new VendingMachineImpl();
	}
}
