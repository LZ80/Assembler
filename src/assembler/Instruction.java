/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assembler;

/**
 *
 * @author Arkai
 */
public class Instruction 
{
    private String label;
    private String instruction;
    private Symbol symbol;
    
    private String Address;
    private String lowAddress;
    private String highAddress;
    
    private String sValue;
    private String finalValue;
    
    private String finalLowValue;
    private String finalHighValue;
    
    public Instruction(String l, String i, Symbol s)
    {
        label = l;
        instruction = i;
        symbol = s;
        
        switch(instruction)
        {
            case "LOADA":
                sValue = "000";
                break;
            case "STOREA":
                sValue = "001";
                break;
            case "ADDA":
                sValue = "010";
                break;
            case "SUBA":
                sValue = "011";
                break;
            case "INA":
                sValue = "100";
                break;
            case "OUTA":
                sValue = "101";
                break;
            case "JZ":
                sValue = "110";
                break;
            case "JPOS":
                sValue = "111";
                break;
        }
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }
    
    public void setAddress(int a) 
    {
        this.Address = Integer.toBinaryString(a);
    }

    public String getsValue() {
        return sValue;
    }

    public void setsValue(String sValue) {
        this.sValue = sValue;
    }

    public String getFinalValue() {
        return finalValue;
    }

    public void setFinalValue(String finalValue) {
        this.finalValue = finalValue;
    }
    
    public String getLowAddress() {
        return lowAddress;
    }

    public void setLowAddress(String lowAddress) {
        this.lowAddress = lowAddress;
    }
    
    public void setLowAddress(int a) {
        this.lowAddress = Integer.toBinaryString(a);
    }

    public String getHighAddress() {
        return highAddress;
    }

    public void setHighAddress(int a) {
         this.highAddress = Integer.toBinaryString(a);
    }
    
    public String getLowFinalValue() {
        return finalLowValue;
    }

    public void setLowFinalValue(String finalValue) {
        this.finalLowValue = finalValue;
    }

    public String getFinalHighValue() {
        return finalHighValue;
    }

    public void setFinalHighValue(String finalHighValue) {
        this.finalHighValue = finalHighValue;
    }
    
    public void makeValue1()
    {
        if(symbol != null)
        {
            finalValue = sValue + symbol.getAddress();
            System.out.println(symbol.getSymbol());
        }
        else
        {
            finalValue = sValue + "00000";
        }
        
        System.out.println(finalValue);
    }
    
    public void makeValue2()
    {
        finalHighValue = sValue + "00000";
        
        if(symbol != null)
        {
            finalLowValue = symbol.getAddress();
        }
        else
        {
            finalLowValue = "00000000";
        }
        System.out.println(finalLowValue);
    }
}
