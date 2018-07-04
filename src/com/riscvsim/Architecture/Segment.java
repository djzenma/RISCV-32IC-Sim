package com.riscvsim.Architecture;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Segment {
	private String name;
	private Integer startBit;
	private Integer stopBit;
	private boolean secondary;
	private Integer precedence;

	/**
	 * Jackson Constructor
	 *
	 * @param name
	 * @param startBit
	 * @param stopBit
	 * @param primary
	 * @param precedence
	 */
	@JsonCreator
	public Segment(@JsonProperty(value = "name", required = true) String name,
	               @JsonProperty(value = "startBit", required = true) Integer startBit,
	               @JsonProperty(value = "stopBit", required = true) Integer stopBit,
	               @JsonProperty(value = "secondary", defaultValue = "false") boolean secondary,
	               @JsonProperty(value = "precedence", defaultValue = "0") Integer precedence) {
		this.name = name;
		this.startBit = startBit;
		this.stopBit = stopBit;
		this.secondary = secondary;
		this.precedence = precedence;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStartBit() {
		return startBit;
	}

	public void setStartBit(Integer startBit) {
		this.startBit = startBit;
	}

	public Integer getStopBit() {
		return stopBit;
	}

	public void setStopBit(Integer stopBit) {
		this.stopBit = stopBit;
	}

	public boolean isSecondary() {
		return secondary;
	}

	public void setSecondary(boolean secondary) {
		this.secondary = secondary;
	}

	public Integer getPrecedence() {
		return precedence;
	}

	public void setPrecedence(Integer precedence) {
		this.precedence = precedence;
	}
}
