package com.riscvsim.Architecture;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Instruction {
	private String name;
	private String opcode;
	@JsonIgnore
	private String funct3;
	@JsonIgnore
	private String funct7;
	private String format;
	@JsonIgnore
	private String immediate;
	@JsonIgnore
	private ImmediateLoadable immediateLoadable;

	public Instruction() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpcode() {
		return opcode;
	}

	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}

	public String getFunct3() {
		return funct3;
	}

	public void setFunct3(String funct3) {
		this.funct3 = funct3;
	}

	public String getFunct7() {
		return funct7;
	}

	public void setFunct7(String funct7) {
		this.funct7 = funct7;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
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
}
