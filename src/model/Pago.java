package model;
import java.io.Serializable;

    public class Pago  implements Serializable {
        private static final long serialVersionUID = 1L;
        private int id;
        private Pedido pedido;
        private int monto;
        private boolean pagado;

        public Pago(int id, Pedido pedido, int monto) {
            this.id = id;
            this.pedido = pedido;
            this.monto = monto;
            this.pagado = false;
        }

        public int getId() {
            return id;
        }

        public Pedido getPedido() {
            return pedido;
        }

        public int getMonto() {
            return monto;
        }

        public boolean isPagado() {
            return pagado;
        }

        public void marcarComoPagado() {
            this.pagado = true;
        }

        @Override
        public String toString() {
            return "Pago{id=" + id +
                    ", pedido=" + pedido.getId() +
                    ", monto=" + monto +
                    ", pagado=" + pagado + "}";
        }
    }
