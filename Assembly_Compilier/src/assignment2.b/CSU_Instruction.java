/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

/**
 *
 * @author James Ellerbee
 */
import java.io.*;
import java.util.*;

public final class CSU_Instruction implements Instruction {

    final String[] VAILD_OPERATIONS = {"LOAD", "STOR", "ADD", "SUB", "MUL", "DIV", "DIV", "MOD", "CMP", "GRT", "LSS", "STOP", "M:"};
    private String fileName;
    private String instructions;
    private String[] instrArr;
    private ArrayList<String> instrArray;
    private boolean error;

    public CSU_Instruction() {
        fileName = "";
        instructions = "";
        instrArr = new String[0];
        instrArray = new ArrayList();
        error = false;
    }

    
    public CSU_Instruction(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
        int i = 0;
        File instrFile = new File(this.fileName);

        try {
            Scanner in = new Scanner(instrFile);
            while (in.hasNext()) {
                if (i == 0) {
                    instructions = in.nextLine();
                    instructions += "\n";
                }
                else{
                    instructions += in.nextLine();
                    instructions += "\n";
                }
                i++;
            }
            try {
                instrArr = instructions.split("\n");

                List<String> instrList = Arrays.asList(instrArr);
                //create an array list and populate with elements from List in order to perform operations on it
                this.instrArray = new ArrayList();
                instrList.forEach(this.instrArray::add);

                removeElements(0);
                if(instrArray.size() > 100){
                    performDump("ERROR: More than 100 instruction lines");
                    error = true;
                }
                int k = 0;
                for (String element : instrArray) {
                    if (inVaildOperations(element) != true) {
                        //Error Dump
                        performDump(String.format("Operation not understood at line: %d", k - 1));
                        error = true;
                        
                    }
                    k++;
                }

            } catch (IOException e) {
                System.out.println("ERROR; file not found.");
                error = true;
 
            }
        } catch (NullPointerException e) {
            try{
                performDump("Empty File");
                error = true;
            }catch(IOException f){
                System.out.println("Unexpected error");
            }
        }
    }
    
    private void performDump(String exception)throws IOException{
        MemoryDump progFinished = new MemoryDump();
        progFinished.compilierWriteToFile(fileName, exception);          
    }
    
    private String removeElements(int index) {
        try {
            if (instrArray.get(index).charAt(0) == '#') {
                instrArray.remove(index);
                return removeElements(index + 1);
            } else {
                return removeElements(index + 1);
            }
        } catch (IndexOutOfBoundsException e) {
            return "Done";//basecase
        }
    }

    private boolean inVaildOperations(String instruction) {
        for (String element : VAILD_OPERATIONS) {
            if (instruction.contains(element) == true) {
                return true;
            }
        }
        return false;
    }

    /**
     *Returns fileName instance variable for CSU_Instruction
     * @return String fileName
     */
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String[] getInstrArr() {
        return instrArr;
    }

    public void setInstrArr(String[] instrArr) {
        this.instrArr = instrArr;
    }

    public ArrayList<String> getInstrArray() {
        return instrArray;
    }

    public void setInstrArray(ArrayList<String> instrArray) {
        this.instrArray = instrArray;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
    

    /**
     *"Loads" the contents of specified memory address into specified register address
     * @param memAddress
     * @param registerAddress
     * @param memArr
     * @param registerArr
     */
    @Override
    public void Load(int memAddress, int registerAddress, int[] memArr, int[] registerArr) {
        registerArr[registerAddress] = memArr[memAddress];
    }

    /**
     *"Stores" the contents of specified register address into specified memory address
     * @param memAddress
     * @param registerAddress
     * @param memArr
     * @param registerArr
     */
    @Override
    public void Store(int memAddress, int registerAddress, int[] memArr, int[] registerArr) {
        memArr[memAddress] = registerArr[registerAddress];
    }

    /**
     *Adds register1 with register2 and stores it into register1
     * @param register1
     * @param register2
     * @param registerArr
     */
    @Override
    public void Add(int register1, int register2, int[] registerArr) {
        registerArr[register1] = registerArr[register1] + registerArr[register2];

    }

    /**
     *Subtracts register2 from register1 and stores it into register1
     * @param register1
     * @param register2
     * @param registerArr
     */ 
    @Override
    public void Sub(int register1, int register2, int[] registerArr) {
        registerArr[register1] = registerArr[register1] - registerArr[register2];
    }

    /**
     *Multiplies register1 with register2 and stores result in register1
     * @param register1
     * @param register2
     * @param registerArr
     */
    @Override
    public void Mul(int register1, int register2, int[] registerArr) {
        registerArr[register1] = registerArr[register1] * registerArr[register2];
    }

    /**
     *Divides register2 from register1 and stores result in register1
     * @param register1
     * @param register2
     * @param registerArr
     */
    @Override
    public void Div(int register1, int register2, int[] registerArr){
        try {
            registerArr[register1] = registerArr[register1] / registerArr[register2];
        } catch (ArithmeticException e) {

            try{
                performDump("ERROR: Divde by zero.");
            }catch(IOException f){
                System.out.println("Unexpected error: Error writing to file\n" + String.valueOf(f));
            }
        }
    }

    /**
     *Takes the Modulus of register2 from register1 and stores it in register1
     * @param register1
     * @param register2
     * @param registerArr
     */
    @Override
    public void Mod(int register1, int register2, int[] registerArr) {

        registerArr[register1] = registerArr[register1] % registerArr[register2];
    }

    /**
     *Compares register1 equal to register2
     * @param register1
     * @param register2
     * @param registerArr
     * @return boolean of registerArr[register1] == registerArr[register2]
     */
    @Override
    public boolean Compare(int register1, int register2, int[] registerArr) {
        return registerArr[register1] == registerArr[register2];
    }

    /**
     *Compares register1 greater than register2
     * @param register1
     * @param register2
     * @param registerArr
     * @return boolean of registerArr[register1] greater than registerArr[register2]
     */
    @Override
    public boolean GRT(int register1, int register2, int[] registerArr) {
        return registerArr[register1] > registerArr[register2];
    }

    /**
     *Compares register1 less than register2
     * @param register1
     * @param register2
     * @param registerArr
     * @return boolean of registerArr[register1] less than registerArr[register2]
     */
    @Override
    public boolean LSS(int register1, int register2, int[] registerArr) {
        return registerArr[register1] < registerArr[register2];
    }
}