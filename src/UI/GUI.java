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
        //fillTable1();

        
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
    
    public void fillTable1()
    {
        /*table1.setValueAt(null, 0, 0);
        table1.setValueAt("INA", 0, 1);
        table1.setValueAt(null, 0, 2);
        table1.setValueAt(null, 1, 0);
        table1.setValueAt("STOREA", 1, 1);
        table1.setValueAt("X", 1, 2);
        table1.setValueAt(null, 2, 0);
        table1.setValueAt("INA", 2, 1);
        table1.setValueAt(null, 2, 2);
        table1.setValueAt(null, 3, 0);
        table1.setValueAt("STOREA", 3, 1);
        table1.setValueAt("Y", 3, 2);
        table1.setValueAt(null, 4, 0);
        table1.setValueAt("LOADA", 4, 1);
        table1.setValueAt("X", 4, 2);
        table1.setValueAt(null, 5, 0);
        table1.setValueAt("ADDA", 5, 1);
        table1.setValueAt("Y", 5, 2);
        table1.setValueAt(null, 6, 0);
        table1.setValueAt("STOREA", 6, 1);
        table1.setValueAt("S", 6, 2);
        table1.setValueAt(null, 7, 0);
        table1.setValueAt("LOADA", 7, 1);
        table1.setValueAt("S", 7, 2);
        table1.setValueAt(null, 8, 0);
        table1.setValueAt("OUTA", 8, 1);
        table1.setValueAt(null, 8, 2);
        table1.setValueAt("FIN", 9, 0);
        table1.setValueAt("LOADA", 9, 1);
        table1.setValueAt("ONE", 9, 2);
        table1.setValueAt(null, 10, 0);
        table1.setValueAt("JPOS", 10, 1);
        table1.setValueAt("FIN", 10, 2);*/
        
        String[][] s = {{null,"INA",null},
                        {null,"STOREA","X"},
                        {null,"LOADA","X"},
                        {null,"SUBA","C17"},
                        {null,"JPOS","TRUE"},
                        {null,"LOADA","ONE"},
                        {null,"JPOS","FALSE"},
                        {"TRUE","LOADA","C93"},
                        {null,"STOREA","Y"},
                        {null,"LOADA","ONE"},
                        {null,"JPOS","ENDIF"},
                        {"FALSE","LOADA","CM47"},
                        {null,"STOREA","Y"},
                        {"ENDIF","LOADA","Y"},
                        {null,"OUTA",null},
                        {"FIN","LOADA","ONE"},
                        {null,"JPOS","FIN"}};
        
        for (int i = 0; i < 17; i++)
        {
            if(table1.getRowCount() <= s.length)
            {
                tm.addRow(new Object[]{null,null,null});
            }
            table1.setValueAt(s[i][0], i, 0);
            table1.setValueAt(s[i][1], i, 1);
            table1.setValueAt(s[i][2], i, 2);
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