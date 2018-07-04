package com.riscvsim.Architecture;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ImmediateLoadable {
	private Integer startBit;
	private Integer stopBit;
	private String value;
	private boolean secondary;

	/**
	 * Default Constructor
	 */
	public ImmediateLoadable() {
		this.startBit = 0;
		this.stopBit = 0;
		this.value = "NONE";
		this.secondary = false;
	}

	/**
	 * Jackson Constructor
	 *
	 * @param startBit
	 * @param stopBit
	 * @param secondary
	 * @param value
	 */
	ImmediateLoadable(@JsonProperty(value = "startBit", required = true, defaultValue = "0") Integer startBit,
	                  @JsonProperty(value = "stopBit", required = true, defaultValue = "0") Integer stopBit,
	                  @JsonProperty(value = "secondary", defaultValue = "false") boolean secondary,
	                  @JsonProperty(value = "value", required = true, defaultValue = "NONE") String value) {
		this.startBit = startBit;
		this.secondary = secondary;
		this.stopBit = stopBit;
		this.value = value;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isSecondary() {
		return secondary;
	}

	public void setSecondary(boolean secondary) {
		this.secondary = secondary;
	}
}
