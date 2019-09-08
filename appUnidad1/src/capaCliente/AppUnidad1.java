package capaCliente;

import static java.awt.Frame.MAXIMIZED_BOTH;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.UIManager;

public class AppUnidad1 {

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        //Instancias un objeto del formulario principal
        jfPrincipal objPrincipal = new jfPrincipal();
        objPrincipal.setExtendedState(MAXIMIZED_BOTH);
        objPrincipal.setLocationRelativeTo(null);
        objPrincipal.setDefaultCloseOperation(EXIT_ON_CLOSE);
        objPrincipal.setVisible(true);
        
    }
    
}
