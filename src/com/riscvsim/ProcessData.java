package com.riscvsim;

import com.fasterxml.jackson.core.async.ByteArrayFeeder;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.ByteArraySerializer;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.riscvsim.Architecture.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.BitSet;

public class ProcessData {

	public ProcessData() {
	}

	/**
	 * @param filePath
	 * @return fileContent
	 */
	private static String readFile(String filePath) {
		StringBuilder fileContent = new StringBuilder();
		BufferedReader reader = null;
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(filePath);
			reader = new BufferedReader(fileReader);
			String sCurrentLine;
			while ((sCurrentLine = reader.readLine()) != null) {
				fileContent.append(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
				if (fileReader != null)
					fileReader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return fileContent.toString();
	}

	static BitSet readBinaryFile(String path) throws Exception {
		BitSet bits = null;
		try {
			byte[] byteArray = Files.readAllBytes(Paths.get(path));
			if ((byteArray.length * 8) % 16 != 0)
				throw new Exception("Error: Invalid File Alignment");
			bits = BitSet.valueOf(byteArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bits;
	}

	//static ArrayList<String> processBinaryFile() {
	//		try {
	//			BitSet fileContent = readBinaryFile(Main.BIN_FILE_PATH);
	//			if (fileContent != null) {
	//			String word;
	//			ArrayList<String> wordList = new ArrayList<>();
	//			for (int i = 0; i < fileContent.length(); i += 32) {
	//				word = fileContent.substring(i, i + 32);
	//				wordList.add(word);
	//				System.out.println("[" + Main.date + "] Word Processed (" + word + ")");
	//			}
	//			System.out.println("[" + Main.date + "] Binary file processed successfully");x
	//			}
	//		} catch (Exception e) {
	//			System.out.println("[" + Main.date + "] Error message: " + e.getMessage() +
	//					"\n" + "[" + Main.date + "] Exception occured most likely your memory.txt file is not aligned to 32-bit/compressed 16-bit architecture");
	//		}
	//		return wordList;
	//}

	static ArrayList<String> processInstrFile() {
		String instructions = readFile("");
		String instrCurrent = "";
		ArrayList<String> instrList = new ArrayList<>();
		try {
			for (char c : instructions.toCharArray()) {
				if (c != '-')
					instrCurrent += c;
				else {
					if (instrList.contains(instrCurrent))
						throw new Exception("The instruction already exists");
					instrList.add(instrCurrent);
					System.out.println("[" + Main.date + "] Instruction Processed (" + instrCurrent + ")");
					instrCurrent = "";
				}
			}
			System.out.println("[" + Main.date + "] ISA Template file processed successfully");
		} catch (Exception e) {
			System.out.println("[" + Main.date + "] Error message: " + e.getMessage());
		}
		return instrList;
	}

	static InstructionSet parseYAML() throws Exception {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		InstructionSet isa = mapper.readValue(new File(Main.RISCV32I_TEMPLATE_PATH), InstructionSet.class);
		for (InstructionFormat helper : isa.getFormats()) {
			System.out.print(helper.getName() + " | ");
			for (Segment segmentHelper : helper.getSegments()) {
				System.out.print(segmentHelper.getName() + " | ");
				System.out.print(segmentHelper.getStartBit() + " | ");
				System.out.println(segmentHelper.getStopBit() + " | ");
			}
			for (InstructionGroup op : helper.getInstructionGroups()) {
				System.out.println("InstructionGroup: " + op.getValue());
				//for (Instruction i : op.getInstructions()) {
				//	System.out.println("Name : " + i.getName() + " Funct3: " + " ImmediateLoadable" + i.getImmediateLoadable().getValue() + " / "
				//			+ i.getImmediateLoadable().getStartBit() + " / " + i.getImmediateLoadable().getStopBit() + " / " + i.getImmediateLoadable().isSecondary());
//
//				}
			}
		}
		return isa;
	}
}
