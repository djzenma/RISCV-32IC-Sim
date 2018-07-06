package com.riscvsim.Disassembler;

import com.riscvsim.Architecture.Instruction;
import com.riscvsim.Architecture.InstructionFormat;
import com.riscvsim.Architecture.InstructionGroup;
import com.riscvsim.Architecture.Segment;
import com.riscvsim.Main;

import java.util.ArrayList;

public class Decoder {
	private static String encodedInstruction;

	Decoder() {
	}

	public static String getEncodedInstruction() {
		return encodedInstruction;
	}

	public static void setEncodedInstruction(String encodedInstruction) {
		Decoder.encodedInstruction = encodedInstruction;
	}

	static Instruction decodeInstruction() throws Exception {
		for (InstructionFormat format : Main.isa.getFormats()) {
			for (InstructionGroup instructionGroup : format.getInstructionGroups()) {
				if (isKey(instructionGroup.getValue(), format.getSegmentByName(instructionGroup.getKey()))) {
					for (Instruction instruction : instructionGroup.getInstructions()) {
						if (checkSecondaryKeys(instruction, format)) {
							instruction.setFormat(format);
							instruction.setInstructionGroup(instructionGroup);
							return instruction;
						}
					}
					throw new Exception("No Instruction Exists for this memory value (" + encodedInstruction + ")");
				}
			}
		}
		throw new Exception("No Instruction Group Exists for this memory value (" + encodedInstruction + ")");
	}

	private static boolean isKey(String value, Segment key) {
		return value.equals(encodedInstruction.substring(
				encodedInstruction.length() - key.getStopBit() - 1,
				encodedInstruction.length() - key.getStartBit()
		));
	}

	private static boolean checkSecondaryKeys(Instruction instruction, InstructionFormat format) {
		Integer precedence = 1;
		ArrayList<Segment> secondaryKeys = format.getSecondaryKeys(precedence);
		while (!secondaryKeys.isEmpty()) {
			for (Segment key : secondaryKeys) {
				if (!isKey(instruction.getKeys().get(key.getName()), key))
					return false;
			}
			secondaryKeys = format.getSecondaryKeys(++precedence);
		}
		return true;
	}
}
