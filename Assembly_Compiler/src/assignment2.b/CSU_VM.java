/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

/**
 *
 * @author James Ellerbee
 */
public class CSU_VM {
    private int[] memory = new int[64];
    private int[] register = {0, 0, 0, 0};
    private ArrayList<String> instrArray;
    private String[] unfilterInstrArr;
    private CSU_Instruction program;
    

    /**
     *Initialize instance variables
     */
    public CSU_VM(){
       instrArray = new ArrayList();
       unfilterInstrArr = new String[0];
    }
    
    /**
     *Contains the logic to execute program imported from text file, this was the previous main before implementing the GUI
     * @param fileName
     * @throws FileNotFoundException
     */
    public CSU_VM(String fileName) throws FileNotFoundException {
        //populates memory array with 0's
        for (int i = 0; i < 64; i++) {

            memory[i] = 0;
        }
        program = new CSU_Instruction(fileName);
        if (program.isError() == true) {
            instrArray = new ArrayList();
            unfilterInstrArr = new String[0];       
        } else {
            instrArray = program.getInstrArray(); // the array's first index is "# start"
            unfilterInstrArr = program.getInstrArr();
            String instruction; //used to store one instruction at a time from the instrList
            instruction = instrArray.get(0);
            String str[] = instruction.trim().split("M:");
            String str1[] = str[1].trim().split(",");
            String temp[];
            int j = 0;
            for (String element : str1) {
                temp = element.trim().split("=");
                try {
                    memory[Integer.parseInt(temp[0])] = Integer.parseInt(temp[1]);
                } catch (IndexOutOfBoundsException e) {
                    try {
                        performErrorMDump(memory, register, j - 1, String.valueOf(e));
                    } catch (IOException error) {
                        System.out.println("Error writing to file");
                    }
                }
                j++;
            }
            instrArray.remove(0);
            for (int i = 0; i < instrArray.size(); i++) {
                instruction = instrArray.get(i);
                //System.out.println(i);
                if (instruction.contains("LOAD")) {
                    String arr[] = instruction.split("LOAD"); //splits load out of the string, leaving line number and addresses to preform             
                    int numArr[] = parse(arr);

                    program.Load(numArr[0], numArr[1], memory, register);

                } else if (instruction.contains("STOR")) {
                    String arr[] = instruction.split("STOR");
                    int numArr[] = parse(arr);

                    program.Store(numArr[0], numArr[1], memory, register);

                } else if (instruction.contains("ADD")) {
                    String arr[] = instruction.split("ADD");
                    int numArr[] = parse(arr);

                    program.Add(numArr[0], numArr[1], register);

                } else if (instruction.contains("SUB")) {
                    String arr[] = instruction.split("SUB");
                    int numArr[] = parse(arr);

                    program.Sub(numArr[0], numArr[1], register);

                } else if (instruction.contains("MUL")) {
                    String arr[] = instruction.split("MUL");
                    int numArr[] = parse(arr);

                    program.Mul(numArr[0], numArr[1], register);

                } else if (instruction.contains("DIV")) {
                    String arr[] = instruction.split("DIV");
                    int numArr[] = parse(arr);

                    program.Div(numArr[0], numArr[1], register);

                } else if (instruction.contains("MOD")) {
                    String arr[] = instruction.split("MOD");
                    int numArr[] = parse(arr);

                    program.Mod(numArr[0], numArr[1], register);

                } else if (instruction.contains("CMP")) {
                    String arr[] = instruction.split("CMP");
                    int numArr[] = parse2(arr);

                    if (program.Compare(numArr[0], numArr[1], register) == true) {
                        i = numArr[2];
                    }
                } else if (instruction.contains("GRT")) {
                    String arr[] = instruction.split("GRT");
                    int numArr[] = parse2(arr);

                    if (program.GRT(numArr[0], numArr[1], register) == true) {
                        i = numArr[2];
                    }
                } else if (instruction.contains("LSS")) {
                    String arr[] = instruction.split("LSS");
                    int numArr[] = parse2(arr);
                    if (program.LSS(numArr[0], numArr[1], register) == true) {
                        i = numArr[2] - 1;
                    }

                } else if (instruction.contains("STOP")) {
                    break;

                } else {
                    System.out.println(instruction + " at index: " + i);
                }
            }
            //do a successful memory dump
            try {
                performMDump(memory, register, fileName);
            } catch (IOException e) {
                System.out.println("Error with writing to file.");
            }
        }
    }

