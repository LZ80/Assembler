/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import assembler.Assembler;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Arkai
 */
public class GUI extends JFrame {
    private JPanel panel;
    private JButton ejecutar;
    private JButton addRow;
    private JButton clearTable;
    private JButton salir;
    private JScrollPane scrollPane1;
    private JTable table1;
    private DefaultTableModel tm;
    DefaultTableModel t;
    private JTextArea text;

    private JMenuBar menuBar;
    private JMenu file;
    private JMenu example;
    
    private JMenuItem newP;
    private JMenuItem open;
    private JMenuItem save;
    private JMenuItem saveAs;
    private JMenuItem exit;
    private JMenuItem openExample;
    
    private JButton example1;
    private JButton example2;
    private JButton example3;

    private DefaultTableCellRenderer renderer;
    Insets insets;

    private String option;

    public GUI() {
        init();
        this.setVisible(true);
    }

    public void init() {
        option = "no";
        setTitle("Z80 Assembler");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);

        panel = new JPanel();
        panel.setLayout(null);
        insets = panel.getInsets();
        panel.setVisible(true);

        int top = (550 - insets.top - 400) / 2;
        int left = (800 - 500) / 2;

        ejecutar = new JButton("Generar Archivo de Texto");
        ejecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                ejecutarActionPerformed(event);
            }
        });
        ejecutar.setBounds(insets.left + left + 250 - 50, insets.top + top + 350 + 50, 100, 30);
        panel.add(ejecutar);

        menuBar = new JMenuBar();
        menuBar.setBounds(panel.getInsets().left, panel.getInsets().top, panel.getInsets().left+800, panel.getInsets().top+20);
        file = new JMenu("File");
        example = new JMenu("Examples");
        
        newP = new JMenuItem("New");
        open = new JMenuItem("Open...");
        save = new JMenuItem("Save...");
        saveAs = new JMenuItem("Save As...");
        exit = new JMenuItem("Exit");
        openExample = new JMenuItem("Open...");
        
        newP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                option = "new";
            }
        });
        open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                option = "open";
            }
        });
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                option = "save";
            }
        });
        saveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                option = "saveAs";
            }
        });
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                dispose();
                option = "exit";
            }
        });
        openExample.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                option = "openExample";
            }
        });
        
        
        file.add(newP);
        file.add(open);
        file.add(save);
        file.add(saveAs);
        file.add(exit);
        example.add(openExample);
        
        menuBar.add(file);
        menuBar.add(example);
        panel.add(menuBar);
       
        
        text = new JTextArea();

        scrollPane1 = new JScrollPane(text);
        scrollPane1.setBounds(insets.left + left, insets.top + top, 500, 350);
        panel.add(scrollPane1);
        this.add(panel);
        panel.setBounds(0, 0, 640, 480);
    }

    public void ejecutarActionPerformed(java.awt.event.ActionEvent e) {
        this.option = "ejecutar";
    }

    public String getOption() {
        if(option!=null)
        {
            String op = option;
            option="no";
            return op;
        }else
        {
            return option;
        }
    }

   /*
    public void example2() {
        clearTable();

        String[][] s = { { null, "LD", "B", "02" }, { null, "LD", "C", "03" }, { "Label_Start", "LD", "A", "B" },
                { null, "ADD", "A", "A" }, { null, "LD", "B", "A" }, { null, "LD", "A", "C" },
                { null, "LD", "D", "01" }, { null, "SUB", "A", "D" }, { null, "LD", "C", "A" },
                { null, "JP", "NZ", "Label_Finish" }, { null, "JP", null, "Label_Start" },
                { "Label_Finish", "HALT", null, null } };

        for (int i = 0; i < 12; i++) {
            if (table1.getRowCount() <= s.length) {
                tm.addRow(new Object[] { null, null, null, null });
            }
            table1.setValueAt(s[i][0], i, 0);
            table1.setValueAt(s[i][1], i, 1);
            table1.setValueAt(s[i][2], i, 2);
            table1.setValueAt(s[i][3], i, 3);
        }
    }

    public void example3() {
        clearTable();

        String[][] s = { { null, "LD", "B", "02" }, { null, "LD", "C", "FF" }, { "Label_Start", "LD", "A", "B" },
                { null, "LD", "A", "C" }, { null, "JP", "NZ", "Label_Start" }, { null, "HALT", null, null } };

        for (int i = 0; i < 6; i++) {
            if (table1.getRowCount() <= s.length) {
                tm.addRow(new Object[] { null, null, null, null });
            }
            table1.setValueAt(s[i][0], i, 0);
            table1.setValueAt(s[i][1], i, 1);
            table1.setValueAt(s[i][2], i, 2);
            table1.setValueAt(s[i][3], i, 3);
        }
    }


*/
    public String[] getIniValues(String[] sym) {
        IniValuesWindow vWindow = new IniValuesWindow();
        return vWindow.InValues(sym);
    }

    public void error(String s) {
        JOptionPane.showMessageDialog(this, s);
    }
    
    public String getProgram(){
        return text.getText();
    }
    
    public void setText(String program){
        text.setText(program);
    }
    
    public String filePicker(String base)
    {
        JFileChooser chooser = new JFileChooser(base);
        
        int returnValue = chooser.showOpenDialog(null);
        
        if(returnValue == JFileChooser.APPROVE_OPTION)
        {
            return chooser.getSelectedFile().getAbsolutePath();
        }
        else
        {
            
        }
        return "";
    }
}