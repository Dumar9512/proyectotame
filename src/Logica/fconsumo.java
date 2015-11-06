
package Logica;

import Datos.vconsumo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;




public class fconsumo {
    
    private conexion mysql=new conexion();
    private Connection cn=mysql.conectar();
    private String sSQL="";
    public Integer totalregistro;
    public Double totalconsumo;
    
     public DefaultTableModel mostrar(String Buscar){
         
         DefaultTableModel modelo;
      
        
        String[]titulos = {"ID","IdReserva","IdProducto","Producto","Cantidad","Precio_Venta","Estado"};
         
        String [] Registro = new String [7];
        
        totalregistro=0;
        totalconsumo=0.0;
        
        modelo = new DefaultTableModel(null, titulos);
        
        sSQL = "select c.idconsumo,c.idreserva,c.idproducto,p.nombre,c.cantidad,c.precio_venta "
                + " c.estado from consumo c inner join producto p on c.idproducto=p.idproducto" 
                + " where c.idreserva ="+ Buscar + " order by idconsumo desc ";
          
           
         try {
             
             Statement st = cn.createStatement();
             ResultSet rs=st.executeQuery(sSQL);
              
             while (rs.next()) {                 
                 Registro [0]=rs.getString("idconsumo");
                 Registro [1]=rs.getString("idreserva");
                 Registro [2]=rs.getString("idproducto");
                 Registro [3]=rs.getString("nombre");
                 Registro [4]=rs.getString("cantidad ");
                 Registro [5]=rs.getString("precio_venta");
                 Registro [6]=rs.getString("estado");
                 
                 totalregistro=totalregistro+1;
                 totalconsumo=totalconsumo +(rs.getDouble("cantidad") * rs.getDouble("precio_venta"));
                 modelo.addRow(Registro );
             }   
             return  modelo;
         } catch (Exception e) {
             JOptionPane.showConfirmDialog(null, e);
         }
        return null;
    
     }
    
     public boolean insertar (vconsumo dts){
       sSQL="insert into consumo (idreserva,idproducto,cantidad,precio_venta,estado)" +
          "values (?,?,?,?,?)" ;    
         try {
           
             PreparedStatement pst=cn.prepareStatement(sSQL); 
             pst.setInt( 1, dts.getIdreserva());
             pst.setInt( 2, dts.getIdproducto());
             pst.setDouble( 3, dts.getCantidad());
             pst.setDouble( 4, dts.getPrecio_venta());
             pst.setString( 5, dts.getEstado() );

              
             int n=pst.executeUpdate();
             if (n!=0){
                 return true;
             } 
             else{
                 return false;
             }
             
         } catch (Exception e) {
             JOptionPane.showConfirmDialog(null, e);
         }
        return false;
  
     }
     
      public boolean editar (vconsumo dts){
         sSQL=" update consumo set idreserva=?,idproducto=?,cantidad=?precio_venta=?,estado=?"+
          " where idconsumo=?";           
                 
          try {
              PreparedStatement pst=cn.prepareStatement(sSQL); 
             pst.setInt( 1, dts.getIdreserva());
             pst.setInt( 2, dts.getIdproducto());
             pst.setDouble( 3, dts.getCantidad());
             pst.setDouble( 4, dts.getPrecio_venta());
             pst.setString( 5, dts.getEstado() );
             
             pst.setInt( 6, dts.getIdconsumo());
              
             int n=pst.executeUpdate();
             if (n!=0){
                 return true;
               } 
             else{
                 return false;
             }
         } catch (Exception e) {
             JOptionPane.showConfirmDialog(null, e);
            return false;
         }
    
         
  
     }
    
       public boolean eliminar (vconsumo dts){
        sSQL="delete from consumo where idconsumo=?";
           try {
             PreparedStatement pst=cn.prepareStatement(sSQL); 
             
             pst.setInt( 1, dts.getIdconsumo() );
              
             int n=pst.executeUpdate();
             if (n!=0){
                 return true;
             } 
         } catch (Exception e) {
             JOptionPane.showConfirmDialog(null, e);
         }
        return false;
  
     }
    
}
