/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assembler;

import UI.GUI;
import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import java.util.*;
/**
 *
 * @author Arkai
 */
public class Assembler {

    /**
     * @param args the command line arguments
     */
    
    static String[][] s;
    static Hashtable<String, Instruction> labels;
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
        
        do
        {
            System.out.print("");
            x = gui.ejecutar();
            if(x==1)
            {
                try{
                    x=-1;
                    init();
                    ejecutar();
                }catch(Exception e)
                {
                    printStackTrace();
                    gui.error();
                }
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
        for(int i=0; i<instructions.size(); i++)
        {
            parseInstruction(instructions.get(i));
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
        String m;
        
        switch(ins.getInstruction())
        {
            case "LD":
                m = parseLoad(ins);
                break;
            case "ADD":
                m = parseAdd(ins);
                break;
            case "SUB":
                m = parseSub(ins);
                break;
            case "J":
                m = parseJump(ins);
                break;
            default:
                System.out.println("ERROR");
                break;
        }   
        return "m";
    }

    public static String parseLoad(Instruction ins) 
    {
        recognizePattern(ins.getOperand());        
        
        return "";
    }

    public static String parseAdd(Instruction ins) 
    {
        return "";
    }

    public static String parseSub(Instruction ins) 
    {
        return "";
    }

    public static String parseJump(Instruction ins) 
    {
        return "";
    }
    
    
    public static int recognizePattern(String i)
    {
        return 0;
    }
    
    
    
    
}
