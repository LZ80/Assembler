/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assembler;

import UI.GUI;
import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
/**
 *
 * @author Arkai
 */
public class Assembler {

    /**
     * @param args the command line arguments
     */
    
    
    
    public enum tokenType
    {
        NONE,
        REGISTER,
        HL,
        NUMBER,
        MEM
    }
    
    static String[][] s;
    static Hashtable<String, Integer> labels;
    //static Hashtable<String, Symbol> symbols;
    static ArrayList<Instruction> instructions;
    static ArrayList<String> memory;
    static int memDir;
    
    static GUI gui;
    
    public static void main(String[] args)
    {   
        gui = new GUI();        
        int x = -1;
        
        labels = new Hashtable<>();
        instructions = new ArrayList<>();
        memory = new ArrayList<>();
        
        do
        {
            System.out.print("");
            x = gui.ejecutar();
            if(x==1)
            {
                    x=-1;
                    init();
                    ejecutar();
                
                  
                
            }
        }while(x!=0);
    }
    
    public static void init()
    {
        instructions.clear();
        labels.clear();
        //symbols.clear();
    }
    
    public static void ejecutar()
    {
        fillTables();
        for(int i=0; i < instructions.size(); i++)
        {
            parseInstruction(instructions.get(i));
        }
        try
        {
            writeMemory();
        }catch(IOException e)
        {
            System.out.println("ERROR");
        }
    }

    public static void fillTables()
    {
        s = gui.getTable1();
        
        for (int i = 0; i < s.length; i++)
        {                
            Instruction in = new Instruction(s[i][0], s[i][1], s[i][2], s[i][3] );
            instructions.add(in);
            /*if(s[i][0] != null && !s[i][0].equals(""))
            {
                labels.put(s[i][0], in);
            }*/
        }
    } 
    
    public static String parseInstruction(Instruction ins)
    {
        if(ins.getLabel() != null && ins.getLabel() != "")
        {
            labels.put(ins.getLabel(), memDir);
            
        }
        
        System.out.println(ins.getInstruction());
        
        switch(ins.getInstruction())
        {
            case "LD":
                parseLoad(ins);
                break;
            case "ADD":
                parseAdd(ins);
                break;
            case "SUB":
                parseSub(ins);
                break;
            case "J":
                parseJump(ins);
                break;
            default:
                System.out.println("ERROR");
                break;
        }
        
        return "m";
    }

    public static String parseLoad(Instruction ins) 
    {
        String s = "";
        
        tokenType o = recognizePattern(ins.getOperand());        
        tokenType p = recognizePattern(ins.getParameter());
        
        if((o == tokenType.REGISTER || o == tokenType.HL) && (p == tokenType.REGISTER || p == tokenType.HL))
        {
            
            s = s + "01" + parseRegister(ins.getOperand()) + parseRegister(ins.getParameter());
            memory.add(s);
            memDir++;
        }
        else if((o == tokenType.REGISTER || o == tokenType.HL) && p == tokenType.NUMBER)
        {
            s = s + "00" + parseRegister(ins.getOperand()) + "110";
            
            memory.add(s);
            memory.add(Integer.toBinaryString(Integer.parseInt(ins.getParameter(), 16)));
            memDir += 2;
        }
        else if(o == tokenType.REGISTER  && p == tokenType.MEM)
        {
            s = s + Integer.toBinaryString(Integer.parseInt("3A",16));
            
            memory.add(s);
            memory.add(Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(1,3),16)));
            memory.add(Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(3,5),16)));
        }
        
        System.out.println(memory);
        return "";
    }

    public static String parseAdd(Instruction ins) 
    {
        String instruccion;
        
        tokenType o = recognizePattern(ins.getOperand());        
        tokenType p = recognizePattern(ins.getParameter());
        
        return "";
    }

    public static String parseSub(Instruction ins) 
    {
        String instruccion;
        
        tokenType o = recognizePattern(ins.getOperand());        
        tokenType p = recognizePattern(ins.getParameter());
        
        return "";
    }

    public static String parseJump(Instruction ins) 
    {
        String instruccion;
        
        tokenType o = recognizePattern(ins.getOperand());        
        tokenType p = recognizePattern(ins.getParameter());
        
        return "";
    }
    
    public static tokenType recognizePattern(String i)
    {
        System.out.println(i);
        if(i.length() == 1)
        {
            char first = i.charAt(0);
        
            if((65 <= first && first <= 69) || first==72 || first==76)
            {
                return tokenType.REGISTER;
            }
        }    
        else if(i.length() == 2)
        {
            if(((48 <= i.charAt(0) && i.charAt(0) <=57) || (65 <= i.charAt(0) && i.charAt(0) <= 70)) && ((48 <= i.charAt(1) && i.charAt(1) <=57) || (65 <= i.charAt(1) && i.charAt(1) <= 70)))
            {
                return tokenType.NUMBER;
            }
        }
        else if(i.length() == 4)
        {
            if(i.charAt(0) == '(' && i.charAt(3) == ')')
            {
                if(i.charAt(1) == 'H' && i.charAt(2) == 'L')
                {
                    return tokenType.HL;
                }
            }
        }
        else if(i.length() == 6)
        {   
            return tokenType.MEM;               
        }
        return tokenType.NONE;
    }
    
    public static String parseRegister(String s)
    {
        switch(s)
        {
            case "A":
                return "111";
            case "B":
                return "000";
            case "C":
                return "001";
            case "D":
                return "010";
            case "E":
                return "011";
            case "H":
                return "100";
            case "L":
                return "101";
            case "HL":
                return "110";
        }
        
        return "";
    }
    
    
    
    public static void writeMemory () throws IOException
    {
        String mem = "xx";
        
        
        for(int i=0; i < memory.size(); i++)
        {
            
            String s = Integer.toHexString(Integer.parseInt(memory.get(i),2));
            
            if(s.length() == 2)
            {   
                
                mem = mem + s + "xx";
            }
            else
            {
                mem = mem + "0" + s + "xx";
            }
        }
        
        BufferedWriter writer = new BufferedWriter(new FileWriter("./memory.txt"));
        
        writer.write(mem);
        
        writer.close();
    }
    
}
