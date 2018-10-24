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
public class GUI extends JFrame 
{
    private JPanel panel;
    private JButton ejecutar;
    private JButton addRow;
    private JButton clearTable;
    private JButton salir;
    private JScrollPane scrollPane1;
    private JTable table1;
    private DefaultTableModel tm;
    DefaultTableModel t;
    
    private JButton example1;
    private JButton example2;
    private JButton example3;
    
    private DefaultTableCellRenderer renderer;
    Insets insets;
    
    private int e;
    
    public GUI()
    {
        init();
        this.setVisible(true);
    }
    
    public void init()
    {
        e = -1;
        setTitle("Z80 Assembler");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);

        
        panel = new JPanel();
        panel.setLayout(null);
        insets = panel.getInsets();
        panel.setVisible(true);
        
        int top = (550 - insets.top - 400)/2;
        int left = ( 800 - 500)/2;
        
        
        ejecutar = new JButton("Generar Archivo de Texto");
        ejecutar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent event) 
            {
                ejecutarActionPerformed(event); 
            }
        });
        ejecutar.setBounds(insets.left + left + 250 - 50, insets.top + top + 350 + 50, 100, 30);
        panel.add(ejecutar);
             
        
        example1 = new JButton("Ejemplo 1");
        example1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent event) 
            {
                example1ActionPerformed(event); 
            }

            private void example1ActionPerformed(ActionEvent event) {
                example1();
            }
        });
        example1.setBounds(insets.left + 15, insets.top + top, 100, 30);
        panel.add(example1);
        
        
        example2 = new JButton("Ejemplo 2");
        example2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent event) 
            {
                example2ActionPerformed(event); 
            }

            private void example2ActionPerformed(ActionEvent event) {
                example2();
            }
        });
        example2.setBounds(insets.left + 15, insets.top + top + 100, 100, 30);
        panel.add(example2);
        
        
        example3 = new JButton("Ejemplo 3");
        example3.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent event) 
            {
                example3ActionPerformed(event); 
            }

            private void example3ActionPerformed(ActionEvent event) {
                example3();
            }
        });
        example3.setBounds(insets.left + 15, insets.top + top + 200, 100, 30);
        panel.add(example3);
        
        
        
        
        ImageIcon icon = new ImageIcon("icon.png"); 
        addRow = new JButton("", icon);
        addRow.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent event) 
            {
                addRowActionPerformed(event); 
            }
        });
        addRow.setBounds(insets.left + left - 20, insets.top + top, 20, 20);
        addRow.setBorder(new BevelBorder(BevelBorder.RAISED));
        panel.add(addRow);
        
        ImageIcon icon2 = new ImageIcon("icon2.png"); 
        
        clearTable = new JButton("", icon2);
        clearTable.addActionListener(new java.awt.event.ActionListener()
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent event) 
            {
                clearTableActionPerformed(event); 
            }
        });
        clearTable.setBounds(insets.left + left + 500, insets.top + top, 20, 20);
        clearTable.setBorder(new BevelBorder(BevelBorder.RAISED));
        panel.add(clearTable);
        
        renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        
        tm = new DefaultTableModel(new Object[]{ "Label", "Instruction", "Operator", "Parameter" },20);
        table1 = new JTable(tm);
 
        
        table1 = new JTable(tm);
        table1.setRowHeight(20);
        table1.getColumnModel().getColumn(0).setCellRenderer(renderer);
        table1.getColumnModel().getColumn(1).setCellRenderer(renderer);
        table1.getColumnModel().getColumn(2).setCellRenderer(renderer);
        table1.getColumnModel().getColumn(3).setCellRenderer(renderer);
        table1.setVisible(true);

        
        scrollPane1 = new JScrollPane(table1);
        scrollPane1.setBounds(insets.left + left, insets.top + top, 500, 350);
        panel.add(scrollPane1);
        this.add(panel);
        panel.setBounds(0,0,640,480);
    }
    
    public void ejecutarActionPerformed(java.awt.event.ActionEvent e)
    {
        this.e = 1;
    }
    
    public void addRowActionPerformed(java.awt.event.ActionEvent e)
    {
        tm.addRow(new Object[]{null,null,null});
    }
    
    public void clearTableActionPerformed(java.awt.event.ActionEvent e)
    {
        tm.setRowCount(0);
        tm.setRowCount(20);
        tm.fireTableDataChanged();
    }
    
    public int ejecutar()
    {
        return e;
    }
    
    public void example1()
    {   
        String[][] s = {{null,"LD","A","0A"},
                        {null,"LD","B","0B"},
                        {null,"SUB","A","B"},
                        {null,"JP","NZ","Label_Neg"},
                        {null,"LD","A","00"},
                        {null,"LD","A","C"},
                        {null,"HALT","",""},
                        {"Label_Neg","LD","A","01"},
                        {null,"HALT","",""}};
        
        for (int i = 0; i < 9; i++)
        {
            if(table1.getRowCount() <= s.length)
            {
                tm.addRow(new Object[]{null,null,null,null});
            }
            table1.setValueAt(s[i][0], i, 0);
            table1.setValueAt(s[i][1], i, 1);
            table1.setValueAt(s[i][2], i, 2);
            table1.setValueAt(s[i][3], i, 3);
        }
    }
    
    public void example2()
    {
        String[][] s = {{null,"LD","B","02"},
                        {null,"LD","C","03"},
                        {"Label_Start","LD","A","B"},
                        {null,"ADD","A","A"},
                        {null,"LD","B","A"},
                        {null,"LD","A","C"},
                        {null,"SUB","A","01"},
                        {null,"LD","C","A"},
                        {null,"JP","NZ","Label_Finish"},
                        {null,"JP",null,"Label_Start"},
                        {"Label_Finish","HALT",null,null}};
        
        for (int i = 0; i < 11; i++)
        {
            if(table1.getRowCount() <= s.length)
            {
                tm.addRow(new Object[]{null,null,null,null});
            }
            table1.setValueAt(s[i][0], i, 0);
            table1.setValueAt(s[i][1], i, 1);
            table1.setValueAt(s[i][2], i, 2);
            table1.setValueAt(s[i][3], i, 3);
        }
    }
    
    public void example3()
    {
        String[][] s = {{null,"LD","B","02"},
                        {null,"LD","C","FF"},
                        {"Label_Start","LD","A","B"},
                        {null,"JP","NZ","Label_Start"},
                        {null,"HALT", null, null}};
        
        for (int i = 0; i < 5; i++)
        {
            if(table1.getRowCount() <= s.length)
            {
                tm.addRow(new Object[]{null,null,null,null});
            }
            table1.setValueAt(s[i][0], i, 0);
            table1.setValueAt(s[i][1], i, 1);
            table1.setValueAt(s[i][2], i, 2);
            table1.setValueAt(s[i][3], i, 3);
        }
    }
    
    
    
    public String[][] getTable1()
    {
        e=-1;
        int x = table1.getColumnCount();
            
        int y = table1.getRowCount();
        
        for(int i=0; i<table1.getRowCount();i++)
        {
            if(table1.getValueAt(i, 1) == null || table1.getValueAt(i, 1) == "")
            {
                y = i;
                break;
            }
        }
        
        String[][] s = new String[y][x];
        
        for(int i=0; i<y; i++)
        {   
            
            for (int j = 0; j < x; j++) 
            {
                s[i][j] = (String)table1.getValueAt(i,j);
            }
        }
        return s;
    }
    
    public String[] getIniValues(String[] sym)
    {
        IniValuesWindow vWindow = new IniValuesWindow();
        return vWindow.InValues(sym);
    }
    
    public void error(String s)
    {
        JOptionPane.showMessageDialog(this, s);
    }
}