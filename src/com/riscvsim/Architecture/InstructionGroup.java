package com.riscvsim.Architecture;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class InstructionGroup {
	private String key;
	private String value;
	private ArrayList<Instruction> instructions;

	/**
	 * Jackson Constructor
	 *
	 * @param key
	 * @param value
	 * @param instructions
	 */
	@JsonCreator
	public InstructionGroup(@JsonProperty(value = "key", required = true) String key,
	                        @JsonProperty(value = "value", required = true) String value,
	                        @JsonProperty(value = "instructions", required = true) ArrayList<Instruction> instructions) {
		this.key = key;
		this.value = value;
		this.instructions = instructions;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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
