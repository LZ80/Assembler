/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assembler;

import UI.GUI;
import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
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

    public enum tokenType {
        NONE, REGISTER, HL, NUMBER, MEM, LABEL, IXP, IYP, D_NUMBER, IX, IY
    }

    static String s;
    static Hashtable<String, Integer> labels;
    // static Hashtable<String, Symbol> symbols;
    static ArrayList<Instruction> instructions;
    static ArrayList<String> memory;
    static int memDir = 0;

    static String fileName = "";
    
    static GUI gui;

    public static void main(String[] args) {
        gui = new GUI();
        String option = "no";

        labels = new Hashtable<>();
        instructions = new ArrayList<>();
        memory = new ArrayList<>();

        do {
            System.out.print("");
            option = gui.getOption();
            switch(option){
                case "ejecutar":
                    option="no";
                    init();
                    ejecutar();
                    break;
                case "new":
                    option="no";
                    newFile();
                    break;
                case "open":
                    option="no";
                    openFile("./");
                    break;
                case "save":
                    option="no";
                    saveFile();
                    break;
                case "saveAs":
                    option="no";
                    saveFileAs();
                    break;
                case "openExample":
                    option="no";
                    openFile("./Examples/");;
                    break;
            }
        } while (option != "exit");
    }

    public static void init() {
        instructions.clear();
        labels.clear();
        memory.clear();
        memDir = 0;
        // symbols.clear();
    }

    public static void ejecutar() {
        fillTables();
        
        System.out.println("----------------------PARSER---------------------");
        for (int i = 0; i < instructions.size(); i++) {
            if (instructions.get(i).getLabel() != null && instructions.get(i).getLabel() != "") {
                labels.put(instructions.get(i).getLabel(), -1);
            }
        }

        doLabels();

        for (int i = 0; i < instructions.size(); i++) {
            parseInstruction(instructions.get(i));
        }
        try {
            writeMemory();
        } catch (IOException e) {
            System.out.println("ERROR");
        }
    }

    public static void fillTables() {
        System.out.println("FILL INSTRUCTIONS");
        
        s = gui.getProgram();
        
        String[] ins = s.split("\n");
        
        for(String instruction : ins)
        {
            if(instruction.charAt(instruction.length()-1) == 13)
            {
                instruction = instruction.substring(0, instruction.length()-1);
            }
            String[] aux = instruction.split(",");
            String[] aux2 = aux[0].split(" ");
            String label = null, command = null, operator = null, parameter = null;
            
            if(aux2.length == 3)
            {
                label=aux2[0];
                command=aux2[1];
                operator=aux2[2];
            }
            else if(aux2.length == 2)
            {
                if(isCommand(aux2[0]))
                {
                    command=aux2[0];
                    operator=aux2[1];
                }
                else
                {
                    label=aux2[0];
                    command=aux2[1];
                }
            }
            else
            {
                command=aux2[0];
            }
            
            if(aux.length == 2)
            {
                parameter=aux[1];   
            }
            
            Instruction in = new Instruction(label, command, operator, parameter);
            instructions.add(in);
            
            System.out.println(in);
        }
        
        return;
        
        /*for (int i = 0; i < s.length; i++) {
            Instruction in = new Instruction(s[i][0], s[i][1], s[i][2], s[i][3]);
            instructions.add(in);
            /*
             * if(s[i][0] != null && !s[i][0].equals("")) { labels.put(s[i][0], in); }
             */
        
    }

    public static void parseInstruction(Instruction ins) {
        System.out.print(ins + ": ");
        switch (ins.getInstruction()) {
        case "LD":
            parseLoad(ins);
            break;
        case "ADD":
            parseAdd(ins);
            break;
        case "SUB":
            parseSub(ins);
            break;
        case "JP":
            parseJump(ins);
            break;
        case "CP":
            parseCompare(ins);
            break;
        case "HALT":
            parseHalt(ins);
            break;
        default:
            System.out.println("Instruccion no encontrada. Line: " + Integer.toString(memDir + 1));
            break;
        }

        return;
    }

    public static String parseLoad(Instruction ins) {
        
        String[] s = new String[4];
        
        for(int i=0; i<4; i++)
        {
            s[i] = "";
        }
        
        tokenType o = recognizePattern(ins.getOperand());
        tokenType p = recognizePattern(ins.getParameter());
        
        System.out.println(o + " " + p);

        if ((o == tokenType.REGISTER || o == tokenType.HL) && (p == tokenType.REGISTER || p == tokenType.HL)) {

            s[0] = s[0] + "01" + parseRegister(ins.getOperand()) + parseRegister(ins.getParameter());
            
            memory.add(s[0]);
            memDir++;
            
        } else if ((o == tokenType.REGISTER || o == tokenType.HL) && p == tokenType.NUMBER) {
            
            s[0] = s[0] + "00" + parseRegister(ins.getOperand()) + "110";
            s[1] = s[1] + Integer.toBinaryString(Integer.parseInt(ins.getParameter(), 16));
            
            memory.add(s[0]);
            memory.add(s[1]);
            memDir += 2;
            
        } else if (o == tokenType.REGISTER && p == tokenType.MEM) {
            
            s[0] = s[0] + Integer.toBinaryString(Integer.parseInt("3A", 16));
            s[1] = s[1] + Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(3, 5), 16));
            s[2] = s[2] + Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(1, 3), 16));
            
            memory.add(s[0]);
            memory.add(s[1]);
            memory.add(s[2]);
            memDir += 3;
        } else if (o == tokenType.REGISTER && p == tokenType.LABEL) {

            int m = labels.get(ins.getParameter());
            String a = String.format("%04X", m);
            
            s[0] = s[0] + Integer.toBinaryString(Integer.parseInt("3A", 16));
            s[1] = s[1] + Integer.toBinaryString(Integer.parseInt(a.substring(2, 4), 16));
            s[2] = s[2] + Integer.toBinaryString(Integer.parseInt(a.substring(0, 2), 16));
            
            memory.add(s[0]);
            memory.add(s[1]);
            memory.add(s[2]);
            memDir += 3;
        }
        else if(o == tokenType.REGISTER && p == tokenType.IXP)
        {
            s[0] = s[0] + Integer.toBinaryString(Integer.parseInt("DD", 16));
            s[1] = s[1] + "01" + parseRegister(ins.getOperand()) + "110";
            s[2] = s[2] + Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(4,6), 16));
            
            memory.add(s[0]);
            memory.add(s[1]);
            memory.add(s[2]);
            memDir += 3;
        }
        else if(o == tokenType.REGISTER && p == tokenType.IYP)
        {
            s[0] = s[0] + Integer.toBinaryString(Integer.parseInt("FD", 16));
            s[1] = s[1] + "01" + parseRegister(ins.getOperand()) + "110";
            s[2] = s[2] + Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(4,6), 16));
            
            memory.add(s[0]);
            memory.add(s[1]);
            memory.add(s[2]);
            memDir += 3;
        }
        else if(o == tokenType.IXP && p == tokenType.REGISTER)
        {
            s[0] = s[0] + Integer.toBinaryString(Integer.parseInt("DD", 16));
            s[1] = s[1] + "01" +  "110" + parseRegister(ins.getParameter());
            s[2] = s[2] + Integer.toBinaryString(Integer.parseInt(ins.getOperand().substring(4,6), 16));
            
            memory.add(s[0]);
            memory.add(s[1]);
            memory.add(s[2]);
            memDir += 3;
        }
        else if(o == tokenType.IYP && p == tokenType.REGISTER)
        {
            s[0] = s[0] + Integer.toBinaryString(Integer.parseInt("FD", 16));
            s[1] = s[1] + "01" +  "110" + parseRegister(ins.getOperand());
            s[2] = s[2] + Integer.toBinaryString(Integer.parseInt(ins.getOperand().substring(4,6), 16));
            
            memory.add(s[0]);
            memory.add(s[1]);
            memory.add(s[2]);
            memDir += 3;
        }
        else if(o == tokenType.IXP && p == tokenType.NUMBER)
        {
            s[0] = s[0] + Integer.toBinaryString(Integer.parseInt("DD", 16));
            s[1] = s[1] + Integer.toBinaryString(Integer.parseInt("36", 16));
            s[2] = s[2] + Integer.toBinaryString(Integer.parseInt(ins.getOperand().substring(4,6), 16));
            s[3] = s[3] + Integer.toBinaryString(Integer.parseInt(ins.getParameter(),16));
            
            memory.add(s[0]);
            memory.add(s[1]);
            memory.add(s[2]);
            memory.add(s[3]);
            memDir += 4;
        }
        else if(o == tokenType.IYP && p == tokenType.NUMBER)
        {
            s[0] = s[0] + Integer.toBinaryString(Integer.parseInt("FD", 16));
            s[1] = s[1] + Integer.toBinaryString(Integer.parseInt("36", 16));
            s[2] = s[2] + Integer.toBinaryString(Integer.parseInt(ins.getOperand().substring(4,6), 16));
            s[3] = s[3] + Integer.toBinaryString(Integer.parseInt(ins.getParameter(),16));
            
            memory.add(s[0]);
            memory.add(s[1]);
            memory.add(s[2]);
            memory.add(s[3]);
            memDir += 4;
        }
        else if(o == tokenType.IX && p == tokenType.D_NUMBER)
        {
            s[0] = s[0] + Integer.toBinaryString(Integer.parseInt("DD", 16));
            s[1] = s[1] + Integer.toBinaryString(Integer.parseInt("21", 16));
            s[2] = s[2] + Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(2), 16));
            s[3] = s[3] + Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(0,2),16));
            
            memory.add(s[0]);
            memory.add(s[1]);
            memory.add(s[2]);
            memory.add(s[3]);
            memDir += 4;
        }
        else if(o == tokenType.IY && p == tokenType.D_NUMBER)
        {
            s[0] = s[0] + Integer.toBinaryString(Integer.parseInt("FD", 16));
            s[1] = s[1] + Integer.toBinaryString(Integer.parseInt("21", 16));
            s[2] = s[2] + Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(2), 16));
            s[3] = s[3] + Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(0,2),16));
            
            memory.add(s[0]);
            memory.add(s[1]);
            memory.add(s[2]);
            memory.add(s[3]);
            memDir += 4;
        }
        else if(o == tokenType.IX && p == tokenType.MEM)
        {
            s[0] = s[0] + Integer.toBinaryString(Integer.parseInt("DD", 16));
            s[1] = s[1] + Integer.toBinaryString(Integer.parseInt("2A", 16));
            s[2] = s[2] + Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(3,5), 16));
            s[3] = s[3] + Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(1,3),16));
            
            memory.add(s[0]);
            memory.add(s[1]);
            memory.add(s[2]);
            memory.add(s[3]);
            memDir += 4;
        }
        else if(o == tokenType.IY && p == tokenType.MEM)
        {
            s[0] = s[0] + Integer.toBinaryString(Integer.parseInt("FD", 16));
            s[1] = s[1] + Integer.toBinaryString(Integer.parseInt("2A", 16));
            s[2] = s[2] + Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(3,5), 16));
            s[3] = s[3] + Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(1,3),16));
            
            memory.add(s[0]);
            memory.add(s[1]);
            memory.add(s[2]);
            memory.add(s[3]);
            memDir += 4;
        }
        else if(p == tokenType.IX && o == tokenType.MEM)
        {
            s[0] = s[0] + Integer.toBinaryString(Integer.parseInt("DD", 16));
            s[1] = s[1] + Integer.toBinaryString(Integer.parseInt("22", 16));
            s[2] = s[2] + Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(3,5), 16));
            s[3] = s[3] + Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(1,3),16));
            
            memory.add(s[0]);
            memory.add(s[1]);
            memory.add(s[2]);
            memory.add(s[3]);
            memDir += 4;
        }
        else if(p == tokenType.IY && o == tokenType.MEM)
        {
            s[0] = s[0] + Integer.toBinaryString(Integer.parseInt("FD", 16));
            s[1] = s[1] + Integer.toBinaryString(Integer.parseInt("22", 16));
            s[2] = s[2] + Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(3,5), 16));
            s[3] = s[3] + Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(1,3),16));
            
            memory.add(s[0]);
            memory.add(s[1]);
            memory.add(s[2]);
            memory.add(s[3]);
            memDir += 4;
        }
        
        
        writeLog(s);
        return "";
    }

    public static String parseAdd(Instruction ins) {
        String[] s = new String[4];
        for(int i=0; i<4; i++)
        {
            s[i] = "";
        }

        tokenType o = recognizePattern(ins.getOperand());
        tokenType p = recognizePattern(ins.getParameter());

        if ((o == tokenType.REGISTER) && (p == tokenType.REGISTER || p == tokenType.HL)) {

            s[0] = s[0] + "10000" + parseRegister(ins.getParameter());
            memory.add(s[0]);
            memDir++;
        } else if ((o == tokenType.REGISTER) && p == tokenType.NUMBER) {
            s[0] = s[0] + "11000" + "110";
            s[1] = s[1] + Integer.toBinaryString(Integer.parseInt(ins.getParameter(), 16));
            
            
            memory.add(s[0]);
            memory.add(s[1]);
            memDir += 2;
        }
        else if(o == tokenType.REGISTER && p == tokenType.IXP)
        {
            s[0] = s[0] + Integer.toBinaryString(Integer.parseInt("DD", 16));
            s[1] = s[1] + Integer.toBinaryString(Integer.parseInt("86", 16));
            s[2] = s[2] + Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(4,6), 16));
            
            memory.add(s[0]);
            memory.add(s[1]);
            memory.add(s[2]);
            memDir += 3;
        }
        else if(o == tokenType.REGISTER && p == tokenType.IYP)
        {
            s[0] = s[0] + Integer.toBinaryString(Integer.parseInt("FD", 16));
            s[1] = s[1] + Integer.toBinaryString(Integer.parseInt("86", 16));
            s[2] = s[2] + Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(4,6), 16));
            
            memory.add(s[0]);
            memory.add(s[1]);
            memory.add(s[2]);
            memDir += 3;
        }
        writeLog(s);
        return "";
    }

    public static String parseSub(Instruction ins) {
        String[] s = new String[4];
        
        for(int i=0; i<4; i++)
        {
            s[i] = "";
        }

        tokenType o = recognizePattern(ins.getOperand());
        tokenType p = recognizePattern(ins.getParameter());

        if ((o == tokenType.REGISTER) && (p == tokenType.REGISTER || p == tokenType.HL)) {

            s[0] = s[0] + "10010" + parseRegister(ins.getParameter());
            memory.add(s[0]);
            memDir++;
        } else if ((o == tokenType.REGISTER) && p == tokenType.NUMBER) {
            s[0] = s[0] + "11010" + "110";
            s[1] = s[1] + Integer.toBinaryString(Integer.parseInt(ins.getParameter(), 16));
            
            memory.add(s[0]);
            memory.add(s[1]);
            memDir += 2;
        } 
        else if ((o == tokenType.REGISTER) && p == tokenType.IXP) {
            s[0] = s[0] + Integer.toBinaryString(Integer.parseInt("DD", 16));
            s[1] = s[1] + Integer.toBinaryString(Integer.parseInt("96", 16));
            s[2] = s[2] + Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(4,6), 16));
            
            memory.add(s[0]);
            memory.add(s[1]);
            memory.add(s[2]);
            memDir += 3;
        }
        else if ((o == tokenType.REGISTER) && p == tokenType.IYP) {
            s[0] = s[0] + Integer.toBinaryString(Integer.parseInt("FD", 16));
            s[1] = s[1] + Integer.toBinaryString(Integer.parseInt("96", 16));
            s[2] = s[2] + Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(4,6), 16));
            
            memory.add(s[0]);
            memory.add(s[1]);
            memory.add(s[2]);
            memDir += 3;
        }

        writeLog(s);
        
        return "";
    }

    public static String parseJump(Instruction ins) {
        String s = "";

        tokenType o = recognizePattern(ins.getOperand());
        tokenType p = recognizePattern(ins.getParameter());

        String op = ins.getOperand();

        if ((op == null || op.equals("")) && p == tokenType.MEM) {
            s = s + Integer.toBinaryString(Integer.parseInt("C3", 16));

            memory.add(s);
            memory.add(Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(3, 5), 16)));
            memory.add(Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(1, 3), 16)));

            memDir += 3;
        } else if ((op == null || op.equals("")) && p == tokenType.LABEL) {
            s = s + Integer.toBinaryString(Integer.parseInt("C3", 16));

            int m = labels.get(ins.getParameter());

            String a = String.format("%04X", m);

            memory.add(s);
            memory.add(Integer.toBinaryString(Integer.parseInt(a.substring(2, 4), 16)));
            memory.add(Integer.toBinaryString(Integer.parseInt(a.substring(0, 2), 16)));

            memDir += 3;
        } else {
            if (ins.getOperand().equals("Z")) {
                if (p == tokenType.MEM) {
                    s = s + Integer.toBinaryString(Integer.parseInt("CA", 16));

                    memory.add(s);
                    memory.add(Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(3, 5), 16)));
                    memory.add(Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(1, 3), 16)));

                    memDir += 3;
                } else if (p == tokenType.LABEL) {

                    s = s + Integer.toBinaryString(Integer.parseInt("CA", 16));

                    int m = labels.get(ins.getParameter());

                    String a = String.format("%04X", m);

                    memory.add(s);
                    memory.add(Integer.toBinaryString(Integer.parseInt(a.substring(2, 4), 16)));
                    memory.add(Integer.toBinaryString(Integer.parseInt(a.substring(0, 2), 16)));

                    memDir += 3;
                }
            } else if (ins.getOperand().equals("NZ")) {
                System.out.println("NZ");
                if (p == tokenType.MEM) {
                    System.out.println("Mem");
                    s = s + Integer.toBinaryString(Integer.parseInt("C2", 16));

                    memory.add(s);
                    memory.add(Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(3, 5), 16)));
                    memory.add(Integer.toBinaryString(Integer.parseInt(ins.getParameter().substring(1, 3), 16)));

                    memDir += 3;
                } else if (p == tokenType.LABEL) {
                    
                    s = s + Integer.toBinaryString(Integer.parseInt("C2", 16));

                    int m = labels.get(ins.getParameter());

                    String a = String.format("%04X", m);

                    memory.add(s);
                    memory.add(Integer.toBinaryString(Integer.parseInt(a.substring(2, 4), 16)));
                    memory.add(Integer.toBinaryString(Integer.parseInt(a.substring(0, 2), 16)));

                    memDir += 3;
                }
            }
        }
        return "";
    }

    public static String parseCompare(Instruction ins) {
        String s = "";

        tokenType o = recognizePattern(ins.getOperand());
        tokenType p = recognizePattern(ins.getParameter());

        if (p == tokenType.NONE) {
            if (o == tokenType.REGISTER) {
                s = s + "10111" + parseRegister(ins.getOperand());

                memory.add(s);
                memDir++;
            } else if (o == tokenType.NUMBER) {
                s = s + "11111110";

                memory.add(s);
                memory.add(Integer.toBinaryString(Integer.parseInt(ins.getParameter(), 16)));
                memDir += 2;
            } else if (o == tokenType.HL) {
                s = s + "10111110";
                memDir++;
            }
        }

        return "";
    }

    public static String parseHalt(Instruction ins) {
        memory.add("01110110");
        memDir++;

        return "";
    }

    public static tokenType recognizePattern(String i) {
        if (i != null && !i.equals("")) {
            if (labels.containsKey(i)) {
                return tokenType.LABEL;
            }

            if (i.length() == 1) {
                char first = i.charAt(0);

                if ((65 <= first && first <= 69) || first == 72 || first == 76) {
                    return tokenType.REGISTER;
                }
            } else if (i.length() == 2) {
                if (((48 <= i.charAt(0) && i.charAt(0) <= 57) || (65 <= i.charAt(0) && i.charAt(0) <= 70))
                        && ((48 <= i.charAt(1) && i.charAt(1) <= 57) || (65 <= i.charAt(1) && i.charAt(1) <= 70))) {
                    return tokenType.NUMBER;
                }
                if(i.equals("IX"))
                {
                    return tokenType.IX;
                }
                else if(i.equals("IY"))
                {
                    return tokenType.IY;
                }
            } else if (i.length() == 4) {
                if (i.charAt(0) == '(' && i.charAt(i.length()-1) == ')') {
                    if (i.charAt(1) == 'H' && i.charAt(2) == 'L') {
                        return tokenType.HL;
                    }
                }
                else if(isHexNumber(i.charAt(0)) && isHexNumber(i.charAt(1)) && isHexNumber(i.charAt(2)) && isHexNumber(i.charAt(3)))
                {
                    return tokenType.D_NUMBER;
                }
            } else if (i.length() == 6) {
                return tokenType.MEM;
            }else if(i.length() == 7)
            {
                if(i.charAt(2) == 'X')
                {
                    return tokenType.IXP;
                }else if(i.charAt(2)== 'Y')
                {
                    return tokenType.IYP;
                }
                
            }
        }
        return tokenType.NONE;
    }

    public static String parseRegister(String s) {
        switch (s) {
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
        case "(HL)":
            return "110";
        }

        return "";
    }

    public static void doLabels() {
        int m = 0;

        for (int i = 0; i < instructions.size(); i++) {
            
            Instruction instruction = instructions.get(i);
            String label = instruction.getLabel();

            tokenType p = recognizePattern(instruction.getParameter());
            tokenType o = recognizePattern(instruction.getOperand());

            if (label != null && label.equals("")) {
                labels.put(label, m);
            }
            
            if(instruction.getInstruction().equals("LD"))
            {
                if ((o == tokenType.IX || o == tokenType.IY) || (p == tokenType.IX || p == tokenType.IY))
                {
                    m+=4;
                }
                else if((o == tokenType.IXP || o == tokenType.IYP) && p == tokenType.NUMBER)
                {
                    m+=4;
                }
                else if((o == tokenType.IXP || o == tokenType.IYP) && p == tokenType.REGISTER)
                {
                    m+=3;
                }
                else if((p == tokenType.IXP || p == tokenType.IYP) && o == tokenType.REGISTER)
                {
                    m+=3;
                }
                else if(p == tokenType.MEM || p == tokenType.LABEL)
                {
                    m+=3;
                }
                else if(p == tokenType.NUMBER)
                {
                    m+=2;
                }
                else
                {
                    m++;
                }
            }
            else
            {
                if (p == tokenType.NUMBER || p == tokenType.IX || p == tokenType.IY) {
                    m += 2;
                } else if (p == tokenType.MEM || p == tokenType.LABEL || p == tokenType.IXP || p == tokenType.IYP) {
                    m += 3;
                } else {
                    m++;
                }
            }
        }
    }

    public static void writeMemory() throws IOException {
        String mem = "xx";
        for (int i = 0; i < memory.size(); i++) {

            String s = Integer.toHexString(Integer.parseInt(memory.get(i), 2));

            if (s.length() == 2) {

                mem = mem + s + "xx";
            } else {
                mem = mem + "0" + s + "xx";
            }
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter("./Memory.txt"));

        writer.write(mem);

        writer.close();
    }

    public static void newFile()
    {
        fileName = "";
        gui.setText("");
    }
    
    public static void openFile(String base)
    {
        File file = new File(pickFileName(base));
        
        String text = "";
        
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(file);
            scanner.useDelimiter("\\Z");
            text = scanner.next();
        }
        catch(Exception e)
        {
            
        }
        
        gui.setText(text);
    }
    
    public static void saveFile()
    {
        if(fileName == "")
        {
            fileName = pickFileName("./");
        }
        
        String text = gui.getProgram();
        
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(text);
            writer.close();
        }
        catch(Exception e)
        {
            
        }
        
    }
    
    public static void saveFileAs()
    {
        fileName = pickFileName("./");
        saveFile();
    }
    
    public static String pickFileName(String base)
    {   
        fileName = "";
        fileName = gui.filePicker(base);
        System.out.println(fileName);
        
        return fileName;
    }
    
    public static boolean isHexNumber(char s)
    {
        if (((48 <= s && s <= 57) || (65 <= s && s <= 70))) 
        {
            return true;
        }
        return false;
    }

    public static boolean isCommand(String s)
    {
        switch (s) {
        case "LD":
            return true;
        case "ADD":
            return true;
        case "SUB":
            return true;
        case "JP":
            return true;
        case "CP":
            return true;
        case "HALT":
            return true;
        default:
            return false;
        }
    }
    
    public static void writeLog(String[] s)
    {
        for(int i=0; i<4; i++)
        {
            try{
            s[i] = Integer.toHexString(Integer.parseInt(s[i],2));
            
            if (s[i].length() == 1) {

                s[i] = "0" + s[i];
            }
            }
            catch(Exception e)
            {
                
            }
            System.out.print(s[i] + " ");
        }
        System.out.println();
    }
}
