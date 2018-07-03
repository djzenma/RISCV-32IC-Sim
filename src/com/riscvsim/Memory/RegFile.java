package com.riscvsim.Memory;

import java.util.HashMap;

public class RegFile {
    private static int mRegFileSize = 32;
    private static HashMap<Register, Integer> regFile= new HashMap<>();

    public static int STACK_POINTER = 2;
    static {
        regFile.put(new Register(STACK_POINTER), Memory.getStackSegmentAddr());
    }

    public static int getRegFileSize() {
        return mRegFileSize;
    }

    public static void setRegFileSize(int regFileSize) {
        mRegFileSize = regFileSize;
    }

    public static void setInRegisterByName(int regNumber, int value) throws Exception {
        if(regNumber > mRegFileSize -1 || regNumber < 0)
            throw new Exception("Error: Register Number Out Of Range");
        else {
            if(regNumber == 0) {
                Register register = new Register(regNumber);
                regFile.put(register, 0);
            }
             else{
                Register register = new Register(regNumber);
                regFile.put(register, value);
            }
        }
    }

    public static void setInRegisterByAddress(int regAddress, int value) throws Exception {
        int regNum = Register.getRegNumber(regAddress);

        if(regNum > mRegFileSize -1 || regNum < 0)
            throw new Exception("Error: Register Number Out Of Range");
        else {
            if(regNum == 0) {
                Register register = new Register(regNum);
                regFile.put(register, 0);
            }
            else{
                Register register = new Register(regNum);
                regFile.put(register, value);
            }
        }
    }

    static int getValueFromReg(int regNum) throws Exception {
        if(regNum > mRegFileSize -1 || regNum < 0)
            throw new Exception("Error: Register Number Out Of Range");
        return regFile.get(new Register(regNum));
    }

    public static int getStackPointerValue() {
        return regFile.get(new Register(STACK_POINTER));
    }
}