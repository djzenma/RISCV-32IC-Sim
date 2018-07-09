package com.riscvsim.Memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegFile {
	private static int maxRegisterSize = 32;
	private static ArrayList<Register> registers = new ArrayList<>();

	static {
		for (int i = 0; i < maxRegisterSize; i++) {
			Register reg = new Register("x" + i, i, 0);
			registers.add(reg);
		}
	}

	public static void setRegister(int regNumber, int value) throws Exception {
		if (regNumber > maxRegisterSize - 1 || regNumber < 0)
			throw new Exception("Error: Register Number Out Of Range");
		else {
			if (regNumber == 0) {
				registers.get(0).setRegValue(0);
			} else {
				registers.get(regNumber).setRegValue(value);
			}
		}
	}

	public static int getValueFromReg(int regNum) throws Exception {
		if (regNum > maxRegisterSize - 1 || regNum < 0)
			throw new Exception("Error: Register Number Out Of Range");
		return registers.get(regNum).getRegValue();
	}

	public static long getUnsignedValueFromReg(int regNum) throws Exception {
		if (regNum > maxRegisterSize - 1 || regNum < 0)
			throw new Exception("Error: Register Number Out Of Range");
		return Integer.toUnsignedLong(registers.get(regNum).getRegValue());
	}

	public static void dumpRegisters(){
		for(Register r: registers)
			System.out.println("Register: " + r.getName() + " Number: " + r.getRegNumber() + " Value: " + r.getRegValue());
	}
}