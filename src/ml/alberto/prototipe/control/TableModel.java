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
      // Establecemos os tipos de datos de las columnas
    Class[] tipoColumna = {
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
    
    public TableModel(Object datos[][], Object cabeceras[]) {
        super(datos,cabeceras);
    }
    
    public Class getColumnClass(int indice) {
        return tipoColumna[indice];
    }
    
    public boolean isCellEditable(int fila, int columna) {
        return editColumn[columna];
    }
}
