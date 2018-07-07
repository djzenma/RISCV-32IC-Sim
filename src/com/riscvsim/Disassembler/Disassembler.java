package com.riscvsim.Disassembler;

import com.riscvsim.Architecture.Instruction;
import com.riscvsim.Architecture.Segment;

import java.util.BitSet;

public class Disassembler {
	public Disassembler() {
	}

	public void printInstruction(BitSet encodedInstruction) throws Exception {
		Decoder.setEncodedInstruction(encodedInstruction);
		Instruction instruction = Decoder.decodeInstruction();
		System.out.print(instruction.getName() + '\t');
		for (Segment segment : instruction.getFormat().getSegments()) {
			System.out.print(segment.getName() + ": " + encodedInstruction.get(segment.getStartBit(),segment.getStopBit()) + '\t');
		}
		System.out.print("\n");
	}

}
