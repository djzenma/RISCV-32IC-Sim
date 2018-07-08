package com.riscvsim.Disassembler;

import com.riscvsim.Architecture.ImmediateFormat;
import com.riscvsim.Architecture.Instruction;
import com.riscvsim.Architecture.Segment;
import com.riscvsim.Main;

import java.util.BitSet;

public class Immediate {
	private BitSet value;

	public Immediate(Instruction instruction, BitSet encodedInstruction) throws Exception {
		for (Segment segment : getImmediateFormatByName(instruction.getImmediate()).getSegments()) {
			if (segment.getName().equals("0")) {
				encodedInstruction.set(segment.getStartBit(), segment.getStopBit() + 1, false);
			} else {
				String segmentName = segment.getName();
				Integer instructionStartBit = Integer.parseInt(
						segmentName.substring(segmentName.lastIndexOf('['),
								segmentName.lastIndexOf(']') + 1).substring(segmentName.lastIndexOf(':')
						));
				Integer instructionStopBit = Integer.parseInt(
						segmentName.substring(segmentName.lastIndexOf('['),
								segmentName.lastIndexOf(']') + 1).substring(0, segmentName.lastIndexOf(':')
						));
				this.value = Decoder.mapBitSets(encodedInstruction,
						encodedInstruction.get(instructionStartBit, instructionStopBit + 1),
						segment.getStartBit()
				);
			}
		}
	}

	private ImmediateFormat getImmediateFormatByName(String name) throws Exception {
		for (ImmediateFormat format : Main.isa.getImmediateFormats()) {
			if (format.getName().equals(name))
				return format;
		}
		throw new Exception("Immediate Format Not Found");
	}

	public BitSet getValue() {
		return value;
	}
}
