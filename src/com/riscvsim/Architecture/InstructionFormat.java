package com.riscvsim.Architecture;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class InstructionFormat {
	private String name;
	private ArrayList<InstructionGroup> instructionGroups;
	private ArrayList<Segment> segments;

	/**
	 * Jackson Constructor
	 *
	 * @param name
	 * @param instructionGroups
	 * @param segments
	 */
	@JsonCreator
	public InstructionFormat(@JsonProperty(value = "name", required = true) String name,
	                         @JsonProperty(value = "instructionGroups", required = true) ArrayList<InstructionGroup> instructionGroups,
	                         @JsonProperty(value = "segments", required = true) ArrayList<Segment> segments) {
		this.name = name;
		this.instructionGroups = instructionGroups;
		this.segments = segments;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<InstructionGroup> getInstructionGroups() {
		return instructionGroups;
	}

	public void setInstructionGroups(ArrayList<InstructionGroup> instructionGroups) {
		this.instructionGroups = instructionGroups;
	}

	public ArrayList<Segment> getSegments() {
		return segments;
	}

	public void setSegments(ArrayList<Segment> segments) {
		this.segments = segments;
	}

	public ArrayList<Segment> getSecondaryKeys(Integer precedence) {
		ArrayList<Segment> keys = new ArrayList<>();
		for (Segment segment : segments) {
			if (segment.isSecondary() && segment.getPrecedence().equals(precedence))
				keys.add(segment);
		}
		return keys;
	}

	public Segment getSegmentByName(String name) throws Exception {
		for (Segment segment : segments) {
			if (segment.getName().equals(name))
				return segment;
		}
		throw new Exception("Segment Not Found");
	}
}
