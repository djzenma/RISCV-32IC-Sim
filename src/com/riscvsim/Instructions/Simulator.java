package com.riscvsim.Instructions;

import com.riscvsim.Architecture.Instruction;
import com.riscvsim.Disassembler.Immediate;

import java.util.BitSet;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class Simulator {
    private int pc;
    private Instruction instruction;
    private BitSet instructionBits;

    private Map<String, ISAFunctions2> functions2;
    private Map<String, ISAFunctions3> functions3;
    private Map<String, ISAFunctions4> functions4;
    private int address;

    public Simulator(Instruction inst, BitSet instructionBits, int address, int pc) {
        this.instruction = inst;
        this.instructionBits = instructionBits;
        this.pc = pc;
        this.address = address;

        functions2.put("jal", Instructions::jal);
        functions2.put("jalr", Instructions::jalr);

        functions3.put("add", Instructions::add);
        functions3.put("addi", Instructions::addi);
        functions3.put("and", Instructions::and);
        functions3.put("andi", Instructions::andi);
        functions3.put("auipc", Instructions::auipc);
        functions3.put("lb", Instructions::lb);
        functions3.put("lbu", Instructions::lbu);
        functions3.put("lh", Instructions::lh);
        functions3.put("lhu", Instructions::lhu);
        functions3.put("lw", Instructions::lw);
        functions3.put("lwu", Instructions::lwu);
        functions3.put("or", Instructions::or);
        functions3.put("ori", Instructions::ori);
        functions3.put("sb", Instructions::sb);
        functions3.put("sh", Instructions::sh);
        functions3.put("sw", Instructions::sw);
        functions3.put("sll", Instructions::sll);
        functions3.put("slli", Instructions::slli);
        functions3.put("slt", Instructions::slt);
        functions3.put("slti", Instructions::slti);
        functions3.put("sltiu", Instructions::sltiu);
        functions3.put("sra", Instructions::sra);
        functions3.put("srai", Instructions::srai);
        functions3.put("srl", Instructions::srl);
        functions3.put("sub", Instructions::sub);
        functions3.put("srli", Instructions::srli);
        functions3.put("xor", Instructions::xor);
        functions3.put("xori", Instructions::xori);

        functions4.put("beq", Instructions::beq);
        functions4.put("bge", Instructions::bge);
        functions4.put("bgeu", Instructions::bgeu);
        functions4.put("blt", Instructions::blt);
        functions4.put("bltu", Instructions::bltu);
        functions4.put("bne", Instructions::bne);
    }


    public void simulate(int regD, int immediate) throws Exception {
        switch (instruction.getName()) {
            case "lui":
                BitSet rd = instructionBits.get(instruction.getFormat().getSegmentByName("rd").getStartBit(),
                        instruction.getFormat().getSegmentByName("rd").getStopBit());
                BitSet imm = instructionBits.get(instruction.getFormat().getSegmentByName("imm").getStartBit(),
                        instruction.getFormat().getSegmentByName("imm").getStopBit());
                Immediate toImmediate = new Immediate(instruction, instructionBits);
                Instructions.lui(bitSetToInt(rd), bitSetToInt(toImmediate.getValue()));
                break;
            case "jal":
                Instructions.jal(address, pc);
                break;
            case "jalr":
                BitSet rs1 = instructionBits.get(instruction.getFormat().getSegmentByName("rs1").getStartBit(),
                        instruction.getFormat().getSegmentByName("rs1").getStopBit());
                Instructions.jalr(bitSetToInt(rs1), pc);
                break;
        }
        if(functions3.containsKey(instruction.getName())) {
            BitSet rs1 = instructionBits.get(instruction.getFormat().getSegmentByName("rs1").getStartBit(),
                    instruction.getFormat().getSegmentByName("rs1").getStopBit());
            BitSet rd = instructionBits.get(instruction.getFormat().getSegmentByName("rd").getStartBit(),
                    instruction.getFormat().getSegmentByName("rd").getStopBit());
            Immediate toImmediate = new Immediate(instruction, instructionBits);
            functions3.get(instruction.getName()).method(bitSetToInt(rd), bitSetToInt(rs1), bitSetToInt(toImmediate.getValue()));
        } else if (functions4.containsKey(instruction.getName())) {
            BitSet rs1 = instructionBits.get(instruction.getFormat().getSegmentByName("rs1").getStartBit(),
                    instruction.getFormat().getSegmentByName("rs1").getStopBit());
            BitSet rs2 = instructionBits.get(instruction.getFormat().getSegmentByName("rs2").getStartBit(),
                    instruction.getFormat().getSegmentByName("rs2").getStopBit());
            functions4.get(instruction.getName()).method(bitSetToInt(rs1), bitSetToInt(rs2), this.address,this.pc);
        } else
            throw new Exception("Error: Cannot Execute!");
    }


    public static int bitSetToInt(BitSet bitSet)
    {
        int bitInteger = 0;
        for(int i = 0 ; i < 32; i++)
            if(bitSet.get(i))
                bitInteger |= (1 << i);
        return bitInteger;
    }


    @FunctionalInterface
    public static interface ISAFunctions2 {
        void method(int a, int b) throws Exception;
    }

    @FunctionalInterface
    public static interface ISAFunctions3 {
        void method(int a, int b, int c) throws Exception;
    }

    @FunctionalInterface
    public static interface ISAFunctions4 {
        void method(int a, int b, int c, int d) throws Exception;
    }
}


