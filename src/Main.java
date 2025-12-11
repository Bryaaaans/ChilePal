import controller.ChilePalController;
import view.VistaPrincipal;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            // 1) Cargar datos
            ChilePalController controller =
                    ChilePalController.cargarDesdeArchivo("chilepal.dat");
            // 2) Crear la vista principal
            VistaPrincipal vista = new VistaPrincipal(controller);
            vista.setVisible(true);
            // 3) Guardar
            vista.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    controller.guardarEnArchivo("chilepal.dat");
                    System.exit(0);
                }
            });
        });
    }
}
