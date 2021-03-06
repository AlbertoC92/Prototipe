/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ml.alberto.prototipe.control;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alber
 */
public class TableModel extends DefaultTableModel{
      // Establecemos os tipos de data de las columns
    Class[] ColumnType = {
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class
    };
 
    // Establecemos cuáles son editables
    boolean[] editColumn = {false,false,false,false,false,false,false,false,false};
    
    public TableModel(Object data[][], Object heads[]) {
        super(data,heads);
    }
    
    public Class getColumnClass(int index) {
        return ColumnType[index];
    }
    
    public boolean isCellEditable(int fila, int column) {
        return editColumn[column];
    }
}
