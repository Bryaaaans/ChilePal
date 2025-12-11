package view;

import controller.ChilePalController;
import model.Cliente;
import model.OrdenDespacho;
import model.Pago;
import model.Pedido;
import model.Producto;
import model.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelPedidos extends JPanel {

    private ChilePalController controller;

    private JComboBox<Cliente> comboClientes;
    private JComboBox<Producto> comboProductos;
    private JTextField txtCantidad;

    private DefaultTableModel modeloPedidos, modeloOrdenes, modeloPagos;
    private JTable tablaPedidos, tablaOrdenes, tablaPagos;

    // --------- CONSTRUCTORES ---------

    public PanelPedidos(ChilePalController controller) {
        this.controller = controller;
        initUI();
        cargarCombos();
        refrescarTablas();
    }

    public PanelPedidos(ChilePalController controller, Cliente clientePreseleccionado) {
        this(controller);
        seleccionarClienteEnCombo(clientePreseleccionado);
    }

    // --------- INICIALIZACIÓN UI ---------

    private void initUI() {
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel titulo = new JLabel("Pedidos / Despacho / Pagos", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        // ---crear pedido ----
        JPanel panelCrear = new JPanel(new GridLayout(2, 4, 5, 5));

        comboClientes = new JComboBox<>();
        comboProductos = new JComboBox<>();
        txtCantidad = new JTextField();

        panelCrear.add(new JLabel("Cliente:"));
        panelCrear.add(comboClientes);
        panelCrear.add(new JLabel("Producto:"));
        panelCrear.add(comboProductos);
        panelCrear.add(new JLabel("Cantidad:"));
        panelCrear.add(txtCantidad);

        JButton btnCrear = new JButton("Crear Pedido");
        btnCrear.addActionListener(e -> crearPedido());

        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> refrescarTodo());

        panelCrear.add(btnCrear);
        panelCrear.add(btnRefrescar);

        add(panelCrear, BorderLayout.NORTH);

        // ---pestañas---
        JTabbedPane tabs = new JTabbedPane();

        modeloPedidos = new DefaultTableModel(
                new Object[]{"ID", "Cliente", "Producto", "Cantidad", "Total", "Estado"}, 0
        );
        tablaPedidos = new JTable(modeloPedidos);
        tabs.addTab("Pedidos", new JScrollPane(tablaPedidos));

        modeloOrdenes = new DefaultTableModel(
                new Object[]{"ID Orden", "ID Pedido", "Cliente", "Estado Pedido"}, 0
        );
        tablaOrdenes = new JTable(modeloOrdenes);
        tabs.addTab("Órdenes", new JScrollPane(tablaOrdenes));

        modeloPagos = new DefaultTableModel(
                new Object[]{"Pago"}, 0
        );
        tablaPagos = new JTable(modeloPagos);
        tabs.addTab("Pagos", new JScrollPane(tablaPagos));

        add(tabs, BorderLayout.CENTER);

        // ---Botones---
        JPanel panelAcciones = new JPanel(new GridLayout(1, 3, 10, 10));

        JButton btnOrden = new JButton("Generar Orden");
        btnOrden.addActionListener(e -> generarOrdenDesdeTabla());

        JButton btnEntregar = new JButton("Entregar Pedido");
        btnEntregar.addActionListener(e -> entregarDesdeTabla());

        JButton btnPagar = new JButton("Registrar Pago");
        btnPagar.addActionListener(e -> pagarDesdeTabla());

        panelAcciones.add(btnOrden);
        panelAcciones.add(btnEntregar);
        panelAcciones.add(btnPagar);

        add(panelAcciones, BorderLayout.SOUTH);
    }

    // --------- COMBOS Y TABLAS ---------

    private void cargarCombos() {
        comboClientes.removeAllItems();
        for (Cliente c : controller.obtenerClientes()) {
            comboClientes.addItem(c);
        }

        comboProductos.removeAllItems();
        for (Producto p : controller.obtenerProductos()) {
            comboProductos.addItem(p);
        }
    }

    private void seleccionarClienteEnCombo(Cliente cliente) {
        if (cliente == null) return;
        for (int i = 0; i < comboClientes.getItemCount(); i++) {
            Cliente c = comboClientes.getItemAt(i);
            if (c.getId() == cliente.getId()) {
                comboClientes.setSelectedIndex(i);
                break;
            }
        }
    }

    private void refrescarTodo() {
        cargarCombos();
        refrescarTablas();
    }

    private void refrescarTablas() {
        // Pedidos
        modeloPedidos.setRowCount(0);
        for (Pedido p : controller.obtenerPedidos()) {
            modeloPedidos.addRow(new Object[]{
                    p.getId(),
                    p.getCliente().getNombre(),
                    p.getProducto().getNombre(),
                    p.getCantidad(),
                    p.getTotal(),
                    p.getEstado()
            });
        }

        // Órdenes
        modeloOrdenes.setRowCount(0);
        for (OrdenDespacho o : controller.obtenerOrdenesDespacho()) {
            Pedido p = o.getPedido();
            modeloOrdenes.addRow(new Object[]{
                    o.getId(),
                    p.getId(),
                    p.getCliente().getNombre(),
                    p.getEstado()
            });
        }

        // Pagos
        modeloPagos.setRowCount(0);
        for (Pago pago : controller.obtenerPagos()) {
            modeloPagos.addRow(new Object[]{ pago.toString() });
        }
    }

    // --------- CREAR PEDIDO ---------

    private void crearPedido() {
        Cliente c = (Cliente) comboClientes.getSelectedItem();
        Producto p = (Producto) comboProductos.getSelectedItem();

        if (c == null || p == null) {
            JOptionPane.showMessageDialog(this, "Debe existir al menos un cliente y un producto.");
            return;
        }

        try {
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.");
                return;
            }

            Pedido pedido = controller.registrarPedido(c, p, cantidad);
            if (pedido == null) {
                JOptionPane.showMessageDialog(this, "Stock insuficiente.");
            } else {
                JOptionPane.showMessageDialog(this, "Pedido creado: " + pedido);
            }
            txtCantidad.setText("");
            refrescarTablas();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida.");
        }
    }

    // --------- HELPERS SELECCIÓN ---------

    private Integer obtenerIdPedidoSeleccionado() {
        int fila = tablaPedidos.getSelectedRow();
        if (fila == -1) return null;
        return (Integer) modeloPedidos.getValueAt(fila, 0);
    }

    private Integer obtenerIdOrdenSeleccionada() {
        int fila = tablaOrdenes.getSelectedRow();
        if (fila == -1) return null;
        return (Integer) modeloOrdenes.getValueAt(fila, 0);
    }

    // --------- GENERAR ORDEN ---------

    private void generarOrdenDesdeTabla() {
        Integer idPedido = obtenerIdPedidoSeleccionado();
        if (idPedido == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido en la pestaña 'Pedidos'.");
            return;
        }

        OrdenDespacho orden = controller.generarOrdenDespacho(idPedido);
        if (orden == null) {
            JOptionPane.showMessageDialog(this, "No se pudo generar la orden. Revise el estado del pedido.");
        } else {
            JOptionPane.showMessageDialog(this, "Orden generada: " + orden);
        }
        refrescarTablas();
    }

    // --------- ENTREGAR PEDIDO ---------

    private void entregarDesdeTabla() {
        Integer idOrden = obtenerIdOrdenSeleccionada();

        if (idOrden == null) {
            Integer idPedido = obtenerIdPedidoSeleccionado();
            if (idPedido == null) {
                JOptionPane.showMessageDialog(this,
                        "Seleccione una orden en la pestaña 'Órdenes'\n" +
                                "o un pedido en la pestaña 'Pedidos'.");
                return;
            }

            // Buscar orden que corresponda
            OrdenDespacho encontrada = null;
            for (OrdenDespacho o : controller.obtenerOrdenesDespacho()) {
                if (o.getPedido().getId() == idPedido) {
                    encontrada = o;
                    break;
                }
            }

            if (encontrada == null) {
                JOptionPane.showMessageDialog(this,
                        "Ese pedido no tiene orden todavía.\nPrimero genere la orden.");
                return;
            }

            idOrden = encontrada.getId();
        }

        // Usar primer repartidor
        List<Repartidor> reps = controller.obtenerRepartidores();
        if (reps.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay repartidores registrados.");
            return;
        }

        int idRep = reps.get(0).getId();
        boolean ok = controller.registrarEntrega(idOrden, idRep);

        if (ok) {
            JOptionPane.showMessageDialog(this, "Pedido entregado correctamente.");
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo entregar el pedido.\nRevise el estado del pedido/orden.");
        }

        refrescarTablas();
    }

    // --------- PAGAR ---------

    private void pagarDesdeTabla() {
        Integer idPedido = obtenerIdPedidoSeleccionado();
        if (idPedido == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido en la pestaña 'Pedidos'.");
            return;
        }

        Pago pago = controller.registrarPago(idPedido);
        if (pago == null) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo registrar el pago.\nRevise el estado del pedido.");
        } else {
            JOptionPane.showMessageDialog(this, "Pago registrado: " + pago);
        }

        refrescarTablas();
    }
}
