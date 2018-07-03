package com.riscvsim.Architecture;

public class ImmediateLoadable {
	private Integer startBit;
	private Integer stopBIt;
	private String value;

	public Integer getStartBit() {
		return startBit;
	}

	public void setStartBit(Integer startBit) {
		this.startBit = startBit;
	}

	public Integer getStopBIt() {
		return stopBIt;
	}

	public void setStopBIt(Integer stopBIt) {
		this.stopBIt = stopBIt;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
