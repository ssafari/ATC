package com.atc.codes;

public enum AirCraftSizes {
	large(10),
	small(1);
	
	private final int priority;

    AirCraftSizes(int priority) {
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
