package com.brouwershuis.db.model;

/*
 * Working shift types
 */
public enum EnumShiftType {
	SHIFT_OPENSHIFT("Vrij dienst"),
	SHIFT_SLAAPDIENST("Slaap dienst"),
	SHIFT_SCHAKEl("Schakel"),
	SHIFT_KAFFEE("Kaffee"),
	SHIFT_KEUKEN("Keuken"),
	SHIFT_VVV("VVV");

	private final String _name;
	
	private EnumShiftType(String name){
		_name = name;
	}
	
	@Override
	public String toString(){
		return _name;
	}
}
