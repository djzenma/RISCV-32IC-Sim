package com.riscvsim;

import java.util.BitSet;

public class Fetcher {
	public static Integer pc = 0;

	static BitSet fetch(BitSet file) {
		BitSet word = file.get(pc * 8, (pc + 4) * 8);
		pc += word.get(0) && word.get(1) ? 4 : 2;
		return word;
	}
}
