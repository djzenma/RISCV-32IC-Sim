package com.riscvsim.Disassembler;

import com.riscvsim.Architecture.Instruction;
import com.riscvsim.Architecture.InstructionFormat;
import com.riscvsim.Architecture.Opcode;
import com.riscvsim.Architecture.Segment;
import com.riscvsim.Main;

import java.util.ArrayList;

public class Disassembler {
	public static final String R_TYPE = "R-type";
	public static final String I_TYPE = "I-type";
	public static final String S_TYPE = "S-type";
	public static final String U_TYPE = "U-type";
	public static final String SB_TYPE = "SB-type";
	public static final String UJ_TYPE = "UJ-type";
	public static final String I_TYPE_SHIFT_CATEGORY = "fuck";

	private ArrayList<InstructionFormat> instructionFormatList;
	private ArrayList<Opcode> opcodeList;

	private ArrayList<String> instructionList;

	public Disassembler() {
		instructionFormatList = Main.isa.getFormats();
	}

	public Instruction getInstruction(String opcode, String func3, String funct7) throws Exception {
		boolean contains_funct3 = false;
		boolean contains_funct7 = false;
		for (InstructionFormat format : instructionFormatList) {
			for (Opcode op : format.getOpcodes()) {
				if (op.getValue().equals(opcode)) {
					for (Segment s: format.getSegments()) {
						if(s.getName().equals("funct3"))
							contains_funct3 = true;
						if(s.getName().equals("funct7"))
							contains_funct7 = true;
					}
					if(contains_funct3) {
						for (Instruction instruction : op.getInstructions()) {
							if (instruction.getFunct3().equals(func3)) {
                                if (contains_funct7) {
                                    if(instruction.getFunct7().equals(funct7)) {
                                        instruction.setFormat(format);
                                        instruction.setOpcode(op);
                                        return instruction;
                                    }
                                }
                                else {
                                    instruction.setFormat(format);
                                    instruction.setOpcode(op);
                                    return instruction;
                                }
							}
						}
					}
					else {
						op.getInstructions().get(0).setOpcode(op);
                        op.getInstructions().get(0).setFormat(format);
                        return op.getInstructions().get(0);
					}
				}
			}
		}
		throw new Exception("Error: Instruction Not Found!");
	}
/*
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
*/
	public String getFunct3(String instruction) {
		return instruction.substring(31-14, 32-12);
	}

	public String getFunct7(String instruction) {
		return instruction.substring(0, 7);
	}

	public String getRs1(String instruction) {
		return instruction.substring(31-19, 31-14);
	}

	public String getRs2(String instruction) {
		return instruction.substring(7, 12);
	}

	public String getRd(String instruction) {
		return instruction.substring(31-11, 31-6);
	}
/*
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
*/
	public String getImm12(String instruction) {
		return instruction.substring(0, 12);
	}

	public String getImm20(String instruction) {
		return instruction.substring(0, 20);
	}

	public String getShamt(String instruction) {
		return instruction.substring(8, 13);
	}

	public String getOpcode(String instruction) {
		return instruction.substring(32-7, 32);
	}

	public String getFullInstructionName(String instruction) throws Exception {
		Instruction inst = getInstruction(getOpcode(instruction), getFunct3(instruction), getFunct7(instruction));
		StringBuilder builder = new StringBuilder(inst.getName());
		switch (inst.getFormat().getName()) {
			case R_TYPE:
				builder.append(" " + getRd(instruction) + ", " + getRs1(instruction) + ", " + getRs2(instruction));
				return builder.toString();
			case I_TYPE:
				if(inst.getName().equals("srai") || inst.getName().equals("srli") || inst.getName().equals("slli"))
				    builder.append(" " + getRd(instruction) + ", " + getRs1(instruction) + ", " + getShamt(instruction));
				else
                    builder.append(" " + getRd(instruction) + ", " + getRs1(instruction) + ", " + getImm12(instruction));
				return builder.toString();
			case S_TYPE:
				builder.append(" " + getRs1(instruction) + ", " + getRs2(instruction) + ", " + getImm12(instruction));
                return builder.toString();
			case U_TYPE:
				builder.append(" " + getRd(instruction) + ", " + getImm20(instruction));
				return builder.toString();
			case UJ_TYPE:
				builder.append(" " + getRd(instruction) + ", " + getImm20(instruction));
				return builder.toString();
			case SB_TYPE:
                builder.append(" " + getRs1(instruction) + ", " + getRs2(instruction) + ", " + getImm12(instruction) );
                return builder.toString();
            default:
                throw new Exception("Error: Unknown Instruction");
		}
	}

}
