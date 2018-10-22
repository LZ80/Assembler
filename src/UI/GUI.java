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
    private JScrollPane scrollPane2;
    private JTable table1;
    private JTable table3;
    private DefaultTableModel tm;
    private JComboBox<String> combo;
    private MyTable tb;
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
        setTitle("XiAssembler");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 480);
        setVisible(true);
        
        tb = new MyTable();
        
        panel = new JPanel();
        panel.setLayout(null);
        insets = panel.getInsets();
        panel.setVisible(true);
        
        ejecutar = new JButton("Ejecutar");
        ejecutar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent event) 
            {
                ejecutarActionPerformed(event); 
            }
        });
        ejecutar.setBounds(insets.left + 305, insets.top + 185, 100, 30);
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
        addRow.setBounds(insets.left + 35, insets.top + 100, 20, 20);
        addRow.setBorder(new BevelBorder(BevelBorder.RAISED));
        panel.add(addRow);
        
        ImageIcon icon2 = new ImageIcon("icon2.png"); 
        
        clearTable = new JButton("", icon2);
        clearTable.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent event) 
            {
                clearTableActionPerformed(event); 
            }
        });
        clearTable.setBounds(insets.left + 280, insets.top + 100, 20, 20);
        clearTable.setBorder(new BevelBorder(BevelBorder.RAISED));
        panel.add(clearTable);
        
        renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        
        tm = new DefaultTableModel(new Object[]{"Label", "Nemonic", "Operator"},10);
        table1 = new JTable(tm);
 
        
        table1 = new JTable(tm);
        table1.setRowHeight(20);
        table1.getColumnModel().getColumn(0).setCellRenderer(renderer);
        table1.getColumnModel().getColumn(1).setCellRenderer(renderer);
        table1.getColumnModel().getColumn(2).setCellRenderer(renderer);
        table1.setVisible(true);
        //fillTable1();
        
        
        scrollPane1 = new JScrollPane(table1);
        scrollPane1.setBounds(insets.left + 55, insets.top + 100, 225, 200);
        panel.add(scrollPane1);
        
        /*table2 = new JTable(11,2);
        table2.setEnabled(false);
        table2.setBackground(Color.lightGray);
        table2.setAlignmentX(CENTER_ALIGNMENT);
        table2.setRowHeight(20);
        table2.getColumnModel().getColumn(0).setHeaderValue("Address");
        table2.getColumnModel().getColumn(1).setHeaderValue("Value");
        table2.getColumnModel().getColumn(0).setCellRenderer(renderer);
        table2.getColumnModel().getColumn(1).setCellRenderer(renderer);
        table2.setVisible(true);
        
        scrollPane2 = new JScrollPane(table2);
        scrollPane2.setBounds(insets.left + 430, insets.top + 100, 150, 200);
        panel.add(scrollPane2);
        */
        combo = new JComboBox<>();
        combo.setBounds(insets.left + 55, insets.top + 30, 100, 30);
        combo.addItem("GPC V1");
        combo.addItem("GPC V2");
        //combo.addItem("GPC V3");
        //combo.addItem("GPC V4");
        panel.add(combo);
        
        t  = new DefaultTableModel(new Object[]{"Addresses", "Values"}, 11);
        table3 = new JTable(t);
        table3.setEnabled(false);
        table3.setBackground(Color.lightGray);
        table3.setRowHeight(20);
        table3.getColumnModel().getColumn(0).setCellRenderer(renderer);
        table3.getColumnModel().getColumn(1).setCellRenderer(renderer);
        table3.setVisible(true);
        
        JScrollPane scrollPane3 = new JScrollPane(table3);
        scrollPane3.setBounds(insets.left + 430, insets.top + 100, 150, 200);
        panel.add(scrollPane3);
        
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
        tm.setRowCount(10);
        tm.fireTableDataChanged();
        t.setRowCount(0);
        t.setRowCount(10);
        t.fireTableDataChanged();
    }
    
    public int ejecutar()
    {
        return e;
    }
    
    public String getMode()
    {
        return (String)combo.getSelectedItem();
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
    
    public void fillTable2(Object[][] finalTable)
    {
        Object[] tableHeaders = {"Address", "Value"};
        
        table3.getColumnModel().getColumn(0).setCellRenderer(renderer);
        table3.getColumnModel().getColumn(1).setCellRenderer(renderer);
        table3.setBackground(Color.white);
        t.setRowCount(0);
        t.setDataVector(finalTable, tableHeaders);
        t.fireTableDataChanged();
    }
    
    public String[][] getTable1()
    {
        e=-1;
        int x = table1.getColumnCount();
        int y = table1.getRowCount();
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
    
    public void error()
    {
        JOptionPane.showMessageDialog(this, "ERROR");
    }
}