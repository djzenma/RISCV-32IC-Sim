package com.riscvsim.Architecture;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Instruction {
	private String name;
	private InstructionFormat format;
	private InstructionGroup instructionGroup;
	private Map<String, String> keys;
	private String immediate;
	private ImmediateLoadable immediateLoadable = new ImmediateLoadable();

	/**
	 * Jackson Constructor
	 *
	 * @param name
	 * @param funct3
	 * @param funct7
	 * @param immediate
	 */
	@JsonCreator
	public Instruction(@JsonProperty(value = "name", required = true) String name,
	                   @JsonProperty(value = "funct3", defaultValue = "NONE") String funct3,
	                   @JsonProperty(value = "funct7", defaultValue = "NONE") String funct7,
	                   @JsonProperty(value = "immediate", defaultValue = "NONE") String immediate) {
		this.name = name;
		this.immediate = immediate;
	}

	public InstructionFormat getFormat() {
		return format;
	}

	public void setFormat(InstructionFormat format) {
		this.format = format;
	}

	public InstructionGroup getInstructionGroup() {
		return instructionGroup;
	}

	public void setInstructionGroup(InstructionGroup instructionGroup) {
		this.instructionGroup = instructionGroup;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ImmediateLoadable getImmediateLoadable() {
		return immediateLoadable;
	}

	public void setImmediateLoadable(ImmediateLoadable immediateLoadable) {
		this.immediateLoadable = immediateLoadable;
	}

	public String getImmediate() {
		return immediate;
	}

	public void setImmediate(String immediate) {
		this.immediate = immediate;
	}

	public Map<String, String> getKeys() {
		return keys;
	}

	public void setKeys(Map<String, String> keys) {
		this.keys = keys;
	}
}
