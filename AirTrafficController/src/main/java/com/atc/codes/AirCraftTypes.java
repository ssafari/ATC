package com.atc.codes;

public enum AirCraftTypes {
	Emergency(5), 
	VIP(4), 
	Passenger(3),
	Cargo(2);
	
	private final int priority;

    AirCraftTypes(int priority) {
        this.priority = priority;
    }
    
    public int getPriorityValue() {
        return this.priority;
    }
    
    @Override
    public String toString() {
    		return name();
    }
}
