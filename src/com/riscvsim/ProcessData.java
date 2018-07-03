package com.riscvsim;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.riscvsim.Architecture.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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

	static ArrayList<String> processBinaryFile() {
		String fileContent = readFile(Main.BIN_FILE_PATH);
		String word;
		ArrayList<String> wordList = new ArrayList<>();
		for (int i = 0; i < fileContent.length(); i += 32) {
			try {
				int temp = i + 32;
				word = fileContent.substring(i, temp);
				wordList.add(word);
				System.out.println("[" + Main.date + "] Word Processed (" + word + ")");
			} catch (Exception e) {
				System.out.println("[" + Main.date + "] Error message: " + e.getMessage() +
						"\n" + "[" + Main.date + "] Exception occured most likely your memory.txt file is not aligned to 32-bit architecture");
			}
		}
		System.out.println("[" + Main.date + "] src.Memory file processed successfully");
		return wordList;
	}

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

	static InstructionSet parseYAML() {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		InstructionSet isa = new InstructionSet();
		try {
			isa = mapper.readValue(new File(Main.RISCV32I_TEMPLATE_PATH), InstructionSet.class);
			for (InstructionFormat helper : isa.getFormats()) {
				System.out.print(helper.getName() + " | ");
				System.out.println(helper.getSegmentCount() + " | ");
				for (Segment segmentHelper: helper.getSegments()){
					System.out.print(segmentHelper.getName() + " | ");
					System.out.print(segmentHelper.getStartBit() + " | ");
					System.out.println(segmentHelper.getStopBit() + " | ");
				}
				for (Opcode op : helper.getOpcodes()) {
					System.out.println("Opcode: " + op.getValue());
					for (Instruction i : op.getInstructions()) {
						System.out.println("Name : " +  i.getName() + " Funct3 " + i.getFunct3());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isa;
	}
}
