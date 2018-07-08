package com.riscvsim.Memory;

import com.riscvsim.Architecture.InstructionSet;
import com.riscvsim.Main;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Memory {

    private static final int MEMORY_SIZE = Main.isa.getMemory();
    private static final int TEXT_SEGMENT_ADDR = 1;
    private static final int DATA_SEGMENT_ADDR = 1;
    private static final int HEAP_SEGMENT_ADDR = 1;
    private static final int STACK_SEGMENT_ADDR = 1;

    public static final int WORD = 4;
    public static final int HALF_WORD = 2;
    public static final int BYTE = 1;
    public static final int STRING = 0;

    private static byte[] memory;

    static  {
        memory = new byte[Memory.MEMORY_SIZE];
    }

    public static int getTextSegmentAddr() {
        return TEXT_SEGMENT_ADDR;
    }

    public static int getDataSegmentAddr() {
        return DATA_SEGMENT_ADDR;
    }

    public static int getHeapSegmentAddr() {
        return HEAP_SEGMENT_ADDR;
    }

    public static int getStackSegmentAddr() {
        return STACK_SEGMENT_ADDR;
    }

    public static byte getFromMemory(int index) {
        return memory[index];
    }

    public static void storeInHeap(int register, int destinationAddr, final int TYPE) throws Exception {
        if(TYPE == Memory.WORD && destinationAddr%4 !=0 )
            throw new Exception("Error: Address is not aligned, Word Address must be a multiple of 4");
        else if (TYPE == Memory.HALF_WORD && destinationAddr%2 !=0 )
            throw new Exception("Error: Address is not aligned, Half Word Address must be a multiple of 2");
        else if(destinationAddr < HEAP_SEGMENT_ADDR)
            throw new Exception("Error: You are not storing in the Heap segment, please be sure that your address is in the Heap Segment");
        else if(destinationAddr >= RegFile.getStackPointerValue())
            throw new Exception("Error: Heap Overflow");
        else {
            Integer regValue = RegFile.getValueFromReg(register);
            ByteBuffer byteBuffer = null;

            switch (TYPE){
                case WORD:
                    byteBuffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
                    byteBuffer.putInt(regValue);
                    break;
                case HALF_WORD:
                    byteBuffer = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN);
                    byteBuffer.putShort(regValue.shortValue());
                    break;
                case BYTE:
                    byteBuffer = ByteBuffer.allocate(1).order(ByteOrder.LITTLE_ENDIAN);
                    byteBuffer.put(regValue.byteValue());
                    break;
            }


            try {
                byte[] valueBytes = byteBuffer.array();
                putInMemory(valueBytes, destinationAddr, TYPE);
            } catch (UnsupportedOperationException e) {
                e.printStackTrace();
            }
        }

    }

    private static void putInMemory(byte[] byteArray , int address, final int TYPE) {
        System.arraycopy(byteArray, 0, memory, address, TYPE);
    }

    public static byte[] loadFromHeap(int address, final int TYPE) throws Exception {
        if(TYPE == Memory.WORD && address%4 !=0 )
            throw new Exception("Error: Address is not aligned, Word Address must be a multiple of 4");
        else if (TYPE == Memory.HALF_WORD && address%2 !=0 )
            throw new Exception("Error: Address is not aligned, Half Word Address must be a multiple of 2");
        else if(address < HEAP_SEGMENT_ADDR)
            throw new Exception("Error: You are not loading from the Heap segment, please be sure that your address is in the Heap Segment");

        byte[] loaded = new byte[TYPE];
        if(TYPE == STRING) {
            int i = address;
            while(memory[i] != 0) {
                i++;
            }
            System.arraycopy(memory, address, loaded, 0, i);
        } else {
            loaded = new byte[TYPE];
            System.arraycopy(memory, address, loaded, 0, TYPE);
        }
        return loaded;
    }
}
