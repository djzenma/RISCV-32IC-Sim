package com.riscvsim;

import com.riscvsim.Architecture.InstructionSet;
import com.riscvsim.Disassembler.Disassembler;

import java.util.BitSet;
import java.util.Date;

/**
 * Main Entry point of program
 */
public class Main {
	/**
	 * Flag indicating mode of operation
	 */
	public static boolean debug = false;
	static final String RISCV32I_TEMPLATE_PATH = "./src/RISCV32I.yaml";
	/**
	 * Path to binary file to be disassembled and executed.
	 */
	private static String binFilePath;
	/**
	 * Date object for logging
	 */
	public static Date date = new Date();
	/**
	 * InstructionSet Object defining the ISA used in execution of this program,
	 * its defined through parsing the YAML configuration file defined.
	 */
	public static InstructionSet isa;

	public static void main(String[] args) {
		/* Validate Parameters */
		switch (args.length) {
			case 0:
				Main.binFilePath = "./src/simple2.bin";
				System.out.println("[" + date + "] Binary file path parameter undetected, default binary Path Chosen.");
				System.out.println("[" + date + "] Path: " + binFilePath);
				break;
			case 1:
				Main.binFilePath = args[0];
				System.out.println("[" + date + "] Binary file path detected, default binary Path Chosen.");
				System.out.println("[" + date + "] Path: " + binFilePath);
				break;
			default:
				System.out.println("[" + date + "] Invalid Parameters, Program Exiting...");
				System.exit(0);
		}

		try {
			BitSet bits = ProcessData.readBinaryFile(binFilePath);
			isa = ProcessData.parseYAML();
			Disassembler disassembler = new Disassembler();
			for (int i = 0; i < bits.size() - 32; i += 32)
				disassembler.printInstruction(bits.get(i, i + 32));
		} catch (Exception e) {
			System.out.println("[" + date + "] Error message: " + e.getMessage());
			e.printStackTrace();
		}
	}
}