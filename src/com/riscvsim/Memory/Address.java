package com.riscvsim.Memory;

public class Address {

    private int address;

    public Address() {

    }

    private int incrementWord(int address) {
        return address + 4;
    }

    private int incrementHalfWord(int address) {
        return address + 2;
    }
    private int incrementByte(int address) {
        return address + 1;
    }


    public void setAddress(int address) {
        this.address = address;
    }

    public int getAddress() {
        return address;
    }
}