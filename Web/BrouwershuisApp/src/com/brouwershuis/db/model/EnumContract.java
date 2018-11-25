package com.brouwershuis.db.model;

/*
 * Employee Contracts
 */
public enum EnumContract {
	CONTRACT_CLIENT("Client"),
	CONTRACT_MEDEWERKER("Medewerker"),
	CONTRACT_VRIJWILLIGER("Vrijwilliger");
	
	private final String name;
	
	private EnumContract(String s) {
        name = s;
    }
	
	public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false 
        return name.equals(otherName);
    }
	
	public String toString() {
	       return this.name;
	    }
}
