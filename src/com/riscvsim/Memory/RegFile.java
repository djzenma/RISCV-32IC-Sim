package com.riscvsim.Memory;

import java.util.ArrayList;
import java.util.HashMap;

public class RegFile {
    private static int mRegFileSize = 32;
    private static HashMap<Register, Integer> regFile= new HashMap<>();
    private static ArrayList<Register> registers = new ArrayList<>();

    public static int STACK_POINTER = 2;
    public static int RETURN_ADRESS= 1;

    static {
        for (int i = 0; i < mRegFileSize; i++) {
            Register reg = new Register(i);
            registers.add(i, reg);
            regFile.put(reg, 0);
        }
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
                if (regFile.containsKey(registers.get(regNumber)))
                    regFile.replace(registers.get(regNumber), value);
                else
                    regFile.put(registers.get(regNumber), value);
            }
        }
    }


    public static int getValueFromReg(int regNum) throws Exception {
        if(regNum > mRegFileSize -1 || regNum < 0)
            throw new Exception("Error: Register Number Out Of Range");
        return regFile.get(registers.get(regNum));
    }

    public static long getUnsignedValueFromReg(int regNum) throws Exception {
        if(regNum > mRegFileSize -1 || regNum < 0)
            throw new Exception("Error: Register Number Out Of Range");
        return Integer.toUnsignedLong(regFile.get(registers.get(regNum)));
    }

    public static int getStackPointerValue() {
        return regFile.get(new Register(STACK_POINTER));
    }
}