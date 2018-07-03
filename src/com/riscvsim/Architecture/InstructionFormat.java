package com.riscvsim.Architecture;

import java.util.ArrayList;

public class InstructionFormat {
	private String name;
	private Integer segmentCount;
	private ArrayList<Opcode> opcodes;
	private ArrayList<Segment> segments;

	private InstructionFormat() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSegmentCount() {
		return segmentCount;
	}

	public void setSegmentCount(Integer segmentCount) {
		this.segmentCount = segmentCount;
	}

	public ArrayList<Opcode> getOpcodes() {
		return opcodes;
	}

	public void setOpcodes(ArrayList<Opcode> opcodes) {
		this.opcodes = opcodes;
	}

	public ArrayList<Segment> getSegments() {
		return segments;
	}

	public void setSegments(ArrayList<Segment> segments) {
		this.segments = segments;
	}
}
