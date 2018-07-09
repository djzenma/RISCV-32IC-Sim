package com.riscvsim.Memory;

import com.riscvsim.Fetcher;

public class Register {
	private String name;
	private int regNumber;
	private int regValue;

	public Register(String name, int regNumber, int regValue) {
		this.name = name;
		this.regNumber = regNumber;
		this.regValue = regValue;
	}

	public int getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(int regNumber) {
		this.regNumber = regNumber;
	}

	public int getRegValue() {
		return regValue;
	}

	public void setRegValue(int regValue) {
		this.regValue = regValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
