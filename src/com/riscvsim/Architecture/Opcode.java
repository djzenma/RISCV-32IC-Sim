package com.riscvsim.Architecture;

import java.util.ArrayList;

public class Opcode {
	private String value;
	private ArrayList<Instruction> instructions;

	public Opcode() {
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ArrayList<Instruction> getInstructions() {
		return instructions;
	}

	public void setInstructions(ArrayList<Instruction> instructions) {
		this.instructions = instructions;
	}
}
