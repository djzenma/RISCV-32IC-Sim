package com.riscvsim.Architecture;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class ImmediateFormat {
	private String name;
	private ArrayList<Segment> segments;

	/**
	 * Jackson Constructor
	 *
	 * @param name
	 * @param segments
	 */
	@JsonCreator
	public ImmediateFormat(@JsonProperty(value = "name", required = true) String name,
	                       @JsonProperty(value = "segments", required = true) ArrayList<Segment> segments) {
		this.name = name;
		this.segments = segments;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Segment> getSegments() {
		return segments;
	}

	public void setSegments(ArrayList<Segment> segments) {
		this.segments = segments;
	}

}
