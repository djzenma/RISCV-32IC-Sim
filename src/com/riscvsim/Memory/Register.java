package com.riscvsim.Memory;

public class Register {
    private int regNumber;
    private int regAddress;
    private int regValue;

    public Register(int regNumber) {
        this.regNumber = regNumber;
        this.regAddress = Memory.getTextSegmentAddr() + (4*regNumber);
    }

    public void setRegNumber(int regNumber) {
        this.regNumber = regNumber;
    }

    public void setRegAddress(int regAddress) {
        this.regAddress = regAddress;
    }

    public void setRegValue(int regValue) {
        this.regValue = regValue;
    }

    public int getRegNumber() {
        return regNumber;
    }

    public int getRegAddress() {
        return regAddress;
    }

    public int getRegValue() {
        return regValue;
    }

    // general purpose
    public static int getRegAddress(int registerNumber) {
        return  Memory.getTextSegmentAddr() + (4*registerNumber);
    }

    public static int getRegNumber(int registerAddress) {
        return ( registerAddress - Memory.getTextSegmentAddr() ) / 4;
    }
}
