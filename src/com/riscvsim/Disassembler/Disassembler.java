package com.riscvsim.Disassembler;

import com.riscvsim.Architecture.Instruction;
import com.riscvsim.Architecture.InstructionFormat;
import com.riscvsim.Architecture.InstructionGroup;
import com.riscvsim.Architecture.Segment;
import com.riscvsim.Main;

import java.util.ArrayList;

public class Disassembler {
	public static final String R_TYPE = "R-type";
	public static final String I_TYPE = "I-type";
	public static final String S_TYPE = "S-type";
	public static final String U_TYPE = "U-type";
	public static final String SB_TYPE = "SB-type";
	public static final String UJ_TYPE = "UJ-type";

	public Disassembler() {
	}

	public Instruction getInstruction(String instructionValue) throws Exception {
		for (InstructionFormat format : Main.isa.getFormats()) {
			for (InstructionGroup instructionGroup : format.getInstructionGroups()) {
				if (instructionGroup.getValue().equals(instructionValue.substring(
						instructionValue.length() - format.getSegmentByName(instructionGroup.getKey()).getStopBit() - 1,
						instructionValue.length() - format.getSegmentByName(instructionGroup.getKey()).getStartBit()))) {
					Integer precedence = 1;
					ArrayList<Segment> secondaryKeys = format.getSecondaryKeys(precedence);
					nextInstruction:
					for (Instruction instruction : instructionGroup.getInstructions()) {
						while (!secondaryKeys.isEmpty()) {
							for (Segment key : secondaryKeys) {
								if (!instruction.getKeys().get(key.getName()).equals(instructionValue.substring(
										instructionValue.length() - key.getStopBit() - 1,
										instructionValue.length() - key.getStartBit())))
									continue nextInstruction;
							}
							secondaryKeys = format.getSecondaryKeys(++precedence);
						}
						instruction.setFormat(format);
						instruction.setInstructionGroup(instructionGroup);
						return instruction;
					}
				}
			}
		}
		throw new Exception("Error: Instruction Not Found!");
	}

	public String getRs1(String instruction) {
		return instruction.substring(31 - 19, 31 - 14);
	}

	public String getRs2(String instruction) {
		return instruction.substring(7, 12);
	}

	public String getRd(String instruction) {
		return instruction.substring(31 - 11, 31 - 6);
	}

	public String getImm12(String instruction) {
		return instruction.substring(0, 12);
	}

	public String getImm20(String instruction) {
		return instruction.substring(0, 20);
	}

	public String getShamt(String instruction) {
		return instruction.substring(8, 13);
	}

	public String getFullInstructionName(String instruction) throws Exception {
		Instruction inst = getInstruction(instruction);
		StringBuilder builder = new StringBuilder(inst.getName());
		switch (inst.getFormat().getName()) {
			case R_TYPE:
				builder.append(" " + getRd(instruction) + ", " + getRs1(instruction) + ", " + getRs2(instruction));
				return builder.toString();
			case I_TYPE:
				if (inst.getName().equals("srai") || inst.getName().equals("srli") || inst.getName().equals("slli"))
					builder.append(" " + getRd(instruction) + ", " + getRs1(instruction) + ", " + getShamt(instruction));
				else
					builder.append(" " + getRd(instruction) + ", " + getRs1(instruction) + ", " + getImm12(instruction));
				return builder.toString();
			case S_TYPE:
				builder.append(" " + getRs1(instruction) + ", " + getRs2(instruction) + ", " + getImm12(instruction));
				return builder.toString();
			case U_TYPE:
				builder.append(" " + getRd(instruction) + ", " + getImm20(instruction));
				return builder.toString();
			case UJ_TYPE:
				builder.append(" " + getRd(instruction) + ", " + getImm20(instruction));
				return builder.toString();
			case SB_TYPE:
				builder.append(" " + getRs1(instruction) + ", " + getRs2(instruction) + ", " + getImm12(instruction));
				return builder.toString();
			default:
				throw new Exception("Error: Unknown Instruction");
		}
	}

}
