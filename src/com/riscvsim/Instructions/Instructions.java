package com.riscvsim.Instructions;

import com.riscvsim.Fetcher;
import com.riscvsim.Memory.Memory;
import com.riscvsim.Memory.RegFile;

import java.util.BitSet;
import java.util.Scanner;

import static com.riscvsim.Memory.RegFile.getValueFromReg;
import static com.riscvsim.Memory.RegFile.setRegister;

public class Instructions {
	private static final int MAX_20_BITS_NUM = 0xFFFFF;

	/* U Type */
	public static void lui(int rd, int imm) throws Exception {
		RegFile.setRegister(rd, imm);
	}

	public static void auipc(int rd, int imm) throws Exception {
		if (imm > MAX_20_BITS_NUM)
			throw new Exception("Error: immediate must be 20 bits maximum");
		else
			RegFile.setRegister(rd, Fetcher.pc + imm);
	}

	/* UJ Type */
	public static void jal(int rd, int jumpAd) throws Exception {
		RegFile.setRegister(rd, Fetcher.pc);
		Fetcher.pc += jumpAd - 4;
	}

	public static void jalr(int rd, int rs1, int jumpAd) throws Exception {
		RegFile.setRegister(rd, Fetcher.pc);
		Fetcher.pc = (RegFile.getValueFromReg(rs1) + jumpAd) & ~1;
	}

	/* SB Type*/

	public static void beq(int r1, int r2, int address) throws Exception {
		if (getValueFromReg(r1) == getValueFromReg(r2)) {
			Fetcher.pc += address - 4;
		}
	}

	public static void bne(int r1, int r2, int address) throws Exception {
		if (getValueFromReg(r1) != getValueFromReg(r2)) {
			Fetcher.pc += address - 4;
		}
	}

	public static void blt(int r1, int r2, int address) throws Exception {
		if (getValueFromReg(r1) < getValueFromReg(r2)) {
			Fetcher.pc += address - 4;
		}
	}

	public static void bge(int r1, int r2, int address) throws Exception {
		if (getValueFromReg(r1) > getValueFromReg(r2)) {
			Fetcher.pc += address - 4;
		}
	}

	public static void bltu(int r1, int r2, int address) throws Exception {
		if (RegFile.getUnsignedValueFromReg(r1) < RegFile.getUnsignedValueFromReg(r2)) {
			Fetcher.pc += address - 4;
		}
	}

	public static void bgeu(int r1, int r2, int address) throws Exception {
		if (RegFile.getUnsignedValueFromReg(r1) > RegFile.getUnsignedValueFromReg(r2)) {
			Fetcher.pc += address - 4;
		}
	}

	/* I Type */

	public static void lb(int rd, int reg, int imm) throws Exception {
		RegFile.setRegister(rd, Simulator.bitSetToInt(BitSet.valueOf(Memory.loadFromHeap(getValueFromReg(reg) + imm, Memory.BYTE))));
	}

	public static void lh(int rd, int reg, int imm) throws Exception {
		RegFile.setRegister(rd, Simulator.bitSetToInt(BitSet.valueOf(Memory.loadFromHeap(getValueFromReg(reg) + imm, Memory.HALF_WORD))));
	}

	public static void lw(int rd, int reg, int imm) throws Exception {
		RegFile.setRegister(rd, Simulator.bitSetToInt(BitSet.valueOf(Memory.loadFromHeap(getValueFromReg(reg) + imm, Memory.WORD))));
	}

	public static void lbu(int rd, int reg, int imm) throws Exception {
		RegFile.setRegister(rd, Simulator.bitSetToInt(BitSet.valueOf(Memory.loadFromHeap(getValueFromReg(reg) + imm, Memory.BYTE))));
	}

	public static void lhu(int rd, int reg, int imm) throws Exception {
		RegFile.setRegister(rd, Simulator.bitSetToInt(BitSet.valueOf(Memory.loadFromHeap(getValueFromReg(reg) + imm, Memory.HALF_WORD))));
	}

	public static void lwu(int rd, int reg, int imm) throws Exception {
		RegFile.setRegister(rd, Simulator.bitSetToInt(BitSet.valueOf(Memory.loadFromHeap(getValueFromReg(reg) + imm, Memory.WORD))));
	}

	public static void addi(int rd, int reg, int imm) throws Exception {
		RegFile.setRegister(rd, getValueFromReg(reg) + imm);
	}

	public static void slti(int rd, int rs1, int imm) throws Exception {
		if (RegFile.getValueFromReg(rs1) < imm)
			setRegister(rd, 1);
		else
			setRegister(rd, 0);
	}

	public static void sltiu(int rd, int rs1, int imm) throws Exception {
		if (RegFile.getUnsignedValueFromReg(rs1) < imm)
			setRegister(rd, 1);
		else
			setRegister(rd, 0);
	}

