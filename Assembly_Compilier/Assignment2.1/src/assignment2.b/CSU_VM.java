/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author James Ellerbee
 */
public class CSU_VM {

    /**
     * takes a string array returns the numerical value stored in the string
     * array
     *
     * @param arr
     * @return integer array of size two containing address numbers
     */
    public static int[] parse(String[] arr) {
        char temp;
        int numArr[] = new int[2];
        temp = arr[1].charAt(1);
        numArr[0] = Character.getNumericValue(temp);
        temp = arr[1].charAt(3);
        numArr[1] = Character.getNumericValue(temp);
        return numArr;
    }

    /**
     * takes a string array returns the numerical value stored in the string
     * array
     *
     * @param arr
     * @return integer array of size three containing address numbers and line
     * number
     */
    public static int[] parse2(String[] arr) {
        char temp;
        int numArr[] = new int[3];
        temp = arr[1].charAt(1);
        numArr[0] = Character.getNumericValue(temp);
        temp = arr[1].charAt(3);
        numArr[1] = Character.getNumericValue(temp);
        temp = arr[1].charAt(5);
        numArr[2] = Character.getNumericValue(temp);
        return numArr;
    }

    public static void main(String args[]) throws FileNotFoundException {
        Scanner userInput = new Scanner(System.in);

        int[] memory = new int[64];
        int[] register = {0, 0, 0, 0};

        //populates memory array with 0's
        for (int i = 0; i < 64; i++) {

            memory[i] = 0;
        }
        System.out.println("Type the name of the file.");
        String file = userInput.nextLine();
        CSU_Instruction program = new CSU_Instruction(file);
        ArrayList<String> instrArray = program.getInstrArray();

        String instruction; //used to store one instruction at a time from the instrList         
        for (int i = 0; i < instrArray.size(); i++) {
            instruction = instrArray.get(i);
            if ((String.valueOf(instrArray.get(i).charAt(0)).equals("M"))) {
                String str[] = instruction.split(",");
                //for loop iterates over values stored in temp, 
                //sets memory address at index 0 (string stored in temp[j]) of index j in tempArr with value of index 2 of index j in temp
                for (int j = 0; j < str.length; j++) {
                    if (j == 0) {
                        memory[Integer.parseInt(String.valueOf(str[j].charAt(2)))] = Integer.parseInt(String.valueOf(str[j].charAt(4)));
                    } else {
                        memory[Integer.parseInt(String.valueOf(str[j].charAt(0)))] = Integer.parseInt(String.valueOf(str[j].charAt(2)));
                    }
                }
                instrArray.remove(i);
            } else if (instruction.contains("LOAD")) {
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
                    i = numArr[2];
                }

            } else if (instruction.contains("STOP")) {
                break;

            } else {
                System.out.println(instruction + " at index: " + i);
            }

        }
        //do a successful memory dump

        System.out.println(memory[2]);
    }
}
