package model;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class TMProductos implements TableModel{
    
    private List<ProductoSelect> listaTodosProdSele;
    Data d;
    
    public TMProductos(List<ProductoSelect> listaTodosProdSele){
        try {
            this.listaTodosProdSele = listaTodosProdSele;
            d = new Data();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TMProductos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TMProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ProductoSelect getProductos(int index){
        return listaTodosProdSele.get(index);
    }

    @Override
    public int getRowCount() {
        return listaTodosProdSele.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch(columnIndex){
            case 0:
                return "ID";
            case 1:
                return "NOMBRE";
            case 2:
                return "MARCA";
            case 3:
                return "STOCK";
            default:
                return "PRECIO";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ProductoSelect ps = listaTodosProdSele.get(rowIndex);
        
        switch(columnIndex){
            case 0:
                return ps.getId();
            case 1:
                return ps.getNombre();
            case 2:
                return ps.getMarca();
            case 3:
                return ps.getStock();
            default:
                return ps.getPrecio();
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }

}
