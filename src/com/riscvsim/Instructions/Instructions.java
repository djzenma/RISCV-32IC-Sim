package com.riscvsim.Instructions;

import com.riscvsim.Memory.Memory;
import com.riscvsim.Memory.RegFile;

import static com.riscvsim.Memory.RegFile.getValueFromReg;
import static com.riscvsim.Memory.RegFile.setInRegisterByName;

public class Instructions {
    private static final int MAX_20_BITS_NUM = 0xFFFFF;


    public static void lui(int rd, int imm) throws Exception {
        if (imm > MAX_20_BITS_NUM)
            throw new Exception("Error: immediate must be 20 bits maximum");
        else {
            int shifted_imm = imm << 20;
            RegFile.setInRegisterByName(rd, shifted_imm);
        }
    }

    public static void auipc(int pc, int imm, int rd) throws Exception {
        if (imm > MAX_20_BITS_NUM)
            throw new Exception("Error: immediate must be 20 bits maximum");
        else
            RegFile.setInRegisterByName(rd, pc + imm);
    }

    public static void jal(int address, Integer pc) throws Exception {
        if (address >= Memory.getDataSegmentAddr() || address < Memory.getTextSegmentAddr())
            throw new Exception("Error: Address out of Text Segment Range");
        RegFile.setInRegisterByName(RegFile.RETURN_ADRESS, pc);
        pc = address;
    }

    public static void jalr(int reg, Integer pc) throws Exception {
        RegFile.setInRegisterByName(RegFile.RETURN_ADRESS, pc);
        int jumpAd = getValueFromReg(reg);
        if (jumpAd >= Memory.getDataSegmentAddr() || jumpAd < Memory.getTextSegmentAddr())
            throw new Exception("Error: Address out of Text Segment Range");
        pc = jumpAd;
    }

    public static void beq(int r1, int r2, int address, Integer pc) throws Exception {
        if(getValueFromReg(r1) == getValueFromReg(r2)) {
            if (address >= Memory.getDataSegmentAddr() || address < Memory.getTextSegmentAddr())
                throw new Exception("Error: Address out of Text Segment Range");
            else
                pc = address;
        }
    }

    public static void bne(int r1, int r2, int address, Integer pc) throws Exception {
        if(getValueFromReg(r1) != getValueFromReg(r2)) {
            if (address >= Memory.getDataSegmentAddr() || address < Memory.getTextSegmentAddr())
                throw new Exception("Error: Address out of Text Segment Range");
            else
                pc = address;
        }
    }

    public static void blt(int r1, int r2, int address, Integer pc) throws Exception {
        if(getValueFromReg(r1) < getValueFromReg(r2)) {
            if (address >= Memory.getDataSegmentAddr() || address < Memory.getTextSegmentAddr())
                throw new Exception("Error: Address out of Text Segment Range");
            else
                pc = address;
        }
    }

    public static void bge(int r1, int r2, int address, Integer pc) throws Exception {
        if(getValueFromReg(r1) > getValueFromReg(r2)) {
            if (address >= Memory.getDataSegmentAddr() || address < Memory.getTextSegmentAddr())
                throw new Exception("Error: Address out of Text Segment Range");
            else
                pc = address;
        }
    }

    public static void bltu(int r1, int r2, int address, Integer pc) throws Exception {
        if(RegFile.getUnsignedValueFromReg(r1) < RegFile.getUnsignedValueFromReg(r2)) {
            if (address >= Memory.getDataSegmentAddr() || address < Memory.getTextSegmentAddr())
                throw new Exception("Error: Address out of Text Segment Range");
            else
                pc = address;
        }
    }

    public static void bgeu(int r1, int r2, int address, Integer pc) throws Exception {
        if(RegFile.getUnsignedValueFromReg(r1) > RegFile.getUnsignedValueFromReg(r2)) {
            if (address >= Memory.getDataSegmentAddr() || address < Memory.getTextSegmentAddr())
                throw new Exception("Error: Address out of Text Segment Range");
            else
                pc = address;
        }
    }

    public static void lb(int rd, int imm, int reg) throws Exception {
        Memory.loadFromHeap(getValueFromReg(reg) + imm, Memory.BYTE);
    }

    public static void lh(int rd, int imm, int reg) throws Exception {
        Memory.loadFromHeap(getValueFromReg(reg) + imm, Memory.HALF_WORD);
    }

    public static void lw(int rd, int imm, int reg) throws Exception {
        Memory.loadFromHeap(getValueFromReg(reg) + imm, Memory.WORD);
    }

    // TODO: Unsigned not done
    public static void lbu(int rd, int imm, int reg) throws Exception {
        Memory.loadFromHeap(getValueFromReg(reg) + imm, Memory.BYTE);
    }

