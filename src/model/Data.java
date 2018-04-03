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
    private List<Canasta> productosEnCanasta;

    public Data() throws ClassNotFoundException, SQLException {
        con = new Conexion("Localhost", "root", "123456", "sofVenta");
    }

    public List<Marca> getMarcas() throws SQLException {
        query = "select * from marca";

        todasMarcas = new ArrayList<>();

        Marca m;

        rs = con.ejecutarSelect(query);

        while (rs.next()) {
            m = new Marca();

            m.setId(rs.getInt(1));
            m.setNombre(rs.getString(2));

            todasMarcas.add(m);
        }
        con.desconectar();

        return todasMarcas;
    }

    public List<Producto> getProductos() throws SQLException {
        query = "select * from producto";

        todoProductos = new ArrayList<>();

        Producto p;

        rs = con.ejecutarSelect(query);

        while (rs.next()) {
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

    public List<ProductoSelect> getProdSele() throws SQLException {
        query = "select producto.id,producto.nombre,marca.nombre,producto.stock,producto.precio from marca,producto where producto.marca = marca.id";

        todoProductosSelec = new ArrayList<>();

        ProductoSelect ps;

        rs = con.ejecutarSelect(query);

        while (rs.next()) {
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

    public List<ProductoSelect> getProdSeleBusqueda(String busqueda) throws SQLException {
        query = "select producto.id,producto.nombre,marca.nombre,producto.stock,producto.precio from marca,producto where producto.marca = marca.id and (producto.nombre like '%" + busqueda + "%'or producto.id like '%" + busqueda + "%')";
        todoProductosSelec = new ArrayList<>();

        ProductoSelect ps;

        rs = con.ejecutarSelect(query);

        while (rs.next()) {
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

    public List<ProductoSelect> getProductosCanasta() throws SQLException {
        query = "select producto.id,producto.nombre,marca.nombre,canasta.cantidad,producto.precio from marca,producto,canasta where producto.marca = marca.id AND canasta.producto = producto.id;";

        todoProductosSelec = new ArrayList<>();

        ProductoSelect ps;

        rs = con.ejecutarSelect(query);

        while (rs.next()) {
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

    public void eliminarProducto(Producto p) throws SQLException {
        query = "delete from producto where id = " + p.getId();

        con.ejecutar(query);
    }

    public void actualizarProducto(Producto p) throws SQLException {
        query = "update producto set nombre = '"
                + p.getNombre() + "', marca = '"
                + p.getMarca() + "', stock = '"
                + p.getStock() + "', precio = '"
                + p.getPrecio() + "' where id = '"
                + p.getId() + "'";
        con.ejecutar(query);
    }

    public void agregarproducto(String nombre, int marca, int stock, int precio) throws SQLException {
        query = "insert into producto values(null,'" + nombre + "'," + marca + "," + stock + "," + precio + ")";

        System.out.println(query);
        con.ejecutar(query);
    }

    public void agregarproductoCanasta(int producto, int cantidad) throws SQLException {
        query = "insert into canasta values(null,'" + producto + "'," + cantidad + ")";
        System.out.println(query);
        con.ejecutar(query);
    }

    public void agregarMarca(Marca m) throws SQLException {
        query = "insert into marca values(null,'" + m.getNombre() + "')";
        System.out.println(query);
        con.ejecutar(query);
    }

    public void canastaVaciada() throws SQLException {
        query = "truncate canasta";

        System.out.println(query);
        con.ejecutar(query);
    }
//    public String sumarPromedioNotas(int idAsig) throws SQLException {
//
//        float promedioNotas = 0;
//        sql = "select valor,porcentaje from nota where asignatura ="+idAsig;
//        
//        tablaVirtual = con.ejecutarSelect(sql);
//        
//        while (tablaVirtual.next()) {            
//            promedioNotas = promedioNotas+(tablaVirtual.getFloat(1)*tablaVirtual.getFloat(2))/100;
//            
//            
//        }
//        con.desconectar();
//        return aproximar(promedioNotas);
//    }

    public int sumarPrecio(int idCanasta, int idProd) throws SQLException {
        int subtotal = 0;
        query = "select producto.precio, canasta.cantidad , (producto.precio * canasta.cantidad) from producto,canasta where canasta.producto ='" + idCanasta + "' and producto.id ='" + idProd + "'";

        rs = con.ejecutarSelect(query);

        while (rs.next()) {
            subtotal = subtotal + (rs.getInt(3));

        }
        con.desconectar();
        return subtotal;
    }

    public void canastaCancelada() throws SQLException {
        List<ProductoSelect> prodCanasta = getProductosCanasta();
        Producto p = new Producto();

        for (ProductoSelect producto : prodCanasta) {
            p.setId(producto.getId());
            p.setNombre(producto.getNombre());
            query = "select id from marca where marca.nombre like '%" + producto.getMarca() + "%'";

            rs = con.ejecutarSelect(query);

            while (rs.next()) {
                p.setMarca(rs.getInt(1));
            }
            con.desconectar();

            query = "select stock from producto where producto.id = " + p.getId() + "";

            rs = con.ejecutarSelect(query);

            while (rs.next()) {

                p.setStock(rs.getInt(1) + producto.getStock());
            }
            con.desconectar();
            p.setPrecio(producto.getPrecio());
            actualizarProducto(p);
        }
        canastaVaciada();
    }

}
