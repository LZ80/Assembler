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
public class Symbol 
{
    private String symbol;
    private String address;
    private String value = "0";
    
    public Symbol(String s)
    {
        symbol = s;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        while(this.address.length() < 5)
        {
            this.address = "0" + this.address;
        }
    }
    
    public void setAddress(int a) {
        this.address = Integer.toBinaryString(a);
        
        while(this.address.length() < 5)
        {
            this.address = "0" + this.address;
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String v) 
    {
        this.value = String.format("%02X", Byte.parseByte(v));
    }
    
    public void setValue(int v) {
        
        this.value = Integer.toBinaryString(v);
    }
}