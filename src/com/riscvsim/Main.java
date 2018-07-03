package com.riscvsim;

import com.riscvsim.Architecture.InstructionSet;
import com.riscvsim.Disassembler.Disassembler;

import java.util.ArrayList;
import java.util.Date;

public class Main {
	public static final String BIN_FILE_PATH = "./src/memory.txt";
	public static final String RISCV32I_TEMPLATE_PATH = "./src/RISCV32I.yaml";

	public static Date date = new Date();

	public static InstructionSet isa;

	public static void main(String[] args) {
		ArrayList<String> wordList = ProcessData.processBinaryFile();
		isa = ProcessData.parseYAML();
		Disassembler disassembler = new Disassembler();
        try {
            System.out.println(disassembler.getFullInstructionName(wordList.get(0)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}