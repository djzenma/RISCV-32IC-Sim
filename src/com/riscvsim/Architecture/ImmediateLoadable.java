package com.riscvsim.Architecture;

public class ImmediateLoadable {
	private Integer startBit;
	private Integer StopBit;
	private String value;

	public Integer getStartBit() {
		return startBit;
	}

	public void setStartBit(Integer startBit) {
		this.startBit = startBit;
	}

	public Integer getStopBit() {
		return StopBit;
	}

	public void setStopBit(Integer stopBit) {
		this.StopBit = stopBit;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
