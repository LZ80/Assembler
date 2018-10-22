/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import javax.swing.*;
import javax.swing.table.*;
/**
 *
 * @author Arkai
 */
public class MyTable extends AbstractTableModel implements TableModel
{
    Object[][] rowData;
    @Override
    public int getRowCount() 
    {
        return rowData.length;
    }

    @Override
    public int getColumnCount() 
    {
       return rowData[0].length;
    }

    @Override
    public Object getValueAt(int i, int i1)
    {
        return rowData[i][i1];
    }
    
    @Override
    public void setValueAt(Object a, int row, int col)
    {
        rowData[row][col] = a;
        fireTableCellUpdated(row, col);
    }
}
