package capaNegocio;
import capaDatos.clsJDBC;
import java.sql.ResultSet;

public class clsCliente {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;

    public ResultSet listarTipoClientes() throws Exception{
        strSQL = "select * from TIPO_CLIENTE" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar categoría");
        }
    }    
    
    public Integer obtenerCodigoTipoCliente(String nom) throws Exception{
        strSQL = "select codTipo from TIPO_CLIENTE where nombre='" + nom + "'" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            if (rs.next()) return rs.getInt("codTipo");
        } catch (Exception e) {
            throw new Exception("Error al buscar tipo cliente");
        }
        return 0;
    }
    
    public ResultSet listarClientes() throws Exception{
        strSQL = "select * from CLIENTE C inner join TIPO_CLIENTE T on C.codTipo=T.codTipo" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar categoría");
        }
    }  
    
    public Integer generarCodigoCliente() throws Exception{
        strSQL = "SELECT COALESCE(max(codCliente),0)+1 as codigo from CLIENTE" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar código de cliente");
        }
        return 0;
    }
    
    public void registrar(Integer cod, Integer codTipo, String dni, String ruc, String nom, String tel, String cor, String dir, Boolean vig) throws Exception{
        if (codTipo==2){strSQL="insert into CLIENTE values(" + cod + ",'" + dni + "',null,'" + nom + "','" + tel + "','" + cor + "','" + dir + "'," + vig + "," + codTipo + ")";}
        if (codTipo==3){strSQL="insert into CLIENTE values(" + cod + ",null,'" + ruc + "','" + nom + "','" + tel + "','" + cor + "','" + dir + "'," + vig + "," + codTipo + ")";}
        if (codTipo==1){strSQL="insert into CLIENTE values(" + cod + ",'" + dni + "','" + ruc + "','" + nom + "','" + tel + "','" + cor + "','" + dir + "'," + vig + "," + codTipo + ")";}
       // strSQL="insert into CLIENTE values(" + cod + ",'" + dni + "','" + ruc + "','" + nom + "','" + tel + "','" + cor + "','" + dir + "'," + vig + "," + codTipo + ")";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar cliente");
        }
    }
    
    public ResultSet buscarCliente(Integer cod) throws Exception{
        strSQL = "select * from CLIENTE C inner join TIPO_CLIENTE T on C.codTipo=T.codTipo where codCliente=" + cod ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar cliente");
        }
    }
    
    public void eliminarCliente(Integer cod) throws Exception {
        strSQL="delete from CLIENTE where codCliente=" + cod;
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al eliminar cliente");
        }
    }
    
    public void darBajaCliente(Integer cod) throws Exception {
        strSQL="update CLIENTE set vigencia=false where codCliente=" + cod;
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al eliminar cliente");
        }
    }
    //esta funcion la creo la profesora no sé para qué
    public ResultSet buscarClienteDniRuc(String cod, Boolean tipo) throws Exception{
        if (tipo){
            strSQL = "select * from CLIENTE C inner join TIPO_CLIENTE T on C.codTipo=T.codTipo where dni='" + cod + "'";
        }else{
            strSQL = "select * from CLIENTE C inner join TIPO_CLIENTE T on C.codTipo=T.codTipo where ruc='" + cod + "'";
        }
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar cliente");
        }
    }
      
    public void modificarCliente (Integer cod, String dni,String ruc,String nombres,String telefono,String correo,String direccion, Boolean vig, Integer codTipo) throws Exception{
      strSQL="update cliente set " +
              " nombres='" + nombres + 
              "', telefono='" + telefono + 
              "', correo='" + correo + 
              "', direccion='" + direccion+
              "', vigencia=" + vig+ 
              ", codTipo=" + codTipo;
      if (codTipo == 1){
          strSQL= strSQL+ " , dni='" + dni + "', ruc='" + ruc + "' where codcliente="+cod;
      }
      if (codTipo==2){
          strSQL= strSQL+ " , dni='" + dni + "' where codcliente="+cod;
      }
      if (codTipo==3){
          strSQL= strSQL+ " , ruc='" + ruc + "' where codcliente="+cod;
      }
      try {
          objConectar.ejecutarBD(strSQL);
      } catch (Exception e) {
          throw new Exception ("Error al modificar un producto!");
      }
    }
}