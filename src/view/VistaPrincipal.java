package view;

import controller.ChilePalController;

import javax.swing.*;
import java.awt.*;

public class VistaPrincipal extends JFrame {

    private ChilePalController controller;

    public VistaPrincipal(ChilePalController controller) {
        this.controller = controller;
        initUI();
    }

    private void initUI() {
        setTitle("Sistema ChilePal");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(480, 380);
        setLocationRelativeTo(null);

        JPanel panelFondo = new JPanel();
        panelFondo.setBackground(new Color(230, 242, 255));
        panelFondo.setLayout(new GridBagLayout());
        add(panelFondo);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10,10,10,10);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblTitulo = new JLabel("Menú Principal");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        gbc.gridy = 0;
        panelFondo.add(lblTitulo, gbc);

        JPanel panelBotones = new JPanel(new GridLayout(4,1,10,10));
        panelBotones.setOpaque(false);

        JButton btnClientes = new JButton("Gestión Clientes");
        JButton btnProductos = new JButton("Gestión Inventario");
        JButton btnPedidos = new JButton("Pedidos / Despacho / Pagos");
        JButton btnSalir = new JButton("Salir");

        panelBotones.add(btnClientes);
        panelBotones.add(btnProductos);
        panelBotones.add(btnPedidos);
        panelBotones.add(btnSalir);

        gbc.gridy = 1;
        panelFondo.add(panelBotones, gbc);

        // Acciones
        btnClientes.addActionListener(e -> abrirVentanaClientes());
        btnProductos.addActionListener(e -> abrirVentanaProductos());
        btnPedidos.addActionListener(e -> abrirVentanaPedidos());
        btnSalir.addActionListener(e -> salirGuardando());
    }

    private void abrirVentanaClientes() {
        JFrame frame = new JFrame("Gestión de Clientes");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(new PanelClientes(controller));
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }

    private void abrirVentanaProductos() {
        JFrame frame = new JFrame("Gestión de Inventario");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(new PanelProductos(controller));
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }

    private void abrirVentanaPedidos() {
        JFrame frame = new JFrame("Pedidos / Despacho / Pagos");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(new PanelPedidos(controller));
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }

    private void salirGuardando() {
        int op = JOptionPane.showConfirmDialog(
                this,
                "¿Desea guardar los datos antes de salir?",
                "Salir",
                JOptionPane.YES_NO_CANCEL_OPTION
        );

        if (op == JOptionPane.CANCEL_OPTION) return;

        if (op == JOptionPane.YES_OPTION) {
            controller.guardarEnArchivo("chilepal.dat");
        }
        System.exit(0);
    }
}
