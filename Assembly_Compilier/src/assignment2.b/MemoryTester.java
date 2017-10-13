/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.io.IOException;
/**
 *
 * @author James Ellerbee
 */
public class MemoryTester {
    public static void main(String[] arg)throws IOException {
        int[] memory = new int[64];
        int[] register = {0,0,0,0};
        String file = "test";
        MemoryDump dump = new MemoryDump();
        dump.sucessWriteToFile(memory, register, file);           
    }
}
