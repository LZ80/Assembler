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
    static Hashtable<String, Symbol> symbols;
    static ArrayList<Instruction> instructions;
    static int a;
    
    static GUI gui;
    
    public static void main(String[] args)
    {   
        gui = new GUI();        
        int x = -1;
        
        labels = new Hashtable<>();
        symbols = new Hashtable<>();
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
        symbols.clear();
        a=0;
    }
    
    public static void ejecutar()
    {
        fillTables();
        
        String version = gui.getMode();
        
        switch(version)
        {
            case "GPC V1":
                ejecutar1();
                break;
            case "GPC V2":
                ejecutar2();
                break;
            case "GPC V3":
                break;
            case "GPC V4":
                break;
        }
    }
    
    public static void ejecutar1()
    {
        generateAddresses();
        getInitialValues();
        
        for (int i = 0; i < instructions.size(); i++) 
        {
            instructions.get(i).makeValue1();
        }
        generateFinalTable1();
    }
    
    public static void ejecutar2()
    {
        generateAddresses2();
        getInitialValues();
        
         for (int i = 0; i < instructions.size(); i++) 
        {
            instructions.get(i).makeValue2();
        }
         generateFinalTable2();
    }
    
    public static void fillTables()
    {
        Symbol sy = null;
        s = gui.getTable1();
        
        for (int i = 0; i < s.length; i++)
        {   
            sy = null;
            if(s[i][1] != null && !s[i][1].equals(""))
            {
                if(s[i][2] != null && !s[i][2].equals("") && !symbols.containsKey(s[i][2]))
                {   
                    sy =  new Symbol(s[i][2]);
                    symbols.put(sy.getSymbol(), sy);
                }
                else if(s[i][2] != null)
                {
                    sy = symbols.get(s[i][2]);
                }
                Instruction in = new Instruction(s[i][0], s[i][1], sy);
                instructions.add(in);
                if(s[i][0] != null && !s[i][0].equals(""))
                {
                    labels.put(s[i][0], in);
                }
            }
        }
    } 
    
    public static void generateAddresses()
    {
        for (int i = 0; i < instructions.size(); i++) 
        {
            instructions.get(i).setAddress(a);
            a++;
        }
        
        Set<String> set = symbols.keySet();
        for(String s : set)
        {
            if(!labels.containsKey(s))
            {
                symbols.get(s).setAddress(a);
                a++;
            }
            else
            {
                symbols.get(s).setAddress(labels.get(s).getAddress());
            }
        }
    }
    
    public static void generateAddresses2()
    {
        for (int i = 0; i < instructions.size(); i++) 
        {
            instructions.get(i).setLowAddress(a);
            a++;
            instructions.get(i).setHighAddress(a);
            a++;
        }
        
        Set<String> set = symbols.keySet();
        for(String s : set)
        {
            if(!labels.containsKey(s))
            {
                symbols.get(s).setAddress(a);
                a++;
            }
            else
            {
                symbols.get(s).setAddress(labels.get(s).getLowAddress());
            }
        }
    }
    
    public static void getInitialValues()
    {
        Set<String> set = symbols.keySet();
        String[] sym = new String[set.size()];
        int i = 0;
        for(String s : set)
        {
            sym[i] = s;
            i++;
        }
        
        String[] inValues = gui.getIniValues(sym);
        int j=0;
        for(String s : set)
        {
            if(inValues[j] != null)
            {
                symbols.get(s).setValue(Integer.parseInt(inValues[j]));
            }
            j++;
        }        
    }
    
    public static void generateFinalTable1()
    {
        int aux = instructions.size();
        int aux2 = symbols.size();
        int aux3 = labels.size();
        
        Object[][] finalTable = new String[aux + aux2 - aux3][2];
        
        for (int i = 0; i < aux; i++)
        {
            finalTable[i][0] = String.format("%02X", Integer.parseInt(instructions.get(i).getAddress(), 2));
            finalTable[i][1] = String.format("%02X", Integer.parseInt(instructions.get(i).getFinalValue() ,2));
        }
        int i = 0;
        int j = aux;
        
        while(i < aux2) 
        {
            if(!labels.containsKey(symbols.keySet().toArray()[i]))
            {
                finalTable[j][0] = String.format("%02X", Integer.parseInt(symbols.get(symbols.keySet().toArray()[i]).getAddress(), 2));
                finalTable[j][1] = String.format("%02X", Integer.parseInt(symbols.get(symbols.keySet().toArray()[i]).getValue(), 2));
                j++;
            }
            i++;
        }
        gui.fillTable2(finalTable);
    }
    
    public static void generateFinalTable2()
    {
        int aux = instructions.size();
        int aux2 = symbols.size();
        int aux3 = labels.size();
        
        Object[][] finalTable = new String[aux*2 + aux2 - aux3][2];
        int k=0;
        
        for (int i = 0; i < aux*2; i+=2)
        {
            finalTable[i][0] = String.format("%02X", Integer.parseInt(instructions.get(k).getLowAddress(), 2));
            finalTable[i][1] = String.format("%02X", Integer.parseInt(instructions.get(k).getLowFinalValue() ,2));
            finalTable[i+1][0] = String.format("%02X", Integer.parseInt(instructions.get(k).getHighAddress(), 2));
            finalTable[i+1][1] = String.format("%02X", Integer.parseInt(instructions.get(k).getFinalHighValue() ,2));
            k++;
        }
        
        int i = 0;
        int j = aux*2;
        
        while(i < aux2) 
        {
            if(!labels.containsKey(symbols.keySet().toArray()[i]))
            {
                finalTable[j][0] = String.format("%02X", Integer.parseInt(symbols.get(symbols.keySet().toArray()[i]).getAddress(), 2));
                finalTable[j][1] = String.format("%02X", Integer.parseInt(symbols.get(symbols.keySet().toArray()[i]).getValue(), 2));
                j++;
            }
            i++;
        }
        gui.fillTable2(finalTable);
    }
}