    public boolean isError(){
        return program.isError();
    }
    /**
     *Returns memory array instance variable
     * @return memory[]
     */
    public int[] getMemory() {
        return memory;
    }

    /**
     *Sets the instance variable for memory to parameter
     * @param memory
     */
    public void setMemory(int[] memory) {
        this.memory = memory;
    }

    /**
     *Returns register array instance variable
     * @return
     */
    public int[] getRegister() {
        return register;
    }

    /**
     *Sets the instance variable register to parameter
     * @param register
     */
    public void setRegister(int[] register) {
        this.register = register;
    }

    /**
     *Returns the instrArray instance variable
     * @return
     */
    public ArrayList<String> getInstrArray() {
        return instrArray;
    }

    /**
     *Sets the instrArray instance variable to parameter
     * @param instrArray
     */
    public void setInstrArray(ArrayList<String> instrArray) {
        this.instrArray = instrArray;
    }

    /**
     *Returns the unfilterInstrArr instance variable, this is used to set the GUI's Original content text field
     * @return
     */
    public String[] getUnfilterInstrArr() {
        return unfilterInstrArr;
    }

    public void setUnfilterInstrArr(String[] unfilterInstrArr) {
        this.unfilterInstrArr = unfilterInstrArr;
    }
    
    

    /**
     * Takes a string array returns the numerical value stored in the string
     * array
     *
     * @param arr Array that contains tokens from instruction line (Split from command).
     * @return integer array of size two containing address numbers
     */
    private int[] parse(String[] arr) {
        String[] temp = arr[1].trim().split(",");
        int numArr[] = new int[2];
        numArr[0] = Integer.parseInt(temp[0]);
        numArr[1] = Integer.parseInt(temp[1]);    
        
        return numArr;
    }

    /**
     * Takes a string array returns the numerical value stored in the string
     * array
     *
     * @param arr String Array that contains instruction line (Split from command).
     * @return integer array of size three containing address numbers and line
     * number
     */
    private int[] parse2(String[] arr) {
        String[] temp = arr[1].trim().split(",");
        //System.out.println(temp[0]);
        int numArr[] = new int[3];
        numArr[0] = Integer.parseInt(temp[0]);
        numArr[1] = Integer.parseInt(temp[1]);    
        numArr[2] = Integer.parseInt(temp[2]);
        return numArr;
    }
    
    /**
     * Dumps contents of memory and register into a text file
     * 
     * @param memory Array that holds contents of memory addresses
     * @param register Array that holds contents of register addresses
     * @param file String that contains path/fileName for file
     * @throws IOException
     */
    private void performMDump(int[] memory, int[]register,String file)throws IOException{
        MemoryDump progFinished = new MemoryDump();
        progFinished.sucessWriteToFile(memory, register, file);        
    }
    
    /**
     * Dumps contents of memory and registers into a text file given an error occurs.
     * 
     * @param memory Array that holds contents of memory addresses
     * @param register Array that holds contents of register addresses
     * @param lineNum Contains the current line number the instruction is on
     * @param exception String containing what error was thrown
     * @throws IOException
     */
    private void performErrorMDump(int[] memory, int[]register, int lineNum, String exception)throws IOException{
        MemoryDump runtimeError = new MemoryDump();
        runtimeError.failureWriteToFile(memory, register, exception, lineNum, exception);
    }
    
    
}