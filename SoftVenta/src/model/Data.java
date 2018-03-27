package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Data {

    private Conexion con;
    private String query;
    private ResultSet rs;
    private List<Marca> todasMarcas;
    private List<Producto> todoProductos;
    private List<ProductoSelect> todoProductosSelec;
    
    
    public Data() throws ClassNotFoundException, SQLException{
        con = new Conexion("Localhost", "root", "", "sofVenta");
    }
    
    public List<Marca> getMarcas() throws SQLException{
        query = "select * from marca";
        
        todasMarcas = new ArrayList<>();
        
        Marca m;
        
        rs = con.ejecutarSelect(query);
        
        while(rs.next()){
            m = new Marca();
            
            m.setId(rs.getInt(1));
            m.setNombre(rs.getString(2));
            
            todasMarcas.add(m);
        }
        con.desconectar();
        
        return todasMarcas;
    }
    
    public List<Producto> getProductos() throws SQLException{
        query = "select * from producto";
        
        todoProductos = new ArrayList<>();
        
        Producto p;
        
        rs = con.ejecutarSelect(query);
        
        while(rs.next()){
            p = new Producto();
            
            p.setId(rs.getInt(1));
            p.setNombre(rs.getString(2));
            p.setMarca(rs.getInt(3));
            p.setStock(rs.getInt(4));
            p.setPrecio(rs.getInt(5));
            
            todoProductos.add(p);
        }
        con.desconectar();
        
        return todoProductos;
    }
    
    public List<ProductoSelect> getProdSele() throws SQLException{
        query = "select producto.id,producto.nombre,marca.nombre,producto.stock,producto.precio from marca,producto where producto.marca = marca.id";
        
        todoProductosSelec = new ArrayList<>();
        
        ProductoSelect ps;
        
        rs = con.ejecutarSelect(query);
        
        while(rs.next()){
            ps = new ProductoSelect();
            
            ps.setId(rs.getInt(1));
            ps.setNombre(rs.getString(2));
            ps.setMarca(rs.getString(3));
            ps.setStock(rs.getInt(4));
            ps.setPrecio(rs.getInt(5));
            
            todoProductosSelec.add(ps);
        }
        con.desconectar();
        
        return todoProductosSelec;
    }
    
    public void eliminarProducto(Producto p) throws SQLException{
        query = "delete from producto where id = "+p.getId();
        
        con.ejecutar(query);
    }
    
    public void agregarproducto(String nombre,int marca,int stock,int precio) throws SQLException{
        query = "insert into producto value(null,'"+nombre+"',"+marca+","+stock+","+precio+")";
        
        System.out.println(query);
        con.ejecutar(query);
    }
    
    
}
