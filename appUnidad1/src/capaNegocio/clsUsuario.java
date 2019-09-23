package capaNegocio;

import capaDatos.clsJDBC;
import java.sql.*;

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
    
    public void ingresarMovimiento (int coduser) throws Exception{
         int indice;
         strSQL = "insert into movimiento values (DEFAULT,"+coduser +", CURRENT_DATE, true, CURRENT_TIME) ";
         //String str = "select numMovimiento from movimiento order by numMovimiento limit 1";
         try {
                rs = objConectar.consultarBD("select max(numMovimiento) from movimiento");
                while (rs.next()){
                    indice = rs.getInt("max");
                    objConectar.ejecutarBD("update movimiento set estado=false where numMovimiento="+indice);
                }  
            objConectar.ejecutarBD(strSQL);
         } catch (Exception e) {
             throw new Exception("Error al actualizar la tabla movimientos ");
         }
    }

    public Time obLastSesionTime (String us) throws Exception{
        int coduser = conocerCodusuario(us);
        strSQL = "select hora from movimiento where codusuario="+coduser+" order by numMovimiento desc limit 1";
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
        strSQL = "select fecha from movimiento where codusuario="+coduser+" order by numMovimiento desc limit 1";
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

    public int numeroIngresos (int coduser) throws Exception{
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

    public ResultSet buscarUsuario(Integer cod) throws Exception{
         strSQL = "select * from usuario where codusuario=" + cod ;
         try {
             rs=objConectar.consultarBD(strSQL);
             return rs;
         } catch (Exception e) {
             throw new Exception("Error al buscar el usuario");
         }
     }

    public void modificarVigenciaUsuario (int cod) throws Exception{
         strSQL = "update usuario set estado=false where codusuario="+cod;
         try {
             objConectar.ejecutarBD(strSQL);
         } catch (Exception e) {
             throw new Exception("Error al modificar la vigencia del usuario");
         }
     }

    public void modificarUsuario (int cod, String nom, String clv, String ncom,String carg, String prg, String rsp,Boolean vg) throws Exception{
         strSQL = "update usuario set nomusuario='"+nom+ "',clave='"+clv + "' , nombrecompleto=' "+ncom +"', cargo='"+carg 
                 +"' , estado="+vg+ ", pregunta='"+prg+"', respuesta='"+rsp+"' where codusuario="+cod;
         try {
             objConectar.ejecutarBD(strSQL);
         } catch (Exception e) {
             throw new Exception("Error al modificar la categoria");
         }
    }

    public ResultSet listarUsuario() throws Exception{
         strSQL = "select * from usuario";
         try {
             rs = objConectar.consultarBD(strSQL);
             return rs;
         } catch (Exception e) {
             throw new Exception("Error al listar usuario");
         }
    }

    public void borrarUsuario(Integer cod) throws Exception{
         strSQL = "delete from usuario where codusuario= "+cod;
         try {
             objConectar.ejecutarBD(strSQL);
         } catch (Exception e) {
             throw new Exception("Error al borrar usuario");
         }
    }
    
    public void registrarUsuario (int cod, String nom, String clv, String ncom,String carg, String prg, String rsp,Boolean vg) throws Exception{
         strSQL = "INSERT INTO usuario values (" +cod +", '"+ nom +"','"+ clv +"','"+ ncom +"','"+ carg +"',"+ vg+",'"+ prg+"','"+ rsp+"')";
         try {
             objConectar.ejecutarBD(strSQL);
         } catch (Exception e) {
             throw new Exception("Error al modificar la categoria");
         }
    }
    
    public Integer generarCodigoUsuario() throws Exception{
        strSQL="select coalesce (max(codusuario),0)+1 as codigo from usuario";
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
}
