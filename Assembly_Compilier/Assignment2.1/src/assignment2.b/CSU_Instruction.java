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
    
    final String[] VAILD_OPERATIONS = {"LOAD", "SOTR", "ADD", "SUB", "MUL", "DIV", "DIV", "MOD", "CMP", "GRT", "LSS", "STOP"};
    private String fileName;
    private String instructions;
    private String[] instrArr;
    private ArrayList<String> instrArray;

    public CSU_Instruction() {
        fileName = "";
        instructions = "";
        instrArr = new String[0];
    }

    public CSU_Instruction(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
        int i = 0;
        File instrFile = new File(this.fileName);

        try {
            Scanner in = new Scanner(instrFile);
            while (in.hasNext()) {
                if(i == 0){
                    instructions = in.nextLine();
                    instructions += "\n";
                }
                else if(i > 100){
                //TODO: add code for a memory dump
                }
                else if(i < 100){
                instructions += in.nextLine();
                instructions += "\n";
                }                    
                else{
                    System.out.println("Something went wrong...");
                }
                i++;
            }
        } catch (IOException e) {
            System.out.println("ERROR; file not found.");
        }

        instrArr = instructions.split("\n");
        List<String> instrList = Arrays.asList(instrArr);
        //create an array list and populate with elements from List in order to perform operations on it
        instrArray= new ArrayList();
        instrList.forEach(instrArray::add);
        for(String instr: instrArray){
            if( !inVaildOperations(instr) || instr.contains("#") || instr.contains("M") == true){
                boolean remove = instrArray.remove(instr);
            }
        }
    }
    
    public final boolean inVaildOperations(String instruction){
        for(String element: VAILD_OPERATIONS){
            if (instruction.contains(element) == true){
                return true;
            }
        }
        return false;        
    }
    

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
    
    @Override
    public void Load(int memAddress, int registerAddress, int[] memArr, int[] registerArr) {
        registerArr[registerAddress] = memArr[memAddress];
    }

    @Override
    public void Store(int memAddress, int registerAddress, int[] memArr, int[] registerArr) {
        memArr[memAddress] = registerArr[registerAddress];
    }

    @Override
    public void Add(int register1, int register2, int[] registerArr) {
        registerArr[register1] = registerArr[register1] + registerArr[register2];
    }

    @Override
    public void Sub(int register1, int register2, int[] registerArr) {
        registerArr[register1] = registerArr[register1] - registerArr[register2];
    }

    @Override
    public void Mul(int register1, int register2, int[] registerArr) {
        registerArr[register1] = registerArr[register1] * registerArr[register2];
    }

    @Override
    public void Div(int register1, int register2, int[] registerArr) {
        try{
            registerArr[register1] = registerArr[register1] / registerArr[register2];
        }catch(ArithmeticException e){
           //do an error dump
        }
    }
    
    @Override
    public void Mod(int register1, int register2, int[] registerArr){
        
        registerArr[register1] = registerArr[register1] % registerArr[register2];
    }
    
    @Override
    public boolean Compare(int register1, int register2, int[] registerArr){
        return registerArr[register1] == registerArr[register2];
    }
    
    @Override
    public boolean GRT(int register1, int register2, int[] registerArr){
        return registerArr[register1] > registerArr[register2];
    }
    
    @Override
    public boolean LSS(int register1, int register2, int[] registerArr){
        return registerArr[register1] < registerArr[register2];
    }
}
