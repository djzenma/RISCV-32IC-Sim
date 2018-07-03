package com.riscvsim.Architecture;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SegmentHelper {
    @JsonProperty("Segment")
    private Segment segment;

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }
}
