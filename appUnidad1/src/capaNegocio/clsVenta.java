/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capaNegocio;

import capaDatos.clsJDBC;
import java.sql.ResultSet;

/**
 *
 * @author laboratorio_computo
 */
public class clsVenta {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    
     public void registrarVenta(Integer numventa, String fecha, float total, float subtotal,float igv, boolean tc, boolean estadop, Integer codCliente) throws Exception{
        strSQL="insert into venta values("+numventa+",'"+fecha+"',"+total+",'"+subtotal+"','"+igv+"',"+tc+","+estadop+","+codCliente+")";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception ("Error al registrar");
        }
    }
   
    public ResultSet buscarCliente(String num) throws Exception{
        if(num.length()==8){
            strSQL="Select cliente.codcliente,cliente.nombres,cliente.direccion,cliente.codtipo,venta.tipocomprobante  from cliente inner join venta where cliente.dni='"+num+"'";
            try {
                rs=objConectar.consultarBD(strSQL);
                return rs;
            } catch (Exception e) {
                throw new Exception(e.getMessage() + ": Error al Buscar") ;
            }
        }else {
            if(num.length()==11){
            strSQL="Select cliente.codcliente,cliente.nombres,cliente.direccion,cliente.codtipo,venta.tipocomprobante  from cliente inner join venta where cliente.ruc='"+num+"'";
            try {
                rs=objConectar.consultarBD(strSQL);
                return rs;
            } catch (Exception e) {
                throw new Exception(e.getMessage() + ": Error al Buscar") ;
                }
            }else {
                throw new Exception( "Rango invalido") ;
                 
            }
        }
       
       
    }

}
