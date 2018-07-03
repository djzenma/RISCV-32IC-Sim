package com.riscvsim.Disassembler;

import com.riscvsim.Architecture.InstructionFormat;
import com.riscvsim.Architecture.Opcode;

import java.util.ArrayList;

public class Disassembler {
	public static final int R_TYPE = 0;
	public static final int I_TYPE = 1;
	public static final int S_TYPE = 2;
	public static final int U_TYPE = 3;
	public static final int SB_TYPE = 4;
	public static final int UJ_TYPE = 5;
	public static final int I_TYPE_SHIFT_CATEGORY = 6;

	private ArrayList<InstructionFormat> instructionFormatList;
	private ArrayList<Opcode> opcodeList;

	private ArrayList<String> instructionList;

	public Disassembler() {
		//for (InstructionFormatHelper ifh : Main.isa.getFormats()) {
		//	instructionFormatList.add(ifh.getInstructionFormat());
		//}
	}

	public Opcode getOpcodeAndFormat(String opcode) {
		//for (InstructionFormat format : instructionFormatList) {
		//	for (OpcodeHelper helper : format.getOpcodes()) {
		//		if (helper.getOpcode().getValue().equals(opcode))
		//			return helper.getOpcode();
		//	}
		//}
		return new Opcode();
	}

	public int getFormat(String instruction) throws Exception {
		String opcode = "000";//getOpcode(instruction);
		switch (opcode) {
			case "0110011":
				return R_TYPE;
			case "0010011":
				if (getInstructionName(instruction).equals("slli") ||
						getInstructionName(instruction).equals("srli") ||
						getInstructionName(instruction).equals("srai"))
					return I_TYPE_SHIFT_CATEGORY;
				return I_TYPE;
			case "0000011":
				return I_TYPE;
			case "0001111":
				return I_TYPE;
			case "0100011":
				return S_TYPE;
			case "1100011":
				return SB_TYPE;
			case "0110111":
				return U_TYPE;
			case "0010111":
				return U_TYPE;
			case "1100111":
				return UJ_TYPE;
			case "1101111":
				return UJ_TYPE;
			default:
				throw new Exception("Format not defined");
		}
	}

	public String getFunct3(String instruction) {
		return instruction.substring(17, 20);
	}

	public String getFunct7(String instruction) {
		return instruction.substring(0, 7);
	}

	public String getRs1(String instruction) {
		return instruction.substring(12, 17);
	}

	public String getRs2(String instruction) {
		return instruction.substring(7, 12);
	}

	public String getRd(String instruction) {
		return instruction.substring(21, 25);
	}

	public String getInstructionName(String instruction) throws Exception {
		String opcode = "";// getOpcode(instruction);
		switch (opcode) {
			case "1100011":
				switch (getFunct3(instruction)) {
					case "000":
						return "beq";
					case "001":
						return "bne";
					case "100":
						return "blt";
					case "101":
						return "bge";
					case "110":
						return "bltu";
					case "111":
						return "bgeu";
					default:
						throw new Exception("Error: Unknow funct3 for branching instruction");
				}
			case "1100111":
				if (getFunct3(instruction).equals("000"))
					return "jalr";
				break;
			case "1101111":
				return "jal";
			case "0110111":
				return "lui";
			case "0010111":
				return "auipc";
			case "0010011":
				switch (getFunct3(instruction)) {
					case "000":
						return "addi";
					case "001":
						return "slli";
					case "010":
						return "slti";
					case "011":
						return "sltiu";
					case "100":
						return "xori";
					case "101":
						if (instruction.substring(0, 7).equals("000000"))
							return "srli";
						else if (instruction.substring(0, 7).equals("010000"))
							return "srai";
						else
							break;
					case "110":
						return "ori";
					case "111":
						return "andi";
					default:
						break;
				}
				break;
			case "0110011":
				switch (getFunct3(instruction)) {
					case "000":
						if (getFunct7(instruction).equals("0000000"))
							return "add";
						else if (getFunct7(instruction).equals("0100000"))
							return "sub";
						else
							break;
					case "001":
						if (getFunct7(instruction).equals("0000000"))
							return "sll";
						else
							break;
					case "010":
						if (getFunct7(instruction).equals("0000000"))
							return "slt";
						else
							break;
					case "011":
						if (getFunct7(instruction).equals("0000000"))
							return "sltu";
						else
							break;
					case "100":
						if (getFunct7(instruction).equals("0000000"))
							return "xor";
						else
							break;
					case "101":
						if (getFunct7(instruction).equals("0000000"))
							return "srl";
						else if (getFunct7(instruction).equals("0100000"))
							return "sra";
						else
							break;
					case "110":
						if (getFunct7(instruction).equals("0000000"))
							return "or";
						else
							break;
					case "111":
						if (getFunct7(instruction).equals("0000000"))
							return "and";
						else
							break;
				}
				break;
			case "0000011":
				switch (getFunct3(instruction)) {
					case "000":
						return "lb";
					case "001":
						return "lh";
					case "010":
						return "lw";
					case "100":
						return "lbu";
					case "101":
						return "lhu";
					default:
						throw new Exception("Error: Unknown funct3 for Load instruction");
				}
			case "0100011":
				switch (getFunct3(instruction)) {
					case "000":
						return "sb";
					case "001":
						return "sh";
					case "010":
						return "sw";
					default:
						throw new Exception("Error: Unknown funct3 for Store instruction");
				}
			case "0001111":
				switch (getFunct3(instruction)) {
					case "000":
						return "fence";
					case "001":
						return "fence.i";
					default:
						throw new Exception("Error: Unknown funct3 for Fence instruction");
				}
			default:
				throw new Exception("Error: Unknown Opcode");
		}
		throw new Exception("Error: Unknown Instruction");
	}

	public String getImm12(String instruction) {
		return instruction.substring(0, 12);
	}

	public String getImm20(String instruction) {
		return instruction.substring(0, 20);
	}

	public String getShamt(String instruction) {
		return instruction.substring(8, 13);
	}

	public String getFullInstructionName(String instruction) throws Exception {
		String instructionName = getInstructionName(instruction);
		StringBuilder builder = new StringBuilder(instructionName);

		switch (getFormat(instruction)) {
			case R_TYPE:
				builder.append(" " + getRd(instruction) + ", " + getRs1(instruction) + ", " + getRs2(instruction));
				//return builder.toString();
			case I_TYPE:
				builder.append(" " + getRd(instruction) + ", " + getRs1(instruction) + ", " + getImm12(instruction));
				return builder.toString();
			case I_TYPE_SHIFT_CATEGORY:
				builder.append(" " + getRd(instruction) + ", " + getRs1(instruction) + getShamt(instruction));
				return builder.toString();
			case S_TYPE:
				builder.append(" " + getRs1(instruction) + ", " + getRs2(instruction) + ", " + getImm12(instruction));
				break;
			case U_TYPE:
				builder.append(" " + getRd(instruction) + ", " + getImm20(instruction));
				return builder.toString();
			case UJ_TYPE:
				builder.append(" " + getRd(instruction) + ", " + getImm20(instruction));
				return builder.toString();
			case SB_TYPE:

				break;
		}

		return "";
	}

}
