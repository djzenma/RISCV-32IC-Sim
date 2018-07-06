package com.riscvsim.Disassembler;

import com.riscvsim.Architecture.Instruction;
import com.riscvsim.Architecture.Segment;

public class Disassembler {
	public Disassembler() {
	}

	public void printInstruction(String encodedInstruction) throws Exception {
		Decoder.setEncodedInstruction(encodedInstruction);
		Instruction instruction = Decoder.decodeInstruction();
		System.out.print(instruction.getName() + '\t');
		for (Segment segment : instruction.getFormat().getSegments()) {
			System.out.print(segment.getName() + ": " + encodedInstruction.substring(
					encodedInstruction.length() - segment.getStopBit() - 1,
					encodedInstruction.length() - segment.getStartBit()
			) + '\t');
		}
		System.out.print("\n");
	}

}
