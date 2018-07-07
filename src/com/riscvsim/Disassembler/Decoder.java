package com.riscvsim.Disassembler;

import com.riscvsim.Architecture.*;
import com.riscvsim.Main;

import java.util.ArrayList;
import java.util.BitSet;

public class Decoder {
	private static BitSet encodedInstruction;

	Decoder() {
	}

	static Instruction decodeInstruction() throws Exception {
		for (InstructionFormat format : Main.isa.getFormats()) {
			for (InstructionGroup instructionGroup : format.getInstructionGroups()) {
				if (isKey(instructionGroup.getValue(),
						format.getSegmentByName(instructionGroup.getKey()).getStartBit(),
						format.getSegmentByName(instructionGroup.getKey()).getStopBit())
						) {
					for (Instruction instruction : instructionGroup.getInstructions()) {
						if (checkSecondaryKeys(instruction, format)) {
							if (checkLoadables(instruction)) {
								instruction.setFormat(format);
								instruction.setInstructionGroup(instructionGroup);
								return instruction;
							}
						}
					}
					throw new Exception("No Instruction Exists for this memory value (" + encodedInstruction.length() + ")");
				}
			}
		}
		throw new Exception("No Instruction Group Exists for this memory value (" + encodedInstruction.size() + ")");
	}

	private static String bitSetToString(String bits) {
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		for (int i = 0; i < bits.length(); i++) {
			if (bits.charAt(i) == '1') {
				builder.append(i);
				builder.append(", ");
			}
		}
		if (builder.toString().length() > 2)
			builder.delete(builder.toString().length() - 2, builder.toString().length());
		builder.append("}");
		return builder.toString();
	}

	private static boolean isKey(String value, Integer startBit, Integer stopBit) {
		return encodedInstruction.get(startBit, stopBit + 1).toString().equals(bitSetToString(new StringBuilder(value).reverse().toString()));

	}


	private static boolean checkSecondaryKeys(Instruction instruction, InstructionFormat format) {
		Integer precedence = 1;
		ArrayList<Segment> secondaryKeys = format.getSecondaryKeys(precedence);
		while (!secondaryKeys.isEmpty()) {
			for (Segment key : secondaryKeys) {
				if (!isKey(instruction.getKeys().get(key.getName()), key.getStartBit(), key.getStopBit()))
					return false;
			}
			secondaryKeys = format.getSecondaryKeys(++precedence);
		}
		return true;
	}

	private static boolean checkLoadables(Instruction instruction) {
		try {
			String loadable = instruction.getKeys().get("Loadable");
			if (loadable.equals("1")) {
				for (ImmediateLoadable tertiaryKey : instruction.getImmediateLoadable()) {
					if (!isKey(tertiaryKey.getValue(), tertiaryKey.getStartBit(), tertiaryKey.getStopBit()))
						return false;
				}
			}
		} catch (Exception e) {
		}
		return true;
	}

	public static BitSet getEncodedInstruction() {
		return encodedInstruction;
	}

	public static void setEncodedInstruction(BitSet encodedInstruction) {
		Decoder.encodedInstruction = encodedInstruction;
	}
}
