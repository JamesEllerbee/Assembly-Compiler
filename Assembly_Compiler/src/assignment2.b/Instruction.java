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
public interface Instruction {
    
    //Memory instructions

    /**
     *Loads the content of memAddress into RegisterAddress
     * @param memAddress
     * @param registerAddress
     * @param memArr
     * @param registerArr
     */
    public void Load(int memAddress, int registerAddress, int[] memArr, int[] registerArr);
    
    /**
     *Loads the content of RegisterAddress into the MemAddress
     * @param memAddress
     * @param registerAddress
     * @param memArr
     * @param registerArr
     */
    public void Store(int memAddress, int registerAddress, int[] memArr, int[] registerArr );
   
    //Arithmetic
    public void Add(int register1, int register2, int[] registerArr);
    
    public void Sub(int register1, int register2, int[] registerArr);
    
    public void Mul(int register1, int register2, int[] registerArr);
    
    public void Div(int register1, int register2, int[] registerArr);
    
    public void Mod(int register1, int register2, int[] registerArr);
    
    //Logic
    public boolean Compare(int register1, int register2, int[] registerArr);
    
    public boolean GRT(int register1, int register2, int[] registerArr);
    
    public boolean LSS(int register1, int register2, int[] registerArr);
   
    
}
