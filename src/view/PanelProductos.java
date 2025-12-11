package view;

import controller.ChilePalController;
import model.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelProductos extends JPanel {

    private ChilePalController controller;
    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private JTextField txtNombre, txtPrecio, txtStock;

    public PanelProductos(ChilePalController controller) {
        this.controller = controller;
        initUI();
        cargarProductos();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel titulo = new JLabel("Gestión de Inventario", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Precio Neto", "Stock"}, 0
        );
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelForm = new JPanel(new GridLayout(4, 2, 5, 5));

        panelForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Precio neto:"));
        txtPrecio = new JTextField();
        panelForm.add(txtPrecio);

        panelForm.add(new JLabel("Stock:"));
        txtStock = new JTextField();
        panelForm.add(txtStock);

        JButton btnAgregar = new JButton("Registrar Producto");
        btnAgregar.addActionListener(e -> registrarProducto());

        panelForm.add(new JLabel());
        panelForm.add(btnAgregar);

        add(panelForm, BorderLayout.SOUTH);
    }

    private void cargarProductos() {
        modeloTabla.setRowCount(0);
        List<Producto> lista = controller.obtenerProductos();
        for (Producto p : lista) {
            modeloTabla.addRow(new Object[]{
                    p.getId(), p.getNombre(), p.getPrecioNeto(), p.getStock()
            });
        }
    }

    private void registrarProducto() {
        String nombre = txtNombre.getText().trim();
        String precioStr = txtPrecio.getText().trim();
        String stockStr = txtStock.getText().trim();

        if (nombre.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        try {
            int precio = Integer.parseInt(precioStr);
            int stock = Integer.parseInt(stockStr);
            if (precio <= 0 || stock < 0) {
                JOptionPane.showMessageDialog(this, "Precio/stock inválidos.");
                return;
            }
            controller.registrarProducto(nombre, precio, stock);
            cargarProductos();
            txtNombre.setText("");
            txtPrecio.setText("");
            txtStock.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precio y stock deben ser enteros.");
        }
    }
}
