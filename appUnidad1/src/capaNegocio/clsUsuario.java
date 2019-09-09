/* 03 Set 2019 */
package capaNegocio;

import capaDatos.clsJDBC;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class clsUsuario {
    //Crear instancia de la clase clsJDBC
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    
    public String login(String usu, String con) throws Exception{
        strSQL = "select nombrecompleto from usuario where nomusuario='" + usu + "' and clave='" + con + "' and estado=true";
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getString("nombrecompleto");
            }
        } catch (Exception e) {
            throw new Exception("Error al iniciar sesión..");
        }
        return "";
    }
    
    public Boolean validarVigencia(String usu) throws Exception{
        strSQL = "select estado from usuario where nomusuario='" + usu + "'" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getBoolean("estado");
            }
        } catch (Exception e) {
            throw new Exception("Error al validar usuario..");
        }
        return false;
     }
    
    public String obtenerPreguntaSecreta(String usu) throws Exception{
        strSQL = "select pregunta from usuario where nomusuario='" + usu + "'";
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getString("pregunta");
            }
        } catch (Exception e) {
            throw new Exception("Error al consultar pregunta secreta..");
        }
        return "";
    }
    
    public String validarPreguntaSecreta(String usu, String rpta) throws Exception{
    strSQL = "select nombrecompleto from usuario where nomusuario='" + usu + "' and respuesta='" + rpta + "' and estado=true" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getString("nombrecompleto");
            }
        } catch (Exception e) {
            throw new Exception("Error al validar pregunta secreta..");
        }
        return "";
     }
    
    public void cambiarContraseña(String con, String nuevaCon, String nombre) throws Exception{
        strSQL="update usuario set clave='" + nuevaCon + "' where nombrecompleto='" + nombre + "' and clave='" + con + "'";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al cambiar la contraseña..");
        }
    }
    //Calendar fecha = new GregorianCalendar();
    //String fech = String.valueOf(fecha.get(Calendar.DAY_OF_MONTH)) +'/' +String.valueOf(fecha.get(Calendar.MONTH)+1)+'/'+String.valueOf(fecha.get(Calendar.YEAR));
    public int conocerCodusuario (String user) throws Exception{
        String data = "select codUsuario from usuario where nomusuario='"+user+"'";
        try {
            ResultSet resul = objConectar.consultarBD(data);
            while (resul.next()) {
                return resul.getInt("codUsuario");
            }
        } catch (Exception e) {
            throw new Exception("Error al obtener el codigo de usuario");
        }
        return -1;
    }
   public void ingresarMovimiento (String user) throws Exception{
        int coduser = conocerCodusuario(user);
        int indice=-1;
        if (coduser==-1) {
           throw new Exception("Este usuario no existe");
       }
        strSQL = "insert into movimiento values (DEFAULT,"+coduser +", CURRENT_DATE, true, CURRENT_TIME) ";
        String str = "select numMovimiento from movimiento order by numMovimiento limit 1";
        try {
            rs = objConectar.consultarBD(str);
           while (rs.next()){
               ResultSet index = objConectar.consultarBD("select max(numMovimiento) from movimiento");
               while (index.next()){
                   indice = index.getInt("max");
               }
               objConectar.ejecutarBD("update movimiento set estado=false where numMovimiento="+indice);
           }
           objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al actualizar la tabla movimientos ");
        }
   }
   
   public Time obLastSesionTime (String us) throws Exception{
       int coduser = conocerCodusuario(us);
       strSQL = "select hora from movimiento where codusuario="+coduser+" order by fecha,hora desc limit 1";
       try {
           rs = objConectar.consultarBD(strSQL);
           while (rs.next()) {               
               return rs.getTime("hora");
           }
       } catch (Exception e) {
           throw new Exception("Error al obtener la hora del último inicio de sesión");
       }
       return Time.valueOf("00:00:00"); 
   }
   
   public Date obLastSesionDate (String user) throws Exception {
       int coduser = conocerCodusuario(user);
       strSQL = "select fecha from movimiento where codusuario="+coduser+" order by fecha,hora desc limit 1";
       try {
           rs = objConectar.consultarBD(strSQL);
           while (rs.next()) {               
               return rs.getDate("fecha");
           }
       } catch (Exception e) {
           throw new Exception("Error al la fecha del último inicio de sesión");
       }
       return Date.valueOf("1900-01-01") ;
   }
   
   public int numeroIngresos (String user) throws Exception{
       int coduser = conocerCodusuario(user);
       strSQL = "select count(codusuario) from movimiento  where codusuario="+coduser+" group by codusuario";
       try {
           rs = objConectar.consultarBD(strSQL);
           while (rs.next()) {               
               return rs.getInt("count");
           }
       } catch (Exception e) {
           throw new Exception("Error al consultar el numero de ingresos");
       }
       return -1;
   }
}
