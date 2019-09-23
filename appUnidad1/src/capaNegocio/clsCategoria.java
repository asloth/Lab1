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
 * @author Sara
 */
public class clsCategoria {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    
    public Integer generarCodigoCategoria() throws Exception{
        strSQL="select coalesce (max(codcategoria),0)+1 as codigo from categoria";
        try {
            rs = objConectar.consultarBD(strSQL);
            while (rs.next()) {                
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar el codigo de categoria");
        }
        return 0;
    }
    
    public void registrar(int cod, String nom,String des ,Boolean vig) throws Exception{
        strSQL = "insert into categoria values (" + cod+ ",'"+nom+"', ' "+des+" ' ," +vig+")";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar la categoria");
        }
    }
    
    public ResultSet buscarCategoria(Integer cod) throws Exception{
        strSQL = "select * from categoria where codcategoria= "+cod;
        try {
            rs = objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar categoria");
        }
    }
    
    public void borrarCategoria(Integer cod) throws Exception{
        strSQL = "delete from categoria where codcategoria= "+cod;
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al borrar categoria");
        }
    }
    
    public ResultSet listarCategoria() throws Exception{
        strSQL = "select * from categoria";
        try {
            rs = objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar categoria");
        }
    }
    
    public void modificarCategoria (int cod, String nom, String des,Boolean vg) throws Exception{
        strSQL = "update categoria set nomcategoria='"+nom+ "' , descripcion=' "+des +"' , vigencia="+vg+" where codcategoria="+cod;
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al modificar la categoria");
        }
    }
    
    public void modificarVigenciaCategoria (int cod) throws Exception{
        strSQL = "update categoria set vigencia=false where codcategoria="+cod;
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al modificar la vigencia");
        }
    }
    
    public Integer obtenerCodigoCategoria(String nom) throws Exception{
        strSQL = "select codCategoria from categoria where nomcategoria='" + nom + "'" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            if (rs.next()) return rs.getInt("codCategoria");
        } catch (Exception e) {
            throw new Exception("Error al buscar categoria");
        }
        return 0;
    }
}
