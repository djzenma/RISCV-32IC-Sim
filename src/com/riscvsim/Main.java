package com.riscvsim;

import com.riscvsim.Architecture.Instruction;
import com.riscvsim.Architecture.InstructionSet;
import com.riscvsim.Disassembler.Disassembler;
import com.riscvsim.Instructions.Instructions;
import com.riscvsim.Memory.RegFile;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;

public class Main {
	public static final String BIN_FILE_PATH = "./src/simple2.c.bin";
	public static final String RISCV32I_TEMPLATE_PATH = "./src/RISCV32I.yaml";

	public static Date date = new Date();

	public static InstructionSet isa;

	public static void main(String[] args) {
		try {
			BitSet bits = ProcessData.readBinaryFile(BIN_FILE_PATH);
			isa = ProcessData.parseYAML();
			Disassembler disassembler = new Disassembler();
			for(int i = 0; i < bits.size() -32; i+=32)
				disassembler.printInstruction(bits.get(i,i+32));
		} catch (Exception e) {
			System.out.println("[" + date + "] Error message: " + e.getMessage());
			e.printStackTrace();
		}
	}
}