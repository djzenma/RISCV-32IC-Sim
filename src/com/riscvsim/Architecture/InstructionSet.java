package com.riscvsim.Architecture;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class InstructionSet {
	private Integer memory;
	private Integer architecture;
	private ArrayList<InstructionFormat> formats;
	//TODO: ADD IMMEDIATE FORMATS TO YAML CONFIG FILE
	private ArrayList<ImmediateFormat> immediateFormats;

	/**
	 * Jackson Constructor
	 *
	 * @param memory
	 * @param architecture
	 * @param formats
	 * @param immediateFormats
	 */
	@JsonCreator
	public InstructionSet(@JsonProperty(value = "memory", required = true) Integer memory,
	                      @JsonProperty(value = "architecture", required = true) Integer architecture,
	                      @JsonProperty(value = "formats", required = true) ArrayList<InstructionFormat> formats,
	                      @JsonProperty(value = "immediates") ArrayList<ImmediateFormat> immediateFormats) {
		this.memory = memory;
		this.architecture = architecture;
		this.formats = formats;
		this.immediateFormats = immediateFormats;
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

	public ArrayList<ImmediateFormat> getImmediateFormats() {
		return immediateFormats;
	}

	public void setImmediateFormats(ArrayList<ImmediateFormat> immediateFormats) {
		this.immediateFormats = immediateFormats;
	}
}
