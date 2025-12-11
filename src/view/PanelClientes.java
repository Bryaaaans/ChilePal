package view;

import controller.ChilePalController;
import model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelClientes extends JPanel {

    private ChilePalController controller;
    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private JTextField txtNombre, txtRut, txtTelefono, txtDireccion;

    public PanelClientes(ChilePalController controller) {
        this.controller = controller;
        initUI();
        cargarClientes();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel titulo = new JLabel("Gestión de Clientes", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "RUT", "Teléfono", "Dirección"}, 0
        );
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 5, 5));

        panelForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("RUT:"));
        txtRut = new JTextField();
        panelForm.add(txtRut);

        panelForm.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelForm.add(txtTelefono);

        panelForm.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        panelForm.add(txtDireccion);

        JButton btnAgregar = new JButton("Registrar Cliente");
        btnAgregar.addActionListener(e -> registrarCliente());

        panelForm.add(new JLabel());
        panelForm.add(btnAgregar);

        add(panelForm, BorderLayout.SOUTH);
    }

    private void cargarClientes() {
        modeloTabla.setRowCount(0);
        List<Cliente> lista = controller.obtenerClientes();
        for (Cliente c : lista) {
            modeloTabla.addRow(new Object[]{
                    c.getId(), c.getNombre(), c.getRut(), c.getTelefono(), c.getDireccion()
            });
        }
    }

    private void registrarCliente() {
        String nombre = txtNombre.getText().trim();
        String rut = txtRut.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String direccion = txtDireccion.getText().trim();

        if (nombre.isEmpty() || rut.isEmpty() ||
                telefono.isEmpty() || direccion.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        // Crear cliente
        Cliente nuevo = controller.registrarCliente(nombre, rut, telefono, direccion);
        cargarClientes();

        txtNombre.setText("");
        txtRut.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");

        // para crear pedido de inmediato
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "Cliente registrado.\n¿Desea crear un pedido ahora para este cliente?",
                "Crear Pedido",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            JFrame ventana = new JFrame("Pedidos / Despacho / Pagos");
            ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            ventana.setContentPane(new PanelPedidos(controller, nuevo));
            ventana.pack();
            ventana.setLocationRelativeTo(this);
            ventana.setVisible(true);
        }
    }
}
