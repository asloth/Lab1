package capaNegocio;
import capaDatos.clsJDBC;
import java.sql.*;

public class clsMarca {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    
    public Integer generarCodigoMarca() throws Exception{
        strSQL="select coalesce (max(codMarca),0)+1 as codigo from marca";
        try {
            rs = objConectar.consultarBD(strSQL);
            while (rs.next()) {                
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar el codigo de marca");
        }
        return 0;
    }
    
    public void registrar(int cod, String nom, Boolean vig) throws Exception{
        strSQL = "insert into marca values (" + cod+ ",'"+nom+"',"+ vig+")";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar la marca");
        }
    }
    
    public ResultSet buscarMarca(Integer cod) throws Exception{
        strSQL = "select * from marca where codmarca= "+cod;
        try {
            rs = objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar marca");
        }
    }
    
    public void borrarMarca(Integer cod) throws Exception{
        strSQL = "delete from marca where codmarca= "+cod;
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al borrar marca");
        }
    }
}