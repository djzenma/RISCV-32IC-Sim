package com.riscvsim.Architecture;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class ImmediateFormat {
	private String name;
	private ArrayList<SegmentHelper> segments;

	/**
	 * Jackson Constructor
	 *
	 * @param name
	 * @param segments
	 */
	@JsonCreator
	public ImmediateFormat(@JsonProperty(value = "name", required = true) String name,
	                       @JsonProperty(value = "segments", required = true) ArrayList<SegmentHelper> segments) {
		this.name = name;
		this.segments = segments;
	}
}