	public static void xori(int rd, int rs1, int imm) throws Exception {
		RegFile.setRegister(rd, getValueFromReg(rs1) ^ imm);
	}

	public static void ori(int rd, int rs1, int imm) throws Exception {
		RegFile.setRegister(rd, getValueFromReg(rs1) | imm);
	}

	public static void andi(int rd, int rs1, int imm) throws Exception {
		RegFile.setRegister(rd, getValueFromReg(rs1) & imm);
	}

	public static void slli(int rd, int rs1, int imm) throws Exception {
		RegFile.setRegister(rd, getValueFromReg(rs1) << imm);
	}

	public static void srli(int rd, int rs1, int imm) throws Exception {
		RegFile.setRegister(rd, getValueFromReg(rs1) >>> imm);
	}

	public static void srai(int rd, int rs1, int imm) throws Exception {
		RegFile.setRegister(rd, getValueFromReg(rs1) >> imm);
	}

	/* S Type */

	public static void sw(int rs1, int rs2, int imm) throws Exception {
		Memory.storeInHeap(rs2, getValueFromReg(rs1) + imm, Memory.WORD);
	}

	public static void sh(int rs1, int rs2, int imm) throws Exception {
		Memory.storeInHeap(rs1, getValueFromReg(rs2) + imm, Memory.HALF_WORD);
	}

	public static void sb(int rs1, int rs2, int imm) throws Exception {
		Memory.storeInHeap(rs1, getValueFromReg(rs2) + imm, Memory.BYTE);
	}

	/* R Type */

	public static void add(int regD, int reg1, int reg2) throws Exception {
		RegFile.setRegister(regD, getValueFromReg(reg1) + getValueFromReg(reg2));
	}

	public static void sub(int regD, int reg1, int reg2) throws Exception {
		RegFile.setRegister(regD, getValueFromReg(reg1) - getValueFromReg(reg2));
	}

	public static void sll(int rd, int rs1, int reg2) throws Exception {
		RegFile.setRegister(rd, getValueFromReg(rs1) << getValueFromReg(reg2));
	}

	public static void slt(int rd, int rs1, int rs2) throws Exception {
		if (RegFile.getValueFromReg(rs1) < RegFile.getValueFromReg(rs2))
			setRegister(rd, 1);
		else
			setRegister(rd, 0);
	}

	public static void sltu(int rd, int rs1, int rs2) throws Exception {
		if (RegFile.getUnsignedValueFromReg(rs1) < RegFile.getUnsignedValueFromReg(rs2))
			setRegister(rd, 1);
		else
			setRegister(rd, 0);
	}

	public static void xor(int rd, int rs1, int rs2) throws Exception {
		RegFile.setRegister(rd, getValueFromReg(rs1) ^ getValueFromReg(rs2));
	}

	public static void or(int rd, int rs1, int rs2) throws Exception {
		RegFile.setRegister(rd, getValueFromReg(rs1) | getValueFromReg(rs2));
	}

	public static void and(int rd, int rs1, int rs2) throws Exception {
		RegFile.setRegister(rd, getValueFromReg(rs1) & getValueFromReg(rs2));
	}

	public static void srl(int rd, int rs1, int rs2) throws Exception {
		RegFile.setRegister(rd, getValueFromReg(rs1) >>> getValueFromReg(rs2) & 0b1111111);
	}

	public static void sra(int rd, int rs1, int rs2) throws Exception {
		RegFile.setRegister(rd, getValueFromReg(rs1) >> getValueFromReg(rs2) & 0b1111111);
	}

	public static void ecall() throws Exception {
		int a0 = getValueFromReg(10);
		int a7 = getValueFromReg(17);
		Scanner s = new Scanner(System.in);
		switch (a7) {
			case 1:
				System.out.println(a0);
				break;
			case 4:
				System.out.println(new String(Memory.loadFromHeap(a0, Memory.STRING)));
				break;
			case 10:
				System.exit(0);
				break;
			case 11:
				System.out.println((char) a0);
				break;
			case 5:
				System.out.print("Integer Feed: ");
				Integer readFeedInt = Integer.parseInt(s.nextLine());
				setRegister(a0, readFeedInt);
				s.close();
				break;
			case 8:
				System.out.print("String Feed: ");
				String readFeedString = s.nextLine();
				readFeedString += '\0';
				Memory.storeInHeap(readFeedString, getValueFromReg(2));
				setRegister(10, getValueFromReg(2));
				setRegister(2, getValueFromReg(2) + readFeedString.getBytes().length);
				break;
			case 12:
				System.out.print("Character Feed: ");
				char readFeedChar = s.nextLine().charAt(0);
				setRegister(a0, readFeedChar);
				s.close();
				break;
		}
		s.close();
	}

}
