package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultasProducto extends Conexion {
    public boolean registrar (Producto pro){
        PreparedStatement ps = null;
        Connection conn = getConnection();
        
        String sql = "INSERT INTO producto (codigo, nombre, precio, cantidad) VALUES (?,?,?,?)";
        try{
            ps = conn.prepareStatement(sql);
            ps.setString(1, pro.getCodigo());
            ps.setString(2, pro.getNombre());
            ps.setDouble(3, pro.getPrecio());
            ps.setInt(4, pro.getCantidad());
            
            ps.execute();
            return true;
            
        } catch (SQLException e){
            System.err.println(e);
            return false;  
            
        }  finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
        }       
    }
    
    public boolean modificar (Producto pro){
        PreparedStatement ps = null;
        Connection conn = getConnection();
        
        String sql = "UPDATE producto SET codigo=?, nombre=?, precio=?, cantidad=? WHERE id=?";
        try{
            ps = conn.prepareStatement(sql);
            ps.setString(1, pro.getCodigo());
            ps.setString(2, pro.getNombre());
            ps.setDouble(3, pro.getPrecio());
            ps.setInt(4, pro.getCantidad());
            ps.setInt(5, pro.getId());
            
            ps.execute();
            return true;
            
        } catch (SQLException e){
            System.err.println(e);
            return false;  
            
        }  finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
        }       
    }
    
    public boolean eliminar (Producto pro){
        PreparedStatement ps = null;
        Connection conn = getConnection();
        
        String sql = "DELETE FROM producto WHERE id=?";
        
        try{
            ps = conn.prepareStatement(sql);
            ps.setInt(1, pro.getId());
            
            ps.execute();
            return true;
            
        } catch (SQLException e){
            System.err.println(e);
            return false;  
            
        }  finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
        }       
    }
    
    public boolean buscar (Producto pro){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = getConnection();
        
        String sql = "SELECT * FROM producto WHERE codigo=?";
        
        try{
            ps = conn.prepareStatement(sql);
            ps.setString(1, pro.getCodigo());
            
            rs = ps.executeQuery();
            if(rs.next()){
                pro.setId(Integer.parseInt(rs.getString("id")));
                pro.setCodigo(rs.getString("Codigo"));
                pro.setNombre(rs.getString("nombre"));
                pro.setPrecio(Double.parseDouble(rs.getString("precio")));
                pro.setCantidad(Integer.parseInt(rs.getString("cantidad")));
                return true;
            } else {
                return false;
            }
        } catch (SQLException e){
            System.err.println(e);
            return false;  
            
        }  finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
        }       
    }
}
