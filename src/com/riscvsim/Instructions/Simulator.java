package com.riscvsim.Instructions;

import com.riscvsim.Architecture.Instruction;
import com.riscvsim.Disassembler.Immediate;
import com.riscvsim.Fetcher;
import com.riscvsim.Memory.RegFile;
import javafx.util.converter.IntegerStringConverter;

import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class Simulator {
	private static Instruction instruction;
	private static BitSet instructionBits;

	private static Map<String, R> R_Instructions;
	private static Map<String, I> S_Instructions;
	private static Map<String, S> I_Instructions;
	private static Map<String, SB> SB_Instructions;
	private static Map<String, U> U_Instructions;
	private static Map<String, UJ> UJ_Instructions;

	public static void init(Instruction inst, BitSet instructionBits) {
		Simulator.instruction = inst;
		Simulator.instructionBits = instructionBits;
		R_Instructions = new HashMap<>();
		I_Instructions = new HashMap<>();
		S_Instructions = new HashMap<>();
		SB_Instructions = new HashMap<>();
		U_Instructions = new HashMap<>();
		UJ_Instructions = new HashMap<>();


		R_Instructions.put("add", Instructions::add);
		R_Instructions.put("sub", Instructions::sub);
		R_Instructions.put("xor", Instructions::xor);
		R_Instructions.put("or", Instructions::or);
		R_Instructions.put("and", Instructions::and);
		R_Instructions.put("sll", Instructions::sll);
		R_Instructions.put("sra", Instructions::sra);
		R_Instructions.put("srl", Instructions::srl);
		R_Instructions.put("slt", Instructions::slt);
		R_Instructions.put("sltiu", Instructions::sltiu);

		I_Instructions.put("addi", Instructions::addi);
		I_Instructions.put("andi", Instructions::andi);
		I_Instructions.put("xori", Instructions::xori);
		I_Instructions.put("ori", Instructions::ori);
		I_Instructions.put("slli", Instructions::slli);
		I_Instructions.put("slti", Instructions::slti);
		I_Instructions.put("srai", Instructions::srai);
		I_Instructions.put("srli", Instructions::srli);
		I_Instructions.put("lb", Instructions::lb);
		I_Instructions.put("lbu", Instructions::lbu);
		I_Instructions.put("lh", Instructions::lh);
		I_Instructions.put("lhu", Instructions::lhu);
		I_Instructions.put("lw", Instructions::lw);
		//I_Instructions.put("lwu", Instructions::lwu); NOT SUPPORTED IN YAML

		S_Instructions.put("sb", Instructions::sb);
		S_Instructions.put("sh", Instructions::sh);
		S_Instructions.put("sw", Instructions::sw);

		SB_Instructions.put("beq", Instructions::beq);
		SB_Instructions.put("bge", Instructions::bge);
		SB_Instructions.put("bgeu", Instructions::bgeu);
		SB_Instructions.put("blt", Instructions::blt);
		SB_Instructions.put("bltu", Instructions::bltu);
		SB_Instructions.put("bne", Instructions::bne);

		U_Instructions.put("auipc", Instructions::auipc);
		U_Instructions.put("lui", Instructions::lui);

		UJ_Instructions.put("jal", Instructions::jal);
		I_Instructions.put("jalr", Instructions::jalr);

	}


	public static void simulate() throws Exception {
		switch (instruction.getFormat().getName()) {
			case "R-type":
				R_Instructions.get(instruction.getName()).method(getSegmentValue("rd"), getSegmentValue("rs1"), getSegmentValue("rs2"));
				break;
			case "I-type":
				if(instruction.getName().equals("ecall"))
					Instructions.ecall();
				else
					I_Instructions.get(instruction.getName()).method(getSegmentValue("rd"), getSegmentValue("rs1"), getImmediateValue());
				break;
			case "S-type":
				S_Instructions.get(instruction.getName()).method(getSegmentValue("rs1"), getSegmentValue("rs2"), getImmediateValue());
				break;
			case "SB-type":
				SB_Instructions.get(instruction.getName()).method(getSegmentValue("rs1"), getSegmentValue("rs2"), getImmediateValue());
				break;
			case "U-type":
				U_Instructions.get(instruction.getName()).method(getSegmentValue("rd"), getImmediateValue());
				break;
			case "UJ-type":
				UJ_Instructions.get(instruction.getName()).method(getSegmentValue("rd"), getImmediateValue());
				break;
			default:
				throw new Exception("Error: Cannot Execute!");
		}
	}

	private static int getSegmentValue(String name) throws Exception {
		return bitSetToInt(instructionBits.get(instruction.getFormat().getSegmentByName(name).getStartBit(),
				instruction.getFormat().getSegmentByName(name).getStopBit() + 1));
	}

	private static int getImmediateValue() throws Exception {
		return bitSetToInt(new Immediate(instruction, instructionBits).getValue());
	}

	public static int bitSetToInt(BitSet bitSet) {
		Integer bitInteger = 0;
		for (int i = 0; i < 32; i++)
			if (bitSet.get(i))
				bitInteger |= (1 << i);
		return bitInteger;
	}

	@FunctionalInterface
	public interface R {
		void method(int a, int b, int c) throws Exception;
	}

	@FunctionalInterface
	public interface I {
		void method(int a, int b, int c) throws Exception;
	}


	@FunctionalInterface
	public interface S {
		void method(int a, int b, int c) throws Exception;
	}
	@FunctionalInterface
	public interface SB {
		void method(int a, int b, int c) throws Exception;
	}

	@FunctionalInterface
	public interface U {
		void method(int a, int b) throws Exception;
	}

	@FunctionalInterface
	public interface UJ {
		void method(int a, int b) throws Exception;
	}
}


