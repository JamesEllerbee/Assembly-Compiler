/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 *
 * @author James Ellerbee
 */
public class MemoryDump {

    private String pathName;
    final boolean appendToFile = true;
    long timeInMillis;

    /**
     *Constructs a MemoryDump object, setting the instance variables required to write to a file
     */
    public MemoryDump() {
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateObj = new Date();
        String currentDate = df.format(dateObj);
        try {
            dateObj = df.parse(currentDate);
        } catch (ParseException e) {
            System.out.println("Parse Exception");
        }
        this.timeInMillis = dateObj.getTime();
        this.pathName = String.format("CSU_VM_success_[%s].txt", timeInMillis);

    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    /**
     *Dumps the contents of memory and register into a text file in the event the program successfully executed the program
     * 
     * @param memory
     * @param register
     * @param file
     * @throws IOException
     */
    public void sucessWriteToFile(int[] memory, int[] register, String file) throws IOException {
        FileWriter write = new FileWriter(pathName, appendToFile);
        PrintWriter printTo = new PrintWriter(write);
        printTo.println(String.format("Program Executed Successfully: %s", file));
        printTo.println("Memory and Registers Dump:");
        printTo.print("M: ");
        for (int i = 0; i < memory.length; i++) {
            if (i % 8 == 0) {
                printTo.println();
            }
            printTo.print(i + " = " + memory[i] + ", ");
        }
        printTo.println();
        printTo.print("R: ");
        for (int i = 0; i < register.length; i++) {
            printTo.print(i + "=" + register[i] + ", ");
        }
        printTo.close();
    }

    /**
     *Dumps the contents of memory and register into a text file in the event the program throws an exception
     * @param memory
     * @param register
     * @param file
     * @param lineNum
     * @param exception
     * @throws IOException
     */
    public void failureWriteToFile(int[] memory, int[] register, String file, int lineNum, String exception) throws IOException {
        FileWriter write = new FileWriter(pathName, appendToFile);
        PrintWriter printTo = new PrintWriter(write);
        printTo.println(String.format("Program Executed Successfully: %s", file));
        printTo.println("Memory and Registers Dump");
        printTo.print("M: ");
        for (int i = 0; i < memory.length; i++) {
            printTo.print(i + "=" + memory[i] + ", ");
        }
        printTo.println();
        printTo.print("R: ");
        for (int i = 0; i < register.length; i++) {
            if (i == 30) {
                printTo.println();
            }
            printTo.print(i + "=" + register[i] + ", ");
        }
        printTo.println(String.format("\nStopped execution at line: %d", lineNum));
        printTo.print(String.format("Exception thrown: %s", exception));
        printTo.close();
    }

    /**
     *Writes to a file informing the user of a complier issue 
     * @param file
     * @param exception
     * @throws IOException
     */
    public void compilierWriteToFile(String file, String exception) throws IOException {
        FileWriter write = new FileWriter(pathName, appendToFile);
        PrintWriter printTo = new PrintWriter(write);
        printTo.println(String.format("Program Compiled with errors: %s", file));
        printTo.println(String.format("Exception: %s", exception));
        printTo.close();
    }
}
