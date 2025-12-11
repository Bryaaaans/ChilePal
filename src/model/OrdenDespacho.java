package model;
import java.io.Serializable;

public class OrdenDespacho implements Serializable {
    private static final long serialVersionUID = 1L;

        private int id;
        private Pedido pedido;
        private String estado;

        public OrdenDespacho(int id, Pedido pedido) {
            this.id = id;
            this.pedido = pedido;
            this.estado = "Emitida";
        }

        public int getId() {
            return id;
        }

        public Pedido getPedido() {
            return pedido;
        }

        public String getEstado() {
            return estado;
        }

        public void marcarEntregada() {
            this.estado = "Entregada";
        }

        @Override
        public String toString() {
            return "OrdenDespacho{id=" + id +
                    ", pedido=" + pedido.getId() +
                    ", estado='" + estado + "'}";
        }
    }
