package com.atc.codes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class AirCraft implements Comparable<AirCraft> {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotEmpty
	private String code;
	
	@NotNull
	private AirCraftTypes type;
	
	@NotNull
	private AirCraftSizes size;

	public AirCraft() {
		super();
	}

	public AirCraft(String code, AirCraftTypes type, AirCraftSizes size) {
		super();
		this.code = code;
		this.type = type;
		this.size = size;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public AirCraftTypes getType() {
		return type;
	}
	public void setType(AirCraftTypes type) {
		this.type = type;
	}
	public AirCraftSizes getSize() {
		return size;
	}
	public void setSize(AirCraftSizes size) {
		this.size = size;
	}

	@Override
	public int compareTo(AirCraft ac) {
		if (this.type.getPriorityValue() == ac.type.getPriorityValue()) {
			if (this.size.getPriorityValue() == ac.size.getPriorityValue())
				return 0;
			if (this.size.getPriorityValue() > ac.size.getPriorityValue())
				return 1;
			else
				return -1;
		}
		if (this.type.getPriorityValue() > ac.type.getPriorityValue())
			return 1;
		return -1;
	}	
}
