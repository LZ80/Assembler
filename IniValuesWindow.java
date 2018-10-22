/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Insets;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Arkai
 */
public class IniValuesWindow extends JFrame
{
    JPanel panel;
    JTable table;
    JScrollPane scrollPane;
    JButton aceptar;
    int acept = -1;
    private DefaultTableCellRenderer renderer;
    Insets insets;
    
    public IniValuesWindow()
    {
        init();
        setVisible(true);
    }
    
    private void init()
    {
        setTitle("Initial Values");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(320, 480);
        setVisible(true);
        
        panel = new JPanel();
        panel.setLayout(null);
        insets = panel.getInsets();
        panel.setVisible(true);
        
        aceptar = new JButton("Aceptar");
        aceptar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent event) 
            {
                aceptarActionPerformed(event); 
            }
        });
        
        aceptar.setBounds(insets.left + 105, insets.top + 400, 100, 30);
        panel.add(aceptar);
        
        renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        
        this.add(panel);
    }
    
    private void aceptarActionPerformed(java.awt.event.ActionEvent event)
    {
        acept = 1;
        this.dispose();
    }
    
    public int aceptarButton()
    {
        return acept;
    }
    
    public String[] InValues(String[] sym)
    {
        System.out.println("zsd");
        DefaultTableModel tm = new DefaultTableModel(new Object[]{"Symbol","Initial Value"}, sym.length);
        table = new JTable(tm);
        table.setRowHeight(20);
        table.getColumnModel().getColumn(0).setCellRenderer(renderer);
        table.getColumnModel().getColumn(1).setCellRenderer(renderer);
        
        table.setVisible(true);
        
        scrollPane = new JScrollPane();
        scrollPane.getViewport().setView(table);
        scrollPane.setBounds(insets.left + 85, insets.top + 100, 150, 200);
        scrollPane.setVisible(true);
        panel.add(scrollPane);
        
        for(int i=0; i<sym.length; i++)
        {
            table.setValueAt(sym[i], i, 0);
        }    
        
        while(acept == -1)
        {    
            try
            {
                wait(100);
            }catch(Exception e){}
           //System.out.println("asd");
        }
        
        String[] v = new String[sym.length];
        
        for (int i = 0; i < v.length; i++) 
        {
            v[i] = (String)table.getValueAt(i, 1);
        }
        
        return v;
    }
}
