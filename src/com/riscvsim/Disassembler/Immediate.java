package com.riscvsim.Disassembler;

import com.riscvsim.Architecture.ImmediateFormat;
import com.riscvsim.Architecture.Instruction;
import com.riscvsim.Architecture.Segment;
import com.riscvsim.Main;

import java.util.BitSet;

public class Immediate {
	private BitSet value = new BitSet(32);

	public Immediate(Instruction instruction, BitSet encodedInstruction) throws Exception {
		for (Segment segment : getImmediateFormatByName(instruction.getImmediate()).getSegments()) {
			if (segment.getName().equals("0")) {
				value.set(segment.getStartBit(), segment.getStopBit() + 1, false);
			} else {
				String segmentName = segment.getName();
				String integerString = segmentName.substring(segmentName.indexOf('[') + 1, segmentName.indexOf(']'));
				Integer instructionStartBit = Integer.parseInt(integerString.substring(integerString.indexOf(':') + 1));
				Integer instructionStopBit = Integer.parseInt(integerString.substring(0, integerString.indexOf(':')));
				BitSet source = encodedInstruction.get(instructionStartBit,instructionStopBit + 1);
				int sourceLength = instructionStopBit-instructionStartBit+1;
				int targetLength = segment.getStopBit()-segment.getStartBit()+1;
				for(int i = 0; i < targetLength; i+=sourceLength){
					for(int j = 0; j < sourceLength; j++){
						if(source.get(j))
							value.set(segment.getStartBit() + i + j, true);
					}
				}
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
