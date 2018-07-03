package com.riscvsim.Architecture;

public class Segment {
    private String name;
    private Integer startBit;
    private Integer stopBit;

    private Segment() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStartBit() {
        return startBit;
    }

    public void setStartBit(Integer startBit) {
        this.startBit = startBit;
    }

    public Integer getStopBit() {
        return stopBit;
    }

    public void setStopBit(Integer stopBit) {
        this.stopBit = stopBit;
    }
}