    public static void lhu(int rd, int imm, int reg) throws Exception {
        Memory.loadFromHeap(getValueFromReg(reg) + imm, Memory.HALF_WORD);
    }

    public static void lwu(int rd, int imm, int reg) throws Exception {
        Memory.loadFromHeap(getValueFromReg(reg) + imm, Memory.WORD);
    }

    public static void sw(int rs, int imm, int rd) throws Exception {
        Memory.storeInHeap(rs, getValueFromReg(rd) + imm, Memory.WORD);
    }

    public static void sh(int rs, int imm, int rd) throws Exception {
        Memory.storeInHeap(rs, getValueFromReg(rd) + imm, Memory.HALF_WORD);
    }

    public static void sb(int rs, int imm, int rd) throws Exception {
        Memory.storeInHeap(rs, getValueFromReg(rd) + imm, Memory.BYTE);
    }

    public static void addi(int rd, int rs1, int imm) throws Exception {
        RegFile.setInRegisterByName(rd, RegFile.getValueFromReg(rs1) + imm);
    }

    public static void slti(int rd, int rs1, int imm) throws Exception {
        if(RegFile.getValueFromReg(rs1) < imm)
            setInRegisterByName(rd, 1);
        else
            setInRegisterByName(rd, 0);
    }

    public static void sltiu(int rd, int rs1, int imm) throws Exception {
        if(RegFile.getUnsignedValueFromReg(rs1) < imm)
            setInRegisterByName(rd, 1);
        else
            setInRegisterByName(rd, 0);
    }

    public static void xori(int rd, int rs1, int imm) throws Exception {
        RegFile.setInRegisterByName(rd, getValueFromReg(rs1) ^ imm);
    }

    public static void ori(int rd, int rs1, int imm) throws Exception {
        RegFile.setInRegisterByName(rd, getValueFromReg(rs1) | imm);
    }

    public static void andi(int rd, int rs1, int imm) throws Exception {
        RegFile.setInRegisterByName(rd, getValueFromReg(rs1) & imm);
    }

    public static void slli(int rd, int rs1, int imm) throws Exception {
        RegFile.setInRegisterByName(rd, getValueFromReg(rs1) << imm);
    }

    public static void srli(int rd, int rs1, int imm) throws Exception {
        RegFile.setInRegisterByName(rd, getValueFromReg(rs1) >>> imm);
    }

    public static void srai(int rd, int rs1, int imm) throws Exception {
        RegFile.setInRegisterByName(rd, getValueFromReg(rs1) >> imm);
    }

    // TODO: Overflow exception
    public static void add(int regD, int reg1, int reg2) throws Exception {
        RegFile.setInRegisterByName(regD, getValueFromReg(reg1) + getValueFromReg(reg2));
    }

    public static void sub(int regD, int reg1, int reg2) throws Exception {
        RegFile.setInRegisterByName(regD, getValueFromReg(reg1) - getValueFromReg(reg2));
    }

    public static void sll(int rd, int rs1, int reg2) throws Exception {
        RegFile.setInRegisterByName(rd, getValueFromReg(rs1) << getValueFromReg(reg2));
    }

    public static void slt(int rd, int rs1, int rs2) throws Exception {
        if(RegFile.getValueFromReg(rs1) < RegFile.getValueFromReg(rs2))
            setInRegisterByName(rd, 1);
        else
            setInRegisterByName(rd, 0);
    }

    public static void sltu(int rd, int rs1, int rs2) throws Exception {
        if(RegFile.getUnsignedValueFromReg(rs1) < RegFile.getUnsignedValueFromReg(rs2))
            setInRegisterByName(rd, 1);
        else
            setInRegisterByName(rd, 0);
    }

    public static void xor(int rd, int rs1, int rs2) throws Exception {
        RegFile.setInRegisterByName(rd, getValueFromReg(rs1) ^ getValueFromReg(rs2));
    }

    public static void or(int rd, int rs1, int rs2) throws Exception {
        RegFile.setInRegisterByName(rd, getValueFromReg(rs1) | getValueFromReg(rs2));
    }

    public static void and(int rd, int rs1, int rs2) throws Exception {
        RegFile.setInRegisterByName(rd, getValueFromReg(rs1) & getValueFromReg(rs2));
    }

    public static void srl(int rd, int rs1, int rs2) throws Exception {
        RegFile.setInRegisterByName(rd, getValueFromReg(rs1) >>> getValueFromReg(rs2) & 0b1111111);
    }

    public static void sra(int rd, int rs1, int rs2) throws Exception {
        RegFile.setInRegisterByName(rd, getValueFromReg(rs1) >> getValueFromReg(rs2) & 0b1111111);
    }
}
