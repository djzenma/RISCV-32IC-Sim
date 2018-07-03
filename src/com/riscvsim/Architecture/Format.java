package com.riscvsim.Architecture;

import java.util.ArrayList;

public class Format {
	private String name;
	private Integer segmentCount;
	private ArrayList<SegmentHelper> segments;

	public Format() {
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

	public ArrayList<SegmentHelper> getSegments() {
		return segments;
	}

	public void setSegments(ArrayList<SegmentHelper> segments) {
		this.segments = segments;
	}
}
