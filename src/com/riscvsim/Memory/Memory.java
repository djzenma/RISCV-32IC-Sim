package com.riscvsim.Memory;

import com.riscvsim.Main;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Memory {

	private static final int MEMORY_SIZE = Main.isa.getMemory();

	public static final int WORD = 4;
	public static final int HALF_WORD = 2;
	public static final int BYTE = 1;
	public static final int STRING = 0;

	private static byte[] memory;

	static {
		memory = new byte[Memory.MEMORY_SIZE];
	}

	public static byte getFromMemory(int index) {
		return memory[index];
	}

	public static void storeInHeap(int register, int destinationAddr, final int TYPE) throws Exception {
		if (TYPE == Memory.WORD && destinationAddr % 4 != 0)
			throw new Exception("Error: Address is not aligned, Word Address must be a multiple of 4");
		else if (TYPE == Memory.HALF_WORD && destinationAddr % 2 != 0)
			throw new Exception("Error: Address is not aligned, Half Word Address must be a multiple of 2");
		Integer regValue = RegFile.getValueFromReg(register);
		ByteBuffer byteBuffer = null;

		switch (TYPE) {
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

	public static void storeInHeap(String value, int destinationAddr) throws Exception {
		ByteBuffer byteBuffer;
		byteBuffer = ByteBuffer.allocate(value.getBytes().length).order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.put(value.getBytes());
		try {
			byte[] valueBytes = byteBuffer.array();
			putInMemory(valueBytes, destinationAddr, value.getBytes().length);
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		}
	}

	private static void putInMemory(byte[] byteArray, int address, final int TYPE) {
		System.arraycopy(byteArray, 0, memory, address, TYPE);
	}

	public static byte[] loadFromHeap(int address, final int TYPE) throws Exception {
		if (TYPE == Memory.WORD && address % 4 != 0)
			throw new Exception("Error: Address is not aligned, Word Address must be a multiple of 4");
		else if (TYPE == Memory.HALF_WORD && address % 2 != 0)
			throw new Exception("Error: Address is not aligned, Half Word Address must be a multiple of 2");

		byte[] loaded = new byte[TYPE];
		if (TYPE == STRING) {
			int i = address;
			while (memory[i] != 0) {
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
