package com.riscvsim.Disassembler;

import com.riscvsim.Architecture.Instruction;
import com.riscvsim.Architecture.Segment;

import java.util.BitSet;

public class Disassembler {
	public Disassembler() {
	}

	public static void disassemble(Instruction instruction, BitSet encodedInstruction) {
		System.out.print(instruction.getName() + '\t');
		for (Segment segment : instruction.getFormat().getSegments()) {
			System.out.print(segment.getName() + ": " + Decoder.bitSetToString(encodedInstruction.get(segment.getStartBit(), segment.getStopBit() + 1), segment.getStopBit()-segment.getStartBit()+1) + ' ');
		}
		System.out.print("\n");
	}
}
