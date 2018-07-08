package com.riscvsim;

import com.riscvsim.Architecture.InstructionSet;
import com.riscvsim.Disassembler.Disassembler;

import java.util.BitSet;
import java.util.Date;

import static com.riscvsim.Main.BIN_FILE_PATH;

public class Fetcher {
    public static InstructionSet isa;

    public static Date date = new Date();

    public static void fetch() {
        try {
            BitSet bits = ProcessData.readBinaryFile(BIN_FILE_PATH);
            isa = ProcessData.parseYAML();
            Disassembler disassembler = new Disassembler();
            for(int pc = 0; pc < bits.size() -32; pc+=32) {
                disassembler.printInstruction(bits.get(pc, pc + 32));
            }

        } catch (Exception e) {
            System.out.println("[" + date + "] Error message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
