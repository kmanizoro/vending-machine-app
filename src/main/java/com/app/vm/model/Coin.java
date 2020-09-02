/**
 * 
 */
package com.app.vm.model;

/**
 * @author mani.kasi
 *
 */
public enum Coin {

	 CENT(1), NICKLE(5), DIME(10), QUARTER(25);
	   
    private int value;
   
    private Coin(int value){
        this.value = value;
    }
   
    public int getValue(){
        return value;
    }
}
