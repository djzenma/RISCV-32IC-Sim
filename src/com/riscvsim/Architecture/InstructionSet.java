package com.riscvsim.Architecture;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class InstructionSet {
	private Integer memory;
	private Integer architecture;
	private ArrayList<InstructionFormat> formats;
	private ArrayList<ImmediateFormat> immediates;

	public InstructionSet() {
	}

	public Integer getMemory() {
		return memory;
	}

	public void setMemory(Integer memory) {
		this.memory = memory;
	}

	public Integer getArchitecture() {
		return architecture;
	}

	public void setArchitecture(Integer architecture) {
		this.architecture = architecture;
	}

	public ArrayList<InstructionFormat> getFormats() {
		return formats;
	}

	public void setFormats(ArrayList<InstructionFormat> formats) {
		this.formats = formats;
	}

	public ArrayList<ImmediateFormat> getImmediates() {
		return immediates;
	}

	public void setImmediates(ArrayList<ImmediateFormat> immediates) {
		this.immediates = immediates;
	}
}
